# Readme
Spring Cloud Azure Functions Skeleton

## Quickstart
deploy: 
```bash
./mvnw azure-functions:run
```

send request:
- POST raw string request body, application/text response

    ``` bash
    http http://localhost:7071/api/uppercase --raw hello
    
    HTTP/1.1 200 OK
    Content-Type: text/plain; charset=utf-8
    Date: Sat, 26 Aug 2023 10:24:36 GMT
    Server: Kestrel
    Transfer-Encoding: chunked
    
    HELLO
    ```
- POST raw string request body, application/json response
    ```bash
    http http://localhost:7071/api/name --raw bob  
             
    HTTP/1.1 200 OK
    Content-Type: application/json; charset=utf-8
    Date: Sat, 26 Aug 2023 10:18:12 GMT
    Server: Kestrel
    Transfer-Encoding: chunked
    
    {
        "name": "BOB",
        "salutation": "MR"
    }
    ```
  
- POST Json Request body, application/json response
    ```bash
    http http://localhost:7071/api/user name=bob salutation=mrs 
    
    HTTP/1.1 200 OK
    Content-Type: application/json; charset=utf-8
    Date: Sat, 26 Aug 2023 10:17:09 GMT
    Server: Kestrel
    Transfer-Encoding: chunked
    
    {
        "name": "BOB",
        "salutation": "MRS"
    }
    ```
## Sources
1. [Spring Initializr](https://start.spring.io) + Cloud Function Dependency
2. [Spring Docs - Azure adapter](https://docs.spring.io/spring-cloud-function/docs/current/reference/html/spring-cloud-function.html#_microsoft_azure_functions)
3. [Spring Cloud Function Examples](https://github.com/spring-cloud/spring-cloud-function/tree/main/spring-cloud-function-samples)