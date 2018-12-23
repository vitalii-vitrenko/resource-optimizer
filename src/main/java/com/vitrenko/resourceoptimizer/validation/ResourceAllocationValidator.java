package com.vitrenko.resourceoptimizer.validation;

import com.vitrenko.resourceoptimizer.domain.Task;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ResourceAllocationValidator implements ConstraintValidator<CanAllocateResources, Task> {


    @Override
    public boolean isValid(Task value, ConstraintValidatorContext context) {
        return false;
    }
}
