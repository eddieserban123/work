package com.demo.folder;

import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.ResponseEntity;

import com.demo.folder.tata.httpclient.Utils;
import com.google.common.io.CharStreams;

public class KerbTest {

	public static final String serviceURLSocialSize = "http://tca-audiapi.prd.aqrcapital.com/v1/datasources/gt/functioncalls/GetSocialSize";
	public static final String serviceURLUnitOfExposure = "http://gaardsvc.aqrcapital.com/data-access/dataset?name=aqr%2Fgaa%2Funit_of_exposure&contentType=application%2Fgaa-json-v2&startDate=2015-08-21&endDate=2015-08-21";
	public static final String serviceURLParentOrder = "http://orderservice.paas.dev.aqrcapital.com/v1/parentOrders?tradeDate=2018-08-22&assetClass=FX";

	public static void main(String[] args) throws Exception {
		// System.setProperty("javax.security.auth.useSubjectCredsOnly","true");
		ResponseEntity<String> respEntity = Utils.springKerberosRestTemplateTest(serviceURLSocialSize);
		System.out.println("Response from RestTemplate: " + respEntity.getBody());
		//passAuthTokenTest(serviceURLKerb);
		//HttpClient client = Utils.buildHTTPClient();
		HttpClient client = HttpClientBuilder.create().setSSLContext(Utils.buildSSLContext()).build();
		HttpResponse httpResp = client.execute(new HttpGet(serviceURLSocialSize));
		System.out.println("Response with HTTP Client: " + CharStreams.toString(new InputStreamReader(httpResp.getEntity().getContent())));
	}
}
