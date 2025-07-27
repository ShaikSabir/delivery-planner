package org.assignment.delivery.planner.algorithm.optimizer.mapper;

import org.assignment.delivery.planner.algorithm.optimizer.model.VisitNode;
import org.assignment.delivery.planner.algorithm.optimizer.model.VisitType;
import org.assignment.delivery.planner.model.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class responsible for converting {@link Order} domain objects
 * into internal {@link VisitNode} representations used for route optimization.
 *
 */
public final class OrderToVisitNodeMapper {

    /** Singleton instance of the mapper. */
    public static final OrderToVisitNodeMapper INSTANCE = new OrderToVisitNodeMapper();

    /** Private constructor to enforce singleton usage. */
    private OrderToVisitNodeMapper() {}

    /**
     * Maps a list of {@link Order} objects into a flat list of {@link VisitNode}s
     * (each order generates one restaurant node and one customer node).
     *
     * @param orders the list of orders to convert
     * @return a list of visit nodes derived from the orders, suitable for route optimization
     */
    public static List<VisitNode> mapOrdersToVisitNodes(List<Order> orders) {
        List<VisitNode> visitNodes = new ArrayList<>();

        for (Order order : orders) {
            // Create restaurant node
            VisitNode restaurantNode = VisitNode.builder()
                    .orderId(order.getOrderId())
                    .location(order.getRestaurant().getAddress().getLocation())
                    .type(VisitType.RESTAURANT)
                    .visitId(order.getRestaurant().getId())
                    .averagePreparationTime(order.getRestaurant().getAveragePreparationTime())
                    .build();

            // Create customer node with reference to paired restaurant
            VisitNode customerNode = VisitNode.builder()
                    .orderId(order.getOrderId())
                    .location(order.getCustomer().getAddress().getLocation())
                    .type(VisitType.CUSTOMER)
                    .visitId(order.getCustomer().getId())
                    .averagePreparationTime(0)
                    .pairedNode(restaurantNode)
                    .build();

            visitNodes.add(restaurantNode);
            visitNodes.add(customerNode);
        }

        return visitNodes;
    }
}
