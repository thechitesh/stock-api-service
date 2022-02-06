package com.myorg.stock.util;

import com.myorg.stock.model.Stock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Validator {


    public void validateStock(Stock stock) {
        log.info("About to validate Stock");
    }

}
