package com.pyatakov.config.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.pyatakov.controller"})
public class WebConfig implements WebMvcConfigurer {

//    @Autowired
//    private ApplicationContext ctx;

    @Bean
    public MappingJackson2HttpMessageConverter
           mappingJackson2HttpMessageConverter() {
     MappingJackson2HttpMessageConverter
             mappingJackson2HttpMessageConverter =
                new MappingJackson2HttpMessageConverter();
     mappingJackson2HttpMessageConverter
             .setObjectMapper(objectMapper());
     return mappingJackson2HttpMessageConverter;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.setSerializationInclusion(
                JsonInclude.Include.NON_NULL
        );
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        objectMapper.setDateFormat(df);
        
        return objectMapper;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(mappingJackson2HttpMessageConverter());
    }

}
