```
sequenceDiagram
    Client->>RestController: GET /book/{isbn}
    Note over Client, RestController: JSR 303 Bean Validation   
    RestController ->>Service: processRequest(isbnValid)
    Service-->>RestController : Optional<book>
    RestController -->>Client: Optional<book>
   ControllerAdvice -->>Client: "Something goes wrong"  
```