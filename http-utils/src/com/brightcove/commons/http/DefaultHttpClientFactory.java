package com.brightcove.commons.http;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

public class DefaultHttpClientFactory implements HttpClientFactory {
	public HttpClient getHttpClient(){
		return new DefaultHttpClient();
	}
}
