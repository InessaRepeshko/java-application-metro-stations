<div align="center"><img src="https://github.com/user-attachments/assets/d0d383bb-de61-43f4-b2f4-320b2c8cc055" width="200"/></div>
<h1 align="center"><a id="metro-station-database-gui-application"></a> "Metro Stations" Database GUI Application</h1>

<p align="center">
   <img src="https://img.shields.io/badge/Java-007396?logo=java&logoColor=white" alt="Java" />
   <img src="https://img.shields.io/badge/JavaFX-007396?logo=javafx&logoColor=white" alt="JavaFX" />
   <img src="https://img.shields.io/badge/MySQL-4479A1?logo=mysql&logoColor=white" alt="MySQL" />
   <img src="https://img.shields.io/badge/XStream-2A2A2A?logo=xstream&logoColor=white" alt="XStream" />
   <img src="https://img.shields.io/badge/Log4j2-FF4500?logo=apache&logoColor=white" alt="Log4j2" />
   <img src="https://img.shields.io/badge/JUnit-25A162?logo=junit5&logoColor=white" alt="JUnit" />
</p>


## Table of Contents
- [Overview](#overview)
- [Features](#metro-station-database-gui-application-features)
- [Screens](#screens)


## Overview

The "Metro Stations" Database GUI Application project is a database data manipulation application with a graphical user interface (GUI)
developed using JavaFX that organizes information about metro stations,
including statistical data on operating hours and ridership.
It serves as a comprehensive tool for importing, exporting, querying, and manipulating data stored in the database.
The implementation is based on the class hierarchy in the previously created project
["Metro Stations" Database Console Application](https://github.com/InessaRepeshko/java-application-metro-stations/tree/main/applications/src/main/java/applications/database_console_application/README.md#metro-station-database-console-application).

Description of the entities that are represented in the program hierarchy of classes:

| Entity        | Obligatory Fields                  |
|:--------------|:-----------------------------------|
| Metro station | Name, <br/>year of opening         |
| Hour          | Count of passengers, <br/>comments |


## <a id="metro-station-database-gui-application-features"></a> Features

In addition to [the features](https://github.com/InessaRepeshko/java-application-metro-stations/tree/main/applications/src/main/java/applications/database_console_application/README.md#metro-station-database-console-application-features)
of the "Metro Stations" Database Console Application legacy project, this project implements the following features:

### JavaFX
```JavaFX``` platform is used to create an interactive and user-friendly GUI.

### Main application window
The main application window сontains menus for performing various functions of searching, sorting, 
importing and exporting data.

### Data tables
The middle part of the main window contains tables for data visualization.

### Search result display areas 
The left side of the main application window contains the search results display area 
and buttons to perform the search functions of the program.

### Application menu bar
The menu bar includes functions for working with files, such as creating, importing and exporting a file. 
The panel also contains functionality for closing the application, 
sorting data from both tables of the application and information about the application.

### Table editing mode windows
The application includes two additional windows for creating, reading, updating and deleting (CRUD) records
for the database tables with data validation.
Both windows contain three tabs each, on which the functionality of creating, editing and deleting records is divided, 
with corresponding fields, buttons and areas for displaying search results.

### Pop-up windows
The application also contains various pop-up windows with notifications or errors 
that occur as a result of the user interacting with interface functions or validating data received from the user.

### Logging
Logging events related to program execution using ```Log4j2```.

### Javadoc
Contains documentation generated in HTML format using ```JavaDoc```.


## Screens

### The results of the GUI application - creating an object via ```File -> New```
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/main_1.jpg">
</div><br />

### Contents of the formatted "MetroStationsFromDB.json" file with the exported data from database
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/json_file_1.jpg">
</div><br />

### The results of the GUI application - importing the file "MetroStation.json" via ```File -> Open...```
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/main_3.jpg">
</div><br />

### Results of the GUI application - displaying an error when loading an empty JSON file
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/error_1.jpg">
</div><br /><br />


### Results of the GUI application - sorting table data via ```Run``` -> ```Sort "MetroStations" Table``` 
* -> ```Sort by ascending name```
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/sort_1.jpg">
</div><br />

* -> ```Sort by descending name```
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/sort_2.jpg">
</div><br />

* -> ```Sort by ascending opened year```
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/sort_3.jpg">
</div><br />

* -> ```Sort by descending opened year```
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/sort_4.jpg">
</div><br /><br />


### Results of the GUI application - sorting table data via ```Run``` -> ```Sort "Hours" Table``` 
* -> ```Sort by ascending ridership```
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/sort_5.jpg">
</div><br />

* -> ```Sort by descending ridership```
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/sort_6.jpg">
</div><br />

* -> ```Sort by ascending comment length```
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/sort_7.jpg">
</div><br />

* -> ```Sort by descending comment length```
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/sort_8.jpg">
</div><br /><br />


### The results of the GUI application - searching for table data via  ```Search by word button```
*  test data: _""_
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/search_1.jpg">
</div><br />

* test data: _"test"_
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/search_2.jpg">
</div><br />

* test data: _"medium"_
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/search_3.jpg">
</div><br />

* test data: _"ow"_
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/search_4.jpg">
</div><br /><br />


### Results of the GUI application - searching for table data via  ```Search Metro Station``` button 
* test data: _""_
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/search_5.jpg">
</div><br />

* test data: _"test"_
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/search_6.jpg">
</div><br />

* test data: _"Kholodna Gora"_
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/search_7.jpg">
</div><br /><br />


### Results of the GUI application - searching for table data via  ```Get total ridership``` button
* test data: _""_
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/search_8.jpg">
</div><br />

* test data: _"test"_
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/search_9.jpg">
</div><br />

* test data: _"Politekhnichna"_
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/search_10.jpg">
</div><br /><br />


### Results of the GUI application - searching for table data via  ```Search Hours with min ridership``` button
* test data: _""_
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/search_11.jpg">
</div><br />

* test data: _"test"_
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/search_12.jpg">
</div><br />

* test data: _"Politekhnichna"_
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/search_13.jpg">
</div><br /><br />


### Results of the GUI application - searching for table data via  ```Search Hours with max word count``` button
* test data: _""_
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/search_14.jpg">
</div><br />

* test data: _"test"_
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/search_15.jpg">
</div><br />

* test data: _"Politekhnichna"_
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/search_16.jpg">
</div><br /><br />


### The results of the GUI application - opening the ```Edit "MetroStations" Table``` window through the ```Edit mode``` button under the ```Table "MetroStations"```
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/edit_ms_1.jpg">
</div><br />


### Results of the graphical user interface application - window ```Edit "MetroStations" Table``` -> tab ```Add```
* adding a metro station via  ```Add``` button
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/edit_ms_2.jpg">
</div><br />

* clearing field data via  ```Clear``` button
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/edit_ms_3.jpg">
</div><br /><br />

### Results of the graphical user interface application - window ```Edit "MetroStations" Table``` -> tab ```Edit```
* search for a metro station via  ```Search``` button
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/edit_ms_4.jpg">
</div><br />

* update metro station data via the ```Update``` button
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/edit_ms_5.jpg">
</div><br />

* clearing field data via  ```Clear``` button
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/edit_ms_6.jpg">
</div><br /><br />


### Results of the graphical user interface application - window ```Edit "MetroStations" Table``` -> tab ```Remove```
* search for a metro station via  ```Search``` button
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/edit_ms_7.jpg">
</div><br />

* remove a metro station via  ```Remove``` button
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/edit_ms_8.jpg">
</div><br />

* clearing field data via  ```Clear``` button
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/edit_ms_9.jpg">
</div><br /><br />


### The results of the GUI application - closing the ```Edit "MetroStations" Table``` window via  ```Close``` button on the ```Remove``` tab and displaying automatically updated data in the ```Table "MetroStations"```
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/edit_ms_10.jpg">
</div><br /><br />


### The results of the GUI application - opening the ```Edit "Hours" Table``` window through the ```Edit mode``` button under the ```Table "Hours"```
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/edit_hours_1.jpg">
</div><br /><br />


### Results of the graphical user interface application - window ```Edit "Hours" Table``` -> tab ```Add```
* adding an hour via  ```Add``` button
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/edit_hours_2.jpg">
</div><br />

* clearing field data via  ```Clear``` button
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/edit_hours_3.jpg">
</div><br /><br />


### Results of the graphical user interface application - window ```Edit "Hours" Table``` -> tab ```Edit```
* search for an hour via the ```Search``` button
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/edit_hours_4.jpg">
</div><br /><br />

* update the hour data via the ```Update``` button
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/edit_hours_5.jpg">
</div><br /><br />

* clearing field data via  ```Clear``` button
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/edit_hours_6.jpg">
</div><br /><br /><br />


### Results of the graphical user interface application - window ```Edit "Hours" Table``` -> tab ```Remove```
* search for hours via  ```Search``` button
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/edit_hours_7.jpg">
</div><br />

* remove an hour via the ```Remove``` button
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/edit_hours_8.jpg">
</div><br />

* clearing field data via ```Clear``` button
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/edit_hours_9.jpg">
</div><br /><br />


### The results of the GUI application - closing the ```Edit "Hours" Table``` window via ```Close``` button on the ```Remove``` tab and displaying automatically updated data in the ```Table "Hours"```
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/edit_hours_10.jpg">
</div><br /><br />


### The results of the GUI application - opening a window with information about the program and the author via ```Help -> About...```
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/main_3.jpg">
</div><br />

### Results of the GUI application - exporting data from the application to the file _"ExportedMetroStations.json"_ via ```File -> Save```
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/main_4.jpg">
</div><br />

### Formatted contents of the exported file _"ExportedMetroStations.json"_
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/json_file_2.png">
</div><br /><br />

### Results of the GUI application - displaying the ```File``` menu
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/main_5.jpg">
</div><br /><br />

### Results of the GUI application - displaying the ```Help``` menu
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/main_6.jpg">
</div><br /><br />

### The contents of the file _"MetroStationsFX-2024-05-28.log"_ based on the results of the GUI application
<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_gui_application/log_file.jpg">
</div><br /><br />



© Inessa Repeshko. 2024
