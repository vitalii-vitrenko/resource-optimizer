package com.vitrenko.resourceoptimizer.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.ObjectUtils;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@Accessors(chain = true)
public class EffectParameters {

    @NotNull
    @PositiveOrZero
    private Integer minResources;
    @NotNull
    @PositiveOrZero
    private Integer maxResources;
    @NotNull
    @PositiveOrZero
    private Double alpha;
    @PositiveOrZero
    @Max(1)
    private Double weight;

    @AssertTrue(message = "{aggregate.resources.range}")
    public boolean isValidResourceConstraint() {
        return ObjectUtils.compare(minResources, maxResources) <= 0;
    }
}
