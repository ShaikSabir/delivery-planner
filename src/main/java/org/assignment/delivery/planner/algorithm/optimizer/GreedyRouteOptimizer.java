package org.assignment.delivery.planner.algorithm.optimizer;


import lombok.Data;
import org.assignment.delivery.planner.algorithm.optimizer.model.VisitNode;
import org.assignment.delivery.planner.algorithm.optimizer.model.VisitType;
import org.assignment.delivery.planner.model.GeoLocation;
import org.assignment.delivery.planner.service.TravelTimeEstimationService;

import javax.inject.Inject;
import java.util.*;

@Data
public class GreedyRouteOptimizer implements RouteOptimizer {
    final private TravelTimeEstimationService timeEstimationService;

    @Inject
    public GreedyRouteOptimizer(TravelTimeEstimationService timeEstimationService) {
        this.timeEstimationService = timeEstimationService;
    }

    @Override
    public List<VisitNode> computeOptimalRoute(GeoLocation startLocation, List<VisitNode> visitNodes) {
        Set<String> visitedRestaurants = new HashSet<>();
        Set<String> visitedCustomers = new HashSet<>();
        Map<String, VisitNode> restaurantMap = new HashMap<>();
        Map<String, VisitNode> customerMap = new HashMap<>();
        initializeNodeMaps(visitNodes, restaurantMap, customerMap);

        List<VisitNode> route = new ArrayList<>();
        GeoLocation currentLocation = startLocation;
        double currentTime = 0.0;

        while (visitedCustomers.size() < customerMap.size()) {
            VisitNode nextNode = selectNextNode(visitNodes, currentLocation, currentTime, visitedRestaurants, visitedCustomers);
            if (nextNode == null) {
                throw new IllegalStateException("No valid next node found. Check the order constraints.");
            }

            double travelTime = timeEstimationService.estimateTime(currentLocation, nextNode.getLocation());
            double arrivalTime = currentTime + travelTime;

            if (nextNode.getType() == VisitType.RESTAURANT && arrivalTime < nextNode.getAveragePreparationTime()) {
                arrivalTime = nextNode.getAveragePreparationTime();
            }

            currentLocation = nextNode.getLocation();
            currentTime = arrivalTime;
            route.add(nextNode);

            updateVisitState(nextNode, visitedRestaurants, visitedCustomers);
        }

        return route;
    }

    private void initializeNodeMaps(List<VisitNode> visitNodes,
                                    Map<String, VisitNode> restaurantMap,
                                    Map<String, VisitNode> customerMap) {
        for (VisitNode node : visitNodes) {
            if (node.getType() == VisitType.RESTAURANT) {
                restaurantMap.put(node.getOrderId(), node);
            } else {
                customerMap.put(node.getOrderId(), node);
            }
        }
    }

    private VisitNode selectNextNode(List<VisitNode> nodes,
                                     GeoLocation currentLocation,
                                     double currentTime,
                                     Set<String> visitedRestaurants,
                                     Set<String> visitedCustomers) {

        VisitNode bestNode = null;
        double earliestArrival = Double.MAX_VALUE;

        for (VisitNode node : nodes) {
            if (!isNodeVisitable(node, visitedRestaurants, visitedCustomers)) {
                continue;
            }

            double travelTime = timeEstimationService.estimateTime(currentLocation, node.getLocation());
            double arrivalTime = currentTime + travelTime;

            if (node.getType() == VisitType.RESTAURANT && arrivalTime < node.getAveragePreparationTime()) {
                arrivalTime = node.getAveragePreparationTime();
            }

            if (arrivalTime < earliestArrival) {
                earliestArrival = arrivalTime;
                bestNode = node;
            }
        }

        return bestNode;
    }

    private boolean isNodeVisitable(VisitNode node, Set<String> visitedRestaurants, Set<String> visitedCustomers) {
        String orderId = node.getOrderId();

        if (isRestaurant(node) && visitedRestaurants.contains(orderId)) return false;
        if (!isRestaurant(node) && visitedCustomers.contains(orderId)) return false;
        if (!isRestaurant(node) && !visitedRestaurants.contains(orderId)) return false;

        return true;
    }

    private void updateVisitState(VisitNode node,
                                  Set<String> visitedRestaurants,
                                  Set<String> visitedCustomers) {
        if (isRestaurant(node)) {
            visitedRestaurants.add(node.getOrderId());
        } else {
            visitedCustomers.add(node.getOrderId());
        }
    }

    private  Boolean isRestaurant(VisitNode node) {
        return node.getType() == VisitType.RESTAURANT;
    }

}
