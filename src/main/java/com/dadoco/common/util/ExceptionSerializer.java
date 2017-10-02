/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

/**
 *
 * @author Dairon
 */
public class ExceptionSerializer implements JsonSerializer<Exception> {

    @Override
    public JsonElement serialize(Exception src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("cause", new JsonPrimitive(String.valueOf(src.getCause())));
        jsonObject.add("message", new JsonPrimitive(src.getMessage()));
        return jsonObject;
    }
}
