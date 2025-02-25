package com.guidebook.GuideBook;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Profile("prod")
//api.guidebookx.com alias-> dualstack.guidebookx-alb-1586257955.ap-south-1.elb.amazonaws.com.
public class WebConfigProd implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
//                .allowedOrigins(
//                        "http://guidebookx.s3-website.ap-south-1.amazonaws.com",
//                        "http://diugkigakpnwm.cloudfront.net",
//                        "http://guidebookx.com",
//                )
                .allowedOrigins(
                        "https://www.guidebookx.com",
                        "https://guidebookx.com",
                        "https://api.guidebookx.com",
                        "https://a.guidebookx.com",
                        "https://13.235.131.222",
                        "https://diugkigakpnwm.cloudfront.net"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}

/*
CORS POLICY IN ELASTIC BEANSTALK SOURCE BUCKET - NOT ADDED THIS, STILL APIs ARE WORKING
[
    {
        "AllowedHeaders": [
            "*"
        ],
        "AllowedMethods": [
            "GET",
            "POST",
            "PUT",
            "DELETE"
        ],
        "AllowedOrigins": [
            "http://guidebookx.s3-website.ap-south-1.amazonaws.com"
        ],
        "ExposeHeaders": []
    }
]
// This is still optional because we have added cross origin in springboot

*/