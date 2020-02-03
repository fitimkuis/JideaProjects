package com.encrypt.decrypt;

import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class EncryptDecrypt {

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, IOException, InvalidKeyException, InvalidAlgorithmParameterException {
        String originalContent = "password";
        SecretKey secretKey = KeyGenerator.getInstance("AES").generateKey();

        FileEncrypterDecrypter fileEncrypterDecrypter = new FileEncrypterDecrypter(secretKey, "AES/CBC/PKCS5Padding");
        fileEncrypterDecrypter.encrypt(originalContent, "baz.enc");
        //String encryptedContent = fileEncrypterDecrypter.encrypt("baz.enc");

        String decryptedContent = fileEncrypterDecrypter.decrypt("baz.enc");
        System.out.println("decryptedContent: "+decryptedContent);
        assertThat(decryptedContent, is(originalContent));

        new File("baz.enc").delete(); // cleanup
    }


}
