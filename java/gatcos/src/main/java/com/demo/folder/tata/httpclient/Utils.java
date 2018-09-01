package com.demo.folder.tata.httpclient;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.config.Lookup;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.auth.SPNegoSchemeFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.kerberos.authentication.KerberosAuthenticationProvider;
import org.springframework.security.kerberos.authentication.KerberosServiceAuthenticationProvider;
import org.springframework.security.kerberos.authentication.sun.SunJaasKerberosClient;
import org.springframework.security.kerberos.authentication.sun.SunJaasKerberosTicketValidator;
import org.springframework.security.kerberos.client.KerberosRestTemplate;

import com.demo.folder.tata.fetcher.httpclient.NullCredentials;


public class Utils {

	private static final Logger logger = LoggerFactory.getLogger(Utils.class);

	public static SSLContext buildSSLContext() throws KeyStoreException, KeyManagementException, NoSuchAlgorithmException, CertificateException, IOException {
		ClassPathResource trustStoreResource = new ClassPathResource("cacerts");
		KeyStore trustStore = KeyStore.getInstance("JKS");
		trustStore.load(trustStoreResource.getInputStream(), "changeit".toCharArray());
		SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(trustStoreResource.getFile()).build();
		return sslContext;
	}

	public static CloseableHttpClient dummyStoreHTTPClient() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (chain, authType) -> true).build();
        HostnameVerifier hostnameVerifier = new NoopHostnameVerifier();
        Registry<AuthSchemeProvider> authSchemeRegistry = RegistryBuilder.<AuthSchemeProvider>create()
                .register(AuthSchemes.SPNEGO, new SPNegoSchemeFactory())
                .build();
        Credentials dummyCredentials = new NullCredentials();
        CredentialsProvider credProv = new BasicCredentialsProvider();
        credProv.setCredentials(AuthScope.ANY, dummyCredentials);
        return HttpClientBuilder.create()
                .setDefaultAuthSchemeRegistry(authSchemeRegistry)
                .setDefaultCredentialsProvider(credProv)
                .setSSLContext(sslContext)
                .setSSLHostnameVerifier(hostnameVerifier)
                .build();

	}

	public static CloseableHttpClient buildHTTPClient() throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		SSLContext sslContext = buildSSLContext();
		HttpClientBuilder builder = HttpClientBuilder.create();
		Lookup<AuthSchemeProvider> authSchemeRegistry = RegistryBuilder.<AuthSchemeProvider>create().register(AuthSchemes.SPNEGO, new SPNegoSchemeFactory(true)).build();
		builder.setDefaultAuthSchemeRegistry(authSchemeRegistry);
		BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(new AuthScope(null, -1, null), new NullCredentials());
		builder.setDefaultCredentialsProvider(credentialsProvider);
		builder.setSSLContext(sslContext);
		return builder.build();
	}

	public static ResponseEntity<String> springKerberosRestTemplateTest(String url) throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		return springKerberosRestTemplateTest(url, buildHTTPClient());
	}

	public static ResponseEntity<String> springKerberosRestTemplateTest(String url, HttpClient client) throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		logger.debug("Requesting data for URL: {} ", url);
		String userHome = System.getProperty("user.home"), userName = System.getProperty("user.name");
		KerberosRestTemplate restTemplate = new KerberosRestTemplate(userHome + File.separator + userName + ".keytab", userName + "@AQRCAPITAL.COM", client);
		ResponseEntity<String> respEntity = restTemplate.getForEntity(url, String.class);
		logger.debug("Response received: {}", respEntity.getBody());
		return respEntity;
	}


	public static KerberosServiceAuthenticationProvider getKerbAuthProvider() {
		KerberosServiceAuthenticationProvider authProvider = new KerberosServiceAuthenticationProvider();
		SunJaasKerberosTicketValidator ticketValidator = new SunJaasKerberosTicketValidator();
		ticketValidator.setServicePrincipal("krbtgt/AQRCAPITAL.COM@AQRCAPITAL.COM");
		ticketValidator.setKeyTabLocation(new FileSystemResource("C:/Users/malpanir/malpanir.keytab"));
		authProvider.setTicketValidator(ticketValidator);
		authProvider.setUserDetailsService(dummyUserDetailsService());
		return authProvider;
	}

	public static KerberosAuthenticationProvider kerberosAuthenticationProvider() {
    	KerberosAuthenticationProvider provider = new KerberosAuthenticationProvider();
        SunJaasKerberosClient client = new SunJaasKerberosClient();
        client.setDebug(true);
        provider.setKerberosClient(client);
        provider.setUserDetailsService(dummyUserDetailsService());
        return provider;
	}

	public static DummyUserDetailsService dummyUserDetailsService() {
    	return new DummyUserDetailsService();
	}

	public static class DummyUserDetailsService implements UserDetailsService {

		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			return new User(username, "notUsed", true, true, true, true, AuthorityUtils.createAuthorityList("ROLE_USER"));
		}

	}

}
