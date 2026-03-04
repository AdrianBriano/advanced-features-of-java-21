package com.example;


import javax.crypto.DecapsulateException;
import javax.crypto.KEM;
import java.security.InvalidKeyException;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, DecapsulateException {

        final var keyPairGenerator = KeyPairGenerator.getInstance("X25519");
        final var keyPair = keyPairGenerator.generateKeyPair();
        final var privateKey = keyPair.getPrivate();
        final var publicKey = keyPair.getPublic();

        final var senderKem = KEM.getInstance("DHKEM");
        final var sender = senderKem.newEncapsulator(publicKey);
        final var encapsulated = sender.encapsulate();
        final var secretKey = encapsulated.key();

        final var receiversKEM = KEM.getInstance("DHKEM");
        final var receiver = receiversKEM.newDecapsulator(privateKey);
        final var receivedSecretKey = receiver.decapsulate(encapsulated.encapsulation());

        if (Arrays.equals(secretKey.getEncoded(), receivedSecretKey.getEncoded())) {
            System.out.println("Keys match");
        } else  {
            System.out.println("Keys not match");
        }
    }

}
