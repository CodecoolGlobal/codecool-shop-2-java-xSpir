package com.codecool.shop.model;


import java.lang.reflect.Field;

import com.codecool.shop.controller.logger.OurLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseModel {
    private static final Logger logger = LoggerFactory.getLogger(BaseModel.class);
    protected final int id;
    protected String name;
    protected String description;

    public BaseModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public BaseModel(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(this);
                if (value != null) {
                    sb.append(field.getName()).append(":").append(value).append(",");
                }
            } catch (IllegalAccessException e) {
                OurLogger.log(String.format("Access denied: %s", e.getMessage()));
                logger.info(String.format("Access denied: %s", e.getMessage()));
            }
        }
        return sb.toString();
    }

}
