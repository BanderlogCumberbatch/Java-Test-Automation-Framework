package org.model;

import lombok.Data;

@Data
public class TestStep {
    private String name;
    private TestAction action;
    private Element element;
    private String url;
    private String value;
}
