package org.assignment.delivery.planner.algorithm.optimizer;

import org.assignment.delivery.planner.algorithm.optimizer.model.RouteState;
import org.assignment.delivery.planner.algorithm.optimizer.model.VisitNode;
import org.assignment.delivery.planner.algorithm.optimizer.model.VisitType;
import org.assignment.delivery.planner.model.GeoLocation;
import org.assignment.delivery.planner.service.TravelTimeEstimationService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * HeuristicRouteOptimizer implements a beam search strategy to compute a near-optimal
 * delivery route, considering restaurant preparation delays and visit constraints.
 *
 * <p>Each beam iteration retains only a limited number of best paths (defined by BEAM_WIDTH),
 * based on estimated total delivery time using a heuristic that favors nearest unvisited nodes.
 *
 * <p>Constraints enforced:
 * - A customer node can only be visited after their corresponding restaurant node.
 * - Preparation delays at restaurants are respected (you can't pick up earlier than prep time).
 */
public class HeuristicRouteOptimizer implements RouteOptimizer {

    private final TravelTimeEstimationService travelEstimator;
    private static final int BEAM_WIDTH = 3;

    /**
     * Constructs the optimizer with a travel time estimator dependency.
     *
     * @param travelEstimator Service used to estimate travel time between geo-locations.
     */
    @Inject
    public HeuristicRouteOptimizer(TravelTimeEstimationService travelEstimator) {
        this.travelEstimator = travelEstimator;
    }

    /**
     * Computes the optimal route given a starting location and a list of delivery visit nodes.
     *
     * @param startLocation The starting point (e.g., delivery agent's location).
     * @param nodes         List of all visit nodes (restaurants and customers).
     * @return A list of VisitNodes representing the best route found.
     */
    @Override
    public List<VisitNode> computeOptimalRoute(GeoLocation startLocation, List<VisitNode> nodes) {
        Queue<RouteState> beam = new PriorityQueue<>(Comparator.comparingDouble(RouteState::getEstimatedTotalTime));
        beam.add(new RouteState(startLocation, new ArrayList<>(), new HashSet<>(), 0));

        List<VisitNode> bestRoute = null;
        double bestTime = Double.MAX_VALUE;

        while (!beam.isEmpty()) {
            List<RouteState> nextBeam = new ArrayList<>();

            for (RouteState state : beam) {
                if (state.getVisited().size() == nodes.size()) {
                    if (state.getCurrentTime() < bestTime) {
                        bestTime = state.getCurrentTime();
                        bestRoute = state.getRoute();
                    }
                    continue;
                }

                for (VisitNode node : nodes) {
                    if (state.getVisited().contains(node)) continue;

                    // Enforce restaurant visit before customer
                    if (node.getType() == VisitType.CUSTOMER) {
                        VisitNode restaurant = node.getPairedNode();
                        if (!state.getVisited().contains(restaurant)) continue;
                    }

                    double arrivalTime = state.getCurrentTime() +
                            travelEstimator.estimateTime(state.getCurrentLocation(), node.getLocation());

                    // Apply restaurant preparation delay
                    if (node.getType() == VisitType.RESTAURANT) {
                        arrivalTime = Math.max(arrivalTime, node.getAveragePreparationTime());
                    }

                    List<VisitNode> newRoute = new ArrayList<>(state.getRoute());
                    newRoute.add(node);

                    Set<VisitNode> newVisited = new HashSet<>(state.getVisited());
                    newVisited.add(node);

                    GeoLocation newLocation = node.getLocation();
                    double estimatedRemainingTime = heuristicEstimate(newLocation, nodes, newVisited);

                    nextBeam.add(new RouteState(newLocation, newRoute, newVisited, arrivalTime,
                            arrivalTime + estimatedRemainingTime));
                }
            }

            // Retain only top-k most promising states
            beam = nextBeam.stream()
                    .sorted(Comparator.comparingDouble(RouteState::getEstimatedTotalTime))
                    .limit(BEAM_WIDTH)
                    .collect(Collectors.toCollection(LinkedList::new));
        }

        return bestRoute;
    }

    /**
     * Estimates remaining time using a simple heuristic — distance to the nearest unvisited node.
     *
     * @param currentLocation Current location of the agent.
     * @param allNodes        All visit nodes (restaurants/customers).
     * @param visited         Set of already visited nodes.
     * @return Estimated time to complete the remaining deliveries.
     */
    private double heuristicEstimate(GeoLocation currentLocation, List<VisitNode> allNodes, Set<VisitNode> visited) {
        return allNodes.stream()
                .filter(n -> !visited.contains(n))
                .mapToDouble(n -> travelEstimator.estimateTime(currentLocation, n.getLocation()))
                .min()
                .orElse(0);
    }
}
