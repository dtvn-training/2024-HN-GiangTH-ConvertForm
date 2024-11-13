package com.example.convertform.config;

import com.gh.mygreen.xlsmapper.XlsMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanCustomConfig {
    @Bean
    public XlsMapper xlsMapperCfg() {
        return new XlsMapper();
    }
}
