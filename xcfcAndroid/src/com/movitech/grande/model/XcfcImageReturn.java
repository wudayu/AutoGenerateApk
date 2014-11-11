package com.movitech.grande.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @Created Time: Nov 7, 2014, 1:50:59 PM
 * @Description: David Wu created this file.
 *
 **/

@JsonIgnoreProperties(ignoreUnknown = true)
public class XcfcImageReturn {

		private String id;
		private String ralationId;
		private String oname;
		private String uname;

		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getRalationId() {
			return ralationId;
		}
		public void setRalationId(String ralationId) {
			this.ralationId = ralationId;
		}
		public String getOname() {
			return oname;
		}
		public void setOname(String oname) {
			this.oname = oname;
		}
		public String getUname() {
			return uname;
		}
		public void setUname(String uname) {
			this.uname = uname;
		}

		@Override
		public String toString() {
			return "XcfcImageReturn [id=" + id + ", ralationId=" + ralationId
					+ ", oname=" + oname + ", uname=" + uname + "]";
		}

}
