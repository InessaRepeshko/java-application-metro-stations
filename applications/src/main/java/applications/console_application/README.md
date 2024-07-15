# <a id="metro-station-console-application"></a> "Metro Stations" Console Application

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
