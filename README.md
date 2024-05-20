# functional-rosetta-stone

## Goal

Improve the functional programming skills for Java Developers

## Test project

How to test the project?

```
git clone https://github.com/jabrena/functional-rosetta-stone.git
cd functional-rosetta-stone

sdk env install
./mvnw clean test -DexcludedGroups=performance,endtoend
./mvnw clean test -Dgroups=performance
./mvnw clean test -Dgroups=endtoend


./mvnw dependency-check:check
./mvnw versions:display-dependency-updates
./mvnw versions:display-plugin-updates
./mvnw dependency:tree -pl problems 
```

How to run the presentation in local?

```
cd docs
python -m SimpleHTTPServer
```

## Performance

Using the [jmh-results.json](https://github.com/jabrena/functional-rosetta-stone/blob/master/docs/jmh-results.json) 
you can review the performance results: 

- [https://jmh.morethan.io/](http://jmh.morethan.io/?source=https://raw.githubusercontent.com/jabrena/functional-rosetta-stone/master/docs/jmh-results.json)
- https://nilskp.github.io/jmh-charts/
- https://github.com/akarnokd/jmh-compare-gui

