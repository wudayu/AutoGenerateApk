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
import com.movitech.grande.net.protocol.XcfcCommissionConfirmResult;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年7月16日 下午6:08:24
 * 类说明
 */
@Rest(converters = { MappingJackson2HttpMessageConverter.class })
public interface CommissionConfirmClient extends RestClientErrorHandling, RestClientSupport, RestClientHeaders{
	@Get("{url}/rest/broker/updateBrokerageState/{brokerId}/{id}")
	@Accept(MediaType.APPLICATION_JSON_VALUE)
	@RequiresHeader({NetHandler.HEADER_CONTENT_TYPE})
	XcfcCommissionConfirmResult getCommissionsConfirm(String url, String brokerId, String id);
}
