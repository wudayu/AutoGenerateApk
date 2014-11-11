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
import com.movitech.grande.net.protocol.XcfcInfoesResult;


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
public interface InfoesClient extends RestClientErrorHandling, RestClientSupport, RestClientHeaders{
	@Get("{url}/rest/news/queryNews/{pageNo}?catagoryId={catagoryId}&isHot={isHot}&buildings={houseId}")
	@Accept(MediaType.APPLICATION_JSON_VALUE)
	@RequiresHeader({NetHandler.HEADER_CONTENT_TYPE})
	XcfcInfoesResult getInfoes(String url, int pageNo, String catagoryId, String isHot, String houseId);

	@Get("{url}/rest/news/queryBuildingNews/{pageNo}?catagoryId={catagoryId}&isHot={isHot}&buildings={houseId}")
	@Accept(MediaType.APPLICATION_JSON_VALUE)
	@RequiresHeader({NetHandler.HEADER_CONTENT_TYPE})
	XcfcInfoesResult getHotActions(String url, int pageNo, String catagoryId, String isHot, String houseId);
}
