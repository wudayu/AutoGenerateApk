package com.movitech.grande.net.client;

import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.RequiresHeader;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.androidannotations.api.rest.RestClientHeaders;
import org.androidannotations.api.rest.RestClientSupport;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.MultiValueMap;

import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.converter.MappingJacksonDavidHttpMessageConverter;
import com.movitech.grande.net.protocol.XcfcImageReturnResult;

/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @Created Time: Nov 7, 2014, 1:57:08 PM
 * @Description: David Wu created this file.
 *
 **/

@Rest(converters = { ByteArrayHttpMessageConverter.class, FormHttpMessageConverter.class, StringHttpMessageConverter.class, MappingJackson2HttpMessageConverter.class, MappingJacksonDavidHttpMessageConverter.class })
public interface ImageClient extends RestClientErrorHandling, RestClientSupport, RestClientHeaders {
	@Post("{url}/rest/broker/upload")
	@Accept(MediaType.TEXT_HTML_VALUE)
	@RequiresHeader({NetHandler.HEADER_CONTENT_TYPE, NetHandler.HEADER_CONTENT_DISPOSITION})
	XcfcImageReturnResult uploadPic(String url,  MultiValueMap<String,Object> params);

	@Post("{url}/rest/broker/updateBrokerPic?id={userId}")
	@Accept(MediaType.TEXT_HTML_VALUE)
	@RequiresHeader({NetHandler.HEADER_CONTENT_TYPE, NetHandler.HEADER_CONTENT_DISPOSITION})
	XcfcImageReturnResult uploadHeader(String url, String userId, MultiValueMap<String,Object> params);
}