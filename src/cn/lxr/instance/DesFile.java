package cn.lxr.instance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import com.vstsoft.des.Des;

/**
 * DES对文件进行加解密方法二
 * 加解密方法引用第三方jar包des.jar
 */
public class DesFile {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		DesFile m = new DesFile();
		m.enc("D:\\temp\\szpk2018042001.txt", "D:\\temp\\test.txt");
		m.dec("D:\\temp\\test.txt", "D:\\temp\\testResult.txt");
	}
	/*加密,srcPath:明文绝对路径,objPath:生称密文绝对路径*/
	public static int enc(String srcPath, String objPath){
		File srcFile = new File(srcPath);
		File objFile = new File(objPath);
		ByteBuffer inBuf = ByteBuffer.allocate(8);
		ByteBuffer outBuf = ByteBuffer.allocate(8);
		FileChannel outChannel = null;
		FileOutputStream fos = null;
		FileInputStream fis = null;
		Des des = new Des("ABCDEFGH");
		if( srcFile.exists() == false ){
			System.out.println("File Not Exists:" + srcPath);
			return -1;
		}
		try{
			fis = new FileInputStream(srcFile);
			int len = (int)srcFile.length();
			int readLen = 0;
			byte[] fileByte = new byte[len];
			readLen = fis.read(fileByte);
			if( (len != readLen) ){
				System.out.println("Read File Error!\nsrcFile[" + srcPath + "],len[" + len + "],readLen[" + readLen + "].");
				return -1;
			}
			String content = new String(fileByte, "gbk");
			String msg = mod8(content);
			fos = new FileOutputStream(objFile);
			outChannel = fos.getChannel();
			byte[] src = msg.getBytes("gbk");
			//for(int i = 0; i < srcFile.length(); i += 8){
			for(int i = 0; i < src.length; ){
				inBuf.clear();
				outBuf.clear();
				for(int j = 0; j < 8; j++){
					inBuf.put(src[i+j]);
				}
				String a1 = ((ByteBuffer)(inBuf.flip())).asCharBuffer().toString();
				String desStr = des.enc(a1, a1.length());
				for(int k = 0; k < desStr.length(); k++){
					outBuf.putChar(desStr.charAt(k));
				}
				outBuf.flip();
				outChannel.write(outBuf);
				i += 8;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try{
				if( null != outChannel ){
					outChannel.close();
				}
				if( null != fos ){
					fos.close();
				}
				if( null != fis ){
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	/*解密,srcPath:密文绝对路径,objPath:生成明文绝对路径*/
	public static int dec(String srcPath, String objPath){
		File srcFile = new File(srcPath);
		File objFile = new File(objPath);
		ByteBuffer inBuf = ByteBuffer.allocate(8);
		ByteBuffer outBuf = ByteBuffer.allocate(8);
		FileChannel outChannel = null;
		FileOutputStream fos = null;
		FileInputStream fis = null;
		Des des = new Des("ABCDEFGH");
		byte[] buffer = new byte[8];
		int i = 0;
		if( srcFile.exists() == false ){
			System.out.println("File Not Exists:" + srcPath);
			return -1;
		}
		try{
			fis = new FileInputStream(srcFile);
			fos = new FileOutputStream(objFile);
			outChannel = fos.getChannel();
			while( fis.read(buffer) != -1 ){
				i+= 8;
				inBuf.clear();
				outBuf.clear();
				for(int j = 0; j < 8; j++){
					inBuf.put(buffer[j]);
				}
				String a1 = ((ByteBuffer)(inBuf.flip())).asCharBuffer().toString();
				String destStr = des.dec(a1, a1.length());
				for(int k = 0; k < destStr.length(); k++){
					outBuf.putChar(destStr.charAt(k));
				}
				outBuf.flip();
				outChannel.write(outBuf);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try{
				if( null != outChannel ){
					outChannel.close();
				}
				if( null != fos ){
					fos.close();
				}
				if( null != fis ){
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	private static String mod8(String srcString) throws UnsupportedEncodingException{
		String re = srcString;
		if( null == re ){
			re = "";
		}
		int m = (re.getBytes("gbk").length) % 8;
		switch( m ){
		case 0:{
			re = re + "88888888";
			break;
		}
		case 1:{
			re = re + "7777777";
			break;
		}
		case 2:{
			re = re + "666666";
			break;
		}
		case 3:{
			re = re + "55555";
			break;
		}
		case 4:{
			re = re + "4444";
			break;
		}
		case 5:{
			re = re + "333";
			break;
		}
		case 6:{
			re = re + "22";
			break;
		}
		case 7:{
			re = re + "1";
			break;
		}
		}
		return re;
	}
	
	public static String bytesToHexString(byte[] src){
		StringBuilder stringBuilder = new StringBuilder("");
		if(src == null || src.length <= 0)
		{
			return null;
		}
		for(int i = 0; i < src.length; i++){
			int v = src[i]&0xFF;
			String hv = Integer.toHexString(v);
			if(hv.length() < 2){
				stringBuilder.append(0);
			}
			else
			{
				stringBuilder.append(hv);
			}
		}
		return stringBuilder.toString();
	}


}
