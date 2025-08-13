<div align="center"><img src="https://github.com/user-attachments/assets/d0d383bb-de61-43f4-b2f4-320b2c8cc055" width="200"/></div>
<h1 align="center"><a id="metro-station-database-console-application"></a> "Metro Stations" Database Console Application</h1>

<p align="center">
   <img src="https://img.shields.io/badge/Java-007396?logo=java&logoColor=white" alt="Java" />
   <img src="https://img.shields.io/badge/MySQL-4479A1?logo=mysql&logoColor=white" alt="MySQL" />
   <img src="https://img.shields.io/badge/XStream-2A2A2A?logo=xstream&logoColor=white" alt="XStream" />
   <img src="https://img.shields.io/badge/Log4j2-FF4500?logo=apache&logoColor=white" alt="Log4j2" />
   <img src="https://img.shields.io/badge/JUnit-25A162?logo=junit5&logoColor=white" alt="JUnit" />
</p>


## Table of Contents
- [Overview](#overview)
- [Features](#metro-station-database-console-application-features)
- [Screens](#screens)
 

## Overview

The "Metro Stations" Database Console Application project is a console application that manages data 
about metro stations and statistical data on the operating hours and ridership of the stations 
using a ```MySQL``` relational database.
It serves as a comprehensive tool for importing, exporting, querying, and manipulating data stored in the database.
The implementation is based on the class hierarchy in the previously created project 
["Metro Stations" Console Application](https://github.com/InessaRepeshko/java-application-metro-stations/tree/main/applications/src/main/java/applications/console_application/README.md#metro-station-console-application).

Description of the entities that are represented in the program hierarchy of classes:

| Entity        | Obligatory Fields                  |
|:--------------|:-----------------------------------|
| Metro station | Name, <br/>year of opening         |
| Hour          | Count of passengers, <br/>comments |


## <a id="metro-station-database-console-application-features"></a> Features

In addition to [the features](https://github.com/InessaRepeshko/java-application-metro-stations/tree/main/applications/src/main/java/applications/console_application/README.md#metro-station-console-application-features) 
of the "Metro Stations" Console Application legacy project, this project implements the following features:

### MySQL Database
Using the relational database management system ```MySQL``` for data storage and retrieval.

### DML, DDL commands
Execute commands for creating, reading, updating, deleting database records, creating and deleting tables and database.

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


## Screens

### Results of program code execution

<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_console_application/results.jpg">
</div><br />

### Contents of the formatted "MetroStations.json" file with the exported data

<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_console_application/json_file_1.jpg">
</div><br />

### Contents of the formatted "MetroStationsFromDB.json" file with the exported data from database

<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_console_application/json_file_2.jpg">
</div><br />

### Contents of the "MetroStationsApp.log" file

<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/database_console_application/log_file.jpg">
</div><br />



© Inessa Repeshko. 2024
