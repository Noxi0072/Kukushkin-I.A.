package org.example;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String name;
    private List<String> tasks;

    public Student(String name) {
        this.name = name;
        this.tasks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<String> getTasks() {
        return tasks;
    }

    public void addTask(String task) {
        tasks.add(task);
    }
}