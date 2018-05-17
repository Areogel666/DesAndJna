package cn.lxr.util;

import java.awt.Desktop;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class CommonFileUtil {

	public static void main(String[] args) {
		//1.创建txt文本
		String outPath = "D:\\temp\\" + "abc.txt";
		String inPath = "C:/Users/Administrator/Desktop/邮储直联/test/szpk2018042001.txt";
		boolean res = createText(outPath);
		if(res){
			openFile(outPath);
		}
		//2.读取txt文本
		List<String> textList = readText(inPath);
		for (int i = 0; i < textList.size(); i++) {
			System.out.println("==== 第" + i+1 + "行的内容是：" + textList.get(i));
		}
	}

	/** 
	* 创建txt文本
	* @return boolean
	*/ 
	public static boolean createText(String outPath) {
		FileOutputStream fos = null;
		BufferedOutputStream buff = null;
		try {
			fos = new FileOutputStream(outPath);
			buff = new BufferedOutputStream(fos);
			buff.write("来吧，生成一个txt文本".getBytes("GBK"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				buff.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	/**
	 * 按行读取txt文本
	 */
	public static List<String> readText(String inPath){
		//解析TXT文件
		List<String> contentList = new ArrayList<String>();
		//读取
		BufferedReader br = null;
		try {
			//解决中文乱码
			InputStreamReader read = new InputStreamReader(new FileInputStream(inPath), "GBK");
			br=new BufferedReader(read);
			String contentStr = ""; 
			//循环读取行数据并写入list
			while((contentStr=br.readLine())!=null) {
//				System.out.println(contentStr);
				contentList.add(contentStr);
			}
		}catch(Exception e){
		   e.printStackTrace();
		   return null;
		}finally{//关闭流
		   try {
				br.close();
		   } catch (IOException e) {
				e.printStackTrace();
		   }
		}
		return contentList;
	}
	
	/** 
	* 打开文件
	* @return void
	*/ 
	public static void openFile(String fileName) {
		try {
			if (new File(fileName).exists()) {
				Desktop desktop = Desktop.getDesktop();
				desktop.open(new File(fileName));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
