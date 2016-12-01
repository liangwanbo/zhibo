package com.mytv365.zb.views.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fhrj.library.base.impl.BaseActivity;
import com.fhrj.library.config.SysEnv;
import com.fhrj.library.view.horizontalscrollview.ColumnHorizontalScrollView;
import com.fhrj.library.view.viewpager.MyViewPager;
import com.mytv365.zb.R;
import com.mytv365.zb.common.Constant;
import com.mytv365.zb.model.TabView;
import com.mytv365.zb.utils.GlideUtils;
import com.mytv365.zb.views.fragment.home.DynamicFragment;
import com.mytv365.zb.views.fragment.home.QAFragment;
import com.mytv365.zb.views.fragment.home.ReviewFragment;
import com.mytv365.zb.views.fragment.home.TradingFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 老师主页
 * Created by zhangguohao on 16/8/8.
 */
public class HomeActivity extends BaseActivity {
    private Context mContext;
    private LinearLayout top;
    /*老师名称*/
    private TextView name;
    /*老师职位*/
    private TextView position;
    /*老师简介*/
    private TextView description;
    /*头像*/
    private ImageView ioc;
    /*返回*/
    private ImageView back;


    /*自定义HorizontalScrollView*/
    private ColumnHorizontalScrollView mColumnHorizontalScrollView;
    private RelativeLayout rl_column;
    private LinearLayout mRadioGroup_content;
    // private ViewPager mViewPager;
    /*左阴影部分*/
    public ImageView shade_left;
    /*右阴影部分*/
    public ImageView shade_right;
    /*新闻分类列表*/
    private ArrayList<TabView> newsClassify = new ArrayList<TabView>();
    /*当前选中的栏*/
    private int columnSelectIndex = 0;
    /*Item宽度*/
    private int mItemWidth = 0;
    /*屏幕宽度 */
    private int mScreenWidth = 0;
    private LinearLayout tab;
    /*滑动*/
    private MyViewPager viewPager;
    private FragmentManager fragmentManager;
    /*页面集合*/
    private List<Fragment> pagerList = new ArrayList<Fragment>();
    /*动态*/
    private DynamicFragment dynamicFragment = new DynamicFragment();
    /*交易*/
    private TradingFragment tradingFragment = new TradingFragment();
    /*问答*/
    private QAFragment qaFragment = new QAFragment();
    /*回顾*/
    private ReviewFragment reviewFragment = new ReviewFragment();

    /* 老师ID*/
    private String teacherId;

    @Override
    public int bindLayout() {
        return R.layout.activity_teacher_main;
    }

    @Override
    public void initParms(Bundle parms) {
        teacherId = parms.getString("teacherId");
    }

    @Override
    public void initView(View view) {
        setTintManager(R.color.touming);
        initTitle(Constant.getUser().getRealName());
    }

    /***
     * 初始化标题
     *
     * @param title
     */
    private void initTitle(String title) {
        initBackTitleBar(title, Gravity.CENTER);
        showTitleBar();
    }

    @Override
    public void doBusiness(Context mContext) {
        this.mContext = mContext;
        initView();
    }

    /****
     * 初始化控件
     */
    private void initView() {
        initInformatio();
        initViewTab();
        getData();
        initOnClick();
    }

    /***
     * 老师信息
     */
    private void initInformatio() {
        top = (LinearLayout) findViewById(R.id.top);
        name = (TextView) findViewById(R.id.name);
        back = (ImageView) findViewById(R.id.back);
        position = (TextView) findViewById(R.id.position);
        description = (TextView) findViewById(R.id.description);
        ioc = (ImageView) findViewById(R.id.ioc);
        GlideUtils.loadImage(this, Constant.getUser().getHeadImages(),ioc);
    }


    /**
     * 初始化TAB相关控件
     */
    private void initViewTab() {
        tab = (LinearLayout) findViewById(R.id.tab);
//        tab.setVisibility(View.GONE);
        mScreenWidth = SysEnv.SCREEN_WIDTH;
        mColumnHorizontalScrollView = (ColumnHorizontalScrollView)
                findViewById(R.id.mColumnHorizontalScrollView);
        mRadioGroup_content = (LinearLayout)
                findViewById(R.id.mRadioGroup_content);
        shade_left = (ImageView) findViewById(R.id.shade_left);
        shade_right = (ImageView) findViewById(R.id.shade_right);
        viewPager = (MyViewPager) findViewById(R.id.viewpager);
        fragmentManager = getSupportFragmentManager();


    }


    /***
     * 绑定Fragment页面
     *
     * @author 张国浩
     * @version 1.0
     * @TODO QQ:5069506
     * @date 2016年2月25日 下午7:04:43
     */
    private class BindingFragment extends PagerAdapter {

        @Override
        public int getCount() {
            return pagerList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = pagerList.get(position);
            try {
                if (!fragment.isAdded()) {
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.add(fragment, fragment.getClass().getName());
                    ft.commit();
                    fragmentManager.executePendingTransactions();
                }
                if (fragment.getView().getParent() == null) {
                    container.addView(fragment.getView());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return fragment.getView();
        }
    }

    /**
     * 主页滑动事件
     *
     * @author 张国浩
     * @version 1.0
     * @TODO QQ:5069506
     * @date 2016年2月25日 下午6:41:42
     */
    private class MyOnPageChange implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int position) {
            try {
                selectTab(position);
                switch (position) {
                    case 0:
                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 选择的Column里面的Tab
     */
    private void selectTab(int tab_postion) {
        columnSelectIndex = tab_postion;
        for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
            View checkView = mRadioGroup_content.getChildAt(tab_postion);
            int k = checkView.getMeasuredWidth();
            int l = checkView.getLeft();
            int i2 = l + k / 2 - mScreenWidth / 2;
            mColumnHorizontalScrollView.smoothScrollTo(i2, 0);
        }
        // 判断是否选中
        for (int j = 0; j < mRadioGroup_content.getChildCount(); j++) {
            View checkView = mRadioGroup_content.getChildAt(j);
            TextView localTextView_xia = (TextView) checkView
                    .findViewById(R.id.name_xia);
            TextView localTextView = (TextView) checkView
                    .findViewById(R.id.title_name);
//			((FragmentNews)(pagerList.get(j))).getData();
            boolean ischeck;
            if (j == tab_postion) {
                ischeck = true;
                localTextView_xia.setVisibility(View.VISIBLE);

                localTextView_xia.setWidth(localTextView.length() * 30);
                localTextView.setTextColor(ContextCompat.getColor(mContext,
                        R.color.theme_select));

            } else {
                localTextView_xia.setVisibility(View.INVISIBLE);
                localTextView.setTextColor(getResources().getColor(
                        R.color.theme_default));
                ischeck = false;
            }
            checkView.setSelected(ischeck);
        }
    }

    /**
     * 初始化Column栏目项
     */
    private void initTabColumn() {
        mRadioGroup_content.removeAllViews();
        int count = newsClassify.size();

        mColumnHorizontalScrollView.setParam((Activity) mContext, mScreenWidth,
                mRadioGroup_content, shade_left, shade_right, rl_column);
        for (int i = 0; i < count; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    mItemWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
            // params.leftMargin = 10;
            // params.rightMargin = 10;

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view1 = inflater.inflate(R.layout.public_title, null);
            view1.setLayoutParams(lp);
            TextView localTextView = (TextView) view1
                    .findViewById(R.id.title_name);
            localTextView.setPadding(5, 0, 5, 0);
            localTextView.setText(newsClassify.get(i).getTitle());
            if (columnSelectIndex == i) {
                view1.setSelected(true);
            }
            view1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
                        View localView = mRadioGroup_content.getChildAt(i);
                        if (localView != v) {
                            localView.setSelected(false);

                        } else {
                            localView.setSelected(true);
                            viewPager.setCurrentItem(i);
                        }
                    }
                }
            });
            mRadioGroup_content.addView(view1, i, params);
        }

    }


    /***
     * 老师相关数据
     */
    private void getData() {


       // pagerList.add(qaFragment);
        pagerList.add(reviewFragment);
//        pagerList.add(dynamicFragment);
//        pagerList.add(tradingFragment);
//        pagerList.add(qaFragment);
//        pagerList.add(reviewFragment);
//        String []val={"动态","交易","问答","精彩回顾"};
        String[] val = {"精彩回顾"};
        for (int i = 0; i < val.length; i++) {
            TabView classify = new TabView();
            classify = new TabView();
            classify.setId(i);
            classify.setTitle(val[i]);
            newsClassify.add(classify);
        }
        mItemWidth = mScreenWidth / val.length;// 一个Item宽度为屏幕的1/7
        initTabColumn();
        viewPager.setCanSlideable(true);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(new BindingFragment());
        viewPager
                .addOnPageChangeListener(new MyOnPageChange());

        selectTab(0);
    }

    /***
     * 点击事件
     */
    public void initOnClick() {
        back.setOnClickListener(new MyOnClick(1));
    }

    /***
     * 点击事件
     */
    private class MyOnClick implements View.OnClickListener {
        private int item;

        public MyOnClick(int item) {
            this.item = item;
        }

        @Override
        public void onClick(View v) {
            switch (item) {
                case 1:
                    finish();
                    break;
            }
        }
    }


    ;
}

