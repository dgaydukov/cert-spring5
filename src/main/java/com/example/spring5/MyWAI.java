package com.example.spring5;
/**
 * All classes implementing the WebApplicationInitializer interface will be automatically detected by
 * the org.springframework.web.SpringServletContainerInitializer class (which implements Servlet 3.0’s
 * javax.servlet.ServletContainerInitializer interface), which bootstraps automatically in any Servlet 3.0
 * containers.
 */

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MyWAI extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{};
    }
    @Override
    protected Class<?>[] getServletConfigClasses()  {
        return new Class<?>[]{};
    }
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
