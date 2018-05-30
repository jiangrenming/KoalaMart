package com.koalafield.cmart.ui.activity.goods;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.koalafield.cmart.ui.activity.use.ChangeAddressActivity;
import com.koalafield.cmart.ui.view.goods.IHotWordsView;
import com.koalafield.cmart.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * @author jiangrenming
 * @date 2018/5/23
 */

public class SearchActivity extends BaseActivity implements IHotWordsView<List<String>> {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.search_words)
    EditText search_words;
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
    }

    @Override
    public void upDateViews() {
        hotSearchPresenter = new HotSearchPresenter(this);
        hotSearchPresenter.getData();
        search_words.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView view, int action, KeyEvent event) {
                if (search_words.getVisibility() == View.VISIBLE
                        && (action == EditorInfo.IME_ACTION_DONE
                        || action == EditorInfo.IME_ACTION_SEND
                        || action == EditorInfo.IME_ACTION_NEXT
                        || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER))) {
                    String vaule = search_words.getText().toString().trim();
                    if (StringUtils.isEmpty(vaule)  || vaule.length() == 0) {
                        Toast.makeText(SearchActivity.this,"输入的数据不难为空",Toast.LENGTH_SHORT).show();
                    } else {
                        //将数据插入到数据库里
                        //插入之前先查询，如果有相同的就不在插入进去
                        HistoryContent historyContent = new HistoryContent();
                        historyContent.setContent(vaule);
                        try {
                            HistoryContent content = mHistory.findContent(vaule);
                            if (content == null){
                                mHistory.addKeyWords(historyContent);
                            }
                        } catch (DuplicatedTraceException e) {
                            e.printStackTrace();
                        }
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initHistoryTag();
    }

    private void initHistoryTag() {
        List<HistoryContent> allHistory =  mHistory.findAllHistory();
        if (allHistory != null && allHistory.size() >0){
            List<String> historyList = new ArrayList<>();
            for (int i = 0; i < allHistory.size(); i++) {
                historyList.add(allHistory.get(i).getContent());
            }
            ll_history.setVisibility(View.VISIBLE);
            his_flowLayout.setListData(historyList);
            his_flowLayout.setColorful(true);
            his_flowLayout.setOnTagClickListener(new FlowLayout.OnTagClickListener() {
                @Override
                public void TagClick(String text) {
                    Toast.makeText(SearchActivity.this,text,Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @OnClick(R.id.delete)
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
            default:
                break;

        }
    }

    @Override
    public void onHotWordsSucessFul(List<String> data) {
        if (data.size() >0){
            hot_flowLayout.setColorful(true);
            String[] array = data.toArray(new String[data.size()]);
            hot_flowLayout.setData(array);
            hot_flowLayout.setOnTagClickListener(new FlowLayout.OnTagClickListener() {
                @Override
                public void TagClick(String text) {
                    Toast.makeText(SearchActivity.this,text,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onHotWordsNoData() {

    }



    @Override
    public void onHotWordsFailure(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
