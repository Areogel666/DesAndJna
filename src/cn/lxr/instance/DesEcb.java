package cn.lxr.instance;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import cn.lxr.util.ConvertUtil;

/**
 * DES对文件进行加解密方法一
 * 采用java源生库中javax.crypto.Cipher类的方法
 */
public class DesEcb {
    /**
	 * 执行DES算法对文件加解密（暂时只有ECB模式）
	 * <p>返回int是否加密成功 0 成功，-1 失败</p>
	* @param inFileName 待加解/密文件名，绝对路径
	* @param outFileName 待输出加解/密文件名，绝对路径
	* @param bType 类型：加密ENCRYPT 0，解密DECRYPT 1
	* @param bMode 模式：ECB 0,CBC 1 
	* @param key 密钥8位，多出8位部分将被自动裁减
	* @return int
	 */
	public static int getDESFile(String inFileName, String outFileName, int bType, int bMode, String key){
		bMode = 0;//暂时只有ECB模式
		StringBuffer buffer = new StringBuffer();
//		FileReader fr = null;
		BufferedReader reader = null;
		Writer writer = null;
		int ch = 0;
		try {
			String code = getCharset(inFileName);
			System.out.println("文本编码格式为：" + code);
//			fr = new FileReader(inFileName);
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(inFileName), code)); 
			//读取字符文件流
			while ((ch = reader.read()) != -1) {
				buffer.append((char)ch);
			}
			//转码
			String resultText = "";
			try {
				if(bType == 0){//加密
					resultText = encryptDES(buffer.toString(), key);
//					System.out.println(buffer.toString());
				}else{//解密
					resultText = decryptDES(buffer.toString(), key);
//					System.out.println(buffer.toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
				return 1;
			}
			//输出文件流
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFileName), code)); 
			writer.write(resultText);
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		} finally {
			try {
				writer.flush();
				writer.close();
//				fr.close();
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	/** 
	 * 加密数据 
	 * @param encryptString  注意：采用DES/ECB/NoPadding时这里的数据长度只能为8的倍数 
	 * @param encryptKey 
	 * @return 
	 * @throws Exception 
	 */  
	public static String encryptDES(String encryptString, String encryptKey) throws Exception {  
		SecretKeySpec key = new SecretKeySpec(getKey(encryptKey), "DES");  
//        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");  
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");     // CBC/PKCS5Padding---->>>    密码模式和填充模式 
		cipher.init(Cipher.ENCRYPT_MODE, key);  
		byte[] encryptedData = cipher.doFinal(encryptString.getBytes());  
		return ConvertUtil.bytesToHexString(encryptedData);  
	}  
	
	/*** 
	 * 解密数据 
	 * @param decryptString 
	 * @param decryptKey 
	 * @return 
	 * @throws Exception 
	 */  
	public static String decryptDES(String decryptString, String decryptKey) throws Exception {  
		SecretKeySpec key = new SecretKeySpec(getKey(decryptKey), "DES");  
//        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");  
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key);  
		byte decryptedData[] = cipher.doFinal(ConvertUtil.hexStringToByte(decryptString));  
		return new String(decryptedData);  
	}  
	
	/** 
	 * 自定义一个key（密钥超过8字节自动去除） 
	 * @param string  
	 */  
	public static byte[] getKey(String keyRule) {  
		SecretKeySpec key = null;  
		byte[] keyByte = keyRule.getBytes();  
		// 创建一个空的八位数组,默认情况下为0  
		byte[] byteTemp = new byte[8];  
		// 将用户指定的规则转换成八位数组  
		for (int i = 0; i < byteTemp.length && i < keyByte.length; i++) {  
			byteTemp[i] = keyByte[i];  
		}  
		key = new SecretKeySpec(byteTemp, "DES");  
		return key.getEncoded();  
	}  
	
	/**
	 * 
	* 得到文件编码格式
	* @param path 文件路径
	* @return String 编码格式
	 */
	private static String getCharset(String path) {
		String charset ="asci";
        byte[] first3Bytes = new byte[3];
        BufferedInputStream bis = null;
        try {
            boolean checked = false;
            bis = new BufferedInputStream(new FileInputStream(path));
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1)
                return charset;
            if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
                charset = "Unicode";//UTF-16LE
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF) {
                charset = "Unicode";//UTF-16BE
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF8";
                checked = true;
            }
            bis.reset();
            if (!checked) {
                int len = 0;
                int loc = 0;
                while ((read = bis.read()) != -1) {
                    loc++;
                    if (read >= 0xF0)
                        break;
                    if (0x80 <= read && read <= 0xBF) //单独出现BF以下的，也算是GBK
                        break;
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) 
                        //双字节 (0xC0 - 0xDF) (0x80 - 0xBF),也可能在GB编码内
                            continue;
                        else
                            break;
                    } else if (0xE0 <= read && read <= 0xEF) { //也有可能出错，但是几率较小
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            } else
                                break;
                        } else
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException ex) {
                }
            }
        }
        if("asci".equals(charset)){
			// 这里采用GBK编码，而不用环境编码格式，因为环境默认编码不等于操作系统编码 
			// code = System.getProperty("file.encoding");
        	charset = "GBK";
		}
        return charset;
	}

    public static void main(String[] args) throws Exception {  
//        String clearText = "中文中文中文中文springsk";  //DES/ECB/NoPadding时这里的数据长度必须为8的倍数  
//        System.out.println(clearText.getBytes().length);
        String key = "Y@bzdf0L";  
//        System.out.println("明文："+clearText+"\n密钥："+key);  
//        String encryptText = encryptDES(clearText, key);  
//        System.out.println("加密后："+encryptText);  
//        String decryptText = decryptDES(encryptText, key);  
//        System.out.println("解密后："+decryptText);
        
        //生成文件测试
        getDESFile("D:\\temp\\szpk2018042001.txt", "D:\\temp\\test.txt", 0, 0, key);
        getDESFile("D:\\temp\\test.txt", "D:\\temp\\testResult.txt", 1, 0, key);
    }  
    
}
