# Cargo Tracking System

## 1. Original Requirement

### 用例图

1.  Users view tracking information.
2. CargoPlanners view cargos’ information and manage cargos, for example *Book Cargo*.
3. CargoTrackers track the progress of each Cargos itinerary through *Handle Cargo Event*.
4. VoyageManagers manage the voyages through *Create Voyage* and *Add CarrierMovement*
5. Admins are responsible for *Create Location*.

![用例图](README.assets/1-s2.0-S0164121219301475-gr6.jpg)

### Data Stores

| No.  | Data stores        | Explanation                                                  |
| :--- | :----------------- | :----------------------------------------------------------- |
| 1    | Cargo              | Storing the unique identifier of cargos (***TrackingId***).  |
| 2    | RouteSpecification | Storing the expected itinerary information of cargos, e.g., ***Origin, Destination***, and ***arrivalDeadline***. |
| 3    | Itinerary          | Storing the unique identifier of a cargo’s expected route (***ieneraryNumber***). |
| 4    | Leg                | Storing information related to every step in Itinerary, such as loading location (***loadLocation***), unloading location (***unloadLocation***), loading time (***loadTime***), and unloading time (***unloadTime***). |
| 5    | Location           | Storing information about locations, e.g., the unique identifier ***unLocode*** and the name (***name***) of a location. |
| 6    | HandlingEvent      | Storing information about the historical operation events of cargos, such as **operation type** (*upload* etc.), **location** (*location*), completion time (**completionTime**), registration time (***registrationTime***). |
| 7    | Delivery           | Storing the information related to the current delivery status of cargos, including **transport status**, *misdirected* or not, unloaded (***isUnloadedAtDestination***), estimated arrival time (***estimatedArrivalDate***), route status (***routingStatus***). |
| 8    | Voyage             | Storing the unique identifier of voyages (***voyageNumber***). |
| 9    | CarrierMovement    | Storing voyages’ information such as ***departureLocation,arrivalLocation, departureTime* and *arrivalTime*** |

### The interaction between coarse-grained operations and specific data stores in Cargo Tracking system.

| Use cases                | Explanation                                                  |
| :----------------------- | :----------------------------------------------------------- |
| View Tracking            | Users can view all cargos’ tracking information based on the inputted *Cargo.trackingId*, including the route planning information in *RouteSpecification*, the voyage information in *Voyage*, the hand-off event information in *HandlingEvent*, the delivery information in *Delivery*, etc. |
| View Cargos              | Cargo planners can view all cargos’ information, which includes the route information in *RouteSpecification*, the delivery information in *Delivery*, the voyage planning information in *Itinerary*, etc. |
| Book Cargo               | Cargo planners can book cargos, in which the generated *Cargo.trackingId* and location information are written into *RouteSpecification* and *Location* respectively. |
| Change Cargo Destination | Cargo planners can change a cargo’s destination through modifying *RouteSpecification* based on the *Cargo.trackingId* and the new destination input. Meanwhile, it writes the route status in *Delivery*. |
| Route Cargo              | Cargo planners can arrange transportation routes for a cargo. The arrangement of transportation routes is based on the cargo’s route planning information from *RouteSpecification*, the voyage planning information from *Itinerary*, the *Leg*, and the specific voyage information from *Voyage* and *CarrierMovement*. |
| Create Location          | Admins can add new location into *Location*.                 |
| Create Voyage            | Voyage managers can add new voyage into *Voyage*.            |
| Add CarrierMovement      | Voyage managers can also add the docking information into *CarrierMovement*. Each *CarrierMovement* includes the information of one docking, i.e. docking location and arrival time. Each voyage may contain one or more CarrierMovements. |
| Handle Cargo Event       | Cargo trackers can track the progress of each cargo’s itinerary through *HandlingEvent* and *Delivery*. |

### DFDs

![1-s2.0-S0164121219301475-gr8](README.assets/1-s2.0-S0164121219301475-gr8.jpg)

![1-s2.0-S0164121219301475-gr11](README.assets/1-s2.0-S0164121219301475-gr11.jpg)