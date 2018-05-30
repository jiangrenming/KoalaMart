package com.koalafield.cmart.ui.activity.goods;

import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import com.koalafield.cmart.presenter.goods.HotSearchPresenter;
import com.koalafield.cmart.presenter.goods.IHotSearchPresenter;
import com.koalafield.cmart.ui.view.goods.IHotWordsView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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

    @Override
    public int attchLayoutRes() {
        return R.layout.activity_search;
    }

    @Override
    public void initDatas() {}

    @Override
    public void upDateViews() {
        hotSearchPresenter = new HotSearchPresenter(this);
        hotSearchPresenter.getData();
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
