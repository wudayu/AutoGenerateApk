package com.movitech.grande.generic.interfaces;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jul 16, 2014 10:03:21 AM
 * @Description: This is David Wu's property.
 *
 **/

public interface ISDCard {
	public boolean isSdcardAvaliable();

	public boolean isAvaiableSpace(long size);
}
