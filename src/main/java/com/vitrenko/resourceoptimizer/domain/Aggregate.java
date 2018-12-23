package com.vitrenko.resourceoptimizer.domain;

import com.vitrenko.resourceoptimizer.validation.CanComputeEffects;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.ConstraintValidatorContext;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;


@Data
@Accessors(chain = true)
@CanComputeEffects
public class Aggregate {

    @NotBlank
    private String name;
    @Valid
    private EffectParameters effectParameters;
    private List<Double> effects = new ArrayList<>();

    @AssertTrue(message = "task.aggregate.noEffectsAndParameters")
    public boolean hasEffectsOrEffectsParameters() {
        return getEffectParameters() != null || !isEmpty(getEffects());
    }
}
