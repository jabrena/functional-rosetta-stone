# Euler Problems

The project tries to solve Euler problems:
https://projecteuler.net/ using different approaches:

- Java
- Java Streams
- VAVR
- Reactor

For the implementation, it is necessary review the limits of Data types:

| Type    | Size in Bytes         | Range                                                                                             |
|---------|-----------------------|---------------------------------------------------------------------------------------------------|
| byte    | 1 byte                | -128 to 127                                                                                       |
| short   | 2 bytes               | -32,768 to 32,767                                                                                 |
| int     | 4 bytes               | -2,147,483,648 to 2,147,483, 647                                                                  |
| long    | 8 bytes               | -9,223,372,036,854,775,808 to9,223,372,036,854,775,807                                            |
| float   | 4 bytes               | approximately ±3.40282347E+38F (6-7 significant decimal digits) Java implements IEEE 754 standard |
| double  | 8 bytes               | approximately ±1.79769313486231570E+308 (15 significant decimal digits)                           |
| char    | 2 bytes               | 0 to 65,536 (unsigned)                                                                            |
| boolean | not precisely defined | true or false                                                                                     |
