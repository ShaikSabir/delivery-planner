package org.assignment.delivery.planner;

import org.assignment.delivery.planner.dagger.component.AppComponent;
import org.assignment.delivery.planner.dagger.component.DaggerAppComponent;
import org.assignment.delivery.planner.model.*;
import org.assignment.delivery.planner.service.DeliveryRoutePlanner;

import java.util.List;

/**
 * Entry point of the Delivery Route Optimization application.
 * <p>
 * This class demonstrates a sample usage of the delivery planning system by:
 * <ul>
 *   <li>Initializing Dagger dependency injection</li>
 *   <li>Creating sample customers, restaurants, and orders</li>
 *   <li>Invoking the route planner to compute the optimal delivery sequence</li>
 *   <li>Printing the planned delivery route</li>
 * </ul>
 */
public class Main {

    /**
     * Main method that orchestrates the sample delivery route planning.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        AppComponent appComponent = DaggerAppComponent.create();
        DeliveryRoutePlanner routePlanner = appComponent.getDeliveryRoutePlanner();

        // Setup test data
        Customer customer1 = createCustomer("C1", "Alice", 3.0, 0.0, "O1");
        Customer customer2 = createCustomer("C2", "Bob", 0.0, 0.0, "O2");

        Restaurant restaurant1 = createRestaurant("R1", "Pizza Corner", 5.0, 0.0, 1);
        Restaurant restaurant2 = createRestaurant("R2", "Burger House", 9.0, 0.0, 1);

        Order order1 = createOrder("O1", customer1, restaurant1);
        Order order2 = createOrder("O2", customer2, restaurant2);

        restaurant1.setOrders(List.of(order1));
        restaurant2.setOrders(List.of(order2));


        // Plan route
        GeoLocation startLocation = GeoLocation.builder()
                .latitude(0.0)
                .longitude(0.0)
                .build();

        // Create Agent
        Agent agent = Agent.builder()
                .id("A1")
                .location(startLocation)
                .build();


        DeliveryPlan deliveryPlan = routePlanner.planRoute(
                startLocation,
                List.of(order1, order2),
                List.of(customer1, customer2),
                List.of(restaurant1, restaurant2)
        );

        agent.setDeliveryPlan(deliveryPlan);

        // Display results
        List<User> deliveryRoute = deliveryPlan.getUsers();
        if (deliveryRoute.isEmpty()) {
            System.out.println("No delivery route could be planned.");
        } else {
            System.out.println("\nPlanned Delivery Route:");
            deliveryRoute.forEach(user -> System.out.println(" -> " + user));
        }
    }

    /**
     * Creates a mock Customer object with address and order ID.
     *
     * @param id       unique customer ID
     * @param name     customer name
     * @param lat      latitude of customer's location
     * @param lon      longitude of customer's location
     * @param orderId  order ID associated with the customer
     * @return constructed Customer object
     */
    private static Customer createCustomer(String id, String name, double lat, double lon, String orderId) {
        Customer customer = Customer.builder()
                .name(name)
                .address(Address.builder()
                        .location(GeoLocation.builder().latitude(lat).longitude(lon).build())
                        .build())
                .orderIds(List.of(orderId))
                .build();
        customer.setId(id);
        return customer;
    }

    /**
     * Creates a mock Restaurant object with address and preparation time.
     *
     * @param id        unique restaurant ID
     * @param name      restaurant name
     * @param lat       latitude of restaurant's location
     * @param lon       longitude of restaurant's location
     * @param prepTime  average preparation time in minutes
     * @return constructed Restaurant object
     */
    private static Restaurant createRestaurant(String id, String name, double lat, double lon, double prepTime) {
        Restaurant restaurant = Restaurant.builder()
                .name(name)
                .address(Address.builder()
                        .location(GeoLocation.builder().latitude(lat).longitude(lon).build())
                        .build())
                .averagePreparationTime(prepTime)
                .build();
        restaurant.setId(id);
        return restaurant;
    }

    /**
     * Creates a mock Order object linking a customer to a restaurant.
     *
     * @param orderId     unique order ID
     * @param customer    customer placing the order
     * @param restaurant  restaurant preparing the order
     * @return constructed Order object
     */
    private static Order createOrder(String orderId, Customer customer, Restaurant restaurant) {
        return Order.builder()
                .orderId(orderId)
                .customer(customer)
                .restaurant(restaurant)
                .build();
    }
}
