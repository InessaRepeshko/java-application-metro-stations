# <a id="metro-station-console-application"></a> "Metro Stations" Console Application

## Overview

The "Metro Stations" Console Application project is a ```Maven```-based console application 
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

### Testing
Testing methods of individual classes using ```JUnit```.

### Logging
Logging events related to program execution using ```Log4j2```.

### Javadoc
Contains documentation generated in HTML format using ```JavaDoc```.

© Inessa Repeshko. 2024
