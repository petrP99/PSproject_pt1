package com.pers.config;

import com.pers.util.HibernateTestUtil;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Proxy;


@Configuration
@ComponentScan(basePackages = "com.pers.repository")
public class ApplicationConfigurationIT {

    @Bean(destroyMethod = "close")
    public SessionFactory getSessionFactory() {
        return HibernateTestUtil.buildSessionFactory();
    }

    @Bean
    public EntityManager entityManager(SessionFactory sessionFactory) {
        return (EntityManager) Proxy.newProxyInstance(
                SessionFactory.class.getClassLoader(),
                new Class[]{Session.class},
                (proxy, method, args) -> method.invoke(sessionFactory.getCurrentSession(), args)
        );
    }
}