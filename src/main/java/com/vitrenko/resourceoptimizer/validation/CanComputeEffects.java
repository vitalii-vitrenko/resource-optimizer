package com.vitrenko.resourceoptimizer.validation;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ResourceAllocationValidator.class)
public @interface CanComputeEffects {
}
