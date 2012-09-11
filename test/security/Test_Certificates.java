package security;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import junit.framework.TestCase;
import sun.security.x509.CertificateExtensions;

import com.iLirium.utils.commons.Strings;
import com.iLirium.utils.security.Certificates;
import com.iLirium.utils.security.Keys;

public class Test_Certificates extends TestCase
{
	// TODO: rewrite asserts
	public void test1() throws IOException, GeneralSecurityException
	{
		final String dn_subject = "CN=CN_Client, L=L_Zagreb, C=C_HR";
		final String dn_issuer  = "CN=CN_Root, L=L_Zagreb, C=C_HR";
		
		CertificateExtensions extensions = Certificates.generateExtensions();
		
		KeyPair subjectKP = Keys.generateRSAKeyPair(2048);
		KeyPair issuerKP = Keys.generateRSAKeyPair(2048);
		
		X509Certificate certificateRoot = Certificates.generateCertificate(dn_issuer, subjectKP, dn_issuer, issuerKP.getPrivate(), extensions, 365);
		X509Certificate certificate = Certificates.generateCertificate(dn_subject, subjectKP, dn_issuer, issuerKP.getPrivate(), extensions, 365);
		
		// check expiration
		certificate.checkValidity();
		// validate signature
		certificate.verify(issuerKP.getPublic());
		
		System.out.println("Certificate = " + certificate);

		// create key store
		String password = "password1!";
		KeyStore keyStore = KeyStore.getInstance("JKS");
		keyStore.load(null, password.toCharArray());		

		Certificates.addCertificateToKeyStore(keyStore, "subject01", certificate, subjectKP.getPrivate(), password);
		Certificates.addCertificateToKeyStore(keyStore, "rootCert", certificateRoot, issuerKP.getPrivate(), password);

		// dump data
		Enumeration<String> aliases = keyStore.aliases();
		while(aliases.hasMoreElements())
		{
			String alias = (String)aliases.nextElement();
			boolean privKey = keyStore.isKeyEntry(alias);
			boolean certKey = keyStore.isCertificateEntry(alias);
			
			System.out.println("ALIAS: " + alias + " : IS PRIVATE = " + privKey + " IS CERT = " + certKey);
			if(certKey)
			{
				X509Certificate cert = (X509Certificate) keyStore.getCertificate(alias);
				cert.checkValidity();				
				cert.verify(issuerKP.getPublic());
				System.out.println("CERT INFO: " + cert.getSubjectDN() + ", ThumbPrint: " + Strings.toHEX(Certificates.getSha1ThumbPrint(cert)));
			}
		}

		// save to file
		Certificates.saveKeyStore("C:\\testKeyStore.java", keyStore, password);
	}
}
