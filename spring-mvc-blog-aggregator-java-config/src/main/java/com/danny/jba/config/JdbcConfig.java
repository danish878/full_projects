package com.danny.jba.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration
@Order(1)
@ConfigurationProperties(prefix = "spring.datasource")
@Getter
@Setter
public class JdbcConfig {

//    @Value("${spring.datasource.url}")
//    private String url;
//    @Value("${spring.datasource.driver-class-name}")
//    private String driverClassName;
//    @Value("${spring.datasource.username}")
//    private String username;
//    @Value("${spring.datasource.password}")
//    private String password;
//
//    @Value("${spring.datasource.jndi.url}")
//    private String jndiUrl;

    private String url;
    private String driverClassName;
    private String username;
    private String password;
    private String jndiUrl;


//    Using 'Apache commons-dbcp2 JDBC connection pooling'
//    @Bean("dataSource")
//    @Profile("test")
//    public DataSource dataSourceTest() {
////        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setDriverClassName(driverClassName);
//        dataSource.setUrl(url);
//        dataSource.setUsername(username);
//        dataSource.setPassword(password);
//        return dataSource;
//    }

    //    Using 'C3p0 JDBC Connection pooling'
    @Bean("dataSource")
    @Profile("test")
    public DataSource dataSourceTest() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(driverClassName);
            dataSource.setJdbcUrl(url);
            dataSource.setUser(username);
            dataSource.setPassword(password);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return dataSource;
    }
//    public DataSource dataSourceTest() throws NamingException {
//        return (DataSource) new JndiTemplate().lookup(jndiUrl);
//    }

    @Bean("dataSource")
    @Profile("prod")
    public DataSource dataSourceProd() {
//        try {
//            URI dbUri = new URI(System.getenv("DATABASE_URL"));
//
//            username = dbUri.getUserInfo().split(":")[0];
//            password = dbUri.getUserInfo().split(":")[1];
//            url = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
        url = System.getenv("JDBC_DATABASE_URL");
        username = System.getenv("JDBC_DATABASE_USERNAME");
        password = System.getenv("JDBC_DATABASE_PASSWORD");

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }

    @Bean("dataSource")
    @Profile("dev")
    public DataSource dataSource2() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.HSQL).build();
    }

}