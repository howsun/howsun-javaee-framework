/**
 * ========================================================
 * 日 期：2011-10-10 上午10:14:39
 * 作 者：张纪豪
 * 版 本：1.0.0
 * ========================================================
 * 修订日期                        修订人                     描述
 *
 */

package org.howsun.util;

import java.util.Date;
import java.util.Random;

/**
 * 功能描述：
 * 
 * @author howsun(howsun.zhang@google.com)
 * @version 1.0.0
 */
public class DatesFormatTest{

	public static String[] owner = {"今天","周日","周六","周五","周四"};

	public static void main(String[] args) throws Exception {
		new Thread(new Runnable(){
			@Override
			public void run() {
				for(int i = 0; i < owner.length; i++) {
					try {
						Thread.sleep(i * 1000L);
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
					new Thread(new DatesFormatTest.DatesFormatTestTask(i)).start();
				}
			}
		}).start();
	}

	static class DatesFormatTestTask implements Runnable{
		private int index;
		public DatesFormatTestTask(int index){
			this.index = index;
		}
		@Override
		public void run() {
			for(int j = 0; j < 1000; j++) {
				try {
					Thread.sleep(500l);
					for(int i = 0; i < index; i++) {
						System.out.print("\t");
					}
					if(j < 10){
						int randrom = new Random().nextInt(5);
						switch (randrom) {
							case 0:
								System.out.println(String.format("%s%s", owner[index], Dates.getDateFormated("ddHH", new Date(System.currentTimeMillis() - 86400000 * index))));
								break;
							case 1:
								System.out.println(String.format("%s%s", owner[index], Dates.getDateFormated("d", new Date(System.currentTimeMillis() - 86400000 * index))));
								break;
							case 2:
								System.out.println(String.format("%s%s", owner[index], Dates.getDateFormated("d_yy", new Date(System.currentTimeMillis() - 86400000 * index))));
								break;
							default:
								System.out.println(String.format("%s%s", owner[index], Dates.getDateFormated("dd", new Date(System.currentTimeMillis() - 86400000 * index))));
								break;
						}
					}else{
						System.out.println(String.format("%s%s%s", "x",owner[index], Dates.getDateFormated("dd", new Date(System.currentTimeMillis() - 86400000 * index))));
					}
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}