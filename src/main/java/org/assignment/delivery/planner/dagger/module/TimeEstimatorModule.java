package org.assignment.delivery.planner.dagger.module;

import dagger.Module;
import dagger.Provides;
import org.assignment.delivery.planner.time.AverageSpeedTimeEstimator;
import org.assignment.delivery.planner.time.TimeEstimator;

@Module
public class TimeEstimatorModule {

    @Provides
    public TimeEstimator provideTimeEstimator() {
        return new AverageSpeedTimeEstimator(20);
    }

}
