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

import com.movitech.grande.model.XcfcHouseBanner;
import com.movitech.grande.net.NetHandler;


/**
 * @author Warkey.Song
 * @version 创建时间：2014年7月17日 下午4:26:27
 * 类说明
 */

@Rest(converters = { MappingJackson2HttpMessageConverter.class })
public interface HouseBannerClient extends RestClientErrorHandling,RestClientSupport, RestClientHeaders {
	@Get("{url}/rest/news/queryBanners?type={type}&city={city}")
	@Accept(MediaType.APPLICATION_JSON_VALUE)
	@RequiresHeader({ NetHandler.HEADER_CONTENT_TYPE })
	XcfcHouseBanner[] getHouseBanner(String url,String type,String city);
}
