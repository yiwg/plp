package com.yiwg.plp.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtil {

	private static HttpClientUtil httpClientUtil;
	private HttpClient httpClient;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	// private static final String CHARSET = HTTP.UTF_8;

	private HttpClientUtil() {
	}

	public static synchronized HttpClientUtil getSafeHttpClient() {
		/*if (httpClientUtil == null) {
			// 设置组件参数, HTTP协议的版本,1.1/1.0/0.9
			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setUserAgent(params, "HttpComponents/1.1");
			HttpProtocolParams.setUseExpectContinue(params, true);

			// 设置连接超时时间
			int REQUEST_TIMEOUT = 5 * 1000; // 设置请求超时10秒钟
			int SO_TIMEOUT = 5 * 1000; // 设置等待数据超时时间10秒钟
			// HttpConnectionParams.setConnectionTimeout(params,
			// REQUEST_TIMEOUT);
			// HttpConnectionParams.setSoTimeout(params, SO_TIMEOUT);
			params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, REQUEST_TIMEOUT);
			params.setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);

			// 设置访问协议
			SchemeRegistry schreg = new SchemeRegistry();
			schreg.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
			schreg.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));

			// 多连接的线程安全的管理器
			PoolingClientConnectionManager pccm = new PoolingClientConnectionManager(schreg);
			pccm.setDefaultMaxPerRoute(20); // 每个主机的最大并行链接数
			pccm.setMaxTotal(100); // 客户端总并行链接最大数

			DefaultHttpClient httpClient = new DefaultHttpClient(pccm, params);
			httpClientUtil = new HttpClientUtil();
			httpClientUtil.setHttpClient(httpClient);
			return httpClientUtil;
		}*/
		
		
		if (httpClientUtil == null) {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			httpClientUtil = new HttpClientUtil();
			httpClientUtil.setHttpClient(httpClient);
			return httpClientUtil;
		}
		return httpClientUtil;
	}
	
	private void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}
	
	public HttpClient getHttpClient() {
		return this.httpClient;
	}
	
	public String post(String url, HttpPost httpost) throws Exception {
		HttpResponse response = null;
		try {
			logger.info("接口请求连接："+url);
			response = httpClient.execute(httpost);
		} catch (Exception e) {
			//重连
			logger.warn(e.getMessage(),e);
			logger.info("第一次调用httpClient失败...");
			response = httpClient.execute(httpost);
		}
		HttpEntity entity = response.getEntity();
		String msgStr = "";
		if (entity != null) {
			msgStr = EntityUtils.toString(entity, "UTF-8");
		}
		return msgStr;
	}

}
