# Design considerations

## Internal behaviour

Internal methods should trust in their configuration,
so they will not return Option Types.

## Extenal interaction

Methods interacting with external third parties,
will be modelled to return Optional/Option.

## References

- https://stackoverflow.com/questions/11434431/exception-without-stack-trace-in-java
 