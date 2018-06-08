package com.koalafield.cmart.ui.activity.goods;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dl7.recycler.helper.RecyclerViewHelper;
import com.flowlayouttag.FlowLayout;
import com.koalafield.cmart.R;
import com.koalafield.cmart.adapter.HotWordsAdapter;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.bean.HistoryContent;
import com.koalafield.cmart.db.DuplicatedTraceException;
import com.koalafield.cmart.db.HistoryService;
import com.koalafield.cmart.db.IHistoryService;
import com.koalafield.cmart.presenter.goods.HotSearchPresenter;
import com.koalafield.cmart.presenter.goods.IHotSearchPresenter;
import com.koalafield.cmart.ui.activity.search.SearchListActivity;
import com.koalafield.cmart.ui.activity.use.ChangeAddressActivity;
import com.koalafield.cmart.ui.view.goods.IHotWordsView;
import com.koalafield.cmart.utils.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * @author jiangrenming
 * @date 2018/5/23
 */

public class SearchActivity extends BaseActivity implements IHotWordsView<List<String>>,SearchView.OnQueryTextListener {

    @BindView(R.id.back)
    ImageView back;
   /* @BindView(R.id.search_words)
    EditText search_words;*/
    @BindView(R.id.search_words)
    SearchView search_words;
    @BindView(R.id.search_cancle)
    TextView search_cancle;
    //历史记录
    @BindView(R.id.ll_history)
    LinearLayout ll_history;
    @BindView(R.id.delete)
    ImageView delete;
    @BindView(R.id.his_flowLayout)
    FlowLayout his_flowLayout;
    //热搜词
    @BindView(R.id.hot_flowLayout)
    FlowLayout hot_flowLayout;


    private IHotSearchPresenter hotSearchPresenter;
    private IHistoryService mHistory;


    @Override
    public int attchLayoutRes() {
        return R.layout.activity_search;
    }

    @Override
    public void initDatas() {
        mHistory = new HistoryService(this);
        search_words.setIconifiedByDefault(true);
        search_words.setFocusable(true);
        search_words.setIconified(false);
        search_words.setQueryHint("搜索");
        search_words.setOnQueryTextListener(this);
        search_words.requestFocusFromTouch();
    }

    @Override
    public void upDateViews() {
        hotSearchPresenter = new HotSearchPresenter(this);
        hotSearchPresenter.getData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initHistoryTag();
    }

    private void initHistoryTag() {
        List<HistoryContent> allHistory =  mHistory.findAllHistory();
        if (allHistory != null && allHistory.size() >0){
            //list倒序排列
            Collections.reverse(allHistory);
            String[] strings = new String[allHistory.size()];
            for (int i = 0; i < allHistory.size(); i++) {
                strings[i] = allHistory.get(i).getContent();
            }
            ll_history.setVisibility(View.VISIBLE);
            his_flowLayout.setColorful(true);
            his_flowLayout.cleanTag();
            his_flowLayout.setData(strings);
            his_flowLayout.setOnTagClickListener(new FlowLayout.OnTagClickListener() {
                @Override
                public void TagClick(String text) {
                    Intent intent = new Intent(SearchActivity.this, SearchListActivity.class);
                    intent.putExtra("title",text);
                    startActivity(intent);
                }
            });
        }

    }

    @OnClick({R.id.delete,R.id.back,R.id.search_cancle})
    public void searchClick(View view){
        switch (view.getId()){
            case R.id.delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("确定要删除全部历史记录？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ll_history.setVisibility(View.GONE);
                        his_flowLayout.cleanTag();
                        mHistory.deletAllKeys();
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.create().show();
            break;
            case R.id.back:
            case R.id.search_cancle:
                finish();
            default:
                break;

        }
    }

    @Override
    public void onHotWordsSucessFul(List<String> data) {
        if (data.size() >0){
            hot_flowLayout.setColorful(false);
            String[] array = data.toArray(new String[data.size()]);
            hot_flowLayout.setData(array);
            hot_flowLayout.setOnTagClickListener(new FlowLayout.OnTagClickListener() {
                @Override
                public void TagClick(String text) {
                    //插入之前先查询，如果有相同的就不在插入进去
                    try {
                        HistoryContent content = mHistory.findContent(text);
                        if (content == null){
                            HistoryContent historyContent = new HistoryContent();
                            historyContent.setContent(text);
                            mHistory.addKeyWords(historyContent);
                        }
                        Intent intent = new Intent(SearchActivity.this, SearchListActivity.class);
                        intent.putExtra("title",text);
                        startActivity(intent);
                    } catch (DuplicatedTraceException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public void onHotWordsNoData() {

    }



    @Override
    public void onHotWordsFailure(String message,int code ) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        if (StringUtils.isEmpty(query)  || query.length() == 0) {
            Toast.makeText(SearchActivity.this,"输入的数据不难为空",Toast.LENGTH_SHORT).show();
        } else {
            //将数据插入到数据库里
            //插入之前先查询，如果有相同的就不在插入进去
            try {
                HistoryContent content = mHistory.findContent(query);
                if (content == null){
                    HistoryContent historyContent = new HistoryContent();
                    historyContent.setContent(query);
                    mHistory.addKeyWords(historyContent);
                }
                Intent intent = new Intent(SearchActivity.this, SearchListActivity.class);
                intent.putExtra("title",query);
                startActivity(intent);
            } catch (DuplicatedTraceException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
