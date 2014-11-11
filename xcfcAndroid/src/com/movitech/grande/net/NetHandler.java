package com.movitech.grande.net;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.EBean.Scope;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.rest.RestService;
import org.androidannotations.api.rest.RestClientSupport;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import android.content.Context;

import com.movitech.grande.MainApp;
import com.movitech.grande.constant.Constant;
import com.movitech.grande.constant.Timeout;
import com.movitech.grande.generic.JsonUtils;
import com.movitech.grande.generic.Utils;
import com.movitech.grande.generic.interfaces.IJsonUtils;
import com.movitech.grande.model.XcfcHouseBanner;
import com.movitech.grande.net.client.AddSubInstClient;
import com.movitech.grande.net.client.AppointmentClient;
import com.movitech.grande.net.client.BrokerDetailClient;
import com.movitech.grande.net.client.CancelFavBuildClient;
import com.movitech.grande.net.client.CertificationClient;
import com.movitech.grande.net.client.CitiesClient;
import com.movitech.grande.net.client.ClientSearchClient;
import com.movitech.grande.net.client.CommissionApplyClient;
import com.movitech.grande.net.client.CommissionClient;
import com.movitech.grande.net.client.CommissionConfirmClient;
import com.movitech.grande.net.client.CustomerDetailClient;
import com.movitech.grande.net.client.DayMarkClient;
import com.movitech.grande.net.client.DistrictsClient;
import com.movitech.grande.net.client.EarnIntegralClient;
import com.movitech.grande.net.client.FavBuildClient;
import com.movitech.grande.net.client.FocusBuildClient;
import com.movitech.grande.net.client.ForgetPassWordClient;
import com.movitech.grande.net.client.GuestIdClient;
import com.movitech.grande.net.client.HouseBannerClient;
import com.movitech.grande.net.client.HousesClient;
import com.movitech.grande.net.client.HousesDetailClient;
import com.movitech.grande.net.client.HuStyleClient;
import com.movitech.grande.net.client.ImageClient;
import com.movitech.grande.net.client.InfoBannerClient;
import com.movitech.grande.net.client.InfoDetailClient;
import com.movitech.grande.net.client.InfoesClient;
import com.movitech.grande.net.client.IntegralClient;
import com.movitech.grande.net.client.IsCollectClient;
import com.movitech.grande.net.client.MyCustomerClient;
import com.movitech.grande.net.client.MyMessageClient;
import com.movitech.grande.net.client.MyUnreadCountClient;
import com.movitech.grande.net.client.NewsLatestClient;
import com.movitech.grande.net.client.OrgBrokerDetailClient;
import com.movitech.grande.net.client.PushMessageClient;
import com.movitech.grande.net.client.QQBindClient;
import com.movitech.grande.net.client.QQIsBindClient;
import com.movitech.grande.net.client.ReNameClient;
import com.movitech.grande.net.client.RePasswardClient;
import com.movitech.grande.net.client.RecommendedClient;
import com.movitech.grande.net.client.RegClient;
import com.movitech.grande.net.client.RuleBannerClient;
import com.movitech.grande.net.client.ServiceTermsClient;
import com.movitech.grande.net.client.ShareIntegralClient;
import com.movitech.grande.net.client.SubOrgStatusClient;
import com.movitech.grande.net.client.TeamClient;
import com.movitech.grande.net.client.UserLoginClient;
import com.movitech.grande.net.client.VerificationCodeClient;
import com.movitech.grande.net.client.VersionClient;
import com.movitech.grande.net.protocol.BaseResult;
import com.movitech.grande.net.protocol.XcfcAddSubInstResult;
import com.movitech.grande.net.protocol.XcfcAppointmentResult;
import com.movitech.grande.net.protocol.XcfcBrokerDetailResult;
import com.movitech.grande.net.protocol.XcfcCancelFavBuild;
import com.movitech.grande.net.protocol.XcfcCertificationResult;
import com.movitech.grande.net.protocol.XcfcCitiesResult;
import com.movitech.grande.net.protocol.XcfcClientResult;
import com.movitech.grande.net.protocol.XcfcCommissionApplyResult;
import com.movitech.grande.net.protocol.XcfcCommissionConfirmResult;
import com.movitech.grande.net.protocol.XcfcCommissionResult;
import com.movitech.grande.net.protocol.XcfcCustomerDetailResult;
import com.movitech.grande.net.protocol.XcfcDayMarkResult;
import com.movitech.grande.net.protocol.XcfcDistrictsResult;
import com.movitech.grande.net.protocol.XcfcEarnIntegralResult;
import com.movitech.grande.net.protocol.XcfcFavBuildResult;
import com.movitech.grande.net.protocol.XcfcForgetPassWordResult;
import com.movitech.grande.net.protocol.XcfcGuestIdResult;
import com.movitech.grande.net.protocol.XcfcHousesCollectionResult;
import com.movitech.grande.net.protocol.XcfcHousesDetailResult;
import com.movitech.grande.net.protocol.XcfcHousesResult;
import com.movitech.grande.net.protocol.XcfcHuStyleResult;
import com.movitech.grande.net.protocol.XcfcImageReturnResult;
import com.movitech.grande.net.protocol.XcfcInfoBannerResult;
import com.movitech.grande.net.protocol.XcfcInfoDetailResult;
import com.movitech.grande.net.protocol.XcfcInfoesResult;
import com.movitech.grande.net.protocol.XcfcIntegralResult;
import com.movitech.grande.net.protocol.XcfcIsCollectResult;
import com.movitech.grande.net.protocol.XcfcMyCustomerResult;
import com.movitech.grande.net.protocol.XcfcMyMessageResult;
import com.movitech.grande.net.protocol.XcfcOrgBrokerDetailResult;
import com.movitech.grande.net.protocol.XcfcPushMessageResult;
import com.movitech.grande.net.protocol.XcfcRecommendedResult;
import com.movitech.grande.net.protocol.XcfcServiceTermsResult;
import com.movitech.grande.net.protocol.XcfcStringResult;
import com.movitech.grande.net.protocol.XcfcTeamResult;
import com.movitech.grande.net.protocol.XcfcUserResult;
import com.movitech.grande.net.protocol.XcfcVerificationCodeResult;
import com.movitech.grande.net.protocol.XcfcVersionResult;

/**
 * 
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 18, 2014 4:47:38 PM
 * @Description: This is David Wu's property.
 * 
 **/
@EBean(scope = Scope.Singleton)
public class NetHandler implements INetHandler {

	public static final String HEADER_CLIENT_SESSION = "client-session";
	public static final String HEADER_CLIENT_VERSION = "client-version";
	public static final String HEADER_API_VERSION = "api-version";
	public static final String HEADER_CONTENT_TYPE = "Content-Type";
	public static final String HEADER_CONTENT_DISPOSITION = "Content-Disposition";
	public static final String HEADER_CONTENT_ENCODING = "Content-Encoding";

	public static final String HEADER_VALUE_CLOSE = "Close";
	public static final String CONTANT_CODE = "UTF-8";
	public static final String FORM_DATA = "multipart/form-data";

	@Bean(JsonUtils.class)
	IJsonUtils jsonUtils;

	// 用户session，今后用于扩展
	// private String mSession;

	@RootContext
	Context mContext;

	@App
	MainApp mApp;

	// 用于初始化版本信息，Session等
	public void init(String session) {
	}

	/**
	 * 设置超时时间
	 * 
	 * @param client
	 * @param time
	 *            , use final integer from the class Timeout
	 */
	/*private void setTimeout(RestClientSupport client, int time) {
		((SimpleClientHttpRequestFactory) client.getRestTemplate()
				.getRequestFactory()).setReadTimeout(time);
		((SimpleClientHttpRequestFactory) client.getRestTemplate()
				.getRequestFactory()).setConnectTimeout(time);
	}*/
	private void setTimeout(RestClientSupport client, int time) {
		client.getRestTemplate().setRequestFactory(
				new HttpComponentsClientHttpRequestFactory());
		((HttpComponentsClientHttpRequestFactory) client.getRestTemplate()
				.getRequestFactory()).setReadTimeout(time);
		((HttpComponentsClientHttpRequestFactory) client.getRestTemplate()
				.getRequestFactory()).setConnectTimeout(time);
	}


	@RestService
	UserLoginClient mUserLoginClient;

	@Override
	public XcfcUserResult postForUserLoginResult(String username,
			String password) {
		try {
			mUserLoginClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(mUserLoginClient, Timeout.TIMEOUT_THIRTY_SECONDS);
			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("username", username);
			params.add("password", password);

			setTimeout(mUserLoginClient, Timeout.TIMEOUT_THIRTY_SECONDS);
			return mUserLoginClient.userLogin(Constant.SERVER_URL_FOR_SPRING,
					username, password);
		} catch (Throwable e) {
			Utils.debug("postForUserLoginResult : " + e.toString());
		}

		return null;
	}

	@RestService
	CitiesClient mCitysClient;

	@Override
	public XcfcCitiesResult postForGetCitysResult() {
		try {
			mCitysClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(mCitysClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			return mCitysClient.getCitys(Constant.SERVER_URL_FOR_SPRING);
		} catch (Throwable e) {
			Utils.debug("postForGetCitysResult : " + e.toString());
		}

		return null;
	}

	@RestService
	DistrictsClient mDistrictsClient;

	@Override
	public XcfcDistrictsResult postForGetDistrictsResult(String cityName) {
		try {
			mDistrictsClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(mDistrictsClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("cityId", cityName);

			return mDistrictsClient.getDistricts(
					Constant.SERVER_URL_FOR_SPRING, cityName);
		} catch (Throwable e) {
			Utils.debug("postForGetDistrictsResult : " + e.toString());
		}

		return null;
	}

	@RestService
	HousesClient mHousesClient;

	@Override
	public XcfcHousesResult postForGetHousesResult(int pageNo, String city,
			String district, String name, String isRecommend, String isHot) {
		try {
			mHousesClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(mHousesClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("pageNo", pageNo);
			params.add("city", city);
			params.add("area", district);
			params.add("name", name);
			params.add("isRecommend", isRecommend);
			params.add("isHot", isHot);

			return mHousesClient.getHouses(Constant.SERVER_URL_FOR_SPRING,
					pageNo, city, district, name, isRecommend, isHot);
		} catch (Throwable e) {
			Utils.debug("postForGetHousesResult : " + e.toString());
		}

		return null;
	}

	@Override
	public XcfcHousesResult postForGetHousesResultAll(int pageNo) {
		return postForGetHousesResult(pageNo, "", "", "", "", "");
	}

	@RestService
	InfoesClient mInfoesClient;

	@Override
	public XcfcInfoesResult postForGetInfoesResult(int pageNo, String catagoryId,
			String isHot, String houseId) {
		try {
			mInfoesClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(mInfoesClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("pageNo", pageNo);
			params.add("catagoryId", catagoryId);
			params.add("isHot", isHot);

			return mInfoesClient.getInfoes(Constant.SERVER_URL_FOR_SPRING,
					pageNo, catagoryId, isHot, houseId);
		} catch (Throwable e) {
			Utils.debug("postForGetInfoesResult : " + e.toString());
		}

		return null;
	}

	@Override
	public XcfcInfoesResult postForGetHotActionsResult(int pageNo, String catagoryId,
			String isHot, String houseId) {
		try {
			mInfoesClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(mInfoesClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("pageNo", pageNo);
			params.add("catagoryId", catagoryId);
			params.add("isHot", isHot);

			return mInfoesClient.getHotActions(Constant.SERVER_URL_FOR_SPRING,
					pageNo, catagoryId, isHot, houseId);
		} catch (Throwable e) {
			Utils.debug("postForGetInfoesResult : " + e.toString());
		}

		return null;
	}

	@RestService
	RePasswardClient mRePasswardClient;

	@Override
	public BaseResult postForRePasswordResult(String username,
			String oldpassword, String newpassword) {
		try {
			mRePasswardClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(mRePasswardClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("username", username);
			params.add("oldpassword", oldpassword);
			params.add("newpassword", newpassword);

			return mRePasswardClient.rePassward(Constant.SERVER_URL_FOR_SPRING,
					username, oldpassword, newpassword);
		} catch (Throwable e) {
			Utils.debug("postForRePasswordResult : " + e.toString());
		}

		return null;
	}

	@RestService
	ReNameClient mReNameClient;

	@Override
	public BaseResult postForReNameResult(String id, String name) {
		try {
			mReNameClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(mReNameClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("id", id);
			params.add("name", name);

			return mReNameClient.reName(Constant.SERVER_URL_FOR_SPRING, id,
					name);
		} catch (Throwable e) {
			Utils.debug("postForReNameResult : " + e.toString());
		}

		return null;
	}

	@RestService
	CertificationClient mCertificationClient;

	@Override
	public XcfcCertificationResult postForCertificationResult(String id,
			String realName, String birthday, String sex, String bankName,
			String bankNum, String idCardNum, String idCardImg,
			String idCardNegImg) {
		try {
			mCertificationClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(mCertificationClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("brokerId", id);
			params.add("name", realName);
			params.add("bankNo", bankNum);
			params.add("bankName", bankName);
			params.add("idcardImg", idCardImg);
			params.add("idcardNegImg", idCardNegImg);
			params.add("idCardNum", idCardNum);
			params.add("gender", sex);
			params.add("legalBirth", birthday);

			return mCertificationClient
					.certification(Constant.SERVER_URL_FOR_SPRING, id,
							realName, bankNum, bankName, idCardImg,
							idCardNegImg, idCardNum, sex, birthday);
		} catch (Throwable e) {
			Utils.debug("postForCertificationResult : " + e.toString());
		}

		return null;
	}

	@RestService
	CommissionClient mCommissionClient;

	@Override
	public XcfcCommissionResult postForCommissionResult(int pageNo,
			String brokerId, String status) {
		try {
			mCommissionClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(mCommissionClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("pageNo", pageNo);
			params.add("brokerId", brokerId);
			params.add("status", status);

			return mCommissionClient.getCommissions(
					Constant.SERVER_URL_FOR_SPRING, pageNo, brokerId, status);
		} catch (Throwable e) {
			Utils.debug("postForReNameResult : " + e.toString());
		}

		return null;
	}

	@RestService
	MyCustomerClient myCustomerClient;

	@Override
	public XcfcMyCustomerResult postForGetMyCustomersResult(int pageNo,
			String id, String isVip, String isVisited, String isSignup,
			String state) {
		try {
			myCustomerClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(myCustomerClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("pageNo", pageNo);
			params.add("id", id);
			params.add("isVip", isVip);
			params.add("isVisited", isVisited);
			params.add("isSignup", isSignup);
			params.add("state", state);

			return myCustomerClient.getMyCustomer(
					Constant.SERVER_URL_FOR_SPRING, pageNo, id, isVip,
					isVisited, isSignup, state);
		} catch (Throwable e) {
			Utils.debug("postForGetMyCustomersResult : " + e.toString());
		}

		return null;
	}

	@RestService
	HousesDetailClient mHousesDetailClient;

	@Override
	public XcfcHousesDetailResult postForGetHousesDetailResult(String id, String operatorId) {
		try {
			mHousesDetailClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(mHousesDetailClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("id", id);

			return mHousesDetailClient.getHousesDetail(
					Constant.SERVER_URL_FOR_SPRING, id, operatorId);
		} catch (Throwable e) {
			Utils.debug("postForGetHousesDetailResult : " + e.toString());
		}
		return null;
	}

	@RestService
	CommissionApplyClient commissionApplyClient;

	@Override
	public XcfcCommissionApplyResult postForCommissionApplyResult(
			String brokerId, String id) {
		try {
			commissionApplyClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(commissionApplyClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("brokerId", brokerId);
			params.add("id", id);

			return commissionApplyClient.getApplyResult(
					Constant.SERVER_URL_FOR_SPRING, brokerId, id);
		} catch (Throwable e) {
			Utils.debug("postForGetHousesDetailResult : " + e.toString());
		}
		return null;
	}

	@RestService
	CustomerDetailClient mCustomerDetailClient;

	@Override
	public XcfcCustomerDetailResult postForGetCustomerDetailResult(
			String brokerId, String clientId, String status) {
		try {
			mCustomerDetailClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(mCustomerDetailClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("brokerId", brokerId);
			params.add("clientId", clientId);

			return mCustomerDetailClient.getCustomerDetail(
					Constant.SERVER_URL_FOR_SPRING, brokerId, clientId, status);
		} catch (Throwable e) {
			Utils.debug("postForGetCustomerDetailResult : " + e.toString());
		}
		return null;
	}

	@RestService
	InfoDetailClient mInfoDetailClient;

	@Override
	public XcfcInfoDetailResult postForGetInfoDetailResult(String infoId, String operatorId) {
		try {
			mInfoDetailClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(mInfoDetailClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("infoId", infoId);

			return mInfoDetailClient.getInfoDetail(
					Constant.SERVER_URL_FOR_SPRING, infoId, operatorId);
		} catch (Throwable e) {
			Utils.debug("postForGetInfoDetailResult : " + e.toString());
		}
		return null;
	}

	@RestService
	IntegralClient mIntegralClient;

	@Override
	public XcfcIntegralResult postForGetIntegralResult(String brokerId) {
		try {
			mIntegralClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(mIntegralClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("id", brokerId);

			return mIntegralClient.getIntegral(Constant.SERVER_URL_FOR_SPRING,
					brokerId);
		} catch (Throwable e) {
			Utils.debug("postForGetIntegralResult : " + e.toString());
		}
		return null;
	}

	@RestService
	RecommendedClient recommendedClient;

	@Override
	public XcfcRecommendedResult postForGetRecommendResult(String clientName,
			String clientPhone, String recommendedBuilding, String brokerId,
			String recommendMark) {
		try {
			recommendedClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(recommendedClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("clientName", clientName);
			params.add("clientPhone", clientPhone);
			params.add("recommendedBuilding", recommendedBuilding);
			params.add("brokerId", brokerId);
			params.add("recommendMark", recommendMark);

			return recommendedClient.getRecommendResult(
					Constant.SERVER_URL_FOR_SPRING, clientName, clientPhone,
					recommendedBuilding, brokerId, recommendMark);
		} catch (Throwable e) {
			Utils.debug("postForGetRecommendResult : " + e.toString());
		}
		return null;
	}

	@RestService
	AppointmentClient appointmentClient;

	@Override
	public XcfcAppointmentResult postForGetAppointmentResult(String clientName,
			String clientPhone, String recommendedBuilding, String brokerId,
			String bespeakMark, String bespeakTime) {
		try {
			appointmentClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(appointmentClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("clientName", clientName);
			params.add("clientPhone", clientPhone);
			params.add("recommendedBuilding", recommendedBuilding);
			params.add("brokerId", brokerId);
			params.add("bespeakMark", bespeakMark);
			params.add("bespeakTime", bespeakTime);

			return appointmentClient.getAppointmentResult(
					Constant.SERVER_URL_FOR_SPRING, clientName, clientPhone,
					recommendedBuilding, brokerId, bespeakMark, bespeakTime);
		} catch (Throwable e) {
			Utils.debug("postForGetAppointmentResult : " + e.toString());
		}
		return null;
	}

	@RestService
	BrokerDetailClient brokerDetailClient;

	@Override
	public XcfcBrokerDetailResult postForGetBrokerDetail(String brokerId) {
		try {
			brokerDetailClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(brokerDetailClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("brokerId", brokerId);

			return brokerDetailClient.getBrokerDetail(
					Constant.SERVER_URL_FOR_SPRING, brokerId);
		} catch (Throwable e) {
			Utils.debug("postForGetBrokerDetail : " + e.toString());
		}
		return null;
	}

	@RestService
	FavBuildClient favBuildClient;

	@Override
	public XcfcFavBuildResult postForGetFavBuild(String brokerId,
			String relationId, String isLike, String sourceType) {
		try {
			favBuildClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(favBuildClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("brokerId", brokerId);
			params.add("relationId", relationId);
			params.add("isLike", isLike);
			params.add("sourceType", sourceType);

			return favBuildClient.getFavBuild(Constant.SERVER_URL_FOR_SPRING,
					brokerId, relationId, isLike, sourceType);
		} catch (Throwable e) {
			Utils.debug("postForGetFavBuild : " + e.toString());
		}
		return null;
	}

	@RestService
	FocusBuildClient focusBuildClient;

	@Override
	public XcfcHousesCollectionResult postForGetFocusBuild(String brokerId) {
		try {
			focusBuildClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(focusBuildClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("brokerId", brokerId);

			return focusBuildClient.getFocusBuild(
					Constant.SERVER_URL_FOR_SPRING, brokerId);
		} catch (Throwable e) {
			Utils.debug("postForGetFocusBuild : " + e.toString());
		}
		return null;
	}

	@RestService
	CancelFavBuildClient cancelFavBuildClient;

	@Override
	public XcfcCancelFavBuild postForGetCancelFavBuild(String brokerId,
			String relationId) {
		try {
			cancelFavBuildClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(cancelFavBuildClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("brokerId", brokerId);
			params.add("relationId", relationId);

			return cancelFavBuildClient.getCancelFavBuild(
					Constant.SERVER_URL_FOR_SPRING, brokerId, relationId);
		} catch (Throwable e) {
			Utils.debug("postForGetCancelFavBuild : " + e.toString());
		}
		return null;
	}

	@RestService
	IsCollectClient isCollectClient;

	@Override
	public XcfcIsCollectResult postForGetIsCollect(String brokerId,
			String relationId) {
		try {
			isCollectClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(isCollectClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("brokerId", brokerId);
			params.add("relationId", relationId);

			return isCollectClient.getIsCollect(Constant.SERVER_URL_FOR_SPRING,
					brokerId, relationId);
		} catch (Throwable e) {
			Utils.debug("postForGetIsCollect : " + e.toString());
		}
		return null;
	}

	@RestService
	GuestIdClient guestIdClient;

	@Override
	public XcfcGuestIdResult postForGetGuestIdResult(String deviceId) {
		try {
			guestIdClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(guestIdClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("deviceId", deviceId);

			return guestIdClient.getGuestIdResult(
					Constant.SERVER_URL_FOR_SPRING, deviceId);
		} catch (Throwable e) {
			Utils.debug("postForGetGuestIdResult : " + e.toString());
		}
		return null;
	}

	@RestService
	VerificationCodeClient verificationCodeClient;

	@Override
	public BaseResult postForGetVerificationCode(String phone) {
		try {
			verificationCodeClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(verificationCodeClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("phone", phone);

			return verificationCodeClient.getVerificationCode(
					Constant.SERVER_URL_FOR_SPRING, phone);
		} catch (Throwable e) {
			Utils.debug("postForGetVerificationCode : " + e.toString());
		}
		return null;
	}

	@Override
	public XcfcVerificationCodeResult postForCheckVerificationCode(
			String phone, String code) {
		try {
			verificationCodeClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(verificationCodeClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("phone", phone);
			params.add("code", code);

			return verificationCodeClient.checkVerificationCode(
					Constant.SERVER_URL_FOR_SPRING, phone, code);
		} catch (Throwable e) {
			Utils.debug("postForCheckVerificationCode : " + e.toString());
		}
		return null;
	}

	@RestService
	RegClient regClient;

	@Override
	public BaseResult postForRegBroker(String userId, String clientName,
			String clientPhone, String qq, String weixin, String deviceId,
			String brokerType, String password, String city, String buildId) {
		try {
			regClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(regClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("userId", userId);
			params.add("clientName", clientName);
			params.add("clientPhone", clientPhone);
			params.add("qq", qq);
			params.add("weixin", weixin);
			params.add("deviceId", deviceId);
			params.add("brokerType", brokerType);
			params.add("password", password);
			params.add("city", city);
			params.add("buildId", buildId);

			return regClient.regBroker(Constant.SERVER_URL_FOR_SPRING, userId,
					clientName, clientPhone, qq, weixin, deviceId, brokerType,
					password, city, buildId);
		} catch (Throwable e) {
			Utils.debug("postForRegBroker : " + e.toString());
		}
		return null;
	}
	
	@Override
	public BaseResult postForPostRegBroker(String userId, String clientName,
			String clientPhone, String qq, String weixin, String deviceId,
			String brokerType, String password, String city, String buildId) {
		try {
			regClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(regClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
			params.add("id", userId);
			params.add("clientName", clientName);
			params.add("clientPhone", clientPhone);
			params.add("mqq", qq);
			params.add("mweixin", weixin);
			params.add("androidDevice", deviceId);
			params.add("brokerType", brokerType);
			params.add("password", password);
			params.add("city", city);
			params.add("buildId", buildId);

			return regClient.postRegBroker(Constant.SERVER_URL_FOR_SPRING, params);
		} catch (Throwable e) {
			Utils.debug("postForRegBroker : " + e.toString());
		}
		return null;
	}

	@RestService
	ForgetPassWordClient forgetPassWordClient;

	@Override
	public XcfcForgetPassWordResult postForgetNewPassWord(String username,
			String newpassword) {
		try {
			forgetPassWordClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(forgetPassWordClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("username", username);
			params.add("newpassword", newpassword);

			return forgetPassWordClient.sendNewPassWord(
					Constant.SERVER_URL_FOR_SPRING, username, newpassword);
		} catch (Throwable e) {
			Utils.debug("postForgetNewPassWord : " + e.toString());
		}
		return null;
	}

	@RestService
	ServiceTermsClient serviceTermsClient;

	@Override
	public XcfcServiceTermsResult postForGetServiceTerms(int type) {
		try {
			serviceTermsClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(serviceTermsClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("type", type);

			return serviceTermsClient.getServicesTerms(
					Constant.SERVER_URL_FOR_SPRING, type);
		} catch (Throwable e) {
			Utils.debug("postForGetServiceTerms : " + e.toString());
		}
		return null;
	}

	@RestService
	VersionClient versionClient;

	@Override
	public XcfcVersionResult postForGetVersion(int type) {
		try {
			versionClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(versionClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("type", type);

			return versionClient.getVersion(Constant.SERVER_URL_FOR_SPRING,
					type);
		} catch (Throwable e) {
			Utils.debug("postForGetVersion : " + e.toString());
		}
		return null;
	}

	@RestService
	NewsLatestClient newsLatestClient;
	@Override
	public XcfcStringResult postForGetLatestTime(String type) {
		try {
			newsLatestClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(newsLatestClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("type", type);

			return newsLatestClient.getLatestTime(Constant.SERVER_URL_FOR_SPRING, type);
		} catch (Throwable e) {
			Utils.debug("postForGetLatestTime : " + e.toString());
		}
		return null;
	}

	@RestService
	MyUnreadCountClient myUnreadCountClient;
	@Override
	public XcfcStringResult postForGetUnreadCount(String userId) {
		try {
			myUnreadCountClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(myUnreadCountClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("userId", userId);

			return myUnreadCountClient.getUnreadCount(Constant.SERVER_URL_FOR_SPRING, userId);
		} catch (Throwable e) {
			Utils.debug("postForGetUnreadCount : " + e.toString());
		}
		return null;
	}

	@RestService
	MyMessageClient myMessageClient;
	@Override
	public XcfcMyMessageResult postForGetMyMessages(int pageNo, String userId) {
		try {
			myMessageClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(myMessageClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("pageNo", pageNo);
			params.add("userId", userId);

			return myMessageClient.getMyMessages(Constant.SERVER_URL_FOR_SPRING, pageNo, userId);
		} catch (Throwable e) {
			Utils.debug("postForGetMyMessages : " + e.toString());
		}
		return null;
	}

	@RestService
	DayMarkClient dayMarkClient;

	@Override
	public XcfcDayMarkResult postForCheckDayMark(String id) {
		try {
			dayMarkClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(dayMarkClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("id", id);

			setTimeout(dayMarkClient, Timeout.TIMEOUT_THIRTY_SECONDS);
			return dayMarkClient.getDayMark(Constant.SERVER_URL_FOR_SPRING, id);
		} catch (Throwable e) {
			Utils.debug("postForCheckDayMark : " + e.toString());
		}
		return null;
	}

	@RestService
	ClientSearchClient clientSearchClient;

	@Override
	public XcfcClientResult postForGetClients(int pageNum, String brokerId,
			String name) {
		try {
			clientSearchClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(clientSearchClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("pageNum", pageNum);
			params.add("brokerId", brokerId);
			params.add("name", name);

			return clientSearchClient.getClients(
					Constant.SERVER_URL_FOR_SPRING, pageNum, brokerId, name);
		} catch (Throwable e) {
			Utils.debug("postForGetClients : " + e.toString());
		}
		return null;
	}

	@RestService 
	CommissionConfirmClient confirmClient;
	@Override
	public XcfcCommissionConfirmResult postForCheckConfirm(String brokerId,
			String id) {
		try {
			confirmClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(confirmClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("brokerId", brokerId);
			params.add("id", id);

			return confirmClient.getCommissionsConfirm(Constant.SERVER_URL_FOR_SPRING,  brokerId, id);
		} catch (Throwable e) {
			Utils.debug("postForCheckConfirm : " + e.toString());
		}
		return null;
	}

	@RestService
	EarnIntegralClient earnIntegralClient;
	@Override
	public XcfcEarnIntegralResult postForGetEarnIntegral(String brokerId) {
		try {
			earnIntegralClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(earnIntegralClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("brokerId", brokerId);
			
			return earnIntegralClient.getEarnIntegral(Constant.SERVER_URL_FOR_SPRING, brokerId);
		} catch (Throwable e) {
			Utils.debug("postForGetEarnIntegral : " + e.toString());
		}
		return null;
	}

	@RestService
	HouseBannerClient houseBannerClient;
	@Override
	public XcfcHouseBanner[] postForGetHouseBanner(String type, String city) {
		try {
			houseBannerClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(houseBannerClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("type", type);
			params.add("city", city);

			return houseBannerClient.getHouseBanner(Constant.SERVER_URL_FOR_SPRING, type, city);
		} catch (Throwable e) {
			Utils.debug("postForGetHouseBanner : " + e.toString());
		}
		return null;
	}

	@RestService
	PushMessageClient pushMessageClient;
	@Override
	public XcfcPushMessageResult postForGetPushMessage(String brokerId) {
		try {
			pushMessageClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(pushMessageClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("brokerId", brokerId);

			return pushMessageClient.getPushMessage(Constant.SERVER_URL_FOR_SPRING, brokerId);
		} catch (Throwable e) {
			Utils.debug("pushMessageClient : " + e.toString());
		}
		return null;
	}

	@RestService
	RuleBannerClient ruleBannerClient;
	@Override
	public XcfcStringResult postForGetRuleBanner(int typeId) {
		try {
			ruleBannerClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(ruleBannerClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("typeId", typeId);

			return ruleBannerClient.getBannerBroker(Constant.SERVER_URL_FOR_SPRING, typeId);
		} catch (Throwable e) {
			Utils.debug("postForGetBannerBroker : " + e.toString());
		}
		return null;
	}

	@RestService
	QQBindClient qqBindClient;
	@Override
	public XcfcUserResult postForGetQQBind(String token, String clientPhone,
			String password, String id) {
		try {
			qqBindClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(qqBindClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("token", token);
			params.add("clientPhone", clientPhone);
			params.add("password", password);
			params.add("id", id);

			return qqBindClient.bindQQ(Constant.SERVER_URL_FOR_SPRING, token, clientPhone, password, id);
		} catch (Throwable e) {
			Utils.debug("postForGetQQBind : " + e.toString());
		}
		return null;
	}
	@RestService
	QQIsBindClient qqIsBindClient;
	@Override
	public XcfcUserResult postForCheckQQBind(String mqq) {
		try {
			qqIsBindClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(qqIsBindClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("mqq", mqq);

			return qqIsBindClient.isBindQQ(Constant.SERVER_URL_FOR_SPRING, mqq);
		} catch (Throwable e) {
			Utils.debug("postForCheckQQBind : " + e.toString());
		}
		return null;
	}

	@RestService 
	HuStyleClient huStyleClient;
	@Override
	public XcfcHuStyleResult postForGetHuStyle(String itemId) {
		try {
			huStyleClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(huStyleClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("itemId", itemId);
			
			return huStyleClient.getHuStyleResult(Constant.SERVER_URL_FOR_SPRING, itemId);
		} catch (Throwable e) {
			Utils.debug("postForGetHuStyle : " + e.toString());
		}
		return null;
	}

	@RestService 
	InfoBannerClient infoBannerClient;
	@Override
	public XcfcInfoBannerResult postForGetInfoBanner(String type) {
		try {
			infoBannerClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(infoBannerClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("type", type);
			
			return infoBannerClient.getInfoBanner(Constant.SERVER_URL_FOR_SPRING, type);
		} catch (Throwable e) {
			Utils.debug("postForGetInfoBanner : " + e.toString());
		}
		return null;
	}

	@RestService 
	AddSubInstClient addSubInstClient;
	@Override
	public XcfcAddSubInstResult postForAddSubInst(String phone,
			String superiorId, String screenName, String userName,
			String password, String brokerType) {
		try {
			addSubInstClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(addSubInstClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("phone", phone);
			params.add("superiorId", superiorId);
			params.add("screenName", screenName);
			params.add("password", password);
			params.add("brokerType", brokerType);
			
			return addSubInstClient.getSubInst(Constant.SERVER_URL_FOR_SPRING, phone, superiorId, screenName, userName, password, brokerType);
		} catch (Throwable e) {
			Utils.debug("postForAddSubInst : " + e.toString());
		}
		return null;
	}

	@RestService
	TeamClient teamClient;
	@Override
	public XcfcTeamResult postForGetTeamList(String id) {
		try {
			teamClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(teamClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("id", id);
			
			return teamClient.getTeamResult(Constant.SERVER_URL_FOR_SPRING, id);
		} catch (Throwable e) {
			Utils.debug("postForGetTeamList : " + e.toString());
		}
		return null;
	}

	@RestService 
	OrgBrokerDetailClient orgBrokerdetailClient;
	@Override
	public XcfcOrgBrokerDetailResult postForGetOrgBrokerInfo(String id) {
		try {
			orgBrokerdetailClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(orgBrokerdetailClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("id", id);
			
			return orgBrokerdetailClient.getOrgBrokerInfo(Constant.SERVER_URL_FOR_SPRING, id);
		} catch (Throwable e) {
			Utils.debug("postForGetOrgBrokerInfo : " + e.toString());
		}
		return null;
	}

	@RestService
	SubOrgStatusClient subOrgStatusClient;
	@Override
	public BaseResult postForGetSubOrgStatus(String id, String isDisabled) {
		try {
			subOrgStatusClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(subOrgStatusClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("id", id);
			
			return subOrgStatusClient.getSubOrgStatus(Constant.SERVER_URL_FOR_SPRING, id, isDisabled);
		} catch (Throwable e) {
			Utils.debug("postForGetSubOrgStatus : " + e.toString());
		}
		return null;
	}

	@RestService 
	ShareIntegralClient shareIntegralClient;
	@Override
	public BaseResult postForGetShareIntegral(String brokerId, String sourceId) {
		try {
			shareIntegralClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(shareIntegralClient, Timeout.TIMEOUT_THIRTY_SECONDS);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("brokerId", brokerId);
			params.add("sourceId", sourceId);

			return shareIntegralClient.getShareIntegral(Constant.SERVER_URL_FOR_SPRING, brokerId, sourceId);
		} catch (Throwable e) {
			Utils.debug("postForGetShareIntegral : " + e.toString());
		}
		return null;
	}

	@RestService
	ImageClient imageClient;
	@Override
	public XcfcImageReturnResult postForUploadPic(String fileKey, String imagePath) {
		try {
			imageClient.setHeader(HEADER_CONTENT_DISPOSITION, "form-data; name=\"" + fileKey + "\";");
			imageClient.setHeader(HEADER_CONTENT_TYPE, FORM_DATA);
			imageClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(imageClient, Timeout.TIMEOUT_ONE_MINUTE);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add(fileKey, new FileSystemResource(imagePath));

			return imageClient.uploadPic(Constant.SERVER_URL_FOR_SPRING, params);
		} catch (Throwable e) {
			Utils.debug("postForUploadPic : " + e.toString());
		}
		return null;
	}

	@Override
	public XcfcImageReturnResult postForUploadHeader(String fileKey, String imagePath, String userId) {
		try {
			imageClient.setHeader(HEADER_CONTENT_DISPOSITION, "form-data; name=\"" + fileKey + "\";");
			imageClient.setHeader(HEADER_CONTENT_TYPE, FORM_DATA);
			imageClient.setHeader(HEADER_CONTENT_ENCODING, CONTANT_CODE);
			setTimeout(imageClient, Timeout.TIMEOUT_ONE_MINUTE);

			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add(fileKey, new FileSystemResource(imagePath));

			return imageClient.uploadHeader(Constant.SERVER_URL_FOR_SPRING, userId, params);
		} catch (Throwable e) {
			Utils.debug("postForUploadHeader : " + e.toString());
		}
		return null;
	}
}
