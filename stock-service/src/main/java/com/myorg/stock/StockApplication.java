package com.myorg.stock;

import com.myorg.stock.config.StockProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

//@EnableTransactionManagement
//@EnableJpaRepositories
//@EnableSwagger2
//@Import(BeanValidationPostProcessor.class)
@SpringBootApplication
@EnableConfigurationProperties(StockProperties.class)
public class StockApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockApplication.class, args);
    }
}
