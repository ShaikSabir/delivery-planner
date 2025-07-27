package org.assignment.delivery.planner.algorithm.optimizer.model;

/**
 * Represents the type of a visit node in the delivery route.
 *
 * <ul>
 *   <li>{@code RESTAURANT} – A pickup location where the delivery agent collects an order.</li>
 *   <li>{@code CUSTOMER} – A drop-off location where the delivery agent delivers the order.</li>
 * </ul>
 *
 * <p>This enum is used in {@link VisitNode} to distinguish between pickup and delivery points
 * and enforce routing constraints such as visiting the restaurant before the customer.</p>
 */
public enum VisitType {
    RESTAURANT,
    CUSTOMER
}
