package xxx;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.springframework.util.FileCopyUtils;

import net.sf.json.JSONObject;

public class DES {

    // 测试
    public static void main(String args[]) throws IOException {
        JSONObject json = new JSONObject();
        json.put("company", "北京xx软件有限公司");
        json.put("vld", "2017-01-01");
        // 待加密内容
        String str = json.toString();
        // 密码，长度要是8的倍数
        String password = "-pqsoft-";

        String result = Base64.encrypt(DES.encrypt(str.getBytes(), password));
        FileCopyUtils.copy(result.getBytes(), new File("C:/Users/cc/Desktop/" + json.get("company") + ".lic"));
        System.out.println("加密后：" + result);
        result = new String(
                FileCopyUtils.copyToByteArray(new File("C:/Users/cc/Desktop/" + json.get("company") + ".lic")));

        // 直接将如上内容解密
        try {
            byte[] decryResult = DES.decrypt(Base64.decrypt(result), password);
            System.out.println("解密后：" + new String(decryResult));
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }

    /**
     * 加密
     * 
     * @param datasource
     *            byte[]
     * @param password
     *            String
     * @return byte[]
     */
    public static byte[] encrypt(byte[] datasource, String password) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            // 现在，获取数据并加密
            // 正式执行加密操作
            return cipher.doFinal(datasource);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     * 
     * @param src
     *            byte[]
     * @param password
     *            String
     * @return byte[]
     * @throws Exception
     */
    public static byte[] decrypt(byte[] src, String password) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom random = new SecureRandom();
        // 创建一个DESKeySpec对象
        DESKeySpec desKey = new DESKeySpec(password.getBytes());
        // 创建一个密匙工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        // 将DESKeySpec对象转换成SecretKey对象
        SecretKey securekey = keyFactory.generateSecret(desKey);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, random);
        // 真正开始解密操作
        return cipher.doFinal(src);
    }
}

