# <a id="metro-station-console-application"></a> Metro Station Console Application

## Overview

The "Metro Station Console Application" project is a ```Maven```-based console application 
demonstrating various functionalities including ```Stream API``` usage, 
serialization, file operations, and logging. 

Description of the entities that are represented in the program hierarchy of classes:

| Entity        | Obligatory Fields                  |
|:--------------|:-----------------------------------|
| Metro station | Name, <br/>year of opening         |
| Hour          | Count of passengers, <br/>comments |


## <a id="metro-station-console-application-features"></a> Features

### Stream API
Using ```Stream API``` tools for all sequence processing and output functions.

### File Operations
Data output to a text file and subsequent reading using ```Stream API```. 

### Serialization
Serialization of objects into a TXT file, an XML file and a JSON file, 
and corresponding deserialization using the ```XStream``` library.

### Search data
Implementation of search and display methods according to specific criteria:
- total ridership by metro station;
- operating hours of the metro station with minimum ridership;
- operating hours of the metro station with the maximum number of words in the comments.

### Sort data
Implementation of the function of sorting using ```Collections``` and display items 
of the sequence on metro stations' operating hours by the following criteria:
- by ridership in descending order;
- by length of comments in descending order.

### Logging
Logging events related to program execution using ```Log4j2```.

### Testing
Testing methods of individual classes using ```JUnit```.

### Javadoc
Contains documentation generated in HTML format using ```JavaDoc```.



# <a id="metro-station-database-console-application"></a> Metro Station Database Console Application

## Overview

The "Metro Station Database Console Application" project is a console application designed to 
manage entities of an individual task using a ```MySQL``` relational database. 
It serves as a comprehensive tool for importing, exporting, querying, and manipulating data stored in the database.
This implementation is based on the class hierarchy in the previously created project 
["Metro Station Console Application"](#metro-station-console-application).

Description of the entities that are represented in the program hierarchy of classes:

| Entity        | Obligatory Fields                  |
|:--------------|:-----------------------------------|
| Metro station | Name, <br/>year of opening         |
| Hour          | Count of passengers, <br/>comments |


## <a id="metro-station-database-console-application-features"></a> Features

In addition to [the features](#metro-station-console-application-features) 
of the "Metro Station Console Application" legacy project, this project implements the following features:

### MySQL Database
Using the relational database management system ```MySQL``` for data storage and retrieval.

### CRUD Operations
Supports create, read, update, delete operations on database records.

### Search data
Implementation of search and display methods according to specific criteria:
- metro station by name and ID;
- total ridership by specific metro station and for all metro stations;
- operating hours of the metro station by ID and specific metro station and all operating hours;
- operating hours of the metro station with word and word fragment by specific metro station and all metro stations;
- operating hours of the metro station with minimum ridership by specific metro station and all metro stations;
- operating hours of the metro station with the maximum number of words in the comments 
by specific metro station and all metro stations.

### Sort data
Implementation of the function of sorting using ```Collections``` and display items
of the sequence on metro stations' by the following criteria:
- metro stations by name in ascending and descending order;
- metro stations by opened year in ascending and descending order;
- operating hours by ridership in ascending and descending order;
- operating hours by comment length in ascending and descending order.

### Serialization
Serialization of objects into a JSON file and corresponding deserialization using the ```XStream``` library.

### Logging
Logging events related to program execution using ```Log4j2```.

### Javadoc
Contains documentation generated in HTML format using ```JavaDoc```.



# Database GUI Application

## Overview

This project is a database data manipulation application with a graphical user interface (GUI) developed using JavaFX. 
The application processes, displays and stores entity data in a relational database.
This implementation is based on the class hierarchy in the previously created project
["Metro Station Database Console Application"](#metro-station-database-console-application).

Description of the entities that are represented in the program hierarchy of classes:

| Entity        | Obligatory Fields                  |
|:--------------|:-----------------------------------|
| Metro station | Name, <br/>year of opening         |
| Hour          | Count of passengers, <br/>comments |



## Features

### JavaFX
```JavaFX``` platform is used to create an interactive and user-friendly GUI.

### Main window
The main application window сontains menus for performing various functions of searching, sorting, 
importing and exporting data.

### Display areas 
The left side of the main application window contains the search results display area 
and buttons to perform the search functions of the program.

### Data tables
The middle part of the main window contains tables for data visualization.

### Menu bar
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
