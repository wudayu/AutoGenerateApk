package com.movitech.grande.net.client;

import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.RequiresHeader;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.androidannotations.api.rest.RestClientHeaders;
import org.androidannotations.api.rest.RestClientSupport;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.MultiValueMap;

import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.BaseResult;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 18, 2014 4:19:06 PM
 * @Description: This is David Wu's property.
 *
 **/

@Rest(converters = { MappingJackson2HttpMessageConverter.class })
public interface RegClient extends RestClientErrorHandling, RestClientSupport, RestClientHeaders{
	@Get("{url}/rest/broker/addBroker?id={userId}&clientName={clientName}&clientPhone={clientPhone}&mqq={qq}&mweixin={weixin}&iosDevice=&androidDevice={deviceId}&brokerType={brokerType}&password={password}&city={city}&buildId={buildId}")
	@Accept(MediaType.APPLICATION_JSON_VALUE)
	@RequiresHeader({NetHandler.HEADER_CONTENT_TYPE})
	BaseResult regBroker(String url, String userId, String clientName, String clientPhone, String qq, String weixin, String deviceId, String brokerType, String password, String city, String buildId);
	
	
	@Post("{url}/rest/broker/addBroker")
	@Accept(MediaType.APPLICATION_JSON_VALUE)
	@RequiresHeader({NetHandler.HEADER_CONTENT_TYPE})
	BaseResult postRegBroker(String url, MultiValueMap<String, String> params);
}