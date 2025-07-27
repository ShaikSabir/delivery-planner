package org.assignment.delivery.planner.service;

import lombok.Data;
import org.assignment.delivery.planner.algorithm.optimizer.adapter.RoutePlanningEngine;
import org.assignment.delivery.planner.model.Customer;
import org.assignment.delivery.planner.model.DeliveryPlan;
import org.assignment.delivery.planner.model.GeoLocation;
import org.assignment.delivery.planner.model.Order;
import org.assignment.delivery.planner.model.Restaurant;
import org.assignment.delivery.planner.model.User;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service responsible for planning the optimal delivery route for a list of orders.
 */
@Data
public class DeliveryRoutePlanner {
    private final RoutePlanningEngine planningEngine;

    /**
     * Constructs a {@code DeliveryRoutePlanner} with the given {@link RoutePlanningEngine}.
     *
     * @param planningEngine the route planning engine to be used for optimization
     */
    @Inject
    public DeliveryRoutePlanner(RoutePlanningEngine planningEngine) {
        this.planningEngine = planningEngine;
    }

    /**
     * Plans and generates an optimal delivery route for the provided orders.
     * Resolves the computed visiting sequence to concrete {@link Customer} and {@link Restaurant} users.
     *
     * @param startLocation the initial location of the delivery agent
     * @param orders        list of orders to fulfill
     * @param customers     list of customers involved in the orders
     * @param restaurants   list of restaurants involved in the orders
     * @return the optimal {@link DeliveryPlan} containing the ordered list of {@link User}s to visit
     */
    public DeliveryPlan planRoute(
            GeoLocation startLocation,
            List<Order> orders,
            List<Customer> customers,
            List<Restaurant> restaurants
    ) {
        Map<String, User> idToUserMapping = new HashMap<>();
        customers.forEach(customer -> idToUserMapping.put(customer.getId(), customer));
        restaurants.forEach(restaurant -> idToUserMapping.put(restaurant.getId(), restaurant));

        List<String> userIds = planningEngine.generateRoute(startLocation, orders);

        return DeliveryPlan.builder()
                .users(userIds.stream()
                        .map(idToUserMapping::get)
                        .toList())
                .build();
    }
}
