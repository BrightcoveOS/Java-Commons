package com.brightcove.commons.http;

import org.apache.http.client.HttpClient;

public interface HttpClientFactory {
	public HttpClient getHttpClient();
}
