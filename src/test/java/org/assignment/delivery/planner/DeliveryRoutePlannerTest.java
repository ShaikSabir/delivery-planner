package org.assignment.delivery.planner;

import org.assignment.delivery.planner.algorithm.optimizer.model.VisitNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.assignment.delivery.planner.algorithm.optimizer.RouteOptimizer;
import org.assignment.delivery.planner.algorithm.optimizer.adapter.RoutePlanningEngine;
import org.assignment.delivery.planner.model.Address;
import org.assignment.delivery.planner.model.Customer;
import org.assignment.delivery.planner.model.DeliveryPlan;
import org.assignment.delivery.planner.model.GeoLocation;
import org.assignment.delivery.planner.model.Order;
import org.assignment.delivery.planner.model.Restaurant;
import org.assignment.delivery.planner.service.DeliveryRoutePlanner;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeliveryRoutePlannerTest {

    private DeliveryRoutePlanner routePlanner;
    private MockRouteOptimizer mockOptimizer;

    @BeforeEach
    void setUp() {
        mockOptimizer = new MockRouteOptimizer();
        RoutePlanningEngine engine = new RoutePlanningEngine(mockOptimizer);
        routePlanner = new DeliveryRoutePlanner(engine);
    }

    @Test
    void testRoutePlanningWithValidOrders() {
        GeoLocation start = buildGeoLocation(77.0, 13.0);
        Restaurant r = buildRestaurant("R1", "Rest1", 77.1, 13.1);
        Customer c = buildCustomer("C1", "Cust1", 77.2, 13.2);
        Order order = buildOrder("O1", r, c);

        DeliveryPlan plan = routePlanner.planRoute(start, List.of(order), List.of(c), List.of(r));

        assertNotNull(plan);
        assertEquals(2, plan.getUsers().size());
    }

    @Test
    void testEmptyInputEdgeCase() {
        GeoLocation start = buildGeoLocation(77.0, 13.0);
        DeliveryPlan plan = routePlanner.planRoute(start, Collections.emptyList(), Collections.emptyList(), Collections.emptyList());

        assertNotNull(plan);
        assertTrue(plan.getUsers().isEmpty());
    }

    @Test
    void testLongDistanceOrders() {
        GeoLocation start = buildGeoLocation(77.0, 13.0);
        Restaurant r = buildRestaurant("R1", "DistantRest", 78.9, 17.5);
        Customer c = buildCustomer("C1", "FarAwayCust", 80.2, 18.8);

        Order order = buildOrder("O1", r, c);

        DeliveryPlan plan = routePlanner.planRoute(start, List.of(order), List.of(c), List.of(r));

        assertNotNull(plan);
        assertEquals(2, plan.getUsers().size());
    }

    // --- Helper methods and mocks ---

    private GeoLocation buildGeoLocation(double lng, double lat) {
        return GeoLocation.builder()
                .latitude(lat)
                .longitude(lng)
                .build();
    }

    private Restaurant buildRestaurant(String id, String name, double lng, double lat) {
        return Restaurant.builder()
                .id(id)
                .name(name)
                .address(Address.builder().location(buildGeoLocation(lng, lat)).build())
                .averagePreparationTime(15)
                .build();
    }

    private Customer buildCustomer(String id, String name, double lng, double lat) {
        return Customer.builder()
                .id(id)
                .name(name)
                .address(Address.builder().location(buildGeoLocation(lng, lat)).build())
                .build();
    }

    private Order buildOrder(String orderId, Restaurant r, Customer c) {
        return Order.builder()
                .orderId(orderId)
                .restaurant(r)
                .customer(c)
                .build();
    }

    // Mock RouteOptimizer returns nodes in order of appearance
    static class MockRouteOptimizer implements RouteOptimizer {
        @Override
        public List<VisitNode> computeOptimalRoute(
                GeoLocation startLocation,
                List<VisitNode> nodes) {
            return nodes; // No real optimization, returns same order
        }
    }
}
