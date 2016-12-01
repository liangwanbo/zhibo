package com.fhrj.library.tools;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.fhrj.library.config.SysEnv;

/***
 * 单位换算工具类
 *         px ：像素 <br>
 *         in ：英寸<br>
 *         mm ：毫米<br>
 *         pt ：磅，1/72 英寸<br>
 *         dp ：一个基于density的抽象单位，如果一个160dpi的屏幕，1dp=1px<br>
 *         dip ：等同于dp<br>
 *         sp ：同dp相似，但还会根据用户的字体大小偏好来缩放。<br>
 *         建议使用sp作为文本的单位，其它用dip<br>
 *         布局时尽量使用单位dip，少使用px <br>
 * @author ZhangGuoHao
 * @date 2016年4月7日 下午8:28:54
 */
public class ToolUnit {

  /**
   * 获取指定列数+间隙的正方形边长（以当前屏幕宽度为总长）
   * 
   * @param context 上下文
   * @param columns 列数
   * @param itemSpaceDp item之间的间隙
   * @return
   */
  public static int gainSquareItemLength(Context context, int columns, int itemSpaceDp) {
    int widthHeight = 0;
    // 屏幕宽度
    int screenWidth = SysEnv.SCREEN_WIDTH;
    // 抛开间隙space除于列数
    widthHeight =
        (screenWidth - ToolUnit.getRawSize(context, TypedValue.COMPLEX_UNIT_DIP, itemSpaceDp)
            * (columns + 1))
            / columns;
    return widthHeight;
  }

  /**
   * 获取当前分辨率下指定单位对应的像素大小（根据设备信息） px,dip,sp -> px
   * 
   * Paint.setTextSize()单位为px
   * 
   * 代码摘自：TextView.setTextSize()
   * 
   * @param unit TypedValue.COMPLEX_UNIT_*
   * @param size
   * @return
   */
  public static int getRawSize(Context mContext, int unit, float size) {
    Resources r;
    if (mContext == null)
      r = Resources.getSystem();
    else
      r = mContext.getResources();

    return (int) TypedValue.applyDimension(unit, size, r.getDisplayMetrics());
  }

  /** 设备显示材质 **/
  private static DisplayMetrics mDisplayMetrics = SysEnv.getDisplayMetrics();

  /**
   * @Description 根据手机的分辨率从 dip 的单位 转成为 px(像素)
   * @param context
   *            环境
   *            需要转化的dip值
   * @return int 转化后的px值
   */
  public static int dipToPx(Context context, float dipValue) {
      final float scale = context.getResources().getDisplayMetrics().density;
      return (int) (dipValue * scale + 0.5f);
  }

  /**
   * @Description 根据手机的分辨率从 px(像素) 的单位 转成为 dip
   * @param context
   *            环境
   * @param pxValue
   *            需要转换的像素值
   * @return int 转化后的dip值
   */
  public static int pxToDip(Context context, float pxValue) {
      final float scale = context.getResources().getDisplayMetrics().density;
      return (int) (pxValue / scale + 0.5f);
  }

  /**
   * 将px值转换为sp值，保证文字大小不变
   * @param context
   * @param pxValue
   */
  public static int px2sp(Context context, float pxValue) {
      final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
      return (int) (pxValue / fontScale + 0.5f);
  }

  /**
   * 将sp值转换为px值，保证文字大小不变
   * @param spValue
   *            （DisplayMetrics类中属性scaledDensity）
   */
  public static int sp2px(Context context, float spValue) {
      final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
      return (int) (spValue * fontScale + 0.5f);
  }
  
  /**
   * sp转换px
   * 
   * @param spValue sp数值
   * @return px数值
   */
  public static int spTopx(float spValue) {
    return (int) (spValue * mDisplayMetrics.scaledDensity + 0.5f);
  }

  /**
   * px转换sp
   * 
   * @param pxValue px数值
   * @return sp数值
   */
  public static int pxTosp(float pxValue) {
    return (int) (pxValue / mDisplayMetrics.scaledDensity + 0.5f);
  }

  /**
   * dip转换px
   * 
   * @param dipValue dip数值
   * @return px数值
   */
  public static int dipTopx(int dipValue) {
    return (int) (dipValue * mDisplayMetrics.density + 0.5f);
  }

  /**
   * px转换dip
   * 
   * @param pxValue px数值
   * @return dip数值
   */
  public static int pxTodip(float pxValue) {
    return (int) (pxValue / mDisplayMetrics.density + 0.5f);
  }
}
