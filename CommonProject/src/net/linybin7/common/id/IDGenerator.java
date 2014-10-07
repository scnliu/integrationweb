package net.linybin7.common.id;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

/**
 * 
 * IDGenerator.java <br>
 *  <br>
 * Bensir <br>
 * 2009-8-15 下午05:56:02 <br>
 */

public class IDGenerator {
	private static int seq = 0;

	private static long currentTime = 0;

	// 修改该方法为static类型，防止产生重复id
	synchronized public static String generatIdBySystemtime() {
		long t1 = System.currentTimeMillis();
		if (Math.abs(Math.abs(t1) - Math.abs(currentTime)) < 1) {
			if ((seq + 1) >= 100) {
				seq = 0;
			}
			seq++;
		}
		StringBuilder ID = new StringBuilder();
		ID.append(t1);
		ID.append(seq);
		long id = Long.parseLong(ID.toString());
		currentTime = t1;
		return id + "";
	}

	
	public static String generate() {
        int addHash = -1;
        try {
            addHash = java.net.InetAddress.getLocalHost().getAddress().hashCode();
        } catch (Exception ex) {
        }
        Random rand = new Random(System.currentTimeMillis() * addHash);
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < 8; i++) {
            int dig = rand.nextInt() % 16;
            if (dig < 0) {
                dig *= -1;
            }
            sb.append(toHexChar(dig));
        }

        sb.append('-');

        for (int j = 0; j < 3; j++) {
            Random randNew = new Random(System.currentTimeMillis() * 10000 +
                                        (rand.nextInt() % 16) * 100 +
                                        rand.nextInt() * 2);
            for (int k = 0; k < 4; k++) {
                int dig = randNew.nextInt() % 16;
                if (dig < 0) {
                    dig *= -1;
                }
                sb.append(toHexChar(dig));
            }
            sb.append('-');
        }

        String dateStr = "" + System.currentTimeMillis();
        if (dateStr.length() > 10) {
            dateStr = dateStr.substring(dateStr.length() -10);
        }
        int left = 12 - dateStr.length();
        for (int i = 0; i < left; i++) {
            int dig = rand.nextInt() % 16;
            if (dig < 0) {
                dig *= -1;
            }
            sb.append(toHexChar(dig));
        }

        sb.append(dateStr);
        return sb.toString();
    }


    /**
     * 
     * @param dig
     * @return
     */
    private static String toHexChar(int dig) {
        String str = "";
        switch (dig) {
        case 0:
            str = "0";
            break;
        case 1:
            str = "1";
            break;
        case 2:
            str = "2";
            break;
        case 3:
            str = "3";
            break;
        case 4:
            str = "4";
            break;
        case 5:
            str = "5";
            break;
        case 6:
            str = "6";
            break;
        case 7:
            str = "7";
            break;
        case 8:
            str = "8";
            break;
        case 9:
            str = "9";
            break;
        case 10:
            str = "A";
            break;
        case 11:
            str = "B";
            break;
        case 12:
            str = "C";
            break;
        case 13:
            str = "D";
            break;
        case 14:
            str = "E";
            break;
        case 15:
            str = "F";
            break;
        }
        return str;
    }
    
    public static String getSequence(int hashCode) {
		try {
			return hashCode
					+ (InetAddress.getLocalHost().getHostAddress()).replace(
							".", "") + System.nanoTime();
		} catch (UnknownHostException e) {
			System.out.println("获取最大编号出错!");
			e.printStackTrace();
		}
		return null;
	}
}
