package cn.lxr.instance;

/**
* 
* @ClassName: TestJNI 
* @Description: 测试JNI调用so库
* @author Areogel
* @date 2018-04-23 下午02:33:51 
*
*/
public class TestJNI {

	static{
		System.load("/root/psbc/lib11APF_BJ_SZJM.so");
		//加载环境变量时可以用loadLibrary
//		System.loadLibrary("libtrides");
	}
	
	public native static int RunDesFile(String inFileName, String outFileName, int bType, int bMode, String key, int keylen);
	
	public static void main(String[] args) {
		int backCount = TestJNI.RunDesFile("/root/psbc/szpk2018042001.txt", "/root/psbc/create.txt" , 0, 1, "Y@bzdf0L", 8);
		System.out.println("是否加密成功 0 成功，-1 失败。结果："+backCount);
	}
}
