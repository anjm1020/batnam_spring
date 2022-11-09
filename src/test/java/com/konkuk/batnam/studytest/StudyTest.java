package com.konkuk.batnam.studytest;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class StudyTest {

    static class Student {
        private String name;
        private int age;

        public Student(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    @Test
    void test() {
        List<Student> list = new ArrayList<>();
        list.add(new Student("name", 1));
        list.add(new Student("name2", 1));
        list.add(new Student("name3", 1));
        list.add(new Student("name4", 1));
        System.out.println(list);
    }
}
