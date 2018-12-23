package com.vitrenko.resourceoptimizer.solver;

import com.vitrenko.resourceoptimizer.domain.Aggregate;
import com.vitrenko.resourceoptimizer.domain.EffectParameters;
import com.vitrenko.resourceoptimizer.domain.Solution;
import com.vitrenko.resourceoptimizer.domain.Task;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Component
public class TaskSolver {

    @NonNull
    private final Validator validator;

    public Solution solve(@NonNull Task task) {
        Solution solution = new Solution().setTask(task);
        populateEffects(task);
        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        if (violations.isEmpty()) {
            return solution.setAllocation(calculateAllocation(task));
        }
        return solution.setViolations(violations);
    }

    private Map<String, Integer> calculateAllocation(Task task) {
        return Collections.emptyMap();
    }

    private void populateEffects(Task task) {
        task.getAggregates().forEach(aggregate -> populateEffects(aggregate, task.getResources()));
    }

    private void populateEffects(Aggregate aggregate, int totalResources) {
        if (aggregate.getEffectParameters() != null) {
            List<Double> effects = IntStream.rangeClosed(1, totalResources)
                                            .mapToDouble(i -> calculateEffect(aggregate.getEffectParameters(), i))
                                            .boxed()
                                            .collect(Collectors.toList());
            aggregate.setEffects(effects);
        }
    }

    private double calculateEffect(EffectParameters params, int resources) {
        if (resources < params.getMinResources()) {
            return 0;
        }
        int usedResources = resources > params.getMaxResources() ? params.getMaxResources() : resources;
        double quotient = ((double) (usedResources - params.getMinResources())
                / (params.getMaxResources() - params.getMinResources()));
        return params.getWeight() * Math.pow(quotient, params.getAlpha());
    }
}
