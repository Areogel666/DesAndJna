package cn.lxr.instance;

import java.net.URLEncoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 3DES、DES加解密
 * 暂时只支持英文数字，如需中文加密可参考DesEcb.java
 * （其中DES加解密方法测试可能还有问题）
 * @author Administrator
 */
public class TripleDES {

	 private static final String IV = "1234567-";
//	    public static final String KEY = "uatspdbcccgame2014061800";

	    /**
	     * DESCBC加密
	     *
	     * @param src
	     *            数据源
	     * @param key
	     *            密钥，长度必须是8的倍数
	     * @return 返回加密后的数据
	     * @throws Exception
	     */
	    public String encryptDESCBC(final String src, final String key) throws Exception {

	        // --生成key,同时制定是des还是DESede,两者的key长度要求不同
	        final DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
	        final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	        final SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

	        // --加密向量
	        final IvParameterSpec iv = new IvParameterSpec(IV.getBytes("UTF-8"));

	        // --通过Chipher执行加密得到的是一个byte的数组,Cipher.getInstance("DES")就是采用ECB模式,cipher.init(Cipher.ENCRYPT_MODE,
	        // secretKey)就可以了.
//	        final Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
	        final Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
//	        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
	        cipher.init(Cipher.DECRYPT_MODE, secretKey);
	        final byte[] b = cipher.doFinal(src.getBytes("UTF-8"));

	        // --通过base64,将加密数组转换成字符串
	        final BASE64Encoder encoder = new BASE64Encoder();
	        return encoder.encode(b);
	    }

	    /**
	     * DESCBC解密
	     *
	     * @param src
	     *            数据源
	     * @param key
	     *            密钥，长度必须是8的倍数
	     * @return 返回解密后的原始数据
	     * @throws Exception
	     */
	    public String decryptDESCBC(final String src, final String key) throws Exception {
	        // --通过base64,将字符串转成byte数组
	        final BASE64Decoder decoder = new BASE64Decoder();
	        final byte[] bytesrc = decoder.decodeBuffer(src);

	        // --解密的key
	        final DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
	        final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	        final SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

	        // --向量
	        final IvParameterSpec iv = new IvParameterSpec(IV.getBytes("UTF-8"));

	        // --Chipher对象解密Cipher.getInstance("DES")就是采用ECB模式,cipher.init(Cipher.DECRYPT_MODE,
	        // secretKey)就可以了.
//	        final Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
	        final Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
//	        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
	        cipher.init(Cipher.DECRYPT_MODE, secretKey);
	        final byte[] retByte = cipher.doFinal(bytesrc);

	        return new String(retByte);

	    }

	    // 3DESECB加密,key必须是长度大于等于 3*8 = 24 位哈
	    public String encryptThreeDESECB(final String src, final String key) throws Exception {
	        final DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
	        final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
	        final SecretKey securekey = keyFactory.generateSecret(dks);

	        final Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
	        cipher.init(Cipher.ENCRYPT_MODE, securekey);
	        final byte[] b = cipher.doFinal(src.getBytes());

	        final BASE64Encoder encoder = new BASE64Encoder();
	        return encoder.encode(b).replaceAll("\r", "").replaceAll("\n", "");

	    }

	    // 3DESECB解密,key必须是长度大于等于 3*8 = 24 位哈
	    public String decryptThreeDESECB(final String src, final String key) throws Exception {
	        // --通过base64,将字符串转成byte数组
	        final BASE64Decoder decoder = new BASE64Decoder();
	        final byte[] bytesrc = decoder.decodeBuffer(src);
	        // --解密的key
	        final DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
	        final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
	        final SecretKey securekey = keyFactory.generateSecret(dks);

	        // --Chipher对象解密
	        final Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
	        cipher.init(Cipher.DECRYPT_MODE, securekey);
	        final byte[] retByte = cipher.doFinal(bytesrc);

	        return new String(retByte);
	    }
	    
	    public static void main(String[] args) throws Exception {
	        final String key = "cf410f84904a44cc8a7f48fc4134e8f9";
//	        final String key = "10000000";
	        // 加密流程
	        String telePhone = "我的电话是：15629551180";
	        TripleDES threeDES = new TripleDES();
	        String telePhone_encrypt = "";
	        telePhone_encrypt = threeDES.encryptThreeDESECB(URLEncoder.encode(telePhone, "UTF-8"), key);
//	        telePhone_encrypt = threeDES.encryptDESCBC(URLEncoder.encode(telePhone, "UTF-8"), key);
	        System.out.println("加密后信息:" + telePhone_encrypt);// nWRVeJuoCrs8a+Ajn/3S8g==

	        // 解密流程
	        String tele_decrypt = threeDES.decryptThreeDESECB(telePhone_encrypt, key);
//	        String tele_decrypt = threeDES.decryptDESCBC(telePhone_encrypt, key);
	        System.out.println("解密后信息:" + tele_decrypt);
	    }
}
