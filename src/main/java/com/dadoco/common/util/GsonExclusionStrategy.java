/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 *
 * @author Dairon
 */
public class GsonExclusionStrategy implements ExclusionStrategy {

    private final Class<?> typeToExclude;

    public GsonExclusionStrategy(Class<?> clazz) {
        this.typeToExclude = clazz;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return (this.typeToExclude != null && this.typeToExclude == clazz)
                || clazz.getAnnotation(GsonExclude.class) != null;
    }

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return f.getAnnotation(GsonExclude.class) != null;
    }

}
