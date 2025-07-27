package org.assignment.delivery.planner.dagger.module;

import dagger.Module;
import dagger.Provides;
import org.assignment.delivery.planner.algorithm.optimizer.HeuristicRouteOptimizer;
import org.assignment.delivery.planner.algorithm.optimizer.RouteOptimizer;

@Module
public class RouteOptimizerModule {
    @Provides
    RouteOptimizer provideRouteOptimizer(HeuristicRouteOptimizer optimizer) {
        return optimizer;
    }
}
