package com.camp.boot.configuration;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import io.shardingjdbc.core.api.ShardingDataSourceFactory;

import javax.sql.DataSource;
/**
 * Created by yangyibo on 17/1/18.
 */
@Configuration
public class DBconfig {
    @Autowired
    private Environment env;

    @Bean
    public ComboPooledDataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(env.getProperty("ms.db.driverClassName"));
        dataSource.setJdbcUrl(env.getProperty("ms.db.url"));
        dataSource.setUser(env.getProperty("ms.db.username"));
        dataSource.setPassword(env.getProperty("ms.db.password"));
        dataSource.setMaxPoolSize(20);
        dataSource.setMinPoolSize(5);
        dataSource.setInitialPoolSize(10);
        dataSource.setMaxIdleTime(300);
        dataSource.setAcquireIncrement(5);
        dataSource.setIdleConnectionTestPeriod(60);
        return dataSource;
    }
    
   /* @Bean(name="dataSourceS")
    @Primary*/
    public DataSource  shareDataSource() throws IOException
    {
    	/*ClassLoader classLoader = getClass().getClassLoader();
    	
    	URL url = classLoader.getResource("jdbc.yaml");*/
    	
    	Resource res = new ClassPathResource("jdbc.yaml");
    	
    	File jdbcFile = res.getFile();
    	DataSource dataSource = null;
		/*try {
			dataSource = ShardingDataSourceFactory.createDataSource(jdbcFile);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    	
    	return dataSource;
    }
    
}
