package com.BeerAPI.common.support;

import org.springframework.stereotype.Component;

@Component
public class ModuleIdSupport {

    private final ThreadLocal<String> moduleIdThreadLocal = new ThreadLocal<>();

    public String getModuleId() {

        return moduleIdThreadLocal.get();
    }

    public void setModuleId(final String moduleId) {

        moduleIdThreadLocal.set(moduleId);
    }

    public void removeModuleId() {

        moduleIdThreadLocal.remove();
    }
}
