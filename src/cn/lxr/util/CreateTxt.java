package cn.lxr.util;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class CreateTxt {

	public static void main(String[] args) {
		FileOutputStream fos = null;
		BufferedOutputStream buff = null;
		try {
			fos = new FileOutputStream("D:\\temp\\" + "abc.txt");
			buff = new BufferedOutputStream(fos);
			buff.write("来吧，生成一个txt文本".getBytes("GBK"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				buff.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
