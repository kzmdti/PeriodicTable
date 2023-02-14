#
## Cleanup the project

First I delete the `.idea` directory and add a new `.gitignore` file.  
In the `.gitignore` file I added the next two lines:
* build
* .gradle

  
#
## Changes to the build.gradle

In the `build.gradle` file I make the next changes:  
* deleted the previous `plugin` section and added the below one:
```
plugins {
    id 'application'
}
```
* added the `application` section, addressing the Main class:
```
application {
    mainClass = 'Main'
}
```
* added also the `jar` section, and addressed the Main class.
```
jar {
    manifest{
        attributes 'Main-Class': 'Main'
        
    }
}
```
#
## Tests to build and run
From the terminal in the project directory:
1. Build the application
```
~ gradlew build
```
   - and run the `.jar` file.
```
~ java -jar build\libs\PeriodicTable-1.0-SNAPSHOT.jar
```

2. Run directly with
```
~ gradlew run
```

All the tests runs correctly.  
Also, after extracting the application from the **.zip** file in `.\build\distribution` and run the application we find in `.\bin` the result is correct.
