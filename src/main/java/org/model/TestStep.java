package org.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TestStep {
    private String name;
    private TestAction action;
    private Element element;
    private String url;
    private String value;
    private String method;
    private List<Object> args;
    @JsonProperty("assert")
    private Assert assertAction;
}
