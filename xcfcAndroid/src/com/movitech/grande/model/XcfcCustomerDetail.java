package com.movitech.grande.model;

import java.io.Serializable;
import java.util.Arrays;

public class XcfcCustomerDetail implements Serializable {

	private static final long serialVersionUID = -7028705334086050642L;

	XcfcMyCustomer client;
	ClientBuildingRelations[] clientBuildingRelations;

	public XcfcMyCustomer getClient() {
		return client;
	}

	public void setClient(XcfcMyCustomer client) {
		this.client = client;
	}

	public ClientBuildingRelations[] getClientBuildingRelations() {
		return clientBuildingRelations;
	}

	public void setClientBuildingRelations(
			ClientBuildingRelations[] clientBuildingRelations) {
		this.clientBuildingRelations = clientBuildingRelations;
	}

	@Override
	public String toString() {
		return "XcfcCustomerDetail [client=" + client
				+ ", clientBuildingRelations="
				+ Arrays.toString(clientBuildingRelations) + "]";
	}

}
