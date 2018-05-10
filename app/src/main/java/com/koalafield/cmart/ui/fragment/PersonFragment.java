package com.koalafield.cmart.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.koalafield.cmart.R;
import com.koalafield.cmart.base.fragment.BaseFragment;
import com.koalafield.cmart.ui.activity.use.PrivateActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * @author jiangrenming
 * @date 2018/5/9
 * 个人中心界面
 */

public class PersonFragment extends BaseFragment{

    @BindView(R.id.share)
    ImageView share;  //分享
    @BindView(R.id.person_av)
    ImageView person_av;  //头像
    @BindView(R.id.user_name)
    TextView user_name;  //用户名
    @BindView(R.id.order_infos)
    LinearLayout order_infos;  //订单
    @BindView(R.id.no_pay)
    LinearLayout no_pay;  //待付款
    @BindView(R.id.pay_wait)
    LinearLayout pay_wait;  //待发货
    @BindView(R.id.wait_self)
    LinearLayout wait_self;  //待收货
    @BindView(R.id.old)
    ImageView old;  //买过
    @BindView(R.id.discount)
    ImageView discount;  //优惠券
    @BindView(R.id.collection)
    ImageView collection;  //收藏
    @BindView(R.id.adress)
    ImageView adress;  //地址
    @BindView(R.id.contact)
    ImageView contact;  //联系客服
    @BindView(R.id.service)
    ImageView service;  //服务
    @BindView(R.id.set)
    ImageView set;  //设置

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_person;
    }

    @Override
    protected void initViews() {}

    @Override
    protected void updateViews() {}

    @OnClick({R.id.share,R.id.person_av,R.id.order_infos,R.id.no_pay,R.id.pay_wait,R.id.wait_self,R.id.old,R.id.discount,
            R.id.collection,R.id.adress,R.id.contact,R.id.service,R.id.set})
    public  void onClick(View v){
        switch (v.getId()){
            case R.id.share: //进入朋友圈分享
                break;
            case R.id.person_av: //头像进入个人资料
                startActivity(new Intent(mContext, PrivateActivity.class));
                break;
            case R.id.order_infos:
                break;
            case R.id.no_pay:
                break;
            case  R.id.pay_wait:
                break;
            case R.id.wait_self:
                break;
            case  R.id.old:
                break;
            case R.id.discount:
                break;
            case   R.id.collection:
                break;
            case R.id.adress:
                break;
            case R.id.contact:
                break;
            case R.id.service:
                break;
            case R.id.set: //设置
                break;
            default:
                break;
        }
    }









}
