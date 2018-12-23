package com.vitrenko.resourceoptimizer.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class Task {

    @NotBlank
    private String name;
    @NotNull
    @Positive
    private Integer resources;
    @NotEmpty
    @Valid
    private List<Aggregate> aggregates = new ArrayList<>();

    @AssertTrue(message = "task.cannotAllocate")
    public boolean canAllocateResources() {
        return aggregates.stream()
                         .allMatch(aggregate -> aggregate.hasEffectsOrEffectsParameters()
                                        && (aggregate.getEffectParameters() != null
                                        || aggregate.getEffects().size() == resources));
    }
}
   