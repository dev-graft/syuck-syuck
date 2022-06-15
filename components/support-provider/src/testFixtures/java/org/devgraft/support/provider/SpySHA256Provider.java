package org.devgraft.support.provider;

public class SpySHA256Provider extends SHA256Provider {
    public String encrypt_text_argument;
    public String encrypt_hKey_argument;
    public String encrypt_returnValue = "encrypt";

    @Override
    public String encrypt(String text) {
        this.encrypt_text_argument = text;
        return this.encrypt_returnValue;
    }

    @Override
    public String encrypt(String text, String hKey) {
        this.encrypt_text_argument = text;
        this.encrypt_hKey_argument = hKey;
        return this.encrypt_returnValue;
    }
}
