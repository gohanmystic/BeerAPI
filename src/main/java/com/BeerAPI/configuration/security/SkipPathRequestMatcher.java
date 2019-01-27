package com.BeerAPI.configuration.security;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class SkipPathRequestMatcher implements RequestMatcher {

    private OrRequestMatcher matchers;
    private RequestMatcher processingMatcher;

    public SkipPathRequestMatcher(final List<String> pathsToSkip, final String processingPath) {

        final List<RequestMatcher> requestMatchers =
                pathsToSkip.stream()
                           .map(path -> new AntPathRequestMatcher(path))
                           .collect(Collectors.toList());


        matchers = new OrRequestMatcher(requestMatchers);
        processingMatcher = new AntPathRequestMatcher(processingPath);
    }


    @Override
    public boolean matches(HttpServletRequest request) {

        if (matchers.matches(request)) {

            return false;
        }

        return processingMatcher.matches(request);
    }

}
