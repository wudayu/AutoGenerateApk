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


@Rest(converters = { MappingJackson2HttpMessageConverter.class })
public interface ReNameClient extends RestClientErrorHandling,
		RestClientSupport, RestClientHeaders {
	@Get("{url}/rest/broker/updateBroker?id={id}&name={name}")
	@Accept(MediaType.APPLICATION_JSON_VALUE)
	@RequiresHeader({ NetHandler.HEADER_CONTENT_TYPE })
	BaseResult reName(String url, String id, String name);
}
