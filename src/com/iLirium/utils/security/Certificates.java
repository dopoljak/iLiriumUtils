package com.iLirium.utils.security;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.iLirium.utils.commons.Base64;
import com.iLirium.utils.commons.Strings;

import sun.security.x509.AlgorithmId;
import sun.security.x509.BasicConstraintsExtension;
import sun.security.x509.CertificateAlgorithmId;
import sun.security.x509.CertificateExtensions;
import sun.security.x509.CertificateIssuerName;
import sun.security.x509.CertificateSerialNumber;
import sun.security.x509.CertificateSubjectName;
import sun.security.x509.CertificateValidity;
import sun.security.x509.CertificateVersion;
import sun.security.x509.CertificateX509Key;
import sun.security.x509.KeyUsageExtension;
import sun.security.x509.X500Name;
import sun.security.x509.X509CertImpl;
import sun.security.x509.X509CertInfo;

/**
 * @author dopoljak@gmail.com
 */
public class Certificates
{
	/**
	 * Create PEM certificate format
	 * 
	 * -----BEGIN CERTIFICATE REQUEST-----
	 * .... BASE64 certificate ...
	 * -----END CERTIFICATE REQUEST-----
	 */
	public static String createPEM(X509Certificate cert) throws IOException, CertificateEncodingException
	{
		StringBuilder sb = new StringBuilder();
		sb.append("-----BEGIN CERTIFICATE REQUEST-----");
		sb.append(Strings.NEWLINE);
		String base64Certificate = Base64.encodeToString(cert.getEncoded(), true);
		sb.append(base64Certificate);
		sb.append(Strings.NEWLINE);
		sb.append("-----END CERTIFICATE REQUEST-----");
		sb.append(Strings.NEWLINE);
		return sb.toString();
	}
	
	/**
	 * Load Java KeyStore from File
	 */
	public static KeyStore getKeyStore(String keyStorePath, String keyStoreName, String password) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException
	{
		FileInputStream fileKeyStore = new FileInputStream(keyStorePath + keyStoreName);
		KeyStore keystore = KeyStore.getInstance("JKS");
		keystore.load(fileKeyStore, password.toCharArray());
		return keystore;
	}
	
	/**
	 * Save Java KeyStore to File
	 */
	public static void saveKeyStore(String keyStorePath, KeyStore keyStore, String password) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException
	{
		FileOutputStream out = new FileOutputStream(keyStorePath);
		keyStore.store(out, password.toCharArray());
		out.close();
	}
	
	/**
	 * Create new Java KeyStore from provided Certificate and Password 
	 */
	public static KeyStore addCertificateToKeyStore(KeyStore keystore, String alias, X509Certificate certificate, PrivateKey privateKey, String password) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException
	{
		PasswordProtection prot = new KeyStore.PasswordProtection(password.toCharArray());		
		keystore.setCertificateEntry(alias, certificate);			
		Certificate[] certChain = getPrivateKey(Certificates.convertToPKCS7Certificate(certificate));			
		PrivateKeyEntry privateKeyEntry = new PrivateKeyEntry(privateKey, certChain);
		keystore.setEntry(alias + "_priv", privateKeyEntry, prot);
		return keystore;
	}
	
	/**
	 * Get KeyPair from KeyStore
	 */
	public static KeyPair getKeyPair(KeyStore keystore, String alias, String password) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, InvalidKeyException
	{
		Key key = keystore.getKey(alias, password.toCharArray());
		if (key instanceof PrivateKey) 
		{
			java.security.cert.Certificate cert = keystore.getCertificate(alias);
			PublicKey publicKey = cert.getPublicKey();
			return new KeyPair(publicKey, (PrivateKey) key);
		}
		throw new InvalidKeyException("There is no such a key in provided keystore !");
	}

	/**
	 * Convert X509 to PKCS7 encoded bytes
	 */
	public static byte[] convertToPKCS7Certificate(X509Certificate cert) throws CertificateException
	{
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		List<X509Certificate> certs = new ArrayList<X509Certificate>();
		certs.add(cert);
		CertPath cpp = cf.generateCertPath(certs);
		return cpp.getEncoded("PKCS7");
	}
	
	/**
	 * Convert Root & Client X509 certificate to PKCS7 encoded bytes
	 */
	public static byte[] convertToPKCS7Certificate(X509Certificate rootCert, X509Certificate clientCert) throws CertificateException
	{
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		List<X509Certificate> certs = new ArrayList<X509Certificate>();		
		certs.add(rootCert);
		certs.add(clientCert);
		CertPath cpp = cf.generateCertPath(certs);
		return cpp.getEncoded("PKCS7");
	}

	/**
	 * Load X509 certificate from file
	 */
	public static X509Certificate loadCertFromFile(String fileName) throws FileNotFoundException, CertificateException
	{
		final FileInputStream rootCertBytes = new FileInputStream(new File(fileName));
		final CertificateFactory cf = CertificateFactory.getInstance("X.509");
		return (X509Certificate) cf.generateCertificate(rootCertBytes);
	}

	/**
	 * Helper function for adding private key to Java KeyStore
	 */
	public static Certificate[] getPrivateKey(byte[] certBytes) throws CertificateException, IOException
	{
		final CertificateFactory cf = CertificateFactory.getInstance("X509");
		ByteArrayInputStream bais = new ByteArrayInputStream(certBytes);
		java.security.cert.Certificate[] certs = {};
		certs = cf.generateCertificates(bais).toArray(certs);
		bais.close();
		return certs;
	}

	/**
	 * Get thumb-print from certificate
	 */
	public static byte[] getSha1ThumbPrint(X509Certificate cert) throws NoSuchAlgorithmException, CertificateEncodingException
	{
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		byte[] der = cert.getEncoded();
		md.update(der);
		return md.digest();
	}

	/**
	 * Helper function for generating certificate extensions 
	 **/
	public static CertificateExtensions generateSampleExtensions(boolean isCa) throws IOException
	{
		final CertificateExtensions extensions = new CertificateExtensions();

		// key usage extensions
		final KeyUsageExtension kue = new KeyUsageExtension();
		kue.set(KeyUsageExtension.CRL_SIGN, true);
		kue.set(KeyUsageExtension.DATA_ENCIPHERMENT, true);
		kue.set(KeyUsageExtension.DIGITAL_SIGNATURE, true);
		//kue.set(KeyUsageExtension.IDENT, true);
		kue.set(KeyUsageExtension.KEY_AGREEMENT, true);
		kue.set(KeyUsageExtension.KEY_CERTSIGN, true);
		kue.set(KeyUsageExtension.KEY_ENCIPHERMENT, true);
		//kue.set(KeyUsageExtension.NAME, true);
		kue.set(KeyUsageExtension.NON_REPUDIATION, true);

		BasicConstraintsExtension basicConstraintExt = null;
		
		if(isCa) {
			basicConstraintExt = new BasicConstraintsExtension(true, 1);
			basicConstraintExt.set(BasicConstraintsExtension.IS_CA, Boolean.valueOf(true));
		}
		else
			basicConstraintExt = new BasicConstraintsExtension(false, 1);

		// add extensions
		extensions.set(KeyUsageExtension.IDENT, kue);
		extensions.set(BasicConstraintsExtension.IDENT, basicConstraintExt);
		//extensions.set(BasicConstraintsExtension.NAME, basicConstraintExt);

		return extensions;
	}

	/**
	 * Generate new certificate
	 */
	public static X509Certificate generateCertificate(String subject, KeyPair pair, String issuer, PrivateKey issuerKey, CertificateExtensions extensions, int daysValid) throws GeneralSecurityException, IOException
	{
		final String algorithm = "SHA1withRSA";
		AlgorithmId algo = AlgorithmId.get(algorithm);
		//final ObjectIdentifier algorithmId = AlgorithmId.get(algorithm).getOID(); // AlgorithmId.sha1WithRSAEncryption_oid;

		final X509CertInfo info = new X509CertInfo();
		final Date from = new Date();
		final Date to = new Date(from.getTime() + daysValid * 86400000l);
		final CertificateValidity interval = new CertificateValidity(from, to);
		final BigInteger sn = new BigInteger(64, new SecureRandom());
		final X500Name subjectName = new X500Name(subject);
		
		PrivateKey signerKey = null;
		X500Name issuerName = null;		
		if(issuer != null && issuerKey != null) {
			issuerName = new X500Name(issuer);
			signerKey = issuerKey;
		}
		else {
			issuerName = new X500Name(subject);
			signerKey = pair.getPrivate();
		}

		info.set(X509CertInfo.VALIDITY, interval);
		info.set(X509CertInfo.SERIAL_NUMBER, new CertificateSerialNumber(sn));
		info.set(X509CertInfo.SUBJECT, new CertificateSubjectName(subjectName));
		info.set(X509CertInfo.ISSUER, new CertificateIssuerName(issuerName));
		info.set(X509CertInfo.KEY, new CertificateX509Key(pair.getPublic()));
		info.set(X509CertInfo.VERSION, new CertificateVersion(CertificateVersion.V3));
		info.set(X509CertInfo.ALGORITHM_ID, new CertificateAlgorithmId(algo));
		
		if(extensions != null) {
			info.set(X509CertInfo.EXTENSIONS, extensions);
		}

		// Create signature
		X509CertImpl cert = new X509CertImpl(info);
		cert.sign(signerKey, algorithm);

		// Update certificate and create new info
		algo = (AlgorithmId) cert.get(X509CertImpl.SIG_ALG);
		info.set(CertificateAlgorithmId.NAME + "." + CertificateAlgorithmId.ALGORITHM, algo);
		cert = new X509CertImpl(info);
		cert.sign(signerKey, algorithm);

		// Initialize certificate using CertificateFactory because of problem
		// with loading keyUsage
		final ByteArrayInputStream ios = new ByteArrayInputStream(cert.getEncoded());
		final CertificateFactory cf = CertificateFactory.getInstance("X509");
		final X509Certificate c = (X509Certificate) cf.generateCertificate(ios);

		return c;
	}

}
