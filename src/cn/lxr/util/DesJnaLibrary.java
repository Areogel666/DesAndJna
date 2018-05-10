package cn.lxr.util;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface DesJnaLibrary extends Library {
//	String filePath = PSBCLibrary.class.getResource("").getPath().replaceFirst("/","").replaceAll("%20"," ")+"11APFBJSZ.dll";
	/*------Create a instance-------*/
	DesJnaLibrary instance = (DesJnaLibrary)Native.loadLibrary("/root/psbc/libtrides.so", DesJnaLibrary.class);//lib11APF_BJSZ.so
//	PSBCLibrary instance = (PSBCLibrary)Native.loadLibrary("11APF_BJSZ", PSBCLibrary.class);//lib11APF_BJSZ.so
//	PSBCLibrary instance = (PSBCLibrary)Native.loadLibrary(filePath, PSBCLibrary.class);//lib11APF_BJSZ.so

	/*------Define interfaces-------*/
	/**
	 * 执行DES算法对文件加解密
	 * <p>返回int是否加密成功 0 成功，-1 失败</p>
	* @param inFileName 待加解/密文件名，绝对路径
	* @param outFileName 待输出加解/密文件名，绝对路径
	* @param bType 类型：加密ENCRYPT 0，解密DECRYPT 1
	* @param bMode 模式：ECB 0,CBC 1
	* @param key 密钥(可为8位,16位,24位)支持3密钥
	* @param keylen  密钥长度，多出24位部分将被自动裁减
	* @return int
	 */
	public int RunDesFile(String inFileName, String outFileName, int bType, int bMode, String key, int keylen);
	
}
