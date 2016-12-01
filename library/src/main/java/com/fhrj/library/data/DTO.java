package com.fhrj.library.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

/***
 * 数据传输对象
 * @author ZhangGuoHao
 * @date 2016年4月7日 下午8:29:43
 */
public class DTO<K, V> extends HashMap<K, V> implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = -6377960213315892547L;

  /**
   * 赋值
   * 
   * @param objKey 键值
   * @param objValue 对应值
   */
  public V put(K objKey, V objValue) {
    if (readonly) {
      throw new RuntimeException("属性只读");
    } else {
      return super.put(objKey, objValue);
    }

  }

  /**
   * 只读开关
   */
  private boolean readonly = false;

  public void setReadonly(boolean readonly) {
    this.readonly = readonly;
  }

  /**
   * 移除空值的Item
   */
  public void removeEmptyValueItem() {
    // 边遍历边移除操作必须使用迭代器方式遍历
    Iterator<Entry<K, V>> it = entrySet().iterator();
    while (it.hasNext()) {
      Entry<K, V> entry = it.next();
      if (null == entry.getValue() || "".equals(String.valueOf(entry.getValue()))) {
        it.remove();
      }
    }
  }

}
