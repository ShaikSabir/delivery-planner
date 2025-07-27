package org.assignment.delivery.planner.algorithm.optimizer.adapter;

import lombok.Data;
import org.assignment.delivery.planner.algorithm.optimizer.RouteOptimizer;
import org.assignment.delivery.planner.algorithm.optimizer.model.VisitNode;
import org.assignment.delivery.planner.model.GeoLocation;
import org.assignment.delivery.planner.model.Order;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

import static org.assignment.delivery.planner.algorithm.optimizer.mapper.OrderToVisitNodeMapper.INSTANCE;

/**
 * Adapter class responsible for bridging the input {@link Order} data
 * with the internal route optimization engine.
 *
 * <p>This class encapsulates mapping logic and delegates actual routing
 * computation to a {@link RouteOptimizer} implementation (e.g., Heuristic-based).</p>
 *
 */
@Data
public class RoutePlanningEngine {

    /** The core optimizer instance responsible for computing the optimal delivery route. */
    private final RouteOptimizer optimizer;

    /**
     * Constructs a new RoutePlanningEngine with the given optimizer.
     *
     * @param optimizer the strategy implementation used to compute the optimal delivery route
     */
    @Inject
    public RoutePlanningEngine(RouteOptimizer optimizer) {
        this.optimizer = optimizer;
    }

    /**
     * Generates an optimal route for a delivery agent starting at the specified location
     * and visiting all restaurants and customers involved in the provided orders.
     *
     * @param startLocation the starting point of the delivery agent
     * @param orders        the list of orders to deliver
     * @return an ordered list of visit IDs representing the optimized delivery route
     */
    public List<String> generateRoute(GeoLocation startLocation, List<Order> orders) {
        List<VisitNode> visitNodes = INSTANCE.mapOrdersToVisitNodes(orders);
        List<VisitNode> optimizedNodes = optimizer.computeOptimalRoute(startLocation, visitNodes);

        return optimizedNodes.stream()
                .map(VisitNode::getVisitId)
                .collect(Collectors.toList());
    }
}
