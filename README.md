# Functional Rosetta Stone

[![Java CI](https://github.com/jabrena/functional-rosetta-stone/actions/workflows/maven.yml/badge.svg)](https://github.com/jabrena/functional-rosetta-stone/actions/workflows/maven.yml)

![](./docs/rosetta_stone.png)

## Goal

A repository to review the main concepts about Functional Programming with Java.

## How to build the project in local?

```bash
sdk env install
./mvnw clean test -DexcludedGroups=performance,endtoend
./mvnw clean test -DexcludedGroups=performance,endtoend -pl training
./mvnw clean test -DexcludedGroups=performance,endtoend -Dtest=LoomExamplesTest -pl training
./mvnw clean test -Dgroups=performance
./mvnw clean test -Dgroups=endtoend

./mvnw versions:display-property-updates
./mvnw versions:display-dependency-updates
./mvnw versions:display-plugin-updates
./mvnw dependency:tree -pl problems 
```

## Functional programming features in Java

- [x] [Lambda Expressions](https://openjdk.org/jeps/126) (Functional interfaces, Functions, Supplier, Consumer & Predicates)
- [x] [Optional](https://openjdk.org/jeps/401)
- [x] Stream API 
- [x] CompletableFuture & Structural Concurrency
- [x] Immutable Lists
- [x] Sealed Classes
- [ ] Pattern Matching for Switch
- [x] Records & Record Patterns

## Functional programming timeline in Java

| Java Version | Feature                                                                             | Date      | Release notes                                                          |
|--------------|-------------------------------------------------------------------------------------|-----------|------------------------------------------------------------------------|
| Java 8       | - Lambda Expressions - Optional - Stream API - CompletableFuture                   | 18/3/2014 | https://www.oracle.com/java/technologies/javase/8-whats-new.html       |
| Java 9       | - CompletableFuture updates                                                         | 21/9/2017 | https://www.oracle.com/java/technologies/javase/9-all-relnotes.html    |
| Java 10      | - Optional updates - Immutable Lists                                               | 20/3/2018 | https://www.oracle.com/java/technologies/javase/10-relnote-issues.html |
| Java 11      | - Not Predicate operator - Local-Variable Syntax for Lambda                         | 25/9/2018 | https://www.oracle.com/java/technologies/javase/11all-relnotes.html    |
| Java 12      | - Teeing Collector - Pattern Matching                                               | 19/3/2019 | https://www.oracle.com/java/technologies/javase/12-relnote-issues.html |
| Java 13      | - Switch Expressions enhancements                                                   | 17/9/2019 | https://www.oracle.com/java/technologies/javase/13-relnote-issues.html |
| Java 14      | - Records                                                                           | 17/3/2020 | https://www.oracle.com/java/technologies/javase/14-relnote-issues.html |
| Java 15      | - Sealed Classes (Preview)                                                          | 15/9/2020 | https://www.oracle.com/java/technologies/javase/15-relnote-issues.html |
| Java 16      | - Sealed Classes (Preview) - Stream.toList                                          | 16/3/2021 | https://www.oracle.com/java/technologies/javase/16-relnote-issues.html |
| Java 17      | - Sealed Classes (JEP 409) - Pattern Matching for Switch (JEP 406) (Preview)        | 14/9/2021 | https://www.oracle.com/java/technologies/javase/17-relnote-issues.html |
| Java 18      | - Pattern Matching for switch (JEP 420) (Preview)                                   | 22/3/2022 | https://www.oracle.com/java/technologies/javase/18all-relnotes.html    |
| Java 19      | - Record Patterns - Pattern Matching for switch (JEP 427) (Preview) | 20/9/2022 | https://www.oracle.com/java/technologies/javase/19-relnote-issues.html |
| Java 20      | - Record Patterns (JEP 432) - Pattern Matching for Switch (JEP 433) (Preview)       | 21/3/2023 | https://www.oracle.com/java/technologies/javase/20-relnote-issues.html |
| Java 21      | - Record Patterns (JEP 440) - Pattern Matching for switch (JEP 441)                 | 19/9/2023 | https://www.oracle.com/java/technologies/javase/21-relnote-issues.html |
| Java 22      | - Stream Gatherers (JEP 461) (Preview)                                              | 19/3/2024 | https://www.oracle.com/java/technologies/javase/22-relnote-issues.html |


## How to run the presentation in local?

```bash
jwebserver -p 9000 -d "$(pwd)/docs/"
```

## Performance

Using the [jmh-results.json](https://github.com/jabrena/functional-rosetta-stone/blob/master/docs/jmh-results.json) 
you can review the performance results: 

- [https://jmh.morethan.io/](http://jmh.morethan.io/?source=https://raw.githubusercontent.com/jabrena/functional-rosetta-stone/master/docs/jmh-results.json)
- https://nilskp.github.io/jmh-charts/
- https://github.com/akarnokd/jmh-compare-gui

## References

- https://github.com/jabrena/latency-problems
- https://github.com/forax/loom-fiber
- https://cr.openjdk.org/~vklang/Gatherers.html
- https://github.com/forax/we_are_all_to_gather
- https://www.infoq.com/articles/data-oriented-programming-java/
- https://inside.java/2024/05/23/dop-v1-1-introduction/
- https://inside.java/2024/05/27/dop-v1-1-immutable-transparent-data/
- https://inside.java/2024/05/29/dop-v1-1-model-data/
