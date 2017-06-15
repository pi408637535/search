package com.stockemotion.search;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by piguanghua on 6/15/17.
 */
@SpringBootApplication
//@Configuration
//@Import({AutoConfig.class})
@MapperScan("com.stockemotion.search.mapper")
@ComponentScan({"com.stockemotion.search"})
public class SearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class, args);
    }
}
