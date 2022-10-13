As a User, I want to view tracking, so that I can find location of Cargo
{
Basic Flow {
	  (User) 1. the User shall input tracking id of cargo.
	  (System) 2. the System shall return and present relevant information of cargo.
    }
}
As a Admin, I want to create location, so that users can transport cargos to or from different locations
As a CargoPlanner, I want to view cargos, so that I can manage cargos according to their information 
As a CargoPlanner, I want to book cargo, so that I can generate a cargo in system
As a CargoPlanner, I want to change cargo destination, so that I can make cargo transport to right destination
As a CargoPlanner, I want to route carge, so that I can arrange transportation routes for a cargo
As a User, I want to view, so that jgoisg
{
Alternative Flow {
	A. At any time, Manager requests an override operation :
      1.  System enters Manager authorized mode.
      2.  Manager or Cashier performs one Manager mode operation cash balance change resume a suspended sale on another register void a sale etc.
      3.  System reverts to Cashier authorized mode.
    B. At any time, System fails :
      To support recovery and correct accounting ensure all mansaction sensitive state and events can be recovered from any step of scenario.
      1.  Cashier restarts System logs in and requests recovery of prior state.
      2.  System reconstructs prior state.
          a2.  System detects anomalies preventing recovery.
             1.  System signals error to Cashier records error and enters a clean state.
    }
}
As a Cashier, I want to open cash desk, so that customers can pay
As a Cashier, I want to open cash desk, so that customers can pay

