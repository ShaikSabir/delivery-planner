# Delivery Route Optimization System

## Overview 
This project implements a modular, extensible system for optimizing delivery routes for restaurant orders. It models entities such as restaurants, customers, and orders, and computes an efficient route that minimizes delivery time while considering constraints such as preparation time and travel distance.

The solution is not limited to two orders—it is designed to handle multiple orders across multiple restaurants and customers, making it suitable for real-world delivery scenarios involving batching and sequencing.

It uses a heuristic-based Beam Search approach with pruning, allowing a balance between accuracy and performance.
## 🧩 Project Structure

```
org.lucidity.deliveryproject
├── algorithm.optimizer
│ ├── adapter/ # Adapter that bridges API and optimizer logic
│ ├── impl/ # Heuristic optimizer implementation (Beam Search)
│ ├── model/ # VisitNode, VisitType, internal modeling
│ ├── mapper/ # Converts domain orders to VisitNodes
│ └── RouteOptimizer.java # Core optimization interface
│
├── model/ # Domain models (Order, Customer, Restaurant, etc.)
│
├── service/
│ ├── DeliveryRoutePlanner.java # Coordinates order, user and route generation
│ └── TravelTimeEstimationService.java# Estimates time and distance between nodes
│
├── distance/ # Distance calculator using haversine
├── time/ # Travel time estimator using average speed
└── utils/ # Math utilities
```

## 🔄 Flow

1. **Order Input** – Given a list of `Order`s (each with restaurant and customer).
2. **Route Planning** – Calls `RoutePlanningEngine` with input nodes and start location.
3. **Route Optimization** – Uses heuristic algorithm (like Beam Search) for route calculation.
4. **Plan Generation** – Generates `DeliveryPlan` object in order of visits.

## ✅ Why Heuristic Over Greedy?

Greedy approaches choose the nearest neighbor at each step, which can lead to suboptimal total paths due to lack of global foresight.

Heuristic search (e.g., Beam Search with pruning):
- Allows evaluating multiple partial paths simultaneously
- Prunes poor paths to remain efficient
- Offers better global optimization without full brute-force

## 📦 Assumptions
- Each order has one restaurant and one customer.
- Restaurant must be visited before its corresponding customer.
- Travel time is proportional to geographic distance.

## 🧪 Tests

Unit tests are written using **JUnit 5** and located under `src/test/java/`.

### Covered Scenarios:
- ✅ Route planning for valid input (1 order)
- ✅ Edge case with empty input (no orders)
- ✅ Orders with long delivery distances

Each test uses a `MockRouteOptimizer` to simulate routing decisions.

To run tests:
```bash
./gradlew test
```

---