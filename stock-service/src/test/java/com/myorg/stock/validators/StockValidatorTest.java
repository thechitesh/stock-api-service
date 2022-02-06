package com.myorg.stock.validators;

import com.myorg.stock.exception.StockBadRequestException;
import com.myorg.stock.model.Price;
import com.myorg.stock.model.Stock;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static com.myorg.stock.util.ModelProvider.createPrice;
import static com.myorg.stock.util.ModelProvider.createStock;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StockValidatorTest {


  private static  String longName = "Stock Name is too long and is not allowed" +
      "Stock Name is too long and is not allowed Stock Name is too long and is not allowed";

  private static Stream<Arguments> generateStockNameTestCases() {
    return Stream.of(
        Arguments.of("", "Stock name is not present"),
        Arguments.of( "^*()$%",  "Some of the character in name field is not allowed"),
        Arguments.of(longName, "Length of stock name is longer than allowed limit"),
        Arguments.of(longName, "Length of stock name is longer than allowed limit")

    );
  }

  @Test
  void shouldPassAllValidation() {
    Stock stockByName = createStockByName("Valid Name 1");
    StockValidator.validateStock(stockByName);
  }

  @ParameterizedTest
  @NullSource
  void shouldValidateSock(Stock stock) {
    Exception exception= assertThrows(StockBadRequestException.class, () -> StockValidator.validateStock(stock));
    assertWithMessage(exception, "Stock is not present");
  }

  @MethodSource("generateStockNameTestCases")
  @ParameterizedTest
  void shouldValidateStockName(String stockName, String expectedErrorMessage) {
    Stock stockByName = createStockByName(stockName);
    Exception exception = assertThrows(StockBadRequestException.class, () -> StockValidator.validateStock(stockByName));
    assertWithMessage(exception, expectedErrorMessage);
  }

  @Test
  void shouldValidatePrice_ForNull() {
    Exception exception = assertThrows(StockBadRequestException.class, () -> StockValidator.validatePrice(null));
    assertWithMessage(exception, "Current price is not present");
  }


  private static Stream<Arguments> generatePriceTestCases() {
    return Stream.of(
        Arguments.of(-1.0d, "Current price of stock can not have negative amount"),
        Arguments.of( 9999999999d,  "Current price of stock contains amount beyond max limit")

    );
  }

  @ParameterizedTest
  @MethodSource("generatePriceTestCases")
  void shouldValidatePrice_ForAmount(final Double amount, String expectedErrorMessage) {
    val bigAmount = BigDecimal.valueOf(amount);
    Price price = createPrice(bigAmount);
    Exception exception = assertThrows(StockBadRequestException.class, () -> StockValidator.validatePrice(price));
    assertWithMessage(exception, expectedErrorMessage);
  }

  private void assertWithMessage (Exception exception, String expectedMessage) {
    assertTrue(exception.getMessage().contains(expectedMessage));
  }

  private Stock createStockByName(String name) {
    return createStock(1L, name, BigDecimal.TEN);
  }

}