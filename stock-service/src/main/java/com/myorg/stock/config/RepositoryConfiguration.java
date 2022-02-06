package com.myorg.stock.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories({
//        "com.myorg.stock.repository"
//})
//@EntityScan({
//        "com.myorg.stock.model"
//})
public class RepositoryConfiguration {

  /*  @Bean
    public DataSource dataSource() {

//        EntityManagerFactory


        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUsername("user");
        dataSource.setPassword("password");
        dataSource.setUrl(
                "jdbc:mysql://localhost:3306/db?createDatabaseIfNotExist=true");

        return dataSource;
    }*/


   /* @Bean
    public EntityManagerFactory entityManagerFactory()  {
        return Persistence.createEntityManagerFactory("PERSISTENCE");
    }*/
}
