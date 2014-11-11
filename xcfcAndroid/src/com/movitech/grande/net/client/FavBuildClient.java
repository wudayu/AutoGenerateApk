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
import com.movitech.grande.net.protocol.XcfcFavBuildResult;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年7月4日 下午2:56:28
 * 类说明
 */
@Rest(converters = { MappingJackson2HttpMessageConverter.class })
public interface FavBuildClient extends RestClientErrorHandling, RestClientSupport, RestClientHeaders{
	@Get("{url}/rest/building/addCollect?brokerId={brokerId}&relationId={relationId}&isLike={isLike}&sourceType={sourceType}")
	@Accept(MediaType.APPLICATION_JSON_VALUE)
	@RequiresHeader({NetHandler.HEADER_CONTENT_TYPE})
	XcfcFavBuildResult getFavBuild(String url, String brokerId, String relationId, String isLike, String sourceType);
}