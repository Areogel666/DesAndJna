package cn.lxr.instance;


import cn.lxr.util.DesJnaLibrary;
/**
* 
* @ClassName: TestJNA 
* @Description: 测试JNA调用so库
* @author Areogel
* @date 2018-04-18 下午05:50:58 
*
*/
public class TestJNA {
//	static{
//		System.loadLibrary("libtrides.so");
//	}
	
	public static void main(String[] args) {
//		String filePath = DesJnaLibrary.class.getResource("").getPath().replaceAll("%20"," ")+"lib11APF_BJSZ.so";
		///usr/java/packages/lib/amd64:/usr/lib64:/lib64:/lib:/usr/lib
//		System.out.println(System.getProperty("java.library.path"));
//		System.setProperty("jna.library.path", "/root/psbc");
		//加密
		DesJnaLibrary.instance.RunDesFile("/root/psbc/szpk2018042001.txt", "/root/psbc/create.txt" , 0, 1, "Y@bzdf0L", 8);
		//解密
		DesJnaLibrary.instance.RunDesFile("/root/psbc/create.txt", "/root/psbc/decode.txt" , 1, 1, "Y@bzdf0L", 8);
	}
}
