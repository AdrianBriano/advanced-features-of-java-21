package com.example;

import javax.crypto.DecapsulateException;
import javax.crypto.KEM;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Receiver {

    byte[] getKey(KEM.Encapsulated encapsulated, java.security.PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, DecapsulateException {
        // Create a new instance of the KEM with the algorithm name "DHKEM"
        final var receiverKem = KEM.getInstance("DHKEM");
        // Create a new Decapsulator using the private key
        final var decapsulator = receiverKem.newDecapsulator(privateKey);
        // Decapsulate the encapsulated key
        final var decapsulated = decapsulator.decapsulate(encapsulated.encapsulation());
        // Return the encoded secret key
        return decapsulated.getEncoded();
    }


}
