# functional-rosetta-stone

[![Build Status](https://travis-ci.org/jabrena/functional-rosetta-stone.svg?branch=master)](https://travis-ci.org/jabrena/functional-rosetta-stone)

## Goal

Improve the functional programming skills for Java Developers

## Test project

How to test the project?

```
git clone https://github.com/jabrena/functional-rosetta-stone.git
cd functional-rosetta-stone
./mvnw clean test -DexcludedGroups=performance,endtoend
./mvnw clean test -Dgroups=performance
./mvnw clean test -Dgroups=endtoend
```

How to run the presentation in local?

```
cd docs
python -m SimpleHTTPServer
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

- [https://jmh.morethan.io/](http://jmh.morethan.io/?source=https://raw.githubusercontent.com/jabrena/functional-rosetta-stone/master/docs/jmh-results.json)
- https://nilskp.github.io/jmh-charts/
- https://github.com/akarnokd/jmh-compare-gui

