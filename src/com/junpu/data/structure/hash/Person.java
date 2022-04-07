package com.junpu.data.structure.hash;

import java.util.Objects;

/**
 * @author junpu
 * @date 2022/4/7
 */
public class Person {

    public int age;
    public float height;
    public String name;

    public Person() {
    }

    public Person(int age, float height, String name) {
        this.age = age;
        this.height = height;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        return age == person.age &&
                Float.compare(person.height, height) == 0 &&
                Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        int hash = Integer.hashCode(age);
        hash = 31 * hash + Float.hashCode(height);
        hash = 31 * hash + (name == null ? 0 : name.hashCode());
        return hash;
    }
}
