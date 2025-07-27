package org.assignment.delivery.planner.utils;

/**
 * Utility class for mathematical functions used in geographical computations.
 */
public final class MathUtil {
    public static final double EARTH_RADIUS_KM = 6371.0;

    // Private constructor to prevent instantiation
    private MathUtil() {}

    /**
     * Converts degrees to radians.
     *
     * @param degrees value in degrees
     * @return equivalent value in radians
     */
    public static double toRadians(double degrees) {
        return Math.toRadians(degrees);
    }

    /**
     * Computes the haversine of an angle in radians.
     * hav(θ) = sin²(θ / 2)
     *
     * @param angleRad angle in radians
     * @return haversine of the angle
     */
    public static double computeHaversine(double angleRad) {
        double halfAngle = angleRad / 2.0;
        return Math.pow(Math.sin(halfAngle), 2);
    }
}
