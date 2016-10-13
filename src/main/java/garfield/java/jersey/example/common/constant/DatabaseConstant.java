package garfield.java.jersey.example.common.constant;

public final class DatabaseConstant {
    
    public static final String DATASOURCE_NAME = "dataSource.name";
    public static final String JDBC_DRIVER_CLASS_NAME = "jdbc.driverClassName";
    public static final String JDBC_URL = "jdbc.url";
    public static final String JDBC_USERNAME = "jdbc.userName";
    public static final String JDBC_PASSWORD = "jdbc.password";
    public static final String INITIAL_SIZE = "initialSize";
    public static final String MIN_IDLE = "minIdle";
    public static final String MAX_ACTIVE = "maxActive";
    public static final String MAX_WAIT = "maxWait";
    public static final String TIME_BETWEEN_EVICTION_RUNS_MILLIS = "timeBetweenEvictionRunsMillis";
    public static final String MIN_EVICTAABLE_IDLE_TIME_MILLIS = "minEvictableIdleTimeMillis";
    public static final String TIME_BETWEEN_LOG_STATS_MILLIS = "timeBetweenLogStatsMillis";
    public static final String VALIDATION_QUERY = "validationQuery";
    public static final String TEST_WHILE_IDLE = "testWhileIdle";
    public static final String TEST_ON_BORROW = "testOnBorrow";
    public static final String TEST_ON_RETURN = "testOnReturn";
    public static final String POOL_PREPARED_STATEMENTS = "poolPreparedStatements";
    public static final String MAX_POOL_PREPARED_STATEMENT_PER_CONNECTION_SIZE = "maxPoolPreparedStatementPerConnectionSize";
    
    public static final String HIBERNATE_DIALECT = "hibernate.dialect";
    public static final String HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    public static final String HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
    public static final String HIBERNATE_CACHE_REGION_FACTORY = "hibernate.cache.region.factory_class";
    public static final String HIBERNATE_CACHE_SECOND_LEVEL = "hibernate.cache.use_second_level_cache";
    public static final String HIBERNATE_CACHE_QUERY_CACHE = "hibernate.cache.use_query_cache";
    public static final String HIBERNATE_GENARATE_STATISTICS = "hibernate.generate_statistics";
    public static final String HIBERNATE_CACHE_CONGIG_RESOURCE = "net.sf.ehcache.configurationResourceName";
    
    public static final String DB_MIGRATION_LOCATION = "classpath:db-migration";
    
    public static final String DB_PROPERTIES = "classpath:properties/database.properties";
}
