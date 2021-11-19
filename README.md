# About the Project
A scheduling application is one of many important business tool that allows users to book, reschedule, and cancel appointments through user interfaces. 
Other than ability to view appointments in user interface, the major power of this tool is that the user also does not need to contact business managers to schedule or reschedule appointments or can make last-minute cancellation and such convenience overall saves the user and business time and money. 
Additionally, a scheduling application can be more than just working with appointments as it can be further extended to view customer and contact data as well which the business managers can quickly work around with and edit any necessary changes as the application streamlines these processes. 
Overall this project tries to emulate this useful business application and consist of several parts.


## Some Features 
| Name | Description | Screenshot |
| --- | --- | --- |
| **Login System** | * Before viewing the appointment, for security purposes the user must login using the correct username and password. * If the user inputs either one of the fields incorrect, the custom message will be displayed accordingly. * If there are appointment time nearby within 15 minutes, the user will be alerted.  | ![LoginScreen](https://user-images.githubusercontent.com/42017999/142559803-ca106359-1b9c-4468-b032-ba6b56aa1a71.png) ![LoginScreenError](https://user-images.githubusercontent.com/42017999/142560478-60962b2f-dd87-4ad7-9d71-be69eb85761f.png) ![LoginScreenSuccess](https://user-images.githubusercontent.com/42017999/142560625-1e66a811-fe44-4d19-9838-e1c0329a3a97.png) |
| **Main Screen** | The main screen of the application features list of all appointments table and appointments by week and month. The table displays the appointment start and end time according to the user’s local time. The list of customers can be view within by clicking the “view customer list” button and the special report can be access from the “Check Report” button. The data can be view by order by clicking the table column. | ![MainScreen](https://user-images.githubusercontent.com/42017999/142560708-028593fa-4ad2-40d1-a2d9-93973b6c8491.png) |
| **Customer Screen** | The customer screen displays list of customers which only includes their Id, Name and their Division. Similar to the main screen, the existing customer data can be modify and delete or add new customer by clicking on the respective buttons. | ![CustomerScreen](https://user-images.githubusercontent.com/42017999/142561174-a50314cb-1fb7-4389-b2c7-7b778ef8e315.png) |
| **Logging** | Other feature of the application is that the program tracks user activity by recording all user log-in attempts, dates, and time stamps and whether each attempt was successful in a file named login_activity.txt.| ![2021-11-18 22_39_26-Window](https://user-images.githubusercontent.com/42017999/142561054-69389346-c658-48aa-b291-c8f706168679.png) |

There are many more and all of them were discussed on the main project presentation file.


### Directions for how to run the program:
To run the program, open the project from the IDE and open the "main.java" from the src/main/main.java directory.

### Language supports - English, French 

### the MySQL Connector driver version number: mysql-connector-java-8.0.21
