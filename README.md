# Robot Challenge

## Supported Commands

Create an application that can read in commands of the following form:

```plain
PLACE
MOVE
LEFT
RIGHT
REPORT
RESET
MODE
EXIT
```

- PLACE will put the toy robot on the table in position X,Y and facing NORTH, SOUTH, EAST or WEST.
- The origin (0,0) can be considered to be the SOUTH WEST most corner.
- The first valid command to the robot is a PLACE command, after that, any sequence of commands may be issued, in any order, including another PLACE command. The application should discard all commands in the sequence until a valid PLACE command has been executed.
- MOVE will move the toy robot one unit forward in the direction it is currently facing.
- LEFT and RIGHT will rotate the robot 90 degrees in the specified direction without changing the position of the robot.
- REPORT will announce the X,Y and orientation of the robot.
- A robot that is not on the table can choose to ignore the MOVE, LEFT, RIGHT and REPORT commands.
- Provide test data to exercise the application.

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



