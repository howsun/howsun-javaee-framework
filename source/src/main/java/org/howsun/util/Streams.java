/**
 * ========================================================
 * 北京五八信息技术有限公司技术中心开发一部
 * 日 期：2011-8-24 上午10:03:45
 * 作 者：张纪豪
 * 版 本：1.0.0
 * ========================================================
 * 修订日期                        修订人                     描述
 *
 */

package org.howsun.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：流工具类
 *
 * @author howsun(howsun.zhang@google.com)
 * @version 1.0.0
 */
public final class Streams {

	/**
	 * Private constructor, to prevent instantiation. This class has only static
	 * methods.
	 */
	private Streams() {
		// Does nothing
	}

	/**
	 * Default buffer size for use in
	 * {@link #copy(InputStream, OutputStream, boolean)}.
	 */
	private static final int DEFAULT_BUFFER_SIZE = 8192;

	/**
	 * Copies the contents of the given {@link InputStream} to the given
	 * {@link OutputStream}. Shortcut for
	 *
	 * <pre>
	 * copy(pInputStream, pOutputStream, new byte[8192]);
	 * </pre>
	 *
	 * @param pInputStream
	 *            The input stream, which is being read. It is guaranteed, that
	 *            {@link InputStream#close()} is called on the stream.
	 * @param pOutputStream
	 *            The output stream, to which data should be written. May be
	 *            null, in which case the input streams contents are simply
	 *            discarded.
	 * @param pClose
	 *            True guarantees, that {@link OutputStream#close()} is called
	 *            on the stream. False indicates, that only
	 *            {@link OutputStream#flush()} should be called finally.
	 *
	 * @return Number of bytes, which have been copied.
	 * @throws IOException
	 *             An I/O error occurred.
	 */
	public static long copy(InputStream pInputStream, OutputStream pOutputStream, boolean pClose) throws IOException {
		return copy(pInputStream, pOutputStream, pClose, new byte[DEFAULT_BUFFER_SIZE]);
	}

	/**
	 * 将InputStream流中的内容复制到OutputStream中
	 *
	 * @param pIn
	 *            The input stream, which is being read. It is guaranteed, that
	 *            {@link InputStream#close()} is called on the stream.
	 * @param pOut
	 *            The output stream, to which data should be written. May be
	 *            null, in which case the input streams contents are simply
	 *            discarded.
	 * @param pClose
	 *            True guarantees, that {@link OutputStream#close()} is called
	 *            on the stream. False indicates, that only
	 *            {@link OutputStream#flush()} should be called finally.
	 * @param pBuffer
	 *            Temporary buffer, which is to be used for copying data.
	 * @return Number of bytes, which have been copied.
	 * @throws IOException
	 *             An I/O error occurred.
	 */
	public static long copy(InputStream pIn, OutputStream pOut, boolean pClose,	byte[] pBuffer) throws IOException {
		OutputStream out = pOut;
		InputStream in = pIn;
		try {
			long total = 0;
			for (;;) {
				int res = in.read(pBuffer);
				if (res == -1) {
					break;
				}
				if (res > 0) {
					total += res;
					if (out != null) {
						out.write(pBuffer, 0, res);
					}
				}
			}
			if (out != null) {
				if (pClose) {
					out.close();
				} else {
					out.flush();
				}
				out = null;
			}
			in.close();
			in = null;
			return total;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Throwable t) {
					/* Ignore me */
				}
			}
			if (pClose && out != null) {
				try {
					out.close();
				} catch (Throwable t) {
					/* Ignore me */
				}
			}
		}
	}

	/**
	 * This convenience method allows to read a
	 * {@link org.apache.commons.fileupload.FileItemStream}'s content into a
	 * string. The platform's default character encoding is used for converting
	 * bytes into characters.
	 *
	 * @param pStream
	 *            The input stream to read.
	 * @see #asString(InputStream, String)
	 * @return The streams contents, as a string.
	 * @throws IOException
	 *             An I/O error occurred.
	 */
	public static String asString(InputStream pStream) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		copy(pStream, baos, true);
		return baos.toString();
	}

	/**
	 * This convenience method allows to read a
	 * {@link org.apache.commons.fileupload.FileItemStream}'s content into a
	 * string, using the given character encoding.
	 *
	 * @param pStream
	 *            The input stream to read.
	 * @param pEncoding
	 *            The character encoding, typically "UTF-8".
	 * @see #asString(InputStream)
	 * @return The streams contents, as a string.
	 * @throws IOException
	 *             An I/O error occurred.
	 */
	public static String asString(InputStream pStream, String pEncoding) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		copy(pStream, baos, true);
		return baos.toString(pEncoding);
	}

	/**
	 * 通过NIO将文本写入文件中
	 * @param sharSequence
	 * @param targetFile
	 */
	public static void writingCharSequence(String sharSequence, File targetFile){
		FileOutputStream fileOutputStream = null;
		try {
			if(!targetFile.exists()){
				if(!targetFile.getParentFile().exists()){
					targetFile.getParentFile().mkdirs();
				}
				targetFile.createNewFile();
			}
			byte[] b = sharSequence.getBytes("UTF-8");
			fileOutputStream = new FileOutputStream(targetFile);
			fileOutputStream.write(b);
			fileOutputStream.flush();
		}
		catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(fileOutputStream != null){
				try {
					fileOutputStream.close();
				}
				catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
	}


	/**
	 * 通过NIO读取文本文件
	 * @param targetFile
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static String readingTextFileChannel(File targetFile) throws FileNotFoundException, IOException {
		FileInputStream fis = null;
		String result = null;
		try {
			fis = new FileInputStream(targetFile);
			result = asString(fis);
		}
		catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {fis.close();}catch (Exception e2) {e2.printStackTrace();}
		}
		return result;
	}

	/**
	 * 通过NIO将文本写入文件中
	 * @param sharSequence
	 * @param targetFile
	 */
	public static void writingTextFileChannel(String sharSequence, File targetFile){
		FileOutputStream fileOutputStream = null;
		FileChannel fileChannel = null;
		try {
			if(!targetFile.exists()){
				if(!targetFile.getParentFile().exists()){
					targetFile.getParentFile().mkdirs();
				}
				targetFile.createNewFile();
			}
			byte[] b = sharSequence.getBytes("UTF-8");
			fileOutputStream = new FileOutputStream(targetFile);
			fileChannel = fileOutputStream.getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate(b.length);
			byteBuffer.put(b);
			byteBuffer.flip();
			fileChannel.write(byteBuffer);
		}
		catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(fileChannel != null){
				try {
					fileChannel.close();
				}
				catch (Exception e2) {
					// TODO: handle exception
				}
			}
			if(fileOutputStream != null){
				try {
					fileOutputStream.close();
				}
				catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
	}


	/**
	 * 通过NIO读取文本文件
	 * @param targetFile
	 * @return
	 */
	public static String readingCharSequence(File targetFile){
		FileInputStream fileInputStream = null;
		FileChannel fileChannel = null;
		String result = null;
		try {
			fileInputStream = new FileInputStream(targetFile);
			fileChannel = fileInputStream.getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
			fileChannel.read(byteBuffer);
			byteBuffer.flip();
			byte[] b = new byte[byteBuffer.remaining()];
			byteBuffer.get(b);
			result = new String(b);
		}
		catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(fileChannel != null){
				try {
					fileChannel.close();
				}
				catch (Exception e2) {
					// TODO: handle exception
				}
			}
			if(fileInputStream != null){
				try {
					fileInputStream.close();
				}
				catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		return result;
	}

	/**
	 * 从文本文件中读取行
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static List<String> readingLineFormTextFile(File file, String encode) throws IOException{
		return readingLineFormTextFile(new FileInputStream(file), encode);
	}

	public static List<String> readingLineFormTextFile(InputStream in, String encode) throws IOException{
		List<String> lines = new ArrayList<String>();
		String text = asString(in, encode);
		StringReader sr = new StringReader(text);
		BufferedReader br = new BufferedReader(sr);
		String s;
		while(( s = br.readLine()) != null){
			lines.add(s);
		}
		return lines;
	}
	public static void main(String[] args) {
		File file = new File("e:\\zjh.txt");
		//writingCharSequence("张纪豪", file);
		System.out.println(readingCharSequence(file));
	}
}
