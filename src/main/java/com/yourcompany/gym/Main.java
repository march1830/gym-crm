package com.yourcompany.gym;

import com.yourcompany.gym.config.AppConfig;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.File;

public class Main {

    public static void main(String[] args) throws Exception {
        // --- This code starts an embedded Tomcat server ---

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080); // The application will run on http://localhost:8080
        tomcat.getConnector();

        Context tomcatContext = tomcat.addWebapp("", new File(".").getAbsolutePath());

        // --- This code configures Spring MVC within Tomcat ---

        // Create Spring application context
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(AppConfig.class);

        // Create Spring's main servlet
        DispatcherServlet dispatcherServlet = new DispatcherServlet(appContext);

        // Register the servlet with Tomcat
        Tomcat.addServlet(tomcatContext, "dispatcher", dispatcherServlet);
        tomcatContext.addServletMappingDecoded("/*", "dispatcher");

        // Start the server
        tomcat.start();
        System.out.println("--- Server started on http://localhost:8080 ---");
        tomcat.getServer().await();
    }
}