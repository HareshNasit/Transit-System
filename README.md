# Transit-System
Program which manages the transit system in a particular city for bus and subway. 

The UI for the project is divided into 2 screens, the Manager and the User.
The manager decides when to start the transit system and can check the statistics, log and other information offline.
When the system is running, the user can select whether they are travelling by Bus or Subway.
A 2 hour transfer is added when the initial tap is made and works on either of the transit.

# Simulation
To run the simulation, run the class MainSystem that will prompt a screen to either create a new system or load
a system if it exists.

Creating a new system allows the manager to initialize the system with the bus cost and the subway trip cost.
Once the system is created, Users can sign up and use the system when the manager turns it on.
The manager can see the statistics and logs of the system.

# User Screen
The user can sign up for an existing system.
They can add new cards which are initialized with 19 dollars.
Users can travel across different stations available and the costs are reduced accordingly.
They can also view their recent trips and suspend their cards if stolen.
There is a special tap Off button which accelerates the time for checking if a new trip is created when a user
exceeds the 2 hrs limit while travelling if the cap < $6.

# Admin Screen

The admin can view the transit information for that particular day in the Transit Information tab.
The admin can also see the statistics for each station and each card in the system by clicking the Statistics tab.
There is another tab called Log which lets the admin see all the previous days logs and check the revenue and all
the statistics.
Finally the admin will end the day by clicking on End Day button and when the admin closes all the screens, the
data will be saved in the instances directory if the system was loaded or be created as a new system.
