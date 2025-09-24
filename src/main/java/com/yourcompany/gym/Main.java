package com.yourcompany.gym;

import com.yourcompany.gym.config.AppConfig;
import org.apache.catalina.Context;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.File;

public class Main {

    public static void main(String[] args) throws Exception {
        // 1. Настройка Tomcat
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();
        Context tomcatContext = tomcat.addWebapp("", new File(".").getAbsolutePath());

        // 2. Настройка Spring Context
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        // Регистрируем только AppConfig, так как он уже импортирует SecurityConfig
        appContext.register(AppConfig.class);

        // 3. Создание и регистрация DispatcherServlet (мост между Tomcat и Spring)
        DispatcherServlet dispatcherServlet = new DispatcherServlet(appContext);
        // Добавляем сервлет в Tomcat ТОЛЬКО ОДИН РАЗ и получаем Wrapper
        Wrapper dispatcherWrapper = Tomcat.addServlet(tomcatContext, "dispatcher", dispatcherServlet);
        // Устанавливаем принудительную загрузку при старте
        dispatcherWrapper.setLoadOnStartup(1);
        tomcatContext.addServletMappingDecoded("/*", "dispatcher");

        // 4. Настройка фильтра Spring Security
        FilterDef securityFilterDef = new FilterDef();
        securityFilterDef.setFilterName("springSecurityFilterChain");
        securityFilterDef.setFilter(new DelegatingFilterProxy("springSecurityFilterChain"));
        tomcatContext.addFilterDef(securityFilterDef);

        FilterMap securityFilterMap = new FilterMap();
        securityFilterMap.setFilterName("springSecurityFilterChain");
        securityFilterMap.addURLPattern("/*");
        tomcatContext.addFilterMap(securityFilterMap);

        // 5. Запуск сервера
        tomcat.start();
        System.out.println("--- Server started on http://localhost:8080 ---");
        tomcat.getServer().await();
    }
}