package net.linybin7.common.file;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;


public class ZIPWorker {
	/**
	 * 压缩文件
	 * 
	 * @param inFile
	 * @param outFile
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void compress(File inFile, File outFile) throws IOException {
		OutputStream out = this.compress(new FileOutputStream(outFile));
		DataHandler inhd = new DataHandler(new FileDataSource(inFile));
		inhd.writeTo(out);
		out.close();
	}

	/**
	 * 压缩数据??
	 * 
	 * 
	 * @param out
	 * @return
	 * @throws IOException
	 */
	public OutputStream compress(OutputStream out) {
		try {
			ZipOutputStream zos = new ZipOutputStream(out);
			zos.setLevel(9);
			zos.putNextEntry(new ZipEntry("*"));
			return zos;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 文件解压
	 * 
	 * @param inFile
	 * @param outFile
	 * @throws IOException
	 */
	public void extract(File inFile, File outFile) throws IOException {
		InputStream ins = this.extract(new FileInputStream(inFile));
		OutputStream out = new FileOutputStream(outFile);
		byte[] buf = new byte[1024];
		int len = 0;
		while ((len = ins.read(buf)) != -1) {
			out.write(buf, 0, len);
		}
		out.close();
		ins.close();
	}

	/**
	 * 解压数据??
	 * 
	 * 
	 * @param ins
	 * @return
	 */
	public InputStream extract(InputStream ins) {
		ZipInputStream zis = new ZipInputStream(ins);
		ZipEntry zEntry = null;
		try {
			zEntry = zis.getNextEntry();
			if (zEntry != null) {
				return zis;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public byte[] compress(byte[] bytes, int level) throws IOException {
		ByteArrayOutputStream outBuf = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(outBuf);
		zos.setLevel(level);
		zos.putNextEntry(new ZipEntry("*"));
		zos.write(bytes, 0, bytes.length);
		zos.close();
		return outBuf.toByteArray();
	}

	public byte[] extract(byte[] bytes) throws IOException {
		ByteArrayInputStream inBuf = new ByteArrayInputStream(bytes);
		ByteArrayOutputStream outBuf = new ByteArrayOutputStream();
		ZipInputStream zis = new ZipInputStream(inBuf);

		ZipEntry zEntry = zis.getNextEntry();
		if (zEntry == null) {
			outBuf.write(bytes);
		} else {
			do {
				byte[] buf = new byte[1024];
				int length = 0;
				while ((length = zis.read(buf)) != -1) {
					outBuf.write(buf, 0, length);
				}
			} while ((zEntry = zis.getNextEntry()) != null);
		}
		zis.close();
		return outBuf.toByteArray();
	}

	public static void main(String[] args) throws IOException {
		ZIPWorker zip = new ZIPWorker();
		zip.compress(new File("c:/test1.txt"), new File("c:/out.rar"));
		zip.extract(new File("c:/out.rar"), new File("c:/out.txt"));
	}

	/**
	 * 压缩文件
	 * 
	 * @param files
	 *            List
	 * @param targetFile
	 *            File
	 * @throws Exception
	 */
	public static void compress(List<File> files, File targetFile)
			throws Exception {
		FileOutputStream fos = null;
		FileInputStream fis = null;
		ZipOutputStream zos = null;
		try {
			fos = new FileOutputStream(targetFile);
			zos = new ZipOutputStream(fos);
			for (File file : files) {
				try {
					fis = new FileInputStream(file);
					zos.putNextEntry(new ZipEntry(file.getName()));
					byte[] bytes = new byte[1024];
					int length = 0;
					while ((length = fis.read(bytes)) != -1) {
						zos.write(bytes, 0, length);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (fis != null)
						fis.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("无法压缩文件");
		} finally {
			if (zos != null)
				zos.close();
			if (fos != null)
				fos.close();
		}
	}

	public static List<File> extract(File zfile) throws IOException {
		FileInputStream FIS = null;
		try {
			FIS = new FileInputStream(zfile);
		} catch (FileNotFoundException e) {
			throw e;
		}
		ZipInputStream ZIS = new ZipInputStream(FIS);

		ZipEntry zEntry = null;
		List<File> list = new ArrayList<File>();
		try {
			while ((zEntry = ZIS.getNextEntry()) != null) {
				File file = new File(zfile.getParent(), zEntry.getName());
				FileOutputStream FOSTEMP = new FileOutputStream(file);

				byte[] bytes = new byte[1024];
				int length = 0;
				while ((length = ZIS.read(bytes)) != -1) {
					FOSTEMP.write(bytes, 0, length);
				}
				list.add(file);
				FOSTEMP.close();
			}
			ZIS.close();
			FIS.close();
		} catch (IOException e) {
			throw e;
		}
		return list;
	}
}
