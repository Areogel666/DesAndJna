package xxx;

import java.security.MessageDigest;

public class MD5 {

	public String digest(String str) {
		StringBuffer sb = new StringBuffer();
		try {
			MessageDigest md5 = MessageDigest.getInstance("md5");
			md5.update(str.getBytes());
			byte[] array = md5.digest();
			for (int x = 0; x < 16; x++) {
				if ((array[x] & 0xff) < 0x10) {
					sb.append("0");
				}
				sb.append(Long.toString(array[x] & 0xff, 16));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static String pwd(String pwd) {
		String totleStr = "CHINALICHAOLOVEANNI20131117BEIJINGPINGQIANGRUANJIAN";
		pwd = pwd.toUpperCase();
		pwd = pwd.replace('0', '~').replace('1', '$').replace('2', '!').replace('3', '@').replace('4', ':').replace('5', ']').replace('6', '[')
				.replace('7', '{').replace('8', '}').replace('9', '`');
		totleStr = totleStr.substring(0, totleStr.length() - pwd.length());
		pwd = totleStr + pwd;

		StringBuffer sb = new StringBuffer();
		try {
			MessageDigest md5 = MessageDigest.getInstance("md5");
			md5.update(pwd.getBytes());
			byte[] array = md5.digest();
			for (int x = 0; x < 16; x++) {
				if ((array[x] & 0xff) < 0x10) {
					sb.append("0");
				}
				sb.append(Long.toString(array[x] & 0xff, 16));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString().toUpperCase();
	}

	public static void main(String[] args) {
		System.out.println(pwd("123456"));
		System.out.println(new MD5().digest("资产包业务立项流程"));
		// System.out.println(new MD5().digest("不上牌融资租赁流程"));
		// System.out.println(new MD5().digest("多模式项目立项流程"));
		// System.out.println(new MD5().digest("资产包业务立项流程"));
	}

}
