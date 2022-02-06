package com.myorg.stock.exception;

//@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class StockBadRequestException extends RuntimeException {

    public StockBadRequestException(String message) {
        super(message);
    }

}
