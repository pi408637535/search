package com.stockemotion.search.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * Created by pigaunghua on 2016/12/7.
 */
@Configuration
public class AnalyzerService {

    @Bean
    public IKAnalyzer getAnalyzerService() {
        IKAnalyzer analyzer = new IKAnalyzer();
//使用智能分词
        analyzer.setUseSmart(true);
        return analyzer;
    }


}
