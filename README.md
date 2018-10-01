# Poneymon

An awesome game featuring poneys challenging themselves in an intense fight to win a race !


## How to play

In the current version, there is no menu to choose poneys,
so we chose to instantiate 5 poneys. The first and fifth ones
can be played by humans and the power can be used with the keys
NUMPAD1 and NUMPAD5 respectively. The power can only be used once.

### Poneys controlled by IA

* The 2nd poney implements the "MoreSpeedNyanStrategy" which uses the power when he has a high speed (> 0.5), 1 being the highest speed
* The 3rd poney implements the "NotEnoughSpeedNyanStrategy" which uses the power when he has a low speed (< 0.4)
* The 4th poney implements the "ImStillHereNyanStrategy" which uses the power when he can reduce the distance between him and the leading poney by 0.5, with 1 being a full turn 


## Documentation

All the documentation is in the doc/ folder.

### DIA

The DIA diagrams are in doc/dia/.

### Javadoc

The javadoc can be accessed at doc/javadoc/index.html

### Report

The report is at doc/rapport_poneymon.pdf


## Getting Started

To get a copy of the project working, run the following command :

```
clone https://forge.univ-lyon1.fr/p1513280/poneymon.git
```

### Prerequisites

Make sure you have Maven installed :

```
mvn --version
```

### Running the program

To run the program in command line :

```
mvn compile
mvn exec:java
```

### Running the tests

```
mvn test
```

### Building the program

To build the program and run the tests :

```
mvn install
```

It will produce a .jar that you can run giving the main class :

```
java -cp target/poneymon_fx-0.0.1-SNAPSHOT.jar fr.univ_lyon1.info.m1.poneymon_fx.App
```


## Packaging

It will also produce a package containing all the dependances that you can run anywhere :

```
java -jar target/poneymon_fx-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```

## Coding style tests

Based on
Checkstyle configuration that checks the Google coding conventions from Google Java Style
that can be found at https://google.github.io/styleguide/javaguide.html.
 
Checkstyle is very configurable. Documentation can be read at
http://checkstyle.sf.net.

To completely disable a check, just comment it out or delete it from the file.

Authors: Max Vetrenko, Ruslan Diachenko, Roman Ivanov.



## Built With

* [Maven](https://maven.apache.org/) - Dependency Management


## Software used

* [Dia](http://dia-installer.de/index.html.fr) - Diagram Editor


## Authors

* **Jonathan CABEZAS p1513280** - *Main Developper*
* **Rémy NEVEU p1710792** - *Main Developper*


## Acknowledgments

* Hat tip to the teachers for providing the initial code and idea
* Thanks to everyone who gave advice on MVC
