# Poneymon

An awesome game featuring poneys challenging themselves in an intense fight to win a race !


## Documentation

Voir le Wiki.

### DIA



### Javadoc



### Report




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


## Acteurs

* **Jonathan CABEZAS p1513280** - *SCRUM Master et référent technique JavaFX*
* **Louis POITEVIN p1410541** - *Référent technique Animation et Sprites*
* **Gabriello ZAFIFOMENDRAHA p1311399** - *Responsable qualité*
* **Alexandre HOET p1502007** - *Tracker et référent technique réseau*
* **Elodie MONTCARMEL p1710323** - *Product Owner*


## Acknowledgments

* Hat tip to the teachers for providing the initial code and idea
* Thanks to everyone who gave advice on MVC
