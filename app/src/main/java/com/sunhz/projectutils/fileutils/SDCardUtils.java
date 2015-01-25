package com.sunhz.projectutils.fileutils;

import java.io.File;
import java.io.IOException;

import android.os.Environment;
import android.os.StatFs;

/**
 * Sd卡使用
 * 
 * @author win7
 * 
 */
public class SDCardUtils {

	/**
	 * 返回sd卡的路径
	 * 
	 * @return
	 */
	public String getSDCardPath() {
		return Environment.getExternalStorageDirectory().getPath();
	}

	/**
	 * 检测Sd卡是否存在
	 * 
	 * @return
	 */
	public boolean checkSDCard() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
			return true;
		else
			return false;
	}

	/**
	 * 检查sd卡是否可写
	 * 
	 * @return
	 */
	public boolean isSdCardWrittenable() {

		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}

	/**
	 * 获取sd卡的可用存储大小 剩下的空间
	 * 
	 * @return
	 */
	public long getAvailableStorage() {

		String storageDirectory = null;
		storageDirectory = Environment.getExternalStorageDirectory().toString();
		try {
			StatFs stat = new StatFs(storageDirectory);
			long avaliableSize = ((long) stat.getAvailableBlocks() * (long) stat.getBlockSize());
			return avaliableSize;
		} catch (RuntimeException ex) {
			return 0;
		}
	}

	/**
	 * 判断当前的sd空间是否可保存该文件
	 * 
	 * @param currentFileSize
	 * @return
	 * @throws Exception
	 */
	public boolean isAvailableStorage(long currentFileSize) throws Exception {
		// / 检测sd卡是否存在
		if (!checkSDCard()) {
			throw new Exception("sd卡不存在");
		}
		// 检查sd卡是否可读
		if (!isSdCardWrittenable()) {
			throw new Exception("sd卡不能执行写入操作");
		}
		long avaliableSize = getAvailableStorage();
		if (Float.compare(avaliableSize, currentFileSize) == 1) {
			return true;
		}
		return false;
	}

	/**
	 * 在SD卡上创建文件
	 * 
	 * @param fileName
	 *            要创建的文件名
	 * @return 创建得到的文件
	 */
	public File createSDFile(String fileName) throws IOException {
		File file = new File(new SDCardUtils().getSDCardPath() + File.separator + fileName);
		file.createNewFile();
		return file;
	}

	/**
	 * 在SD卡上创建目录
	 * 
	 * @param dirName
	 *            要创建的目录名
	 * @return 创建得到的目录
	 */
	public File createAbsoluteSDDir(String absoluteDirName) {
		File dir = new File(absoluteDirName);
		dir.mkdir();
		return dir;
	}

}
