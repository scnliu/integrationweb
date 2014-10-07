package net.linybin7.common.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;


/**
 * IO工具类
 * @author JackenCai
 *
 */
public final class IOUtil {
	public static final int BUFFER_SIZE = 2048;
	
	private IOUtil(){
		
	}
	
	/**
	 * 关闭流
	 * @param is
	 */
	public static void close(InputStream is){
		try {
			if(is != null){
				is.close();
			}
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * 关闭流
	 * @param os
	 */
	public static void close(OutputStream os){
		try {
			if(os != null){
				os.close();
			}
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * 关闭流
	 * @param reader
	 */
	public static void close(Reader reader){
		try {
			if(reader != null){
				reader.close();
			}
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * 关闭流
	 * @param writer
	 */
	public static void close(Writer writer){
		try {
			if(writer != null){
				writer.close();
			}
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * 复制
	 * @param in
	 * @param out
	 * @return
	 */
	public static int copy(InputStream in, OutputStream out){
		try {
			int total = 0;
			int readCount = -1;
			byte[] buffer = new byte[BUFFER_SIZE];
			while((readCount = in.read(buffer)) != -1){
				out.write(buffer, 0, readCount);
				total += readCount;
			}
			out.flush();
			return total;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtil.close(in);
			IOUtil.close(out);
		}
		return 0;
	}
	
	/**
	 * 复制
	 * @param in
	 * @param out
	 * @return
	 */
	public static int copy(byte[] in, OutputStream out){
		try {
			out.write(in);
			out.flush();
			return in.length;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtil.close(out);
		}
		return 0;
	}
	
	/**
	 * 复制
	 * @param in
	 * @param out
	 * @return
	 */
	public static int copy(String in, OutputStream out){
		try {
			byte[] content = in.getBytes();
			out.write(content);
			out.flush();
			return content.length;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtil.close(out);
		}
		return 0;
	}
	
	/**
	 * 复制
	 * @param in
	 * @param out
	 * @return
	 */
	public static int copy(Reader in, Writer out){
		try {
			int total = 0;
			int readCount = -1;
			char[] buffer = new char[BUFFER_SIZE];
			while((readCount = in.read(buffer)) != -1){
				out.write(buffer, 0, readCount);
				total += readCount;
			}
			out.flush();
			return total;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtil.close(in);
			IOUtil.close(out);
		}
		return 0;
	}
	
	/**
	 * 复制
	 * @param in
	 * @param out
	 * @return
	 */
	public static int copy(String in, Writer out){
		try {
			out.write(in);
			out.flush();
			return in.length();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtil.close(out);
		}
		return 0;
	}
}
