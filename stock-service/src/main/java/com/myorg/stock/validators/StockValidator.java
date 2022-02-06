package com.myorg.stock.validators;

import com.myorg.stock.exception.StockBadRequestException;
import com.myorg.stock.model.Price;
import com.myorg.stock.model.Stock;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class StockValidator {

  private static int NAME_LENGTH = 64;
  private static BigDecimal MAX_AMOUNT = BigDecimal.valueOf(99999999);
  private static final String NAME_PATTERN = "^[ A-Za-z0-9_@./'&+-]*$";

  public static void validateStock(Stock stock) {

    if (stock == null) {
      throwException("Stock is not present");
    }
    if (stock.getName() == null || stock.getName().length() == 0) {
      throwException("Stock name is not present");
    }
    if (!stock.getName().matches(NAME_PATTERN)) {
      throwException("Some of the character in name field is not allowed");
    }

    if (stock.getName().length() > NAME_LENGTH) {
      throwException("Length of stock name is longer than allowed limit");
    }
    validatePrice(stock.getCurrentPrice());

  }

  public static void validatePrice(Price price) {
    if (price ==  null) {
      throwException("Current price is not present");
    }
    if(price.getAmount() == null) {
      throwException("Current price of stock has null amount");
    }
    if(price.getAmount().compareTo(BigDecimal.ZERO) == -1) {
      throwException("Current price of stock can not have negative amount");
    }

    if (price.getAmount().compareTo(MAX_AMOUNT) == 1) {
      throwException("Current price of stock contains amount beyond max limit");
    }
  }

  private static void throwException(String message) {
    throw new StockBadRequestException(message);
  }
}
