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
import com.movitech.grande.net.protocol.XcfcHuStyleResult;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年8月19日 下午2:31:04
 * 类说明
 */
@Rest(converters = { MappingJackson2HttpMessageConverter.class })
public interface HuStyleClient extends RestClientErrorHandling,RestClientSupport, RestClientHeaders {
	//@Get("{url}/rest/building/queryBuildingHouseType?buildingId={buildingId}&houseType={houseType}")
	@Get("{url}/rest/building/queryBuildingHouseType?itemId={itemId}")
	@Accept(MediaType.APPLICATION_JSON_VALUE)
	@RequiresHeader({ NetHandler.HEADER_CONTENT_TYPE })
	XcfcHuStyleResult getHuStyleResult(String url, String itemId);
}