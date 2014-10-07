package net.linybin7.core.license;

import java.security.InvalidKeyException;
import java.security.PublicKey;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import sun.security.rsa.RSAPublicKeyImpl;

/**
 * 逸信科技 <br>
 * 
 * @author WuLinbin <br>
 * @since 2011-2011-7-26-上午07:39:18 <br>
 * @description <br>
 *              TODO
 **/
public final class EpublicKey {
	private PublicKey publicKey;

	public EpublicKey() throws LicenseException {
		String eshine000000 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvDT3rYVjxR3NU6dkzBTHVrgavZJKRGR6UqHyDjuyhJaCRDuG5aO35jHWgyJBHLWBpIcr7vl29AnXVn7zJqVqkKkHN7Jxt2igL+8aZkiIUyPwvEtMzBygNLLkwVWcghXhhnHFuG8NBlTqc6A0Pd0H/4qml0AzVP1NhRnXFXzlkshy32argiNaTnTyuQ8WD8QgoECqEmK9GSs4BcMCsPoTBCPbX2iqxs9BpgLJiiwOdwV6TkdXv6B69U67Eqyb2ZCnRXk+bISor1yfSt+UHM1/2FfimTfJEHZOC/EzPkEIDI03V4L60+Z41i3bRV0hogJM2JkvVvQsxYk3ljH/I2yvYQIDAQAB";
		String key = eshine000000;
		try {
			this.publicKey = new RSAPublicKeyImpl(Base64.decode(key));
		} catch (InvalidKeyException e) {
			throw new LicenseException("无效的公钥", e);
		} catch (Base64DecodingException e) {
			throw new LicenseException("公钥Base64编码错误", e);
		}
	}

	public PublicKey getPublicKey() {
		return this.publicKey;
	}
}
