package com.xuwab.qiushitest.util;

import android.content.Context;
import android.os.Environment;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SdCardHelper {
	private Context context;

	public SdCardHelper(Context context) {
		this.context = context;
	}

	/**
	 * 判断SdCard是否处于挂在状态
	 */
	public boolean isExternalStorageMounted() {
		return (Environment.getExternalStorageState()
				.equals(Environment.MEDIA_MOUNTED));
	}

	/**
	 * 获取SdCard的根路径 /mnt/sdcard/
	 *
	 * @return
	 */
	public String getExternalStoragePath() {
		if (isExternalStorageMounted()) {
			return Environment.getExternalStorageDirectory().getAbsolutePath();  ///mnt/sdcard
		}
		return null;
	}

	/**
	 * 保存一个byte[]类型的数据到Sdcard的跟路径下
	 */
	public boolean saveByteToSdCard(String fileName, byte[] data) {
		FileOutputStream fos = null;
		// 1. 判断媒体设备是否挂在上
		if (isExternalStorageMounted()) {
			// 2. 获取sdCard的根路径
			String sdCard_rootPath = getExternalStoragePath();
			// 4. 创建一个文件
			File file = new File(sdCard_rootPath, fileName);
			try {
				fos = new FileOutputStream(file);
				fos.write(data);
				fos.flush();
				return true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return false;
	}

	/**
	 * 保存数据到SdCard的共有目录下
	 *
	 * @param type
	 * @param fileName
	 * @param data
	 * @return
	 */
	public boolean saveByteToSdCardPublicDir(String type, String fileName,
											 byte[] data) {
		FileOutputStream fos = null;
		// 1. 判断SdCard是否存在
		if (isExternalStorageMounted()) {
			// 2. 获取SdCard的共有目录
			File public_dir = Environment
					.getExternalStoragePublicDirectory(type);

			// 3. 在SdCard的共有目录下创建一个文件
			File file = new File(public_dir, fileName);
			try {
				fos = new FileOutputStream(file);
				fos.write(data);
				fos.flush();
				return true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return false;
	}

	/**
	 * 保存数据到SdCard的缓存目录下
	 *
	 * @param fileName
	 * @param data
	 * @return
	 * 通过Context.getExternalCacheDir()方法可以获取到 SDCard/Android/data/你的应用包名/cache/目录，
	 * 一般存放临时缓存数据
	 */
	public boolean saveByteToSdCardCacheDir(String fileName, byte[] data) {
		FileOutputStream fos = null;
		if (isExternalStorageMounted()) {
			File cache_dir = context.getExternalCacheDir();
//			Log.i(TAG,"getExternalCacheDir():"+cache_dir.toString());
			File file = new File(cache_dir, fileName);
			try {
				fos = new FileOutputStream(file);
				fos.write(data);
				fos.flush();
				return true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return false;
	}

	/**
	 * 保存数据到SdCard的私有目录下
	 *
	 * @param fileName
	 * @param data
	 * @return
	 * 通过Context.getExternalFilesDir()方法可以获取到 SDCard/Android/data/你的应用的包名/files/ 目录，
	 * 一般放一些长时间保存的数据
	 */
	public boolean saveByteToSdCardFileDir(String type, String fileName,
										   byte[] data) {
		FileOutputStream fos = null;
		if (isExternalStorageMounted()) {
			File file_dir = context.getExternalFilesDir(type);
			File file = new File(file_dir, fileName);
			try {
				fos = new FileOutputStream(file);
				fos.write(data);
				fos.flush();
				return true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return false;
	}

	/**
	 * 保存数据到SdCard的自定义目录下
	 *
	 * @param fileName
	 * @param data
	 * @return
	 */
	public boolean saveByteToSdCardCustomDir(String dir, String fileName,
											 byte[] data) {
		FileOutputStream fos = null;
		if (isExternalStorageMounted()) {
			File sdcard_rootDir = Environment.getExternalStorageDirectory();

			// 在根目录下创建一个目录
			File fileDir = new File(sdcard_rootDir, dir);
			if (!fileDir.exists()) {
				// 如果这个目录不存在,则创建
				fileDir.mkdirs();
			}

			File file = new File(fileDir, fileName);

			try {
				fos = new FileOutputStream(file);
				fos.write(data);
				fos.flush();
				return true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return false;
	}

	/**
	 * 读取SdCard某个目录下的文件数据,以byte[]类型返回
	 *
	 * @param filePath
	 * @return
	 */
	public byte[] readFromSdCard(String filePath) {
		FileInputStream fis = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		// 1. 创建一个文件
		File file = new File(filePath);

		// 2. 获取文件的输入流
		try {
			fis = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			int line = 0;
			while ((line = fis.read(buffer)) != -1) {
				bos.write(buffer, 0, line);
				bos.flush();
			}
			return bos.toByteArray();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
