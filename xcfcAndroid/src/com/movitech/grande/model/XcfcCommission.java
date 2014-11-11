package com.movitech.grande.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author Warkey.Song
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class XcfcCommission {
	String id;
	String status;// 佣金状态
	String clientId;// 客户id
	String brokerName;
	String clientPhone;
	String buildingId;// 楼盘id
	String contractNo;
	String brokerId;
	String clientName;// 推荐客户
	String amount;
	String superBrokerId;
	String finalAmount;// 佣金
	String buildingName;// 楼盘名
	String saleId;
	String remark;
	String createTime;
	String createDate;
	String financeDate;
	String activityAllowance;
	String activityDescribe;
	String editAmount;
	String batchId;
	String brokerPhone;
	String brokerScreenName;
	String recheakMark;
	String rechecker;
	String dealAmount;
	String applyTime;
	String dynamicBrokerageImportId;
	String dealRoomNum;
	String brokerIcard;
	
	public String getBrokerIcard() {
		return brokerIcard;
	}
	public void setBrokerIcard(String brokerIcard) {
		this.brokerIcard = brokerIcard;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getBrokerName() {
		return brokerName;
	}
	public void setBrokerName(String brokerName) {
		this.brokerName = brokerName;
	}
	public String getClientPhone() {
		return clientPhone;
	}
	public void setClientPhone(String clientPhone) {
		this.clientPhone = clientPhone;
	}
	public String getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getBrokerId() {
		return brokerId;
	}
	public void setBrokerId(String brokerId) {
		this.brokerId = brokerId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getSuperBrokerId() {
		return superBrokerId;
	}
	public void setSuperBrokerId(String superBrokerId) {
		this.superBrokerId = superBrokerId;
	}
	public String getFinalAmount() {
		return finalAmount;
	}
	public void setFinalAmount(String finalAmount) {
		this.finalAmount = finalAmount;
	}
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public String getSaleId() {
		return saleId;
	}
	public String getDealRoomNum() {
		return dealRoomNum;
	}
	public void setDealRoomNum(String dealRoomNum) {
		this.dealRoomNum = dealRoomNum;
	}
	public void setSaleId(String saleId) {
		this.saleId = saleId;
	}
	public String getRemark() {
		return remark;
	}
	public String getDynamicBrokerageImportId() {
		return dynamicBrokerageImportId;
	}
	public void setDynamicBrokerageImportId(String dynamicBrokerageImportId) {
		this.dynamicBrokerageImportId = dynamicBrokerageImportId;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getFinanceDate() {
		return financeDate;
	}
	public void setFinanceDate(String financeDate) {
		this.financeDate = financeDate;
	}
	public String getActivityAllowance() {
		return activityAllowance;
	}
	public void setActivityAllowance(String activityAllowance) {
		this.activityAllowance = activityAllowance;
	}
	public String getActivityDescribe() {
		return activityDescribe;
	}
	public void setActivityDescribe(String activityDescribe) {
		this.activityDescribe = activityDescribe;
	}
	public String getEditAmount() {
		return editAmount;
	}
	public void setEditAmount(String editAmount) {
		this.editAmount = editAmount;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getBrokerPhone() {
		return brokerPhone;
	}
	public void setBrokerPhone(String brokerPhone) {
		this.brokerPhone = brokerPhone;
	}
	public String getBrokerScreenName() {
		return brokerScreenName;
	}
	public void setBrokerScreenName(String brokerScreenName) {
		this.brokerScreenName = brokerScreenName;
	}
	public String getRecheakMark() {
		return recheakMark;
	}
	public void setRecheakMark(String recheakMark) {
		this.recheakMark = recheakMark;
	}
	public String getRechecker() {
		return rechecker;
	}
	public void setRechecker(String rechecker) {
		this.rechecker = rechecker;
	}
	public String getDealAmount() {
		return dealAmount;
	}
	public void setDealAmount(String dealAmount) {
		this.dealAmount = dealAmount;
	}
	public String getApplyTime() {
		return applyTime;
	}
	
	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	@Override
	public String toString() {
		return "XcfcCommission [id=" + id + ", status=" + status
				+ ", clientId=" + clientId + ", brokerName=" + brokerName
				+ ", clientPhone=" + clientPhone + ", buildingId=" + buildingId
				+ ", contractNo=" + contractNo + ", brokerId=" + brokerId
				+ ", clientName=" + clientName + ", amount=" + amount
				+ ", superBrokerId=" + superBrokerId + ", finalAmount="
				+ finalAmount + ", buildingName=" + buildingName + ", saleId="
				+ saleId + ", remark=" + remark + ", createTime=" + createTime
				+ ", createDate=" + createDate + ", financeDate=" + financeDate
				+ ", activityAllowance=" + activityAllowance
				+ ", activityDescribe=" + activityDescribe + ", editAmount="
				+ editAmount + ", batchId=" + batchId + ", brokerPhone="
				+ brokerPhone + ", brokerScreenName=" + brokerScreenName
				+ ", recheakMark=" + recheakMark + ", rechecker=" + rechecker
				+ ", dealAmount=" + dealAmount + ", applyTime=" + applyTime
				+ ", dynamicBrokerageImportId=" + dynamicBrokerageImportId
				+ ", dealRoomNum=" + dealRoomNum + "]";
	}
	
}
