package com.mytv365.zb.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

/**
 * 要存储、打开的对象或里面的对象不能为且必须实现Serializable接口，否则是不会序列化的（报异常）
 * 
 * @author 锐
 * 
 */
public class SaveListObject {

	/**
	 * 单例模式
	 */
	public static SaveListObject saveListObject;

	/**
	 * 私有构造函数
	 */
	private SaveListObject() {

	}

	public static SaveListObject getInstance() {
		if (saveListObject == null) {
			saveListObject = new SaveListObject();
		}
		return saveListObject;
	}

	/**
	 * 打开集合对象到内存
	 * 
	 * @param file
	 *            文件路径，必须是文件
	 * @return 返回null为走到异常
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Object> openList(File file) {
		ArrayList<Object> list = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			list = (ArrayList<Object>) ois.readObject();
			fis.close();
			ois.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("SaveListObject中读取文件路径异常。。。");
			return null;
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("SaveListObject中创建ObjectInputStream异常。。。");
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("SaveListObject中IO流处理异常。。。");
			return null;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("SaveListObject中返回序列化类异常。。。");
			return null;
		}
		return list;
	}

	/**
	 * 打开对象到内存
	 * 
	 * @param file
	 *            文件路径，必须是文件
	 * @return 返回null为走到异常
	 */
	public Object openObject(File file) {
		Object obj = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			obj = (Object) ois.readObject();
			fis.close();
			ois.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("SaveListObject中读取文件路径异常。。。");
			return null;
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("SaveListObject中创建ObjectInputStream异常。。。");
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("SaveListObject中IO流处理异常。。。");
			return null;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("SaveListObject中返回序列化类异常。。。");
			return null;
		}
		return obj;
	}

	/**
	 * 存储集合对象到指定路径
	 * 
	 * @param file
	 *            存储文件路径
	 * @param list
	 *            需要存储的集合对象
	 * @return 返回false为走到异常，返回true为存储成功
	 */
	public boolean saveList(File file, ArrayList<Object> list) {
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(list);
			fos.close();
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("SaveListObject中读取文件路径异常。。。");
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("SaveListObject中创建ObjectInputStream异常。。。");
			return false;
		}
		return true;
	}

	/**
	 * 存储对象到指定路径
	 * 
	 * @param file
	 *            存储文件路径
	 * @param list
	 *            需要存储的对象
	 * @return 返回false为走到异常，返回true为存储成功
	 */
	public boolean saveObject(File file, Object object) {
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(object);
			fos.close();
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("SaveListObject中读取文件路径异常。。。");
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("SaveListObject中创建ObjectInputStream异常。。。");
			return false;
		}
		return true;
	}
}
