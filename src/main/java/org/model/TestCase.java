package org.model;

import lombok.Data;

import java.util.List;

@Data
public class TestCase {
    private String name;
    private String description;
    private List<String> tags;
    private List<TestStep> steps;
}
