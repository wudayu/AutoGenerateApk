//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package com.movitech.grande.generic;

import android.content.Context;

public final class NetworkUtils_
    extends NetworkUtils
{

    private Context context_;

    private NetworkUtils_(Context context) {
        context_ = context;
        init_();
    }

    public static NetworkUtils_ getInstance_(Context context) {
        return new NetworkUtils_(context);
    }

    private void init_() {
        context = context_;
    }

    public void rebind(Context context) {
        context_ = context;
        init_();
    }

}