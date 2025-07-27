package org.assignment.delivery.planner.service;

import org.assignment.delivery.planner.algorithm.optimizer.adapter.RoutePlanningEngine;
import org.assignment.delivery.planner.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class DeliveryRoutePlannerTest {

    private RoutePlanningEngine planningEngine;
    private DeliveryRoutePlanner planner;

    @BeforeEach
    void setUp() {
        planningEngine = Mockito.mock(RoutePlanningEngine.class);
        planner = new DeliveryRoutePlanner(planningEngine);
    }

    @Test
    void testPlanRoute_shouldReturnOrderedDeliveryPlan() {
        // Arrange
        GeoLocation start = GeoLocation.builder().latitude(0).longitude(0).build();

        Restaurant restaurant = Restaurant.builder()
                .id("R1")
                .address(Address.builder().location(GeoLocation.builder().latitude(1).longitude(1).build()).build())
                .averagePreparationTime(10)
                .build();

        Customer customer = Customer.builder()
                .id("C1")
                .address(Address.builder().location(GeoLocation.builder().latitude(2).longitude(2).build()).build())
                .build();

        Order order = Order.builder()
                .orderId("O1")
                .restaurant(restaurant)
                .customer(customer)
                .build();

        when(planningEngine.generateRoute(start, List.of(order)))
                .thenReturn(List.of("R1", "C1"));

        // Act
        DeliveryPlan plan = planner.planRoute(
                start,
                List.of(order),
                List.of(customer),
                List.of(restaurant)
        );

        // Assert
        assertEquals(2, plan.getUsers().size());
        assertEquals("R1", plan.getUsers().get(0).getId());
        assertEquals("C1", plan.getUsers().get(1).getId());
    }
}
