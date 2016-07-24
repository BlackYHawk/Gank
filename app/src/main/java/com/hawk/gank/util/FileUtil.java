package com.hawk.gank.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * 文件操作工具包
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class FileUtil {
	
	/**
     * InputStream to byte
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        byte[] buffer = new byte[1024]; 
        int len = -1; 
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        
        while ((len = inStream.read(buffer)) != -1) { 
        	outStream.write(buffer, 0, len); 
        }
        
        byte[] data = outStream.toByteArray(); 
        outStream.close();
        inStream.close();
        
        return data; 
   }

    /**
     * InputStream to byte
     * @param inStream
     * @return String
     */
    public static String readInStream(InputStream inStream) {
        try {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[512];
            int length = -1;
            while ((length = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, length);
            }
            outStream.close();
            inStream.close();
            return outStream.toString();
        } catch (IOException e) {
        }
        return null;
    }
    
   /**
    * Byte to bitmap
    * @param bytes
    * @param opts
    * @return
    */
   public static Bitmap getBitmapFromBytes(byte[] bytes, BitmapFactory.Options opts) {
	   if (bytes != null){
		   if (opts != null){ 
			   return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
		   }
           else{
        	   return BitmapFactory.decodeByteArray(bytes, 0, bytes.length); 
           }
	   }
       return null; 
   } 
   
    /**
	 * 写文本文件 在Android系统中，文件保存在 /data/data/PACKAGE_NAME/files 目录下
	 * 
	 * @param context
     * @param fileName
	 * @param content
	 */
	public static void write(Context context, String fileName, String content) {
		if (content == null)
			content = "";
		try {
			FileOutputStream fos = context.openFileOutput(fileName,
					Context.MODE_PRIVATE);
			fos.write(content.getBytes());

			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取文本文件
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static String read(Context context, String fileName) {
		try {
			FileInputStream in = context.openFileInput(fileName);
			return readInStream(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 向文件写数据
	 * 
	 * @param buffer
	 * @param directoryName
	 * @param fileName
	 * @return
	 */
	public static boolean writeFile(byte[] buffer, String directoryName,
			String fileName) {
		boolean writeSucc = false;

        if (!directoryName.equalsIgnoreCase("") && !fileName.equalsIgnoreCase("")) {
            if(checkDirectoryExists(directoryName)) {    //检查目录是否存在
                File file = new File(getSDRoot() + File.separator + directoryName, fileName);
                FileOutputStream out = null;
                try {
                    out = new FileOutputStream(file);
                    out.write(buffer);
                    writeSucc = true;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

		return writeSucc;
	}

    /**
     * 截取路径名
     *
     * @param absolutePath
     * @return
     */
    public static String getFileName(String absolutePath) {
        int start = absolutePath.lastIndexOf(File.separator) + 1;
        int end = absolutePath.length();
        return absolutePath.substring(start, end);
    }

	/**
	 * 根据文件的绝对路径获取文件名但不包含扩展名
	 *
	 * @param filePath
	 * @return
	 */
	public static String getFileNameNoFormat(String filePath) {
		if (StringUtil.isEmpty(filePath)) {
			return "";
		}
		int point = filePath.lastIndexOf('.');
		return filePath.substring(filePath.lastIndexOf(File.separator) + 1,
				point);
	}

	/**
	 * 获取文件扩展名
	 *
	 * @param fileName
	 * @return
	 */
	public static String getFileFormat(String fileName) {
		if (StringUtil.isEmpty(fileName))
			return "";

		int point = fileName.lastIndexOf('.');
		return fileName.substring(point + 1);
	}

	/**
	 * 获取文件大小
	 * 
	 * @param filePath
	 * @return
	 */
	public static long getFileSize(String filePath) {
		long size = 0;

		File file = new File(filePath);
		if (file != null && file.exists()) {
			size = file.length();
		}
		return size;
	}

    /**
     * 获取目录文件大小
     *
     * @param dir
     * @return
     */
    public static long getDirSize(File dir) {
        if (dir == null) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                dirSize += file.length();
            } else if (file.isDirectory()) {
                dirSize += file.length();
                dirSize += getDirSize(file); // 递归调用继续统计
            }
        }
        return dirSize;
    }

    /**
     * 获取文件大小
     *
     * @param size
     *            字节
     * @return
     */
    public static String getFileSize(long size) {
        if (size <= 0)
            return "0";
        java.text.DecimalFormat df = new java.text.DecimalFormat("##.##");
        float temp = (float) size / 1024;
        if (temp >= 1024) {
            return df.format(temp / 1024) + "M";
        } else {
            return df.format(temp) + "K";
        }
    }

	/**
	 * 转换文件大小
	 * 
	 * @param fileS
	 * @return B/KB/MB/GB
	 */
	public static String formatFileSize(long fileS) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "KB";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "MB";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

    /**
     * 检查是否安装SD卡
     *
     * @return
     */
    public static boolean checkSaveLocationExists() {
        String sDCardStatus = Environment.getExternalStorageState();
        boolean status;
        if (sDCardStatus.equals(Environment.MEDIA_MOUNTED)) {
            status = true;
        } else
            status = false;
        return status;
    }

    /**
     * 检查是否安装外置的SD卡
     *
     * @return
     */
    public static boolean checkExternalSDExists() {

        Map<String, String> evn = System.getenv();
        return evn.containsKey("SECONDARY_STORAGE");
    }

    /**
     * 计算SD卡的剩余空间
     *
     * @return 返回-1，说明没有安装sd卡
     */
    public static long getFreeDiskSpace() {
        String status = Environment.getExternalStorageState();
        long freeSpace = 0;
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            try {
                File path = Environment.getExternalStorageDirectory();
                StatFs stat = new StatFs(path.getPath());
                long blockSize = stat.getBlockSize();
                long availableBlocks = stat.getAvailableBlocks();
                freeSpace = availableBlocks * blockSize / 1024;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return -1;
        }
        return (freeSpace);
    }

    /**
     * 获取SD卡的根目录
     *
     * @return
     */
    public static String getSDRoot() {

        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 获取手机外置SD卡的根目录
     *
     * @return
     */
    public static String getExternalSDRoot() {

        Map<String, String> evn = System.getenv();

        return evn.get("SECONDARY_STORAGE");
    }

	/**
	 * 新建目录
	 * 
	 * @param directoryName
	 * @return
	 */
	public static boolean createDirectory(String directoryName) {
		boolean status = false;
		if (!directoryName.equalsIgnoreCase("")) {
            if(checkSaveLocationExists()) {    //检查SD是否存在
                File dir = new File(getSDRoot() + File.separator + directoryName);
                status = dir.mkdir();
                status = true;
            }
		}
		return status;
	}

    /**
     * 检查目录是否存在
     *
     * @param directoryName
     * @return
     */
    public static boolean checkDirectoryExists(String directoryName) {
        boolean status = false;
        if (!directoryName.equalsIgnoreCase("")) {
            if(checkSaveLocationExists()) {    //检查SD是否存在
                File dir = new File(getSDRoot() + File.separator + directoryName);
                status = (dir.exists() && dir.isDirectory());
            }
        }
        return status;
    }

    /**
     * 获取目录中文件个数
     *
     * @param directoryName
     * @return
     */
    public long getDirectoryCount(String directoryName) {
        long count = 0;
        if(checkDirectoryExists(directoryName)) {    //检查SD是否存在
            File dir = new File(getSDRoot() + File.separator + directoryName);
            File[] files = dir.listFiles();
            count = files.length;
            for (File file : files) {
                if (file.isDirectory()) {
                    count = count + getDirectoryCount(file.getName());// 递归
                    count--;
                }
            }
        }
        return count;
    }

    /**
     * 删除目录(包括：目录里的所有文件)
     *
     * @param directoryName
     * @return
     */
    public static boolean deleteDirectory(String directoryName) {
        boolean status = false;
        SecurityManager checker = new SecurityManager();

        if (!directoryName.equalsIgnoreCase("")) {
            if (checkSaveLocationExists()) {    //检查SD是否存在
                File dir = new File(getSDRoot() + File.separator + directoryName);
                checker.checkDelete(dir.toString());
                if (dir.exists() && dir.isDirectory()) {   //如果是目录
                    String[] listFileName = dir.list();   //目录内所有文件
                    try {
                        for (int i = 0; i < listFileName.length; i++) {
                            File deletedFile = new File(dir.getAbsolutePath() + File.separator
                                    + listFileName[i].toString());
                            deletedFile.delete();
                        }
                        dir.delete();
                        status = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        status = false;
                    }
                }
            }
        }
        return status;
    }

    /**
     * 删除空目录
     *
     * @param directoryName
     * 返回 0代表成功 ,1 代表没有删除权限, 2代表不是空目录,3 代表未知错误
     *
     * @return
     */
    public static int deleteBlankDriectory(String directoryName) {
        int status = 3;
        SecurityManager checker = new SecurityManager();
        if (!directoryName.equalsIgnoreCase("")) {
            if (checkSaveLocationExists()) {    //检查SD是否存在
                File dir = new File(getSDRoot() + File.separator + directoryName);
                checker.checkDelete(dir.toString());
                if (dir.exists() && dir.isDirectory()) {   //如果是目录
                    if (!dir.canWrite()) {
                        status = 1;
                    } else if (dir.list() != null && dir.list().length > 0) {
                        status = 2;
                    } else if (dir.delete()) {
                        status = 0;
                    }
                }
            }
        }
        return status;
    }

    /**
     * 清空目录(包括：目录里的所有文件)
     *
     * @param directoryName
     * @return
     */
    public static boolean clearDirectory(String directoryName) {
        boolean status = false;
        SecurityManager checker = new SecurityManager();

        if (!directoryName.equalsIgnoreCase("")) {
            if (checkSaveLocationExists()) {    //检查SD是否存在
                File dir = new File(getSDRoot() + File.separator + directoryName);
                checker.checkDelete(dir.toString());
                if (dir.exists() && dir.isDirectory()) {   //如果是目录
                    String[] listFileName = dir.list();   //目录内所有文件
                    try {
                        for (int i = 0; i < listFileName.length; i++) {
                            File deletedFile = new File(dir.getAbsolutePath() + File.separator
                                    + listFileName[i].toString());
                            deletedFile.delete();
                        }
                        status = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        status = false;
                    }
                }
            }
        }
        return status;
    }

    /**
     * 新建文件
     *
     * @param directoryName
     * @param fileName
     * @return
     */
    public static File createFile(String directoryName, String fileName) {
        if (!directoryName.equalsIgnoreCase("") && !fileName.equalsIgnoreCase("")) {
            if(!checkDirectoryExists(directoryName)) {    //检查目录是否存在
                createDirectory(directoryName);
            }
            File file = new File(getSDRoot() + File.separator + directoryName, fileName);
            return file;
        }
        return null;
    }

    /**
     * 检查文件是否存在
     *
     * @param directoryName
     * @param fileName
     * @return
     */
    public static boolean checkFileExists(String directoryName, String fileName) {
        boolean status = false;
        if (!directoryName.equalsIgnoreCase("") && !fileName.equalsIgnoreCase("")) {
            if(checkDirectoryExists(directoryName)) {   //检查目录是否存在
                File file = new File(getSDRoot() + File.separator + directoryName, fileName);
                status = file.exists();
            }
        }
        return status;
    }

    /**
     * 检查路径是否存在
     *
     * @param path
     * @return
     */
    public static boolean checkFilePathExists(String path) {
        return new File(path).exists();
    }

    /**
     * 获取文件
     *
     * @param filePath
     * @return
     */
    public static File getFile(String filePath) {
        if (!filePath.equalsIgnoreCase("")) {
            File file = new File(filePath);
            return file;
        }
        return null;
    }

    /**
     * 获取文件
     *
     * @param directoryName
     * @param fileName
     * @return
     */
    public static File getFile(String directoryName, String fileName) {
        if (!directoryName.equalsIgnoreCase("") && !fileName.equalsIgnoreCase("")) {
            if(checkDirectoryExists(directoryName)) {   //检查目录是否存在
                File file = new File(getSDRoot() + File.separator + directoryName, fileName);
                return file;
            }
        }
        return null;
    }

	/**
	 * 删除文件
	 *
     * @param directoryName
	 * @param fileName
	 * @return
	 */
    public static boolean deleteFile(String directoryName, String fileName) {
        boolean status = false;
        SecurityManager checker = new SecurityManager();
        if (!directoryName.equalsIgnoreCase("") && !fileName.equalsIgnoreCase("")) {
            if (checkDirectoryExists(directoryName)) {
                File file = new File(getSDRoot() + File.separator + directoryName, fileName);
                checker.checkDelete(file.toString());
                if (file.isFile()) {
                    try {
                        file.delete();
                        status = true;
                    } catch (SecurityException se) {
                        se.printStackTrace();
                        status = false;
                    }
                }
            }
        }
        return status;
    }

	/**
	 * 重命名文件
	 * 
	 * @param oldFileName
	 * @param newFileName
	 * @return
	 */
	public static boolean renameFile(String directoryName, String oldFileName, String newFileName) {
        boolean status = false;
        if (!directoryName.equalsIgnoreCase("") && !oldFileName.equalsIgnoreCase("") &&
                !newFileName.equalsIgnoreCase("")) {
            if (checkDirectoryExists(directoryName)) {   //检查目录是否存在
                File oldFile = new File(getSDRoot() + File.separator + directoryName, oldFileName);
                File newFile = new File(getSDRoot() + File.separator + directoryName, newFileName);
                status = oldFile.renameTo(newFile);
            }
        }
        return status;
    }

	/**
	 * 获取应用程序缓存文件夹下的指定目录
	 * @param context
	 * @param dir
	 * @return
	 */
	public static String getAppCache(Context context, String dir) {
		String savePath = context.getCacheDir().getAbsolutePath() + "/" + dir + "/";
		File savedir = new File(savePath);
		if (!savedir.exists()) {
			savedir.mkdirs();
		}
		savedir = null;
		return savePath;
	}
}