package ru.job4j.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sql2o.Sql2o;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfig {

    @Bean
    public DataSource connectionPool(@Value("${database.source.url}") String url,
                                     @Value("${database.source.username}") String username,
                                     @Value("${database.source.password}") String password) {
        return new BasicDataSource() {
            {
                setUrl(url);
                setUsername(username);
                setPassword(password);
            }
        };
    }

    @Bean
    public Sql2o databaseClient(DataSource dataSource) {
        return new Sql2o(dataSource);
    }
}