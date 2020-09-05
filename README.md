# Robot Challenge

## Supported Commands

The robot can read in commands of the following form:

```plain
PLACE     PLACE 0,0,NORTH,jack
MOVE      MOVE jack
LEFT      LEFT jack
RIGHT     RIGHT jack
REPORT    REPORT jack
RESET*    RESET
MODE*     MODE SINGLE
EXIT*     EXIT
EXTEND    EXTEND 10,10
```

- PLACE will put the toy robot on the table in position X,Y(The origin (0,0) can be considered to be the SOUTH WEST most corner.) and facing NORTH, SOUTH, EAST or WEST.
- MOVE will move the toy robot one unit forward in the direction it is currently facing.
- LEFT and RIGHT will rotate the robot 90 degrees in the specified direction without changing the position of the robot.
- REPORT will announce the X,Y and orientation of the robot.
- RESET will remove all toys on the table.
- MODE 4 modes supported.
    * SINGLE default
    * MULTI_EAT Support multiple toys(less than the number of squares table) running on one table, it will "eat" previously placed toy in the same direction when it is moved.
    * MULTI_BOUNCE If there is already a toy in the target position, the toy will be pushed forward together in same direction.
    * MULTI_BOUNCE_WHEEL_WAY If there is already a toy in the target position with the same direction, the toy will be pushed forward together in same direction, otherwise, it will stands still. 
- EXIT will shutdown the application.
- EXTEND will extend the dimensions to X units to y units
## Getting started

### Dependencies
Java 1.8
- Check if JDK has been installed, or download from https://docs.aws.amazon.com/corretto/latest/corretto-8-ug/downloads-list.html
```
java -version
```

### Build
Checkout the source code and run the following command in the /toy_robot directory:
```
./mvnw clean package
```
### Usage

```
java -jar ./module-terminal/target/terminal.jar -Dlog.path=YOUR_LOG_PATH
```



