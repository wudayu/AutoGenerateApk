package com.movitech.grande.net.protocol;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.movitech.grande.model.XcfcTeam;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年9月2日 下午1:45:08
 * 类说明
 */
public class XcfcTeamResult extends BaseResult{
	@JsonProperty(value="objValue")
	XcfcTeam [] teams;
	
	@JsonProperty(value="ok")
	boolean ok;
	
	public XcfcTeam[] getTeams() {
		return teams;
	}

	public void setTeams(XcfcTeam[] teams) {
		this.teams = teams;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	@Override
	public String toString() {
		return "XcfcTeamResult [teams=" + Arrays.toString(teams) + ", ok=" + ok
				+ "]";
	}
	
}
