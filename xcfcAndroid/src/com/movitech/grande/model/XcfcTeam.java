package com.movitech.grande.model;
/**
 * @author Warkey.Song
 * @version 创建时间：2014年9月2日 下午1:42:31
 * 类说明
 */
public class XcfcTeam {

	private String name;
	private String id;
	private String pic;
	private String waitNums;
	private String recommands;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getWaitNums() {
		return waitNums;
	}

	public void setWaitNums(String waitNums) {
		this.waitNums = waitNums;
	}

	public String getRecommands() {
		return recommands;
	}

	public void setRecommands(String recommands) {
		this.recommands = recommands;
	}

	@Override
	public String toString() {
		return "XcfcTeam [name=" + name + ", id=" + id + ", pic=" + pic
				+ ", waitNums=" + waitNums + ", recommands=" + recommands + "]";
	}
}
