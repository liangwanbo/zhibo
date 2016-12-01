package com.fhrj.library.base.impl;

import com.fhrj.library.base.IBaseConstant;
import com.fhrj.library.base.IBaseFragment;
import com.fhrj.library.base.Operation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/***
 * Fragment基类
 * @author ZhangGuoHao
 * @date 2016年4月8日 下午3:21:24
 */
@SuppressLint({ "NewApi", "Override" })
public abstract class BaseFragment extends Fragment implements IBaseFragment,IBaseConstant {

  /** 当前Fragment渲染的视图View **/
  private View mContextView = null;
  /** 共通操作 **/
  protected Operation mOperation = null;
  /** 依附的Activity **/
  protected Activity mContext = null;
  /** 日志输出标志 **/
  protected final String TAG = this.getClass().getSimpleName();

 @Override
	public void onAttach(Context context) {
		// TODO Auto-generated method stub
		super.onAttach(context);
		 mContext = (Activity) context;
		   Log.d(TAG, "BaseFragment-->onAttach()");
	}


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(TAG, "BaseFragment-->onCreate()");
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    Log.d(TAG, "BaseFragment-->onCreateView()");
    // 渲染视图View
    if (null == mContextView) {
      // 初始化参数
      Bundle parms = getArguments();
      if (null == parms) {
        parms = new Bundle();
      }
      initParams(parms);

      View mView = bindView();
      if (null == mView) {
        mContextView = inflater.inflate(bindLayout(), container, false);
      } else {
        mContextView = mView;
      }
      // 控件初始化
      initView(mContextView);
      // 实例化共通操作
      mOperation = new Operation(getActivity());
      // 业务处理
      doBusiness(getActivity());
    }

    return mContextView;
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    Log.d(TAG, "BaseFragment-->onActivityCreated()");
    super.onActivityCreated(savedInstanceState);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    Log.d(TAG, "BaseFragment-->onViewCreated()");
    super.onViewCreated(view, savedInstanceState);
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    Log.d(TAG, "BaseFragment-->onSaveInstanceState()");
    super.onSaveInstanceState(outState);
  }

  @Override
  public void onStart() {
    Log.d(TAG, "BaseFragment-->onStart()");
    super.onStart();
  }

  @Override
  public void onResume() {
    Log.d(TAG, "BaseFragment-->onResume()");
    super.onResume();
  }

  @Override
  public void onPause() {
    Log.d(TAG, "BaseFragment-->onPause()");
    super.onPause();
  }

  @Override
  public void onStop() {
    Log.d(TAG, "BaseFragment-->onStop()");
    super.onStop();
  }

  @Override
  public void onDestroy() {
    Log.d(TAG, "BaseFragment-->onDestroy()");
    super.onDestroy();
  }

  @Override
  public void onDetach() {
    Log.d(TAG, "BaseFragment-->onDetach()");
    super.onDetach();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    if (mContextView != null && mContextView.getParent() != null) {
      ((ViewGroup) mContextView.getParent()).removeView(mContextView);
    }
  }

  @Override
  public View bindView() {
    return null;
  }

  /**
   * 查找view
   * @param id
   * @return
   */
  protected View findViewById(int id){
    if(null == mContextView)return null;
    
    return mContextView.findViewById(id);
  }
  
  /**
   * 获取当前Fragment依附在的Activity
   * 
   * @return
   */
  public Activity getContext() {
    return getActivity();
  }

  /**
   * 获取共通操作机能
   */
  public Operation getOperation() {
    return this.mOperation;
  }
}
