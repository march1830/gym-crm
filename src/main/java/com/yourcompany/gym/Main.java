package com.yourcompany.gym;

import com.yourcompany.gym.config.AppConfig;
import com.yourcompany.gym.config.SecurityConfig;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.File;

public class Main {

    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();

        Context tomcatContext = tomcat.addWebapp("", new File(".").getAbsolutePath());

        // --- Spring MVC Setup ---
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(AppConfig.class, SecurityConfig.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(appContext);
        Tomcat.addServlet(tomcatContext, "dispatcher", dispatcherServlet);
        tomcatContext.addServletMappingDecoded("/*", "dispatcher");

        // --- CORRECTED Spring Security Setup ---

        // Step 1: Define the filter
        FilterDef securityFilterDef = new FilterDef();
        securityFilterDef.setFilterName("springSecurityFilter");
        // We still use the "bridge" to connect to our Spring bean
        securityFilterDef.setFilter(new DelegatingFilterProxy("springSecurityFilterChain"));
        tomcatContext.addFilterDef(securityFilterDef);

        // Step 2: Map the filter to URLs
        FilterMap securityFilterMap = new FilterMap();
        securityFilterMap.setFilterName("springSecurityFilter");
        // Map it to all incoming requests
        securityFilterMap.addURLPattern("/*");
        tomcatContext.addFilterMap(securityFilterMap);

        tomcat.start();
        System.out.println("--- Server started on http://localhost:8080 ---");
        tomcat.getServer().await();
    }
}