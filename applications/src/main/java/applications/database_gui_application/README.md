# <a id="metro-station-database-gui-application"></a> "Metro Stations" Database GUI Application

## Overview

The "Metro Stations" Database GUI Application project is a database data manipulation application with a graphical user interface (GUI)
developed using JavaFX that organizes information about metro stations,
including statistical data on operating hours and ridership.
It serves as a comprehensive tool for importing, exporting, querying, and manipulating data stored in the database.
The implementation is based on the class hierarchy in the previously created project
["Metro Stations" Database Console Application](https://github.com/InessaRepeshko/java-application-metro-stations/blob/bb04c0dfdf8b50868897276c234086df909bc531/applications/src/main/java/applications/database_console_application/README.md#metro-station-database-console-application).

Description of the entities that are represented in the program hierarchy of classes:

| Entity        | Obligatory Fields                  |
|:--------------|:-----------------------------------|
| Metro station | Name, <br/>year of opening         |
| Hour          | Count of passengers, <br/>comments |


## <a id="metro-station-database-gui-application-features"></a> Features

In addition to [the features](https://github.com/InessaRepeshko/java-application-metro-stations/blob/bb04c0dfdf8b50868897276c234086df909bc531/applications/src/main/java/applications/database_console_application/README.md#metro-station-database-console-application-features)
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

© Inessa Repeshko. 2024
