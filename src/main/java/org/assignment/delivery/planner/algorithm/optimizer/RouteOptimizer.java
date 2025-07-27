package org.assignment.delivery.planner.algorithm.optimizer;

import org.assignment.delivery.planner.algorithm.optimizer.model.VisitNode;
import org.assignment.delivery.planner.model.GeoLocation;

import java.util.List;

/**
 * RouteOptimizer defines a contract for computing the optimal delivery route
 * given a starting location and a list of visit nodes (restaurants and customers).
 *
 */
public interface RouteOptimizer {

    /**
     * Computes the optimal route starting from the given location and visiting all nodes.
     *
     * @param startLocation The starting point of the delivery agent.
     * @param nodes         A list of visit nodes (restaurants and customers), where each customer
     *                      must be paired with a restaurant node.
     * @return An ordered list of VisitNodes representing the optimal route.
     */
    List<VisitNode> computeOptimalRoute(GeoLocation startLocation, List<VisitNode> nodes);
}
