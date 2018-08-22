package org.demo.imstargram.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "org.demo.imstargram.**.dao", sqlSessionFactoryRef = "sqlSessionFactory")
public class ImstargramDataSource {
    @Bean
    public DataSource dataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();

        basicDataSource.setUsername("imstar");
        basicDataSource.setPassword("1q2w3e4r!");
        basicDataSource.setUrl("jdbc:mysql://localhost:3306/imstar?useSSL=false&serverTimezone=UTC");
        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        return basicDataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        bean.setDataSource(dataSource);
        bean.setConfigLocation(resolver.getResource("classpath:/mybatis-config.xml"));
        bean.setMapperLocations(resolver.getResources("classpath*:/org/demo/imstargram/**/dao/*DAO.xml"));

        return bean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
