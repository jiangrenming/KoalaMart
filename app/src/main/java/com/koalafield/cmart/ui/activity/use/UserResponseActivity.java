package com.koalafield.cmart.ui.activity.use;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dl7.recycler.helper.RecyclerViewHelper;
import com.dl7.recycler.listener.OnRecyclerViewItemClickListener;
import com.jrm.retrofitlibrary.retrofit.BaseResponseBean;
import com.koalafield.cmart.R;
import com.koalafield.cmart.adapter.AdviceAdapter;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.bean.user.AdvicesBean;
import com.koalafield.cmart.presenter.user.IUserAdavicePresenter;
import com.koalafield.cmart.presenter.user.UserAdavicePresenter;
import com.koalafield.cmart.ui.activity.LoginActivity;
import com.koalafield.cmart.ui.view.user.IUserAdaviceView;
import com.koalafield.cmart.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by jiangrenming on 2018/6/7.
 */

public class UserResponseActivity extends BaseActivity implements IUserAdaviceView<BaseResponseBean>{

    @BindView(R.id.submit)
    TextView submit;
    @BindView(R.id.content)
    EditText content_edt;
    @BindView(R.id.advices_type)
    RecyclerView advices_type;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.top_name)
    TextView top_name;

    private String content = null;
    private List<AdvicesBean> advicesBeen =  new ArrayList<>();

    @Override
    public int attchLayoutRes() {
        return R.layout.activity_userrespose;
    }

    @Override
    public void initDatas() {
        hideLoading();
        top_name.setText("意见反馈");
        AdvicesBean ads_one = new AdvicesBean();
        ads_one.setContent("购物体验");
        advicesBeen.add(ads_one);
        AdvicesBean ads_two = new AdvicesBean();
        ads_two.setContent("产品细节");
        advicesBeen.add(ads_two);
        AdvicesBean ads_three = new AdvicesBean();
        ads_three.setContent("物流相关");
        advicesBeen.add(ads_three);
        AdvicesBean ads_four = new AdvicesBean();
        ads_four.setContent("产品建议");
        advicesBeen.add(ads_four);
        AdvicesBean ads_five = new AdvicesBean();
        ads_five.setContent("App故障");
        advicesBeen.add(ads_five);
        AdvicesBean ads_six = new AdvicesBean();
        ads_six.setContent("其他");
        advicesBeen.add(ads_six);
        final AdviceAdapter adapter = new AdviceAdapter(this,advicesBeen);
        RecyclerViewHelper.initRecyclerViewG(this,advices_type,false,adapter,3);
        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                AdvicesBean advicesBean = advicesBeen.get(position);
                content = advicesBean.getContent();
                for (int i = 0; i < advicesBeen.size(); i++) {
                    if (advicesBean.getContent().equals(advicesBeen.get(i).getContent())){
                        advicesBeen.get(i).setSelect(true);
                    }else {
                        advicesBeen.get(i).setSelect(false);
                    }
                }
                adapter.updateItems(advicesBeen);
                content_edt.setText(content+":");
            }
        });
    }

    @Override
    public void upDateViews() {}

    @OnClick({R.id.back,R.id.submit})
    public  void adViceClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.submit:
                String string = content_edt.getText().toString();
                if (StringUtils.isEmpty(content)){
                    Toast.makeText(this,"请选择反馈类型",Toast.LENGTH_LONG).show();
                    return;
                }
                if (StringUtils.isEmpty(string)){
                    Toast.makeText(this,"请填写反馈的详情",Toast.LENGTH_LONG).show();
                    return;
                }
                Map<String,String> params = new HashMap<>();
                params.put("type",content);
                params.put("content",string);
                IUserAdavicePresenter adavicePresenter = new UserAdavicePresenter(this);
                adavicePresenter.setParams(params);
                adavicePresenter.getData();
                break;
            default:
                break;
        }

    }




    @Override
    public void onAdaviceSucessFul(BaseResponseBean data) {
        if (data != null &&data.getCode() == 200){
            Toast.makeText(this,"反馈成功",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onAdaviceFailure(String message, int code) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        if (code == 401){
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("type",3);
            startActivity(intent);
        }
    }
}
