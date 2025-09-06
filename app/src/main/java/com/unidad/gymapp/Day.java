package com.unidad.gymapp;

public class Day {
    public final String name;
    public final Class<?> activityClass;

    public Day(String name, Class<?> activityClass) {
        this.name = name;
        this.activityClass = activityClass;
    }
}
