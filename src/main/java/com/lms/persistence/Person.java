package com.lms.persistence;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Person {
    private String name;
    private int age;
    private int id;

    @Override
    public String toString() {
        return id + "::" + name + "::" + age;
    }
}
