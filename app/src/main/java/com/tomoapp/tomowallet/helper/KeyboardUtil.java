package com.tomoapp.tomowallet.helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.tomoapp.tomowallet.base.MainApplication;

/**
 * Created by cityme on 1/10/18.
 */

public class KeyboardUtil {
    /**
     * Use to hide keyboard system
     *
     * @param activity
     */
    public static void hideSoftKeyboard(final Activity activity) {
        try {
            if(activity == null) return;
            new Handler().post(new Runnable() {

                @Override
                public void run() {
                    InputMethodManager inputMethodManager = (InputMethodManager) activity
                            .getSystemService(Activity.INPUT_METHOD_SERVICE);
                    View view = activity.getCurrentFocus();
                    if (view != null) {
                        IBinder binder = view.getWindowToken();
                        if (binder != null) {
                            inputMethodManager.hideSoftInputFromWindow(binder, 0);
                        }
                    }
                }
            });
        }catch (Exception e){
            LogUtil.e(e);
        }

    }

    public static void hideSoftKeyboard(final Context activity) {
        try {
            if(activity == null) return;
            new Handler().post(new Runnable() {

                @Override
                public void run() {
                    InputMethodManager inputMethodManager = (InputMethodManager) activity
                            .getSystemService(Activity.INPUT_METHOD_SERVICE);
                    if (activity instanceof Activity){
                        View view = ((Activity)activity).getCurrentFocus();
                        if (view != null) {
                            IBinder binder = view.getWindowToken();
                            if (binder != null) {
                                inputMethodManager.hideSoftInputFromWindow(binder, 0);
                            }
                        }
                    }
                    else if (activity instanceof MainApplication){
                        MainApplication application = (MainApplication)activity;
                        if (application != null && application.getCurrentActivity() == null) return;
                        View view = application.getCurrentActivity().getCurrentFocus();
                        if (view != null) {
                            IBinder binder = view.getWindowToken();
                            if (binder != null) {
                                inputMethodManager.hideSoftInputFromWindow(binder, 0);
                            }
                        }
                    }
                }
            });
        }catch (Exception e){
            LogUtil.e(e);
        }

    }

    public static void hideSoftKeyboard(final Dialog dialog) {
        if(dialog == null) return;
        new Handler().post(new Runnable() {

            @Override
            public void run() {
                InputMethodManager inputMethodManager = (InputMethodManager) dialog
                        .getContext().getSystemService(
                                Activity.INPUT_METHOD_SERVICE);
                View view = dialog.getCurrentFocus();
                if (view != null) {
                    IBinder binder = view.getWindowToken();
                    if (binder != null) {
                        inputMethodManager.hideSoftInputFromWindow(binder, 0);
                    }
                }
            }
        });

    }

    /**
     * Clear focus and hide keyboard
     *
     * @param view
     * @param event
     * @param text
     */
    public static void clearFocus(View view, MotionEvent event, View text) {
        if (text.isFocused()) {
            Rect outRect = new Rect();
            text.getGlobalVisibleRect(outRect);
            if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                text.clearFocus();
                InputMethodManager imm = (InputMethodManager) view.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    /**
     * @param view
     * @param activity
     */
    public static void showSoftKeyboard(final Activity activity, final View view) {
        if(activity == null || view == null) return;
        new Handler().post(new Runnable() {

            @Override
            public void run() {
                if (view.hasFocus()) {
                    view.clearFocus();
                }
                view.requestFocus();
                InputMethodManager imm = (InputMethodManager) activity
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
            }
        });
    }

    public static void showSoftKeyboard(final Dialog dialog, final View view) {
        new Handler().post(new Runnable() {

            @Override
            public void run() {
                if (view.hasFocus()) {
                    view.clearFocus();
                }
                view.requestFocus();
                InputMethodManager imm = (InputMethodManager) dialog
                        .getContext().getSystemService(
                                Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
            }
        });
    }
}
