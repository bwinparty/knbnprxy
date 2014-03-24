package com.versionone.apiclient;

import org.apache.http.*;
import org.apache.http.auth.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Lookup;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicSchemeFactory;
import org.apache.http.impl.auth.NTLMSchemeFactory;
import org.apache.http.impl.client.*;
import sun.net.www.protocol.http.AuthCacheImpl;
import sun.net.www.protocol.http.AuthCacheValue;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a connection to the VersionOne server.
 *
 * @author Jerry D. Odenwelder Jr.
 */
public class V1APIConnector implements IAPIConnector {

	private static final String UTF8 = "UTF-8";

	private final CookieStore cookieStore = new BasicCookieStore();
	private String _url = null;
	private ProxyProvider proxy = null;
	private final Map<String, HttpEntity> _requests = new HashMap<>();
    private CloseableHttpClient connection = null;


    /**
	 * Create Connection.
	 * @param url - URL to VersionOne system.
	 * @param userName - Name of the user wishing to connect.
	 * @param password - password of the user wishing to connect.
	 */
	public V1APIConnector(String url, String userName, String password) {
		this(url, userName, password, null);
	}

	/**
	 * Create Connection.
	 * @param url - URL to VersionOne system.
	 * @param userName - Name of the user wishing to connect.
	 * @param password - password of the user wishing to connect.
	 * @param proxy	- proxy for connection.
	 */
	public V1APIConnector(String url, String userName, String password, ProxyProvider proxy) {
		this.proxy = proxy;
		_url = url;

        String hostname = "localhost";
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
        }
        CredentialsProvider credsProvider = null;
        Lookup<AuthSchemeProvider> authProviders = null;
        RequestConfig rc = null;
        // WORKAROUND: http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6626700
		if (userName != null) {
            org.apache.http.auth.Credentials creds;
            if(userName.contains("\\")) { //go for ntlm
                String userdomain[] = userName.split("\\\\", 2);
                creds = new NTCredentials(userdomain[1], password, hostname, userdomain[0]);
                authProviders = RegistryBuilder.<AuthSchemeProvider>create()
                        .register(AuthSchemes.NTLM, new NTLMSchemeFactory())
                        .build();
                rc = RequestConfig.custom()
                        .setTargetPreferredAuthSchemes(Arrays.asList("NTLM"))
                        .build();
            }else{ //go for basic auth
                creds = new UsernamePasswordCredentials(userName, password);
                authProviders = RegistryBuilder.<AuthSchemeProvider>create()
                        .register(AuthSchemes.BASIC, new BasicSchemeFactory())
                        .build();
                rc = RequestConfig.custom()
                        .setTargetPreferredAuthSchemes(Arrays.asList("BASIC"))
                        .build();
            }
			AuthCacheValue.setAuthCache(new AuthCacheImpl());
            credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(
                    new AuthScope(AuthScope.ANY),
                    creds);
        }
        connection = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider)
                .setDefaultAuthSchemeRegistry(authProviders)
                .setDefaultCookieStore(cookieStore)
                .setDefaultRequestConfig(rc)
                .build();
    }

    /**
	 * Create a connection with only the URL.
	 * Use this constructor to access MetaData, which does not require or if you want to use
	 * have Windows Integrated Authentication or
	 * MetaData does not require the use of credentials
	 *
	 * @param url - Complete URL to VersionOne system
	 */
	public V1APIConnector(String url) {
		this(url, null, null);
	}


	/**
	 * Create a connection with only the URL and proxy.
	 * Use this constructor to access MetaData, which does not require or if you want to use
	 * have Windows Integrated Authentication or
	 * MetaData does not require the use of credentials
	 *
	 * @param url - Complete URL to VersionOne system
	 * @param proxy - Proxy for connection.
	 */
	public V1APIConnector(String url, ProxyProvider proxy) {
		this(url, null, null, proxy);
	}

	/**
	 * Read data from the root of the connection
	 *
	 * Note: Caller is responsible for closing the returned stream
	 *
	 * @return the stream for reading data
	 * @throws IOException
	 */
	public Reader getData() throws ConnectionException {
		return getData("");
	}

	/**
	 * Read data from the path provided
	 *
	 * Note: Caller is responsible for closing the returned stream
	 *
	 * @param path
	 * @return the stream for reading data
	 * @throws IOException
	 */
	public Reader getData(String path) throws ConnectionException {
        try{
            HttpGet httpget = new HttpGet(_url + path);
            HttpResponse response = connection.execute(httpget);
            switch(response.getStatusLine().getStatusCode()) {
                case 200:
                    return new InputStreamReader(response.getEntity().getContent(), UTF8);
                case 401:
                    throw new SecurityException();
                default: {
                    StringBuffer message = new StringBuffer("Received Error ");
                    message.append(response.getStatusLine().getStatusCode());
                    message.append(" from URL ");
                    message.append(httpget.getURI().toString());
                    throw new ConnectionException(message.toString(), response.getStatusLine().getStatusCode());
                }
            }
        } catch (IOException e) {
            throw new ConnectionException("Error processing result from URL "
                + path, e);
        }
	}

	/**
	 * Send data to the path
	 *
	 * Note: Caller is responsible for closing the returned stream
	 *
	 * @param path
	 * @param data
	 * @return the response in a stream
	 * @throws IOException
	 */
	public Reader sendData(String path, String data) throws ConnectionException {
        InputStream resultStream = null;
        int code = -1;
        try{
            HttpPost httppost = new HttpPost(_url + path);
            StringEntity entity = new StringEntity(data, ContentType.create("text/xml", Consts.UTF_8));
            //entity.setChunked(true);
            httppost.setEntity(entity);
            HttpResponse response = connection.execute(httppost);
            code = response.getStatusLine().getStatusCode();
            resultStream = response.getEntity().getContent();
        } catch (IOException e) {
            throw new ConnectionException("Error writing to output stream",
                    code, e);
        }
		return new InputStreamReader(resultStream);
	}

	/**
	 * Beginning HTTP request to server.
	 * <p/>
	 * To begin POST request contentType parameter must be defined. If
	 * contentType parameter is null, GET request used. It's obligatory to
	 * complete request and get response by {@link #endRequest(String)} method
	 * with the same path parameter.
	 *
	 * @param path        path to the data on server.
	 * @param contentType Content-type of output content. If null - GET request
	 *                    used.
	 * @return the stream for writing POST data.
	 * @see #endRequest(String)
	 */
	public OutputStream beginRequest(String path, String contentType)
			throws ConnectionException {
		OutputStream outputStream = new ByteArrayOutputStream();
        BasicHttpEntity entity = new BasicHttpEntity();
		if (contentType != null)
			try {
                entity.setContentType(contentType);
                entity.writeTo(outputStream);
                //entity.setChunked(true);
			} catch (IOException e) {
                throw new ConnectionException("Error writing to output stream",
						e);
			}
		_requests.put(path, entity);

		return outputStream;
	}

	/**
	 * Completing HTTP request and getting response.
	 *
	 * @param path path to the data on server.
	 * @return the response stream for reading data.
	 * @see #beginRequest(String, String)
	 */
	public InputStream endRequest(String path) throws ConnectionException {
		InputStream resultStream = null;
        HttpEntity req = _requests.remove(path);
        CloseableHttpClient connection = HttpClients.createDefault();
        int code = -1;
        try{
            HttpPost httppost = new HttpPost(_url + path);
            httppost.setEntity(req);
            HttpResponse response = connection.execute(httppost);
            code = response.getStatusLine().getStatusCode();
            resultStream = response.getEntity().getContent();
        } catch (IOException e) {
            throw new ConnectionException("Error writing to output stream",
                    code, e);
        }

		return resultStream;
	}
}
