package org.model;

import lombok.Data;

@Data
public class Assert {
    private AssertMethod method;
    private Object expected;
    private Boolean soft;
}
