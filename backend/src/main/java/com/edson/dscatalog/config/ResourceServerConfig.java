package com.edson.dscatalog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private Environment env;

    private static final String[] PUBLIC_ROUTES = {"/oauth/token", "/h2-console/**"};

    private static final String[] OPERATOR_OR_ADMIN_ROUTES = {"/products/**", "/categories/**"};

    private static final String[] ADMIN_ROUTES = {"/users/**"};


    @Autowired
    private JwtTokenStore tokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            http.headers().frameOptions().disable();
        }

        http.authorizeRequests()
                .antMatchers(PUBLIC_ROUTES).permitAll()
                .antMatchers(HttpMethod.GET, OPERATOR_OR_ADMIN_ROUTES).permitAll()
                .antMatchers(OPERATOR_OR_ADMIN_ROUTES).hasAnyRole("OPERATOR", "ADMIN")
                .antMatchers(ADMIN_ROUTES).hasRole("ADMIN")
                .anyRequest().authenticated();
    }
}
