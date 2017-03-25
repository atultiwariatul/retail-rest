package com.atul.retail.configs;

import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

import java.util.concurrent.TimeUnit;

/**
 * Created by atiwa00 on 6/3/16.
 */

/**
 * Open below configuration if you want this class to be loaded.
 */
//@Configuration
public class BootCustomization {
    //You can customize the way default tomcat container behaves through below method
    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.setPort(9000);
        factory.setSessionTimeout(10, TimeUnit.MINUTES);
        factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/notfound.html"));
        return factory;
    }
    /**
     * Embedded servlet containers will not directly execute the Servlet 3.0+
     * javax.servlet.ServletContainerInitializer interface, or
     * Spring’s org.springframework.web.WebApplicationInitializer interface.
     * This is an intentional design decision intended to reduce the risk that 3rd party
     * libraries designed to run inside a war will break Spring Boot applications.
     * If you need to perform servlet context initialization in a Spring Boot application,
     * you should register a bean that implements the
     * org.springframework.boot.context.embedded.ServletContextInitializer interface.
     * The single onStartup method provides access to the ServletContext,
     * and can easily be used as an adapter to an existing WebApplicationInitializer if necessary.
     * Scanning for Servlets, Filters, and listeners
     * When using an embedded container, automatic registration of
     * @WebServlet, @WebFilter, and @WebListener annotated classes can be enabled using
     * @ServletComponentScan. [Tip]@ServletComponentScan will have no effect in a standalone
     * container, where the container’s built-in discovery mechanisms will be used instead.
     */
}
