package com.ibm.pmc.ticket.common.crypto;

public class CryptoException extends RuntimeException {

    public CryptoException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
