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
import com.movitech.grande.net.protocol.XcfcCommissionApplyResult;
/**
 * 
 * @author Warkey.Song
 *
 */
@Rest(converters = { MappingJackson2HttpMessageConverter.class })
public interface CommissionApplyClient extends RestClientErrorHandling,RestClientSupport, RestClientHeaders {
	@Get("{url}/rest/broker/givemebrokerage?brokerId={brokerId}&id={id}")
	@Accept(MediaType.APPLICATION_JSON_VALUE)
	@RequiresHeader({ NetHandler.HEADER_CONTENT_TYPE })
	XcfcCommissionApplyResult getApplyResult(String url,String brokerId,String id);
}
