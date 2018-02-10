package com.tomoapp.tomowallet.model.wallet;

/**
 * Created by macbook on 12/21/17.
 */

public class Wallet {


    public String mnemonic;
    public String address;
    public String privateKey;

    public Wallet(String mnemonic, String address, String privateKey) {
        this.mnemonic = mnemonic;
        this.address = address;
        this.privateKey = privateKey;
    }

    public Wallet() {
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public String getAddress() {
        return address;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "mnemonic='" + mnemonic + '\'' +
                ", address='" + address + '\'' +
                ", privateKey='" + privateKey + '\'' +
                '}';
    }
}
