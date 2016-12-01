package com.fhrj.library.base.impl;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.fhrj.library.MApplication;
import com.fhrj.library.base.IBaseActivity;
import com.fhrj.library.base.IBaseConstant;
import com.fhrj.library.base.Operation;
import com.fhrj.library.base.SystemBarTintManager;
import com.fhrj.library.tools.ToolFile;
import com.fhrj.library.view.SwipeBackLayout;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/***
 * android 系统中的四大组件之一Activity基类
 * @author ZhangGuoHao
 * @date 2016年4月10日 下午1:21:37
 */

public abstract class BaseActivity extends FragmentActivity implements IBaseActivity,IBaseConstant {
  /*腾讯直播互通相关*/
  private BroadcastReceiver recv;
  /*** 整个应用Applicaiton **/
  private MApplication mApplication = null;
  /** 当前Activity的弱引用，防止内存泄露 **/
  private WeakReference<Activity> mContextWR = null;
  /** 当前Activity渲染的视图View **/
  private ViewGroup mContextView = null;
  /** 动画类型 **/
  private int mAnimationType = NONE;
  /** 是否运行截屏 **/
  private boolean isCanScreenshot = true;
  /** 右滑关闭当前Activity顶层容器 **/
  protected SwipeBackLayout rootView;
  /** 共通操作 **/
  protected Operation mOperation = null;
  /** 日志输出标志 **/
  protected final String TAG = this.getClass().getSimpleName();
  /**状态栏*/
  private SystemBarTintManager tintManager ;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(TAG, "BaseActivity-->onCreate()");

    // 获取应用Application
    mApplication = (MApplication) getApplicationContext();
    
    //需要在setContentView之前配置window的一些属性
    config(savedInstanceState);
    
    // 设置渲染视图View
    int baseLayout = BaseView.gainResId(mApplication, BaseView.LAYOUT, "activity_base_container");
    mContextView = (ViewGroup) LayoutInflater.from(this).inflate(baseLayout, null);
    setContentView(mContextView);

    // 将当前Activity压入栈
    mContextWR = new WeakReference<Activity>(this);
    mApplication.pushTask(mContextWR);

    // 实例化共通操作
    mOperation = new Operation(this);

    // 初始化参数
    Bundle bundle = getIntent().getExtras();
    if (bundle != null) {
      mAnimationType = bundle.getInt(ANIMATION_TYPE, NONE);
    } else {
      bundle = new Bundle();
    }
    initParms(bundle);

    
    View mView = bindView();
    if (null == mView) {
      mView = LayoutInflater.from(this).inflate(bindLayout(), null);
    }
    ViewGroup mContent =
        (ViewGroup) findViewById(BaseView.gainResId(mApplication, BaseView.ID, "fl_content"));
    mContent.addView(mView);

    // 初始化控件
    initView(mContextView);

    // 业务操作
    doBusiness(this);

    // 显示VoerFlowMenu
    displayOverflowMenu(getContext());

    // 是否可以截屏
    if (!isCanScreenshot) {
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
    }

    setButtomLine(com.fhrj.library.R.color.touming);
//    recv = new BroadcastReceiver() {
//      @Override
//      public void onReceive(Context context, Intent intent) {
//        if (intent.getAction().equals(Constants.BD_EXIT_APP)){
//          finish();
//        }
//      }
//    };
//
//    IntentFilter filter = new IntentFilter();
//    filter.addAction(Constants.BD_EXIT_APP);
//    registerReceiver(recv, filter);

  }
  /***
   * 修改状态栏颜色
   * @param color
   */
  @SuppressLint("InlinedApi")
public void setTintManager(int color){
	  
	  
	  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {  
	        Window window = getWindow();  
	        window.setFlags(  
	                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,  
	                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); 
	        setTranslucentStatus(true);  
	        tintManager = new SystemBarTintManager(this);  
	        WindowManager.LayoutParams attrs = getWindow().getAttributes();

	        if((attrs.flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN){
	        	 tintManager.setStatusBarTintEnabled(false);  
	         }else{
	        	 tintManager.setStatusBarTintEnabled(true);
	        }
	        tintManager.setStatusBarTintResource(color);//通知栏所需颜色

      }
  }

	@TargetApi(19)
    private void setTranslucentStatus(boolean on) {  
        Window win = getWindow();  
        WindowManager.LayoutParams winParams = win.getAttributes();  
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;  
        if (on) {  
            winParams.flags |= bits;  
        } else {  
            winParams.flags &= ~bits;  
        }  
        win.setAttributes(winParams);  
    }
  @Override
  public void config(Bundle savedInstanceState) {
    
  }
  
  @Override
  public View bindView() {
    return null;
  }

  @Override
  protected void onRestart() {
    super.onRestart();
    Log.d(TAG, "BaseActivity-->onRestart()");
  }

  @Override
  protected void onStart() {
    super.onStart();
    Log.d(TAG, "BaseActivity-->onStart()");
  }

  @Override
  protected void onResume() {
    super.onResume();
    Log.d(TAG, "BaseActivity-->onResume()");
    resume();
  }

  @Override
  public void resume() {

  }

  @Override
  protected void onPause() {
    super.onPause();
    Log.d(TAG, "BaseActivity-->onPause()");
  }

  @Override
  protected void onStop() {
    super.onStop();
    Log.d(TAG, "BaseActivity-->onStop()");
  }

  @Override
  protected void onDestroy() {
    Log.d(TAG, "BaseActivity-->onDestroy()");
    destroy();
    mApplication.removeTask(mContextWR);
//    unregisterReceiver(recv);
    super.onDestroy();
  }

  @Override
  public void destroy() {

  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    //让Fragment可以消费
    super.onActivityResult(requestCode, resultCode, data);
  }
  
  /**
   * 显示Actionbar菜单图标
   */
  @Override
  public boolean onMenuOpened(int featureId, Menu menu) {
    if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
      if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
        try {
          Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
          m.setAccessible(true);
          m.invoke(menu, true);// 显示
        } catch (Exception e) {
          Log.e(TAG, "onMenuOpened-->" + e.getMessage());
        }
      }
    }
    return super.onMenuOpened(featureId, menu);
  }

  /**
   * 显示OverFlowMenu按钮
   * 
   * @param mContext 上下文Context
   */
  public void displayOverflowMenu(Context mContext) {
    try {
      ViewConfiguration config = ViewConfiguration.get(mContext);
      Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
      if (menuKeyField != null) {
        menuKeyField.setAccessible(true);
        menuKeyField.setBoolean(config, false);// 显示
      }
    } catch (Exception e) {
      Log.e("ActionBar", e.getMessage());
    }
  }

  /**
   * 获取当前Activity
   * 
   * @return
   */
  protected Activity getContext() {
    if (null != mContextWR)
      return mContextWR.get();
    else
      return null;
  }

  /**
   * 获取共通操作机能
   */
  public Operation getOperation() {
    return this.mOperation;
  }

  /**
   * 设置是否可截屏
   *
   */
  public void setCanScreenshot(boolean isCanScreenshot) {
    this.isCanScreenshot = isCanScreenshot;
  }

  /**
   * 隐藏标题栏
   */
  public void hiddeTitleBar() {
    // 标题栏容器
    View mTitleBarContainer =
        findViewById(BaseView.gainResId(mApplication, BaseView.ID, "ll_title"));
    if (null == mTitleBarContainer) {
      return;
    }
    mTitleBarContainer.setVisibility(View.GONE);
  }
  /**
   *显示标题栏
   */
  public void showTitleBar() {
    // 标题栏容器
    View mTitleBarContainer =
        findViewById(BaseView.gainResId(mApplication, BaseView.ID, "ll_title"));
    if (null == mTitleBarContainer) {
      return;
    }
    mTitleBarContainer.setVisibility(View.VISIBLE);
  }
  
  /**
   * 设置标题
   * @param strTitle 标题文本
   */
  public void setWindowTitle(String strTitle){
    setWindowTitle(strTitle,Gravity.LEFT|Gravity.CENTER_VERTICAL);
  }
  
  /**
   * 设置标题以及文本对齐方式
   * @param strTitle 标题文本
   * @param mTitleGravity 标题文本对齐方式
   */
  public void setWindowTitle(String strTitle,int mTitleGravity){
    // 标题
    TextView mTitleText = (TextView) findViewById(BaseView.gainResId(mApplication, BaseView.ID, "tv_title"));
    mTitleText.setGravity(mTitleGravity);
    mTitleText.setText(strTitle);
  }
  
  /**
   * 设置标题栏底部线
   * @param visibility 是否可见
   */
  public void setButtomLine(int visibility){
    View mLineView = findViewById(BaseView.gainResId(mApplication, BaseView.ID, "title_buttom_line"));
    mLineView.setVisibility(visibility);
  }
  
  /**
   * 设置标题栏背景颜色
   * 
   * @param strColor 背景颜色，如：#FFFFFF
   */
  public void setTitleBarBgColor(String strColor) {
    if (TextUtils.isEmpty(strColor)) return;
    setTitleBarBgColor(Color.parseColor(strColor));
  }

  /**
   * 设置标题栏背景颜色
   * 
   * @param mResId 背景资源文件-->mContext.getResources().getColor(R.color.actionbar_bg)
   */
  public void setTitleBarBgColor(int mResId) {
    View mTitleBarContainer =
        findViewById(BaseView.gainResId(mApplication, BaseView.ID, "ll_title"));
    mTitleBarContainer.setBackgroundColor(mResId);
  }
  
  /**
   * 设置标题栏背景颜色
   * 
   * @param mResId 背景资源文件-->R.color.actionbar_bg/R.drawable.actionbar_bg
   */
  public void setTitleBgWithResColor(int mResId) {
    View mTitleBarContainer =
        findViewById(BaseView.gainResId(mApplication, BaseView.ID, "ll_title"));
    mTitleBarContainer.setBackgroundResource(mResId);
  }

  /**
   * 设置标题栏背景图片
   * 
   * @param strResName 图片资源文件名称，如：actionbar_bg
   */
  public void setTitleBarBg(String strResName) {
    if (TextUtils.isEmpty(strResName)) return;
    setTitleBarBg(BaseView.gainResId(mApplication, BaseView.DRAWABLE, strResName));
  }

  /**
   * 设置标题栏背景图片
   * 
   * @param mResId 图片资源id，如：R.drawable.actionbar_bg
   */
  public void setTitleBarBg(int mResId) {
    View mTitleBarContainer =
        findViewById(BaseView.gainResId(mApplication, BaseView.ID, "ll_title"));
    mTitleBarContainer.setBackgroundResource(mResId);
  }

  /**
   * 初始化返回按钮+标题左对齐
   * 
   * @param strTitle 标题名称
   * @param mBtnClickListener Home/Menu按钮点击监听事件
   */
  public void initHomeMenuTitleBar(String strTitle, View.OnClickListener mBtnClickListener) {
    View mMenuBtn = findViewById(BaseView.gainResId(mApplication, BaseView.ID, "iv_menu"));
    mMenuBtn.setVisibility(View.VISIBLE);
    if (null != mBtnClickListener) {
      mMenuBtn.setOnClickListener(mBtnClickListener);
    }
    View mBackBtn = findViewById(BaseView.gainResId(mApplication, BaseView.ID, "iv_back"));
    mBackBtn.setVisibility(View.GONE);

    // 标题
    TextView mTitleText =
        (TextView) findViewById(BaseView.gainResId(mApplication, BaseView.ID, "tv_title"));
    mTitleText.setText(strTitle);
  }

  /**
   * 初始化返回按钮+标题左对齐
   * 
   * @param strTitle 标题名称
   */
  public void initBackTitleBar(String strTitle) {
    initBackTitleBar(strTitle, Gravity.LEFT | Gravity.CENTER_VERTICAL);
  }

  /**
   * 初始化返回按钮+指定标题文本对齐方式
   * 
   * @param strTitle 标题名称
   * @param mGravity 标题文本对其方式 Gravity.LEFT|Gravity.CENTER_VERTICAL
   */
  public void initBackTitleBar(String strTitle, int mGravity) {
    // 设置标题
    TextView mTitleText =
        (TextView) findViewById(BaseView.gainResId(mApplication, BaseView.ID, "tv_title"));
    mTitleText.setText(strTitle);
    mTitleText.setGravity(mGravity);

    // 设置点击事件
    View mBackBtn = findViewById(BaseView.gainResId(mApplication, BaseView.ID, "iv_back"));
    mBackBtn.setVisibility(View.VISIBLE);
    mBackBtn.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        finish();
      }
    });
    
    View mbtnDone = findViewById(BaseView.gainResId(mApplication, BaseView.ID, "btn_done"));
    mbtnDone.setVisibility(View.INVISIBLE);
  }

  /**
   * 获取标题栏容器，可以自行控制左右按钮和布局
   * 
   * @return
   */
  public ViewGroup gainTitleBarVG() {
    return (ViewGroup) findViewById(BaseView.gainResId(mApplication, BaseView.ID, "ll_title"));
  }

  /**
   * 初始化标题栏右侧[完成/提交]按钮
   * 
   * @param strBtnText 按钮显示文本
   * @param mClickListener 点击监听事件
   */
  public void initRightDoneBtn(String strBtnText, View.OnClickListener mClickListener) {
    Button mDoneBtn =
        (Button) findViewById(BaseView.gainResId(mApplication, BaseView.ID, "btn_done"));
    mDoneBtn.setVisibility(View.VISIBLE);
    mDoneBtn.setText(strBtnText);
    if (null != mClickListener) {
      mDoneBtn.setOnClickListener(mClickListener);
    }
  }

  /**
   * 隐藏标题栏右侧[完成/提交]按钮
   */
  public void hiddenRightDoneBtn() {
    hiddenRightDoneBtn(View.GONE);
  }
  
  /**
   * 隐藏标题栏右侧[完成/提交]按钮
   * @param mViewStatus 按钮的状态 View.GONE/View.INVISIBLE
   */
  public void hiddenRightDoneBtn(int mViewStatus) {
    Button mDoneBtn =
        (Button) findViewById(BaseView.gainResId(mApplication, BaseView.ID, "btn_done"));
    mDoneBtn.setVisibility(mViewStatus);
  }

  /**
   * 隐藏标题栏左侧[返回]按钮
   */
  public void hiddenLeftBackBtn() {
    hiddenLeftBackBtn(View.GONE);
  }
  
  /**
   * 隐藏标题栏左侧[返回]按钮
   * @param mViewStatus 按钮的状态 View.GONE/View.INVISIBLE
   */
  public void hiddenLeftBackBtn(int mViewStatus) {
    ImageButton mBackBtn =
        (ImageButton) findViewById(BaseView.gainResId(mApplication, BaseView.ID, "iv_back"));
    mBackBtn.setVisibility(mViewStatus);
  }
  
  /**
   * 隐藏标题栏左侧[菜单/Home]按钮
   */
  public void hiddenLeftMenuBtn() {
    hiddenLeftMenuBtn(View.GONE);
  }
  
  /**
   * 隐藏标题栏左侧[菜单/Home]按钮
   * @param mViewStatus 按钮的状态 View.GONE/View.INVISIBLE
   */
  public void hiddenLeftMenuBtn(int mViewStatus) {
    ImageButton mBackBtn =
        (ImageButton) findViewById(BaseView.gainResId(mApplication, BaseView.ID, "iv_menu"));
    mBackBtn.setVisibility(mViewStatus);
  }
  
  /**
   * 引导状态存储偏好
   */
  protected final static String GUIDE_STATUS = "guide_status_sp";

  /**
   * 添加遮罩引导图
   * 
   * @param spKey 对应界面的引导图key，可以传入当前类的全名称+引导图序号
   * @param resId 引导图res目录的资源id
   */
  @SuppressWarnings("unchecked")
  public void addMaskGuide(final String spKey, int resId) {
    // 查找通过setContentView上的根布局
    View view = getWindow().getDecorView().findViewById(BaseView.gainResId(mApplication, BaseView.ID, "rootLayout"));
    if (0 == resId || null == view) return;
    // 读取引导状态
    Map<String, Object> perface = (Map<String, Object>) ToolFile.readShrePerface(mApplication, GUIDE_STATUS);
    if (null == perface) {
      perface = new HashMap<String, Object>();
    }
    final Map<String, Object> mPerface = perface;
    boolean isGuided = (null == perface.get(spKey)) ? false : (Boolean) perface.get(spKey);
    if (isGuided) {
      return;
    }
    // 当前Activity界面的容器
    ViewParent viewParent = view.getParent().getParent().getParent();
    if(null == viewParent)return;
    if (viewParent instanceof FrameLayout) {
      final FrameLayout root = (FrameLayout) viewParent;
      final ImageView guide = new ImageView(this);
      FrameLayout.LayoutParams params =
          new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
              ViewGroup.LayoutParams.MATCH_PARENT);
      guide.setScaleType(ScaleType.FIT_XY);
      guide.setImageBitmap(readBitMap(mApplication, resId));
      guide.setBackgroundResource(BaseView.gainResId(mApplication, BaseView.DRAWABLE, "mask_guide_bg"));
      guide.setLayoutParams(params);
      guide.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          // 回收Bitmap
          if (guide != null && guide.getDrawable() != null) {
            Bitmap mBitmap = ((BitmapDrawable) guide.getDrawable()).getBitmap();
            guide.setImageDrawable(null);
            if (mBitmap != null && !mBitmap.isRecycled()) {
              mBitmap.recycle();
              mBitmap = null;
            }
          }
          root.removeView(guide);
          mPerface.put(spKey, true);
          ToolFile.writeShrePerface(mApplication,GUIDE_STATUS, mPerface);
          System.gc();
        }
      });
      root.addView(guide);
    }
  }

  /**
   * 
   * 调用JNI底层实现获取本地图片资源
   * @param mContext
   * @param resId
   * @return
   */
  public Bitmap readBitMap(Context mContext, int resId) {
    BitmapFactory.Options opt = new BitmapFactory.Options();
    opt.inPreferredConfig = Bitmap.Config.RGB_565;
    opt.inPurgeable = true;
    opt.inInputShareable = true;
    opt.inJustDecodeBounds = false;
    // width，hight设为原来的十分一
    // opt.inSampleSize = 10;
    // 获取资源图片
    InputStream is = mContext.getResources().openRawResource(resId);
    return BitmapFactory.decodeStream(is, null, opt);
  }
  
  /**
   * Actionbar点击返回键关闭事件
   */
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
  }

  public void finish() {
    super.finish();
    int mAnimIn = 0;
    int mAnimOut = 0;
    switch (mAnimationType) {
      //左进右出
      case IBaseActivity.LEFT_RIGHT:
        mAnimIn = BaseView.gainResId(mApplication, BaseView.ANIM, "base_slide_left_in");
        mAnimOut = BaseView.gainResId(mApplication, BaseView.ANIM, "base_slide_right_out");
        overridePendingTransition(mAnimIn,mAnimOut);
        break;
       //上进下出
      case IBaseActivity.TOP_BOTTOM:
        mAnimIn = BaseView.gainResId(mApplication, BaseView.ANIM, "base_push_up_in");
        mAnimOut = BaseView.gainResId(mApplication, BaseView.ANIM,"base_push_bottom_out");
        overridePendingTransition(mAnimIn,mAnimOut);
        break;
      case IBaseActivity.FADE_IN_OUT:
        mAnimIn = BaseView.gainResId(mApplication, BaseView.ANIM, "base_fade_in");
        mAnimOut = BaseView.gainResId(mApplication, BaseView.ANIM,"base_fade_out");
        overridePendingTransition(mAnimIn,mAnimOut);
        break;
      default:
        break;
    }
    mAnimationType = NONE;
  }
}
