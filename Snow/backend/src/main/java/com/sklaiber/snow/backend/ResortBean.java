package com.sklaiber.snow.backend;

import java.util.ArrayList;

/**
 * Created by sklaiber on 07.02.16.
 */
public class ResortBean {

    public String name;
    public String conditions;


    public ResortBean(String name, String conditions) {
        this.name = name;
        this.conditions = conditions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }
}
