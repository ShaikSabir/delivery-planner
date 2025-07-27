package org.assignment.delivery.planner.algorithm.optimizer.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.assignment.delivery.planner.model.GeoLocation;

/**
 * Represents a node in the delivery route, which can be either a restaurant (pickup)
 * or a customer (drop-off). Each node holds information about its location, type,
 * associated order, and optionally its paired node (e.g., a customer node is paired with
 * its restaurant node).
 *
 * <p>This class is a key part of the delivery optimization algorithm, allowing the system
 * to enforce pickup-before-drop-off constraints and compute accurate route estimations.
 */
@Getter
@Setter
@Builder
public class VisitNode {

    /** Geographical location of this visit */
    private final GeoLocation location;

    /** Type of the visit: RESTAURANT or CUSTOMER */
    private final VisitType type;

    /** Unique ID for this visit (e.g., node identifier in the graph) */
    private final String visitId;

    /** The order ID associated with this visit */
    private final String orderId;

    /**
     * Paired node reference:
     * <ul>
     *   <li>If this is a CUSTOMER node, the paired node is the corresponding RESTAURANT.</li>
     *   <li>If this is a RESTAURANT node, the paired node is the corresponding CUSTOMER.</li>
     *   <li>If no pairing is needed, this can be null.</li>
     * </ul>
     */
    private final VisitNode pairedNode;

    /**
     * Average preparation time in minutes for orders at the restaurant.
     * <p>Applicable only for RESTAURANT nodes. For CUSTOMER nodes, this should be 0.</p>
     */
    private final double averagePreparationTime;
}
