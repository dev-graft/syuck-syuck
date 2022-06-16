package org.devgraft.support.provider;

public class SpyMD5Provider extends MD5Provider {
    public String encrypt_text_argument;
    public String encrypt_returnValue = "encrypt";

    @Override
    public String encrypt(String text) {
        this.encrypt_text_argument = text;
        return this.encrypt_returnValue;
    }
}
