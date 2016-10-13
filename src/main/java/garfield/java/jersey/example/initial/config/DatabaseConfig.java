package garfield.java.jersey.example.initial.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.CommonsLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;

import garfield.java.jersey.example.common.constant.DatabaseConstant;
import garfield.java.jersey.example.common.constant.PackageConstant;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {
    
    @Resource
    private Environment env;
    
    @Bean(initMethod = "init", destroyMethod = "close")
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setName(env.getProperty(DatabaseConstant.DATASOURCE_NAME));
        dataSource.setDriverClassName(env.getProperty(DatabaseConstant.JDBC_DRIVER_CLASS_NAME));
        dataSource.setUrl(env.getProperty(DatabaseConstant.JDBC_URL));
        dataSource.setUsername(env.getProperty(DatabaseConstant.JDBC_USERNAME));
        dataSource.setPassword(env.getProperty(DatabaseConstant.JDBC_PASSWORD));
        dataSource.setInitialSize(Integer.valueOf(env.getProperty(DatabaseConstant.INITIAL_SIZE)));
        dataSource.setMinIdle(Integer.valueOf(env.getProperty(DatabaseConstant.MIN_IDLE)));
        dataSource.setMaxActive(Integer.valueOf(env.getProperty(DatabaseConstant.MAX_ACTIVE)));
        dataSource.setMaxWait(Integer.valueOf(env.getProperty(DatabaseConstant.MAX_WAIT)));
        dataSource.setTimeBetweenEvictionRunsMillis(Long.valueOf(env.getProperty(DatabaseConstant.TIME_BETWEEN_EVICTION_RUNS_MILLIS)));
        dataSource.setMinEvictableIdleTimeMillis(Long.valueOf(env.getProperty(DatabaseConstant.MIN_EVICTAABLE_IDLE_TIME_MILLIS)));
        dataSource.setTimeBetweenLogStatsMillis(Long.valueOf(env.getProperty(DatabaseConstant.TIME_BETWEEN_LOG_STATS_MILLIS)));
        dataSource.setValidationQuery(env.getProperty(DatabaseConstant.VALIDATION_QUERY));
        dataSource.setTestWhileIdle(Boolean.valueOf(env.getProperty(DatabaseConstant.TEST_WHILE_IDLE)));
        dataSource.setTestOnBorrow(Boolean.valueOf(env.getProperty(DatabaseConstant.TEST_ON_BORROW)));
        dataSource.setTestOnReturn(Boolean.valueOf(env.getProperty(DatabaseConstant.TEST_ON_RETURN)));
        dataSource.setPoolPreparedStatements(Boolean.valueOf(env.getProperty(DatabaseConstant.POOL_PREPARED_STATEMENTS)));
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(Integer.valueOf(env.getProperty(DatabaseConstant.MAX_POOL_PREPARED_STATEMENT_PER_CONNECTION_SIZE)));
        List<Filter> proxyFilter = new ArrayList<Filter>(0);
        proxyFilter.add(statFilter());
        proxyFilter.add(logFilter());
        dataSource.setProxyFilters(proxyFilter);
        return dataSource;
    }
    
    @Bean
    public StatFilter statFilter() {
        StatFilter statFilter = new StatFilter();
        statFilter.setSlowSqlMillis(1000);
        statFilter.setLogSlowSql(true);
        return statFilter;
    }
    
    @Bean
    public CommonsLogFilter logFilter() {
        CommonsLogFilter logFilter = new CommonsLogFilter();
        return logFilter;
    }
    
    @Bean
    public Flyway flyway() {
        Flyway flyway = new Flyway();
        // before Flyway do the db migrate, need set dataSource first.
        flyway.setDataSource(dataSource());
        flyway.clean();
        flyway.setBaselineOnMigrate(true);
        flyway.setValidateOnMigrate(true);
        flyway.setLocations(DatabaseConstant.DB_MIGRATION_LOCATION);
        flyway.setSqlMigrationPrefix("^");
        flyway.setSqlMigrationSeparator("@");
        flyway.setTable("DB_VERSION");
        flyway.migrate();
        flyway.repair();
        return flyway;
    }
    
    @Bean
    @DependsOn("flyway")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan(PackageConstant.ENTITY_PACKAGE);
        entityManagerFactoryBean.setJpaProperties(jpaProperties());
        return entityManagerFactoryBean;
    }
    
    private Properties jpaProperties() {
        Properties properties = new Properties();
        properties.put(DatabaseConstant.HIBERNATE_DIALECT, env.getProperty(DatabaseConstant.HIBERNATE_DIALECT));
        properties.put(DatabaseConstant.HIBERNATE_SHOW_SQL, env.getProperty(DatabaseConstant.HIBERNATE_SHOW_SQL));
        properties.put(DatabaseConstant.HIBERNATE_FORMAT_SQL, env.getProperty(DatabaseConstant.HIBERNATE_FORMAT_SQL));
        properties.put(DatabaseConstant.HIBERNATE_CACHE_REGION_FACTORY, env.getProperty(DatabaseConstant.HIBERNATE_CACHE_REGION_FACTORY));
        properties.put(DatabaseConstant.HIBERNATE_CACHE_SECOND_LEVEL, env.getProperty(DatabaseConstant.HIBERNATE_CACHE_SECOND_LEVEL));
        properties.put(DatabaseConstant.HIBERNATE_CACHE_QUERY_CACHE, env.getProperty(DatabaseConstant.HIBERNATE_CACHE_QUERY_CACHE));
        properties.put(DatabaseConstant.HIBERNATE_GENARATE_STATISTICS, env.getProperty(DatabaseConstant.HIBERNATE_GENARATE_STATISTICS));
        properties.put(DatabaseConstant.HIBERNATE_CACHE_CONGIG_RESOURCE, env.getProperty(DatabaseConstant.HIBERNATE_CACHE_CONGIG_RESOURCE));
        return properties;
    }
    
    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
}
