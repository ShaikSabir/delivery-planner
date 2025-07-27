package org.assignment.delivery.planner.dagger.component;

import dagger.Component;
import org.assignment.delivery.planner.dagger.module.DistanceCalculatorModule;
import org.assignment.delivery.planner.dagger.module.RouteOptimizerModule;
import org.assignment.delivery.planner.dagger.module.TimeEstimatorModule;
import org.assignment.delivery.planner.service.DeliveryRoutePlanner;

import javax.inject.Singleton;

@Singleton
@Component(modules = {DistanceCalculatorModule.class, RouteOptimizerModule.class, TimeEstimatorModule.class})
public interface AppComponent {
    DeliveryRoutePlanner getDeliveryRoutePlanner();
}
