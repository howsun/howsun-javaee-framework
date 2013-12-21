/**
* ========================================================
* 日 期：2011-10-11 下午02:51:51
* 作 者：张纪豪
* 版 本：1.0.0
* ========================================================
* 修订日期                        修订人                     描述
*
*/

package org.howsun.util;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * 功能描述：
 * 
 * @author howsun(howsun.zhang@google.com)
 * @version 1.0.0
 */
public abstract class Images{

	/**
	 * 验证是否为图片：通过Java图像API验证。
	 * 弱点：
	 * 1、效率较低，
	 * 2、尚不清楚能识别几种模式(例如RGB、CMYK、BitMap....)
	 * @param imageInputStream
	 * @return
	 */
	public static boolean checkImageTypeVailable(InputStream imageInputStream) {
		try {
			BufferedImage bufferedImage = ImageIO.read(imageInputStream);
			int imageType = bufferedImage.getType();
			return imageType > -1;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 验证是否为图片
	 * 淘宝团队的做法，据说可以勉强识别90%以上的图片
	 * @param imgContent
	 * @return
	 */
	public static boolean checkImageTypeVailable(byte[] imgContent) {
		try {
			int len = imgContent.length;
			byte n1 = imgContent[len - 2];
			byte n2 = imgContent[len - 1];
			byte b0 = imgContent[0];
			byte b1 = imgContent[1];
			byte b2 = imgContent[2];
			byte b3 = imgContent[3];
			byte b4 = imgContent[4];
			byte b5 = imgContent[5];
			byte b6 = imgContent[6];
			byte b7 = imgContent[7];
			byte b8 = imgContent[8];
			byte b9 = imgContent[9];
			
			//GIF(G I F 8 7 a)
			if (b0 == (byte)'G' && b1 == (byte)'I' && b2 == (byte)'F' && b3 == (byte)'8' && b4 == (byte)'7' && b5 == (byte)'a') {
				return true;
			//GIF(G I F 8 9 a)
			} else if (b0 == (byte)'G' && b1 == (byte)'I' && b2 == (byte)'F' && b3 == (byte)'8' && b4 == (byte)'9' && b5 == (byte)'a') {
				return true;
			//PNG(89 P N G 0D 0A 1A)
			}else if (b0 == -119 && b1 == (byte)'P' && b2 == (byte)'N' && b3 == (byte)'G' && b4 == 13 && b5 == 10 && b6 == 26) {
				return true;
			//JPG JPEG(FF D8 --- FF D9)
			} else if (b0 == -1 && b1 == -40 && n1 == -1 && n2 == -39){
				return true;
			} else if (b6 == (byte)'J' && b7 == (byte)'F' && b8 == (byte)'I' && b9 == (byte)'F'){
				return true;
			} else if (b6 == (byte)'E' && b7 == (byte)'x' && b8 == (byte)'i' && b9 == (byte)'f'){
				return true;
			//BMP(B M)
			} else if (b0 == (byte)'B' && b1 == (byte)'M') {
				return true;
			}else {
				return false;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		} catch (Exception e) {
			return false;
		}
	}
}
