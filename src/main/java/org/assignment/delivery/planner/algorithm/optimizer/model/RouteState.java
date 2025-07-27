package org.assignment.delivery.planner.algorithm.optimizer.model;

import lombok.Getter;
import org.assignment.delivery.planner.model.GeoLocation;

import java.util.List;
import java.util.Set;

/**
 * Represents a single state in the route planning process.
 * Maintains information about the delivery agent's current location,
 * the path taken so far, the nodes already visited, the current time
 * after reaching this state, and the estimated total time for the full route.
 *
 * <p>Used by route optimizers such as the Beam Search based {@code HeuristicRouteOptimizer}
 * to keep track of potential paths and prioritize exploration of better routes.
 */
@Getter
public class RouteState {

    /** Current location of the delivery agent */
    private final GeoLocation currentLocation;

    /** Ordered list of nodes visited so far in the route */
    private final List<VisitNode> route;

    /** Set of nodes that have been visited */
    private final Set<VisitNode> visited;

    /** Time elapsed to reach this state */
    private final double currentTime;

    /** Estimated total time to complete the full route from this state */
    private final double estimatedTotalTime;

    /**
     * Constructs a route state without an estimated total time,
     * using current time as a placeholder.
     *
     * @param loc     Current location
     * @param r       Route so far
     * @param v       Visited nodes
     * @param t       Current time
     */
    public RouteState(GeoLocation loc, List<VisitNode> r, Set<VisitNode> v, double t) {
        this(loc, r, v, t, t);
    }

    /**
     * Constructs a route state with a given estimated total time.
     *
     * @param loc      Current location
     * @param r        Route so far
     * @param v        Visited nodes
     * @param t        Current time
     * @param estimate Estimated total time
     */
    public RouteState(GeoLocation loc, List<VisitNode> r, Set<VisitNode> v, double t, double estimate) {
        this.currentLocation = loc;
        this.route = r;
        this.visited = v;
        this.currentTime = t;
        this.estimatedTotalTime = estimate;
    }
}
