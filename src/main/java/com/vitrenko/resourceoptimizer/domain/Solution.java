package com.vitrenko.resourceoptimizer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@Accessors(chain = true)
public class Solution {

    private Task task;
    private Set<ConstraintViolation<Task>> violations;
    private Map<String, Integer> allocation;
}
