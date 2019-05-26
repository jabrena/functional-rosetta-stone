# functional-rosetta-stone

[![Build Status](https://travis-ci.org/jabrena/functional-rosetta-stone.svg?branch=master)](https://travis-ci.org/jabrena/functional-rosetta-stone)

## Goal

Improve the functional programming skills for Java Developers

## Introduction

Functional programming for Java developers offer new possibilities in the daily job
to improve the Skills. The #FP features was added in Java 8.

What features was added in Java 8+?

 - Lambdas
 - Java Streams
 - Collectors
 - Optional
 - Functional interfaces 

What concepts from Functional programming ecosystem, you should review?

 - Equational reasoning
 - Referential Transparency
 - Side effects
 - Pure functions
 - Higher-order functions
 - Lazy evaluation
 - Closures
 - Currying
 - Monads

What libraries could help you to enrich your #FP experience with Java?

 - VAVR
 - Lombok
 - Reactor

## Functional programming features with Java 8+?

### Lambdas

To understand Lambdas, it is necessary to understand the concept about Anonymous classes. 

> Anonymous classes are inner classes with no name

``` java
Collections.sort(personList, new Comparator<Person>(){
  public int compare(Person p1, Person p2){
    return p1.firstName.compareTo(p2.firstName);
  }
});
```

> A Java lambda expression is thus a function which can be created without belonging to any class. A Java lambda expression can be passed around as if it was an object and executed on demand.

``` java
Collections.sort(personList, (Person p1, Person p2) -> p1.firstName.compareTo(p2.firstName));
```

https://docs.oracle.com/javase/tutorial/java/javaOO/anonymousclasses.html
https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html
https://stackoverflow.com/questions/22637900/java8-lambdas-vs-anonymous-classes

### Java Streams

The Java added a new abstraction called Stream that lets you process data in a declarative way. 

![](https://pbs.twimg.com/media/D6U9cu-WAAMOOGM?format=jpg)

Java 8 docs: https://docs.oracle.com/javase/8/docs/api/?java/util/stream/Stream.html
Java 9 docs: https://docs.oracle.com/javase/9/docs/api/?java/util/stream/Stream.html

**Intermediate Operations**

Java 8

- filter Ok
- map Ok
- flatMap Ok
- distinct Ok
- sorted Ok
- peek Ok
- limit Ok
- skip Ok

Java 9

- takeWhile
- dropWhile

**Terminal Operations**

- forEach Ok
- toArray
- reduce
- collect OK
- min
- max
- count Ok
- anymatch
- allMatch
- noneMatch
- findFirst Ok
- findAny

### Optional

A container object which may or may not contain a non-null value.

https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html

**Note** I recommend to use Option from VAVR.

### Functional Interfaces

**Java +**


**VAVR**

``` java

//VAVR
Function1<String, String> toUpper = String::toUpperCase;
Function1<String, String> trim = String::trim;
Function1<String, String> cheers = (s) -> String.format("Hello %s", s);

assertThat(trim
        .andThen(toUpper)
        .andThen(cheers)
        .apply("   john")).isEqualTo("Hello JOHN");
```

## Functional Programming concepts

###Equational reasoning

```
2*x + x/y
= 2*8 + 8/y { x }
= 2*8 + 8/4 { y }
= 16 + 2 { arithmetic }
= 18 { arithmetic }
```

Sometimes the justification is an explicit reference to an equation; thus the
justification { x } means “the equation defining x allows this step to be taken”.
In other cases, where we don’t want to give a formal justification because it is
too trivial and tedious, an informal reason is given, for example { arithmetic
}.
The format for writing a chain of equational reasoning is not rigidly specified. The reasoning above was written with each step on a separate line, with
the justifications aligned to the right. This format is commonly used; it is
compact and readable for many cases.

Source: https://link.springer.com/chapter/10.1007%2F1-84628-598-4_2

###Referential Transparency

Referential transparency means that a function call can be replaced by its value 
or another referentially transparent call with the same result.

Source: https://www.sitepoint.com/what-is-referential-transparency/#referentialtransparencyinprogramming


###Side effects

A side effect is anything a method does besides computing and returning a value. 
Any change of instance or class field values is a side effect, as is drawing something on the screen, 
writing to a file or a network connection.

``` java 
public class SideEffectClass{

    private int state = 0;

    public doSomething(...){//Does not matter
        state ++;
    }
}
```

Source: https://stackoverflow.com/questions/1073909/side-effect-whats-this

###Pure functions

A function is called pure function if it always returns the same result for same argument values 
and it has no side effects like modifying an argument (or global variable) or outputting something. 

``` java  
public int sum(int a, int b) {
    return a + b;
}
```

Source: https://www.geeksforgeeks.org/pure-functions/

###Higher-order functions

Source: https://dzone.com/articles/higher-order-functions

 - Lazy evaluation
 - Closures
 - Currying
 - Monads
 - Function composition
 - Memoization
 - Lifting
 - Partial application

### Monads

 - Try
 - Option
 - Either
 - Lazy

---
  
## Data sets
 
 - https://github.com/tsevdos/greek-in-tech/blob/master/data/entries.json
 - https://gist.github.com/cdtweb/27f001287c82c913e7a344d7b71141f8
 - https://github.com/jdorfman/awesome-json-datasets#nobel-prize
 - http://api.nobelprize.org/v1/prize.json
 - https://www.w3schools.com/xml/books.xml
 
## Utilities
 
 - https://jsonlint.com/
 - http://www.jsonschema2pojo.org/
 - https://www.freecodeformat.com/json2pojo.php


## References

 - https://rxmarbles.com/
 - https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html
 - https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Mono.html
 - http://reactivex.io/documentation/operators.html
 - http://www.vavr.io/vavr-docs/