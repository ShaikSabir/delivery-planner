package org.assignment.delivery.planner.algorithm.optimizer.adapter;

import org.assignment.delivery.planner.algorithm.optimizer.RouteOptimizer;
import org.assignment.delivery.planner.algorithm.optimizer.model.VisitNode;
import org.assignment.delivery.planner.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoutePlanningEngineTest {

    private RouteOptimizer optimizer;
    private RoutePlanningEngine engine;

    @BeforeEach
    void setUp() {
        optimizer = mock(RouteOptimizer.class);
        engine = new RoutePlanningEngine(optimizer);
    }

    @Test
    void testGenerateRoute_shouldReturnVisitIdsInOptimizedOrder() {
        // Arrange
        GeoLocation startLocation = GeoLocation.builder().latitude(0.0).longitude(0.0).build();

        Restaurant restaurant = Restaurant.builder()
                .id("R1")
                .address(Address.builder().location(GeoLocation.builder().latitude(1.0).longitude(1.0).build()).build())
                .build();

        Customer customer = Customer.builder()
                .id("C1")
                .address(Address.builder().location(GeoLocation.builder().latitude(2.0).longitude(2.0).build()).build())
                .build();

        Order order = Order.builder()
                .orderId("O1")
                .restaurant(restaurant)
                .customer(customer)
                .build();

        VisitNode node1 = VisitNode.builder().visitId("R1").location(restaurant.getAddress().getLocation()).build();
        VisitNode node2 = VisitNode.builder().visitId("C1").location(customer.getAddress().getLocation()).build();

        when(optimizer.computeOptimalRoute(eq(startLocation), anyList()))
                .thenReturn(List.of(node1, node2));

        // Act
        List<String> result = engine.generateRoute(startLocation, List.of(order));

        // Assert
        assertEquals(List.of("R1", "C1"), result);
        verify(optimizer, times(1)).computeOptimalRoute(eq(startLocation), anyList());
    }

    @Test
    void testGenerateRoute_withEmptyOrders_shouldReturnEmptyList() {
        // Arrange
        GeoLocation startLocation = GeoLocation.builder().latitude(0.0).longitude(0.0).build();

        // Act
        List<String> result = engine.generateRoute(startLocation, List.of());

        // Assert
        assertTrue(result.isEmpty(), "Expected empty route for no orders");
        verify(optimizer).computeOptimalRoute(eq(startLocation), eq(List.of()));
    }
}
