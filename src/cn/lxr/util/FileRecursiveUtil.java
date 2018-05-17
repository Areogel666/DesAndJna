package cn.lxr.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件（夹）递归操作工具类
 */
public class FileRecursiveUtil {
	
	public static void main(String[] args) {
		List<String> files = searchFiles(new File("D:/psbc_file/response/des/"), new ArrayList<String>());
		for (String string : files) {
			System.out.println(string);
		}
	}

	/**
     * 
    * 遍历路径下的所有文件
    * <p>返回所有非文件夹的文件路径,无文件则返回null</p>
    * @param dir 遍历文件夹路径
    * @param fileList new ArrayList();
    * @return List<String>
     */
    public static List<String> searchFiles(File dir, List<String> fileList){
    	if (dir.exists() && dir.isDirectory()) {
            String[] children = dir.list();
            //递归查询目录中的所有文件
            for (int i=0; i<children.length; i++) {
            	File childFile = new File(dir, children[i]);
            	if(childFile.isDirectory()){//如果还有子目录则递归查询
            		searchFiles(childFile, fileList);
            	}else{//如果是文件则添加到集合
            		System.out.println("children--------"+children[i]);
            		System.out.println("getPath--------"+childFile.getPath());
            		System.out.println("getAbsolutePath--------"+childFile.getAbsolutePath());
            		fileList.add(childFile.getPath());
            	}
            }
        }else{//如果路径不存在或者路径不是文件夹
        	try {
				throw new Exception("Can't find path : " + dir.getPath() + ", or dir is not derectory");
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    	if(fileList.size() == 0){
    		return null;
    	}
    	return fileList;
    }
    
    /** 
	 * 
	 * 根据路径删除文件
	 * @return void
	 */ 
	private void deleteFileByPath(String pathName) {
		File file = new File(pathName);
		if (file.exists() && file.isFile()) {
			file.delete();
		}
	}
	
    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    private boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
}
