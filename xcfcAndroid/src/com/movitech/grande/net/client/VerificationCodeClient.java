package com.movitech.grande.net.client;

import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.RequiresHeader;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.androidannotations.api.rest.RestClientHeaders;
import org.androidannotations.api.rest.RestClientSupport;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.BaseResult;
import com.movitech.grande.net.protocol.XcfcVerificationCodeResult;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jul 3, 2014 5:23:30 PM
 * @Description: This is David Wu's property.
 *
 **/
@Rest(converters = { MappingJackson2HttpMessageConverter.class })
public interface VerificationCodeClient extends RestClientErrorHandling, RestClientSupport, RestClientHeaders{
	@Get("{url}/rest/sms/getSmsCode/{phone}")
	@Accept(MediaType.APPLICATION_JSON_VALUE)
	@RequiresHeader({NetHandler.HEADER_CONTENT_TYPE})
	BaseResult getVerificationCode(String url, String phone);

	@Get("{url}/rest/sms/checkSmsCode/{phone}/{code}")
	@Accept(MediaType.APPLICATION_JSON_VALUE)
	@RequiresHeader({NetHandler.HEADER_CONTENT_TYPE})
	XcfcVerificationCodeResult checkVerificationCode(String url, String phone, String code);
}