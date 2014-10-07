package net.linybin7.core.license;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * 逸信科技 <br>
 * 
 * @author WuLinbin <br>
 * @since 2011-2011-7-26-上午07:43:55 <br>
 * @description <br>
 *              TODO
 **/
public class Verifier {
	private static final Logger logger = Logger.getLogger(Verifier.class);
	private static final String LICENSE = "license";

	private PublicKey publicKey;
	private X509Certificate x509Cert;

	public Verifier() throws LicenseException {
		super();
		publicKey = new EpublicKey().getPublicKey();
		x509Cert = loadX509Cert();
	}

	public void verifyCert() throws LicenseException {

		/**
		try {
			this.x509Cert.verify(this.publicKey);// 验证证书是否有效
		} catch (InvalidKeyException e) {
			logger.error(e);
			throw new LicenseException("无效的许可证文件", e);
		} catch (CertificateException e) {
			logger.error(e);
			throw new LicenseException("无效的许可证文件", e);
		} catch (NoSuchAlgorithmException e) {
			logger.error(e);
			throw new LicenseException("无效的许可证文件", e);
		} catch (NoSuchProviderException e) {
			logger.error(e);
			throw new LicenseException("无效的许可证文件", e);
		} catch (SignatureException e) {
			logger.error(e);
			throw new LicenseException("无效的许可证文件", e);
		}
		try {
			this.x509Cert.checkValidity(new Date());// 验证证书是否过期
		} catch (CertificateExpiredException e) {
			logger.error(e);
			throw new LicenseException("许可证过期", e);
		} catch (CertificateNotYetValidException e) {
			logger.error(e);
			throw new LicenseException("许可证还没生效", e);
		}
		*/
	}

	public String restrictXML() {
		byte[] extValue = this.x509Cert.getExtensionValue("2.16.840.1.113730.1.13");// 其它限制
		byte[] restrictXML = extValue; // Arrays.copyOfRange(extValue, 6, extValue.length);
		return new String(restrictXML);
	}

	private X509Certificate loadX509Cert() throws LicenseException {
		try {
			byte[] buffer = load();
			InputStream bais = new ByteArrayInputStream(buffer);
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			java.security.cert.Certificate cert = cf.generateCertificate(bais);
			X509Certificate x509Cert = (X509Certificate) cert;
			return x509Cert;
		} catch (CertificateException e) {
			logger.error(e);
			throw new LicenseException("无效的许可证文件", e);
		} catch (LicenseException e) {
			logger.error(e);
			throw e;
		}

	}

	public static byte[] load() throws LicenseException {
		InputStream ins = null;
		try {
			ins = new FileInputStream(new File(licensePath(), LICENSE));
			byte[] buffer = new byte[ins.available()];
			ins.read(buffer);
			return buffer;
		} catch (FileNotFoundException e) {
			logger.error(e);
			throw new LicenseException("没有找到许可证文件", e);
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
			throw new LicenseException("没有找到许可证文件", e);
		} catch (IOException e) {
			logger.error(e);
			throw new LicenseException("读取许可证文件出错", e);
		} finally {
			if (ins != null)
				try {
					ins.close();
				} catch (IOException e) {
				}
		}
	}

	public static File licensePath() throws UnsupportedEncodingException {
		File path = new File(URLDecoder.decode(Verifier.class.getProtectionDomain().getCodeSource()
				.getLocation().getFile(), "UTF-8"));
		String absPath = path.getAbsolutePath();
		String webinfPath = (absPath.toUpperCase().indexOf("WEB-INF") > 0) ? absPath.substring(0,
				absPath.toUpperCase().indexOf("WEB-INF") + 8)
				+ File.separator + "classes" : path.getParentFile().getAbsolutePath();
		logger.info(webinfPath);

		return new File(webinfPath);
	}

}
