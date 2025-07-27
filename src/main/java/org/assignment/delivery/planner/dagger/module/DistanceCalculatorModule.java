package org.assignment.delivery.planner.dagger.module;

import dagger.Module;
import dagger.Provides;
import org.assignment.delivery.planner.distance.DistanceCalculator;
import org.assignment.delivery.planner.distance.HaversineDistanceCalculator;

@Module
public class DistanceCalculatorModule {
    @Provides
    public DistanceCalculator provideHaversineDistanceCalculator(HaversineDistanceCalculator calculator) {
        return  calculator;
    }

}
