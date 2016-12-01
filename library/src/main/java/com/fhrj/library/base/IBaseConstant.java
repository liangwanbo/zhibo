package com.fhrj.library.base;

/**
 * 基础共通常量
 * @author ZhangGuoHao
 * @date 2016年4月7日 下午6:11:49
 */
public interface IBaseConstant {
  
  /**
   * 用于 intent 传输动画类型数据
   */
  String ANIMATION_TYPE = "AnimationType";
  /**
   * 无动画
   */
  int NONE = 0;
  /**
   * 左右动画
   */
  int LEFT_RIGHT = 1;
  /**
   * 上下动画
   */
  int TOP_BOTTOM = 2;
  /**
   * 淡入淡出
   */
  int FADE_IN_OUT = 3;
  
  
  
  /*** 资源类型-array **/
  String ARRAY = "array";

  /*** 资源类型-attr **/
  String ATTR = "attr";

  /*** 资源类型-anim **/
  String ANIM = "anim";

  /*** 资源类型-bool **/
  String BOOL = "bool";

  /*** 资源类型-color **/
  String COLOR = "color";

  /*** 资源类型-dimen **/
  String DIMEN = "dimen";

  /*** 资源类型-drawable **/
  String DRAWABLE = "drawable";

  /*** 资源类型-id **/
  String ID = "id";

  /*** 资源类型-id **/
  String INTEGER = "integer";

  /*** 资源类型-layout **/
  String LAYOUT = "layout";

  /*** 资源类型-drawable **/
  String STRING = "string";

  /*** 资源类型-style **/
  String STYLE = "style";

  /*** 资源类型-styleable **/
  String STYLEABLE = "styleable";
  
  /**
   * 查看器图片默认选中位置
   */
  String PICTURE_VIEWER_DEFAULT_POSTION = "defaultPostion";
  
  /**
   * 查看器数据源
   */
  String PICTURE_VIEWER_DATASOURCE = "pictureViewerDatasource";
  
  /**
   * 图片裁剪A
   */
  String ACTION_CROP = "com.android.camera.action.CROP";
  
  /**
   * [相册选择]选择请求码
   */
  int ALBUM_REQUEST_CODE = 128;

  /**
   * [立即拍照]选择请求码
   */
  int CAMERA_REQUEST_CODE = 127;

  /**
   * [裁剪图片]选择请求码
   */
  int CROPER_REQUEST_CODE = 126;
}
