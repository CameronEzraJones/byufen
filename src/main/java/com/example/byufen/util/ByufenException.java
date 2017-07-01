package com.example.byufen.util;

/**
 * Created by cejon on 6/30/2017.
 */
public class ByufenException extends Exception {
    public ByufenException() {
        super();
    }

    public ByufenException(String message) {
        super(message);
    }

    public ByufenException(String message, Throwable cause) {
        super(message, cause);
    }

    protected ByufenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ByufenException(Throwable cause) {
        super(cause);
    }
}
