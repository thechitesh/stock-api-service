package com.myorg.stock.exception;

public class StockBadRequestException extends RuntimeException {
    public StockBadRequestException(String message) {
        super(message);
    }
}
