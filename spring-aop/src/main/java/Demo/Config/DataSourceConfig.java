package Demo.Config;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.StringUtils;

import javax.activation.DataSource;

@Configuration
public class DataSourceConfig {
    /**
     * 创建 orders 数据源的配置对象
     */
    @Primary
    @Bean(name = "dataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource") // 读取 spring.datasource.orders 配置到 DataSourceProperties 对象
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * 创建 orders 数据源
     */
    @Bean(name = "dataSourcessss")
    @ConfigurationProperties(prefix = "spring.datasource.hikari") // 读取 spring.datasource.orders 配置到 HikariDataSource 对象
    public HikariDataSource dataSource() {
        // <1.1> 获得 DataSourceProperties 对象
        DataSourceProperties properties =  this.dataSourceProperties();
        // <1.2> 创建 HikariDataSource 对象
        return createHikariDataSource(properties);
    }

    private static HikariDataSource createHikariDataSource(DataSourceProperties properties) {
        // 创建 HikariDataSource 对象
        HikariDataSource dataSource = properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        // 设置线程池名
        if (StringUtils.hasText(properties.getName())) {
            dataSource.setPoolName(properties.getName());
        }
        return dataSource;
    }


}
