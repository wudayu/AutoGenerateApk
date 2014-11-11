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
import com.movitech.grande.net.protocol.XcfcAddSubInstResult;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年8月28日 下午5:44:05
 * 类说明
 */
@Rest(converters = { MappingJackson2HttpMessageConverter.class })
public interface AddSubInstClient extends RestClientErrorHandling, RestClientSupport, RestClientHeaders{
	@Get("{url}/rest/broker/addChildOrg?phone={phone}&superiorId={superiorId}&screenName={screenName}&name={userName}&password={password}&brokerType={brokerType}")
	@Accept(MediaType.APPLICATION_JSON_VALUE)
	@RequiresHeader({NetHandler.HEADER_CONTENT_TYPE})
	XcfcAddSubInstResult getSubInst(String url, String phone,  String superiorId,  String screenName, String userName, String password, String brokerType);
}