package com.movitech.grande.net;

import com.movitech.grande.model.XcfcHouseBanner;
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
 * @Created Time: Jun 18, 2014 5:20:40 PM
 * @Description: This is David Wu's property.
 * 
 **/
public interface INetHandler {

	public XcfcUserResult postForUserLoginResult(String username,String password);

	public XcfcCitiesResult postForGetCitysResult();

	public XcfcDistrictsResult postForGetDistrictsResult(String cityName);

	public XcfcHousesResult postForGetHousesResult(int pageNo, String city,String district, String name, String isRecommend, String isHot);

	public XcfcHousesResult postForGetHousesResultAll(int pageNo);

	public XcfcInfoesResult postForGetInfoesResult(int pageNo, String catagoryId,String isHot, String houseId);

	public XcfcInfoesResult postForGetHotActionsResult(int pageNo, String catagoryId,String isHot, String houseId);

	public BaseResult postForRePasswordResult(String username,String oldpassword, String newpassword);

	public BaseResult postForReNameResult(String id, String name);

	public XcfcCertificationResult postForCertificationResult(String id,String realName, String birthday, String sex, String bankName,String bankNum, String idCardNum, String idCardImg,String idCardNegImg);

	public XcfcMyCustomerResult postForGetMyCustomersResult(int pageNo,String id, String isVip, String isVisited, String isSignup,String state);
	
	public XcfcHousesDetailResult postForGetHousesDetailResult(String id, String operatorId);

	public XcfcCommissionResult postForCommissionResult(int pageNo, String brokerId, String status);
	
	public XcfcCommissionApplyResult postForCommissionApplyResult(String brokerId, String id);
	
	public XcfcCustomerDetailResult postForGetCustomerDetailResult(String brokerId,String clientId, String status);

	public XcfcInfoDetailResult postForGetInfoDetailResult(String infoId, String operatorId);
	
	public XcfcIntegralResult postForGetIntegralResult(String brokerId);
    
	public XcfcRecommendedResult postForGetRecommendResult(String clientName, String clientPhone, String recommendedBuilding, String brokerId, String recommendMark);
	
	public XcfcAppointmentResult postForGetAppointmentResult(String clientName, String clientPhone, String recommendedBuilding, String brokerId, String bespeakMark, String bespeakTime);

	public XcfcBrokerDetailResult postForGetBrokerDetail(String brokerId);
    
	public XcfcFavBuildResult postForGetFavBuild(String brokerId, String relationId, String isLike, String sourceType);
	
	public XcfcHousesCollectionResult postForGetFocusBuild(String brokerId);
	
	public XcfcCancelFavBuild postForGetCancelFavBuild(String brokerId, String relationId);
	
	public XcfcIsCollectResult postForGetIsCollect(String brokerId, String relationId);

	public XcfcGuestIdResult postForGetGuestIdResult(String deviceId);

	public BaseResult postForGetVerificationCode(String phone);

	public XcfcVerificationCodeResult postForCheckVerificationCode(String phone, String code);

	public BaseResult postForRegBroker(String userId, String clientName, String clientPhone, String qq, String weixin, String deviceId, String brokerType, String password, String city, String buildId);
	
	public XcfcForgetPassWordResult postForgetNewPassWord(String username, String newpassword);
	
	public XcfcServiceTermsResult postForGetServiceTerms(int type);

	public XcfcVersionResult postForGetVersion(int type);

	public XcfcStringResult postForGetLatestTime(String type);

	public XcfcStringResult postForGetUnreadCount(String userId);

	public XcfcMyMessageResult postForGetMyMessages(int pageNo, String userId);
	
	public XcfcDayMarkResult postForCheckDayMark(String id);
	
	public XcfcClientResult postForGetClients(int pageNum, String brokerId, String name);
	
	public XcfcCommissionConfirmResult postForCheckConfirm(String brokerId, String id);
	
	public XcfcEarnIntegralResult postForGetEarnIntegral(String brokerId);
	
	public XcfcHouseBanner[] postForGetHouseBanner(String type, String city);

	public XcfcPushMessageResult postForGetPushMessage(String brokerId);

	public XcfcStringResult postForGetRuleBanner(int typeId);
	
	public XcfcUserResult postForGetQQBind(String token, String clientPhone, String password, String id);
	
	public XcfcUserResult postForCheckQQBind(String mqq);
	
	public XcfcHuStyleResult postForGetHuStyle(String itemId);
	
	public XcfcInfoBannerResult postForGetInfoBanner(String type);
	
	public XcfcAddSubInstResult postForAddSubInst(String phone, String superiorId, String screenName, String userName, String password, String brokerType);
	
	public XcfcTeamResult postForGetTeamList(String id);
	
	public XcfcOrgBrokerDetailResult postForGetOrgBrokerInfo(String id);
	
	public BaseResult postForGetSubOrgStatus(String id, String isDisabled);
	
	public BaseResult postForGetShareIntegral(String brokerId, String sourceId);

	//post 注册
	public BaseResult postForPostRegBroker(String userId, String clientName,String clientPhone, String qq, String weixin, String deviceId,String brokerType, String password, String city, String buildId);

	/** 图片上传 */
	public XcfcImageReturnResult postForUploadPic(String fileKey, String imagePath);

	/** 头像上传 */
	public XcfcImageReturnResult postForUploadHeader(String fileKey, String imagePath, String userId);
}
