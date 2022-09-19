package cn.edu.nwpu.rj416.util.basic;

import cn.edu.nwpu.rj416.util.exception.runtime.MInvalidParameterException;
import cn.edu.nwpu.rj416.util.exception.runtime.MInvalidValueException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil { //文件工具
	public static final byte[] getFileContent(File file) throws IOException { //获取文件内容（字节流）
		if (file == null) {
			throw new MInvalidParameterException("file==null");//文件对象指向空则抛出无效参数异常
		}
		if (!file.exists()) {
			throw new MInvalidValueException("文件不存在：%s", file.getPath());//文件对象不存在则抛出无效值异常
		}
		
		if (file.isDirectory()) {
			throw new MInvalidValueException("文件不能是目录：%s", file.getPath());//文件对象为目录抛出无效值异常
		}

		try (FileInputStream is = new FileInputStream(file)){
			
			int len = is.available();
			
			byte[] buffer = new byte[len];
			is.read(buffer);
			return buffer;
		}
	}
	
	public static final byte[] getFileContent(String path) throws IOException {//方法重载，传参可以是文件路径
		return FileUtil.getFileContent(new File(path));//通过路径创建文件对象
	}
	
	public static final String getFileContentAsString(File file) throws IOException {//将从文件获取的字节流数组转成字符串
		byte[] buffer = getFileContent(file);
		return new String(buffer);
	}
	
	public static final String getFileContentAsString(String path) throws IOException {
		return FileUtil.getFileContentAsString(new File(path));
	}
	
	
	/**
	 * 获取路径目录下的文件，不包括子目录及子目录中的文件
	 * @param path 目录的路径
	 * @return
	 */
	public static final List<File> getFilesByPath(String path) {
		List<File> list = new ArrayList<>(); //存放文件的列表容器
		
		File file = new File(path);
		if (!file.exists()) {
			return list;
		}
		
		if (file.isDirectory()) { //path路径对应的是文件夹的话
			File[] subFiles = file.listFiles(); //listFiles方法返回一个File数组，里面存的是当前file对象下的所有文件及文件夹
			for (File f : subFiles) {
				if (f.isDirectory()) {
					continue; //遇到文件夹，跳过
				}
				list.add(f); //文件则添加到list中
			}
		} else {
			list.add(file); //path对应的是一个文件则直接将这个文件添加到list中
		}
		return list;
	}
	
	public static void dumpToFile(File file, byte[] buffer) throws IOException {
		if (file == null) {
			throw new MInvalidParameterException("file==null");
		}
		
		if (file.isDirectory()) {
			throw new MInvalidValueException("文件不能是目录：%s", file.getPath());
		}
		
		file.getParentFile().mkdirs(); //创建包括父目录的文件目录
		
		try (FileOutputStream is = new FileOutputStream(file)){
		}
	}
}
