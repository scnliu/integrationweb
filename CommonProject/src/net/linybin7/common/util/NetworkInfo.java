package net.linybin7.common.util;

/**
 * 
 * NetworkInfo.java <br>
 *  <br>
 * Bensir <br>
 * 2009-5-12 下午05:00:33 <br>
 */

import java.net.InetAddress;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.StringTokenizer;

/**
 * Java获取MAC地址及CPU序列号
 * 
 * From:csdn 两种方式，1.就像楼上的说的那样利用Runtime
 * call操作系统的命令，具体的命令取决于不同的操作系统，注意不要调用Runtime.getRuntime().exec(String)接口，要用
 * Runtime.getRuntime().exec(String[])这个接口，不然复杂命令的执行会有问题。例子如下（拿cpu个数，其他类似）：
 * 
 * 定义命令：<br>
 * WindowsCmd ="cmd.exe /c echo %NUMBER_OF_PROCESSORS%"; //windows的特殊<br>
 * SolarisCmd = {"/bin/sh", "-c", "/usr/sbin/psrinfo | wc -l"}; <br>
 * AIXCmd = {"/bin/sh", "-c", "/usr/sbin/lsdev -Cc processor | wc -l"}; <br>
 * HPUXCmd = {"/bin/sh", "-c", "echo \"map\" | /usr/sbin/cstm | grep CPU | wc -l
 * "};<br>
 * LinuxCmd = {"/bin/sh", "-c", "cat /proc/cpuinfo | grep ^process | wc -l"};
 * 
 * 然后判断系统： os = System.getProperty("os.name").toLowerCase();
 * 
 * 根据不同的操作系统call不同的命令。
 * 
 * @author Administrator
 * 
 */

public final class NetworkInfo {
	public final static String getMacAddress() throws IOException {
		String os = System.getProperty("os.name");
		try {
			if (os.startsWith("Windows")) {
				return windowsParseMacAddress(windowsRunIpConfigCommand());
			} else if (os.startsWith("Linux")) {
				return linuxParseMacAddress(linuxRunIfConfigCommand());
			} else {
				throw new IOException("unknown operating system: " + os);
			}
		} catch (ParseException ex) {
			ex.printStackTrace();
			throw new IOException(ex.getMessage());
		}
	}

	/* * Linux stuff */

	private final static String linuxParseMacAddress(String ipConfigResponse)
			throws ParseException {
		String localHost = null;
		try {
			localHost = InetAddress.getLocalHost().getHostAddress();
		} catch (java.net.UnknownHostException ex) {
			ex.printStackTrace();
			throw new ParseException(ex.getMessage(), 0);
		}
		StringTokenizer tokenizer = new StringTokenizer(ipConfigResponse, "\n");
		String lastMacAddress = null;
		while (tokenizer.hasMoreTokens()) {
			String line = tokenizer.nextToken().trim();
			boolean containsLocalHost = line.indexOf(localHost) >= 0; // see
			// if line contains IPaddress
			if (containsLocalHost && lastMacAddress != null) {
				return lastMacAddress;
			} // see if line contains MAC address
			int macAddressPosition = line.indexOf("HWaddr");
			if (macAddressPosition <= 0)
				continue;
			String macAddressCandidate = line.substring(macAddressPosition + 6)
					.trim();
			if (linuxIsMacAddress(macAddressCandidate)) {
				lastMacAddress = macAddressCandidate;
				continue;
			}
		}
		ParseException ex = new ParseException("cannot read MAC address for "
				+ localHost + " from [" + ipConfigResponse + "]", 0);
		ex.printStackTrace();
		throw ex;
	}

	private final static boolean linuxIsMacAddress(String macAddressCandidate) {
		// use a smart regular expression
		if (macAddressCandidate.length() != 17)
			return false;
		return true;
	}

	private final static String linuxRunIfConfigCommand() throws IOException {
		Process p = Runtime.getRuntime().exec("ifconfig");
		InputStream stdoutStream = new BufferedInputStream(p.getInputStream());
		StringBuffer buffer = new StringBuffer();
		// for (;;) {
		// int c = stdoutStream.read();
		// if (c == -1)
		// break;
		// buffer.append((char) c);
		// }
		int len = 0;
		byte[] buf = new byte[512];
		while ((len = stdoutStream.read(buf)) > 0) {
			byte[] red = new byte[len];
			System.arraycopy(buf, 0, red, 0, len);
			String text = new String(red);
			buffer.append(text);
		}
		String outputText = buffer.toString();
		stdoutStream.close();
		return outputText;
	}

	/* * Windows stuff */

	private final static String windowsParseMacAddress(String ipConfigResponse)
			throws ParseException {
		String localHost = null;
		try {
			localHost = InetAddress.getLocalHost().getHostAddress();
		} catch (java.net.UnknownHostException ex) {
			ex.printStackTrace();
			throw new ParseException(ex.getMessage(), 0);
		}
		StringTokenizer tokenizer = new StringTokenizer(ipConfigResponse, "\n");
		String lastMacAddress = null;
		while (tokenizer.hasMoreTokens()) {
			String line = tokenizer.nextToken().trim(); // see if line contains
			// IP address
			if (/* line.endsWith(localHost) */line.contains(localHost)
					&& lastMacAddress != null) {
				return lastMacAddress;
			} // see if line contains MAC address
			int macAddressPosition = line.indexOf(":");
			if (macAddressPosition <= 0)
				continue;
			String macAddressCandidate = line.substring(macAddressPosition + 1)
					.trim();
			if (windowsIsMacAddress(macAddressCandidate))// &Egrave;&iexcl;&micro;&Atilde;MAC&micro;&Oslash;&Ouml;·
			{
				lastMacAddress = macAddressCandidate;
				continue;
			}
		}
		ParseException ex = new ParseException("cannot read MAC address from ["
				+ ipConfigResponse + "]", 0);
		ex.printStackTrace();
		throw ex;
	}

	private final static boolean windowsIsMacAddress(String macAddressCandidate) {
		// use a smart regular expression
		if (macAddressCandidate.length() != 17)
			return false;
		return true;
	}

	private final static String windowsRunIpConfigCommand() throws IOException {
		Process p = Runtime.getRuntime().exec("ipconfig /all");
		InputStream stdoutStream = new BufferedInputStream(p.getInputStream());
		StringBuffer buffer = new StringBuffer();
		// for (;;) {
		// int c = stdoutStream.read();
		// if (c == -1)
		// break;
		// buffer.append((char) c);
		// }
		int len = 0;
		byte[] buf = new byte[512];
		while ((len = stdoutStream.read(buf)) > 0) {
			byte[] red = new byte[len];
			System.arraycopy(buf, 0, red, 0, len);
			String text = new String(red);
			buffer.append(text);
		}
		String outputText = buffer.toString();
		stdoutStream.close();
		// System.out.println(outputText);
		return outputText;
	}

	/* * Main */

	public final static void main(String[] args) {
		try {
			Process p = Runtime.getRuntime().exec("ping 192.168.10.153");			
			InputStream stdoutStream = new BufferedInputStream(p.getInputStream());
			StringBuffer buffer = new StringBuffer();
			int len = 0;
			byte[] buf = new byte[512];
			while ((len = stdoutStream.read(buf)) > 0) {
				byte[] red = new byte[len];
				System.arraycopy(buf, 0, red, 0, len);
				String text = new String(red);
				buffer.append(text);
			}
			String outputText = buffer.toString();
			System.out.println(outputText);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			System.out.println("Network infos:");
			System.out.println("  Operating System: "
					+ System.getProperty("os.name"));
			System.out.println("  IP/Localhost: "
					+ InetAddress.getLocalHost().getHostAddress());
			System.out.println("  MAC Address: " + getMacAddress());
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}