# functional-rosetta-stone

[![Build Status](https://travis-ci.org/jabrena/functional-rosetta-stone.svg?branch=master)](https://travis-ci.org/jabrena/functional-rosetta-stone)

## Goal

Improve the functional programming skills for Java Developers

## Test project

How to test the project?

```
./mvnw clean test -DexcludedGroups=performance
./mvnw clean test -Dgroups=performance
```

## Documentation

- [Introduction](docs/1.Introduction.md)
- [Functional features in Java](docs/2.Functional-features-on-Java.md)
- [The missing parts](docs/3.The-missing-parts.md)
- [Functional programming concepts](docs/4.Functional-programming-concepts.md)
- [References](docs/99.References.md)


## Performance

Using the [jmh-results.json](https://github.com/jabrena/functional-rosetta-stone/blob/master/docs/jmh-results.json) 
you can review the performance results: 

- https://jmh.morethan.io/
- https://nilskp.github.io/jmh-charts/

