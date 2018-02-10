package com.tomoapp.tomowallet.ui.backupWallet;

import com.tomoapp.tomowallet.model.wallet.WalletDataSource;
import com.tomoapp.tomowallet.model.wallet.WalletRepository;
import com.tomoapp.tomowallet.ui.checkPassword.CheckPasswordDialog;

/**
 * Created by cityme on 1/10/18.
 */

public class BackupWalletPresenter implements BackupWalletContract.Presenter , CheckPasswordDialog.CheckPinDialogListener{

    private BackupWalletContract.View mView;
    private CheckPasswordDialog dialog;
    private WalletDataSource mWallet;
    public BackupWalletPresenter(BackupWalletContract.View mView) {
        this.mView = mView;
        this.dialog = CheckPasswordDialog.newInstance(this);
        this.mWallet = new WalletRepository(mView.getContext());
    }

    @Override
    public void init() {
        mView.setContent();
    }

    @Override
    public void onCheckBtnPressed() {
        dialog.show();
    }

    @Override
    public void onPinChecked(String Pin) {
        mView.showMnemonic(mWallet.getMnemonic(Pin), Pin);
    }

    @Override
    public void onCancel() {

    }
}
