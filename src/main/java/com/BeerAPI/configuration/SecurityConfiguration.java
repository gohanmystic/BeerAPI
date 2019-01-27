package com.BeerAPI.configuration;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.BeerAPI.common.Constants;
import com.BeerAPI.configuration.security.SkipPathRequestMatcher;
import com.BeerAPI.configuration.security.TokenAuthenticationFilter;
import com.BeerAPI.configuration.security.TokenAuthenticationProvider;
import com.BeerAPI.service.impl.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String FORM_BASED_LOGIN_ENTRY_POINT = Constants.ROOT_URL + "auth/login";
    private static final String REFRESH_TOKEN_ENTRY_POINT = Constants.ROOT_URL + "auth/renew";
    private static final List<String> skipPath = Arrays.asList(FORM_BASED_LOGIN_ENTRY_POINT,
                                                               REFRESH_TOKEN_ENTRY_POINT);

    @Autowired
    private CustomUserDetailsService jwtUserDetailsService;

    @Autowired
    private TokenAuthenticationProvider tokenAuthenticationProvider;

    @Autowired
    public void configureGlobal( AuthenticationManagerBuilder auth ) throws Exception {
        auth.userDetailsService( jwtUserDetailsService )
            .passwordEncoder( passwordEncoder() );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() throws Exception {

        final SkipPathRequestMatcher matcher =
                new SkipPathRequestMatcher(skipPath, Constants.TOKEN_BASED_AUTH_ENTRY_PONT);

        final TokenAuthenticationFilter filter = new TokenAuthenticationFilter(matcher);
        filter.setAuthenticationManager(authenticationManager());

        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors()
            .and()
                .csrf()
                    .disable()
            .httpBasic()
                .disable()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .exceptionHandling()
            .and()
                .authorizeRequests()
                    .antMatchers(Constants.TOKEN_BASED_AUTH_ENTRY_PONT)
                        .authenticated()
            .and()
                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web
            .ignoring()
            .antMatchers(FORM_BASED_LOGIN_ENTRY_POINT, REFRESH_TOKEN_ENTRY_POINT);
    }

    @Override
    public void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(tokenAuthenticationProvider);
    }
}
