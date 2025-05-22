// Element.java
package org.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Element {
    private LocatorType by;
    private String value;
}
