package org.demo.imstargram.school.model;

import lombok.Data;

@Data
public class StudentSearchCondition {
    private String sortOption;
    private String searchOption;
    private String searchTarget;

}
