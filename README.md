# functional-rosetta-stone

[![Build Status](https://travis-ci.org/jabrena/functional-rosetta-stone.svg?branch=master)](https://travis-ci.org/jabrena/functional-rosetta-stone)

## Concepts

 - Equational reasoning
 - Referential Transparency
 - Side effects
 - Pure functions
 - Higher-order functions
 - Lazy evaluation
 - Closures
 - Currying
 - Lamdas 
 - Functional interfaces
 - Monads

## Monads

### Java Streams

![](https://pbs.twimg.com/media/D6U9cu-WAAMOOGM?format=jpg)

Java 8 docs: https://docs.oracle.com/javase/8/docs/api/?java/util/stream/Stream.html
Java 9 docs: https://docs.oracle.com/javase/9/docs/api/?java/util/stream/Stream.html

#### Intermediate Operations

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

#### Terminal Operations

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


### Option

### Try

### CompletableFuture

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
