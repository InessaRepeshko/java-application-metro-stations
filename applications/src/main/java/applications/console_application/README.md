<div align="center"><img src="https://github.com/user-attachments/assets/d0d383bb-de61-43f4-b2f4-320b2c8cc055" width="200"/></div>
<h1 align="center"><a id="metro-station-console-application"></a> "Metro Stations" Console Application</h1>

<p align="center">
   <img src="https://img.shields.io/badge/Java-007396?logo=java&logoColor=white" alt="Java" />
   <img src="https://img.shields.io/badge/Maven-C71A36?logo=apachemaven&logoColor=white" alt="Maven" />
   <img src="https://img.shields.io/badge/XStream-2A2A2A?logo=xstream&logoColor=white" alt="XStream" />
   <img src="https://img.shields.io/badge/Log4j2-FF4500?logo=apache&logoColor=white" alt="Log4j2" />
   <img src="https://img.shields.io/badge/JUnit-25A162?logo=junit5&logoColor=white" alt="JUnit" />
</p>


## Table of Contents
- [Overview](#overview)
- [Features](#metro-station-console-application-features)
- [Screens](#screens)


## Overview

The "Metro Stations" Console Application ```Maven```-based project of a console application for working with 
TXT, XML and JSON files with data on metro stations and statistics on operating hours of stations and ridership. 
The application demonstrates functionality such as using ```Stream API```, serialization, file operations and logging.

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

### Testing
Testing methods of individual classes using ```JUnit```.

### Logging
Logging events related to program execution using ```Log4j2```.

### Javadoc
Contains documentation generated in HTML format using ```JavaDoc```.


## Screens

### Results of program code execution

<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/console_application/results.jpg">
</div><br />

### Contents of the "MetroStation.txt" file

<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/console_application/txt_file.jpg">
</div><br />

### Contents of the "MetroStation.xml" file

<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/console_application/xml_file.jpg">
</div><br />

### Contents of the formatted "MetroStation.json" file

<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/console_application/json_file.jpg">
</div><br />

### Contents of the "MetroStation.log" file

<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/console_application/log_file.jpg">
</div><br />

### Results of unit testing of program code using Junit

<div align="center">
    <img src="https://github.com/InessaRepeshko/java-application-metro-stations/blob/main/screens/console_application/unit_tests.jpg">
</div><br />



© Inessa Repeshko. 2024
