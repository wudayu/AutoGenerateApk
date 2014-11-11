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
import com.movitech.grande.net.protocol.XcfcCertificationResult;

@Rest(converters = { MappingJackson2HttpMessageConverter.class })
public interface CertificationClient extends RestClientErrorHandling,
		RestClientSupport, RestClientHeaders {
	@Get("{url}/rest/broker/brokerRealInfo?brokerId={brokerId}&name={name}&bankNo={bankNo}&bankName={bankName}&idcardImg={idcardImg}&idcardNegImg={idcardNegImg}&idCardNum={idCardNum}&gender={gender}&legalBirth={legalBirth}")
	@Accept(MediaType.APPLICATION_JSON_VALUE)
	@RequiresHeader({ NetHandler.HEADER_CONTENT_TYPE })
	XcfcCertificationResult certification(String url, String brokerId,
			String name, String bankNo, String bankName, String idcardImg,
			String idcardNegImg, String idCardNum, String gender, String legalBirth);
}
