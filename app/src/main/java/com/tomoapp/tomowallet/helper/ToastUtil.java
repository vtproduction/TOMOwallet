package com.tomoapp.tomowallet.helper;

import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.tomoapp.tomowallet.R;
import com.tomoapp.tomowallet.base.MainApplication;

/**
 * Created by macbook on 12/21/17.
 */

public class ToastUtil {
    private static Toast toast;

    public static void show(String text) {
        if (MainApplication.get() == null) return;
        if (text.equals("")){
            //LogUtil.e("TOAST không hiển thị gì ???");
            return;
        }
        toast = Toast.makeText(MainApplication.get(), text, Toast.LENGTH_SHORT);
        //NIENLM: centered the text inside toast.
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        if( v != null) v.setGravity(Gravity.CENTER);
        if (toast != null && toast.getView() != null && !toast.getView().isShown())
            toast.show();
    }

    public static void show(int textId) {
        if (MainApplication.get() == null) return;
        toast = Toast.makeText(MainApplication.get(), MainApplication.get().getString(textId), Toast.LENGTH_SHORT);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        if( v != null) v.setGravity(Gravity.CENTER);
        if (toast != null && toast.getView() != null && !toast.getView().isShown())
            toast.show();
    }
    public static void showError() {
        show(R.string.dacoloixayra);
    }

    public static void showError(Throwable t) {
        LogUtil.e(t);
        show(R.string.dacoloixayra);
    }
}
