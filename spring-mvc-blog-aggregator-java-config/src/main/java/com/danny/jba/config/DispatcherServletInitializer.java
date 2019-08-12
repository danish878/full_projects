package com.danny.jba.config;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Properties;

public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{AppConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/", "*.html"};
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        try {
            Resource resource = new ClassPathResource("/application.properties");
            Properties props = PropertiesLoaderUtils.loadProperties(resource);

//            servletContext.addListener(new ContextLoaderListener());

            servletContext.addFilter("characterEncodingFilter", CharacterEncodingFilter.class)
                    .addMappingForUrlPatterns(null, false, "/*");

//            servletContext.setInitParameter("defaultHtmlEscape", props.getProperty("true"));
            servletContext.setInitParameter("spring.profiles.active", props.getProperty("spring.profiles.active"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}