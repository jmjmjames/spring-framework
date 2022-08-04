package com.exam;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Method;

@Getter
@AllArgsConstructor
public class RouteInfo {
    private String path;
    private String actionPath;
    private Class controllerCls;
    private Method method;
}
