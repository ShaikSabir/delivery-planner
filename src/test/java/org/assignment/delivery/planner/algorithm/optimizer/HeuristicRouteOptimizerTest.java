package org.assignment.delivery.planner.algorithm.optimizer;

import org.assignment.delivery.planner.algorithm.optimizer.model.VisitNode;
import org.assignment.delivery.planner.algorithm.optimizer.model.VisitType;
import org.assignment.delivery.planner.model.GeoLocation;
import org.assignment.delivery.planner.service.TravelTimeEstimationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class HeuristicRouteOptimizerTest {

    private TravelTimeEstimationService travelEstimator;
    private HeuristicRouteOptimizer optimizer;

    @BeforeEach
    void setUp() {
        travelEstimator = Mockito.mock(TravelTimeEstimationService.class);
        optimizer = new HeuristicRouteOptimizer(travelEstimator);
    }

    @Test
    void testComputeOptimalRoute_withSingleOrder_shouldReturnCorrectSequence() {
        // Define locations
        GeoLocation start = GeoLocation.builder().latitude(0.0).longitude(0.0).build();
        GeoLocation restaurantLocation = GeoLocation.builder().latitude(1.0).longitude(1.0).build();
        GeoLocation customerLocation = GeoLocation.builder().latitude(2.0).longitude(2.0).build();

        // Create VisitNodes
        VisitNode restaurantNode = VisitNode.builder()
                .orderId("O1")
                .visitId("R1")
                .location(restaurantLocation)
                .type(VisitType.RESTAURANT)
                .averagePreparationTime(10)
                .build();

        VisitNode customerNode = VisitNode.builder()
                .orderId("O1")
                .visitId("C1")
                .location(customerLocation)
                .type(VisitType.CUSTOMER)
                .pairedNode(restaurantNode)
                .build();

        // Mock travelEstimator behavior
        when(travelEstimator.estimateTime(start, restaurantLocation)).thenReturn(5.0);
        when(travelEstimator.estimateTime(restaurantLocation, customerLocation)).thenReturn(8.0);
        when(travelEstimator.estimateTime(customerLocation, restaurantLocation)).thenReturn(8.0); // for heuristic
        when(travelEstimator.estimateTime(start, customerLocation)).thenReturn(10.0); // for heuristic

        // Act
        List<VisitNode> result = optimizer.computeOptimalRoute(start, List.of(restaurantNode, customerNode));

        // Assert: route should be restaurant first, then customer
        assertEquals(List.of(restaurantNode, customerNode), result);
    }
}
