package com.octopusthu.dev.configserver;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * The Spring Cloud Config Server Application
 *
 * @author octopusthu
 */
@SpringBootApplication
@EnableConfigServer
public class SpringCloudConfigServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConfigServerApplication.class, args);
    }

    @EnableWebSecurity
    public static class WebSecurity extends WebSecurityConfigurerAdapter {
        public WebSecurity() {
            super(true);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .httpBasic(withDefaults())
                .authorizeRequests().anyRequest().authenticated();
        }
    }
}
