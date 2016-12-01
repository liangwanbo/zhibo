package com.mytv365.zb.views.fragment.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fhrj.library.base.impl.BaseFragment;
import com.fhrj.library.config.SysEnv;
import com.fhrj.library.view.horizontalscrollview.ColumnHorizontalScrollView;
import com.fhrj.library.view.viewpager.MyViewPager;
import com.mytv365.zb.R;
import com.mytv365.zb.model.TabView;
import com.mytv365.zb.views.fragment.news.NewsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangguohao on 16/9/8.
 */
public class MainFragment03 extends BaseFragment {
    /*标题*/
    private TextView title;

    private Context mContext;
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
    /*新闻*/
    private NewsFragment newsFragment=new NewsFragment();

    @Override
    public int bindLayout() {
        return R.layout.main_fragment03;
    }

    @Override
    public void initParams(Bundle params) {

    }

    @Override
    public void initView(View view) {
        title=(TextView)findViewById(R.id.tv_title);
        title.setText("研究院");
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
        initViewTab();
        getData();
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
        fragmentManager = getChildFragmentManager();


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
                localTextView_xia.setWidth(localTextView.length() * 100);
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
        String []val={"早盘","盘中","收盘"};
        for (int i=0;i<val.length;i++){
            TabView classify = new TabView();
            classify = new TabView();
            classify.setId(i);
            classify.setTitle(val[i]);
            newsFragment=new NewsFragment();
            pagerList.add(newsFragment);

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

}
