//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package server;

import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class SeguridadSS
{
	private static final Date NOT_BEFORE = new Date( System.currentTimeMillis( ) - 31536000000L );

	private static final Date NOT_AFTER = new Date( System.currentTimeMillis( ) + 3153600000000L );

	public static final String RSA = "RSA";

	public static final String HMACMD5 = "HMACMD5";

	public static final String HMACSHA1 = "HMACSHA1";

	public static final String HMACSHA256 = "HMACSHA256";

	public static final String RC4 = "RC4";

	public static final String BLOWFISH = "BLOWFISH";

	public static final String AES = "AES";

	public static final String DES = "DES";

	public SeguridadSS( )
	{
	}

	public static X509Certificate generateV3Certificate(KeyPair pair)
			throws Exception {
		// Generate self-signed certificate
		X500NameBuilder nameBuilder = new X500NameBuilder(BCStyle.INSTANCE);
		nameBuilder.addRDN(BCStyle.OU, "OU");
		nameBuilder.addRDN(BCStyle.O, "O");
		nameBuilder.addRDN(BCStyle.CN, "CN");

		String stringDate1 = "2016-10-01";
		String stringDate2 = "2020-12-20";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date notBefore = null;
		Date notAfter = null;
		try {
			notBefore = format.parse(stringDate1);
			notAfter = format.parse(stringDate2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		BigInteger serialNumber = new BigInteger(128, new Random());

		X509v3CertificateBuilder certificateBuilder = new JcaX509v3CertificateBuilder(
				nameBuilder.build(), serialNumber, notBefore, notAfter,
				nameBuilder.build(), pair.getPublic());
		X509Certificate certificate = null;
		try {
			ContentSigner contentSigner = new JcaContentSignerBuilder(
					"SHA256WithRSAEncryption").build(pair.getPrivate());

			certificate = new JcaX509CertificateConverter()
					.getCertificate(certificateBuilder.build(contentSigner));
		} catch (OperatorCreationException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		}
		return certificate;
	}
}
