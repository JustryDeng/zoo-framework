package com.ideaaedi.zoo.diy.artifact.websocket.exception;

public class WebSocketAuthException extends RuntimeException{
    
    public WebSocketAuthException() {
        super();
    }
    
    public WebSocketAuthException(String message) {
        super(message);
    }
    
    public WebSocketAuthException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public WebSocketAuthException(Throwable cause) {
        super(cause);
    }
    
    protected WebSocketAuthException(String message, Throwable cause, boolean enableSuppression,
                                     boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
