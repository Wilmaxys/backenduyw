package fr.formation.uywback.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class UploadConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
// save the address mapping Pictures
        registry.addResourceHandler("/images/**").addResourceLocations("file://" + System.getProperty("user.dir") + "/src/main/resources/static/images/");
    }
}