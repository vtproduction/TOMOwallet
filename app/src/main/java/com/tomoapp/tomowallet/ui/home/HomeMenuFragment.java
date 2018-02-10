package com.tomoapp.tomowallet.ui.home;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tomoapp.tomowallet.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by macbook on 12/25/17.
 */

public class HomeMenuFragment extends BottomSheetDialogFragment {


    String mString;
    @BindView(R.id.txt_view_address)
    TextView txtViewAddress;
    @BindView(R.id.txt_view_mnemonic)
    TextView txtViewMnemonic;
    @BindView(R.id.txt_delete_wallet)
    TextView txtDeleteWallet;

    static final int CODE_VIEW_ADDRESS = 1;
    static final int CODE_VIEW_MNEMONIC = 2;
    static final int CODE_DELETE_ADDRESS = 3;


    public interface OnMenuClickListener{
        void onMenuClicked(int code);
    }


    private OnMenuClickListener callback;
    public static HomeMenuFragment newInstance(String string, OnMenuClickListener callback) {
        HomeMenuFragment f = new HomeMenuFragment();
        Bundle args = new Bundle();
        args.putString("string", string);
        f.setArguments(args);
        f.callback = callback;
        return f;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mString = getArguments().getString("string");
    }


    Unbinder unbinder;

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.fragment_home_menu, null);
        dialog.setContentView(contentView);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent())
                .getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();
        ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        unbinder = ButterKnife.bind(this, contentView);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



    @OnClick({R.id.txt_view_address, R.id.txt_view_mnemonic, R.id.txt_delete_wallet})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_view_address:
                callback.onMenuClicked(CODE_VIEW_ADDRESS);
                break;
            case R.id.txt_view_mnemonic:
                callback.onMenuClicked(CODE_VIEW_MNEMONIC);
                break;
            case R.id.txt_delete_wallet:
                callback.onMenuClicked(CODE_DELETE_ADDRESS);
                break;
        }
    }
}
