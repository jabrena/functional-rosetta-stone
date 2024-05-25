# Functional Rosetta Stone

[![Java CI](https://github.com/jabrena/functional-rosetta-stone/actions/workflows/maven.yml/badge.svg)](https://github.com/jabrena/functional-rosetta-stone/actions/workflows/maven.yml)

![](./docs/rosetta_stone.png)

## Goal

A repository to review the main concepts about Functional Programming with Java.

## How to build the project in local?

```bash
sdk env install
./mvnw clean test -DexcludedGroups=performance,endtoend
./mvnw clean test -Dgroups=performance
./mvnw clean test -Dgroups=endtoend

./mvnw versions:display-dependency-updates
./mvnw versions:display-plugin-updates
./mvnw dependency:tree -pl problems 
```

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

