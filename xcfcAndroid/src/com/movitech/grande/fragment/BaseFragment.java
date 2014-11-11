package com.movitech.grande.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * 
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 10, 2014 10:21:15 AM
 * @Description: This is David Wu's property.
 * 
 **/
public class BaseFragment extends Fragment {

	Context context = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		context = getActivity();

		super.onCreate(savedInstanceState);
	}

}
