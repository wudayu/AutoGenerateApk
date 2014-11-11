package com.movitech.grande.model;

public class XcfcCertification {

	String id;
	String realName;
	String birthday;
	String sex;
	String bankName;
	String bankNum;
	String idCardImg;
	String idCardNegImg;
	String idCardNum;
	String photo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankNum() {
		return bankNum;
	}

	public void setBankNum(String bankNum) {
		this.bankNum = bankNum;
	}

	public String getIdCardImg() {
		return idCardImg;
	}

	public void setIdCardImg(String idCardImg) {
		this.idCardImg = idCardImg;
	}

	public String getIdCardNegImg() {
		return idCardNegImg;
	}

	public void setIdCardNegImg(String idCardNegImg) {
		this.idCardNegImg = idCardNegImg;
	}

	public String getIdCardNum() {
		return idCardNum;
	}

	public void setIdCardNum(String idCardNum) {
		this.idCardNum = idCardNum;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Override
	public String toString() {
		return "XcfcCertification [id=" + id + ", realName=" + realName
				+ ", birthday=" + birthday + ", sex=" + sex + ", bankName="
				+ bankName + ", bankNum=" + bankNum + ", idCardImg="
				+ idCardImg + ", idCardNegImg=" + idCardNegImg + ", idCardNum="
				+ idCardNum + ", photo=" + photo + "]";
	}

}
