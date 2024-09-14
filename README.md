## 🚀 About

the main purpose of this project is to provide an expense tracking facility for user. 


## 📝 How to Build
This project use JDK 17 and H2 embedded database. [Dockerfile](./Dockerfile) and [docker-compose](./docker-compose.yaml)
file is also provided for quick startup. initial model and data is temporarily added in [data.sql](src/main/resources/data.sql).
but for better data migration next steps can be using tools like liquibase as data migration tool.

### Business
this project consist of different entities in order to provide user security and expense tracking
business. each expense has a main category which is defined in CATEGORY entity. these categories are fixed
and should be predefined by system admin. each user can define different SUBCATEGORY's related to a CATEGORY.
so each user can have separate and different SUBCATEGORY's and add expense to each SUBCATEGORY.
USER entity is defined to keep user data and this entity is related to ROLE entity in order to assign different
roles to each user. currently two main role is defined in this project.

> ADMIN  
> USER 

project ER diagram is as below:

### Security
authentication and authorization in this project is provided via spring security.
all endpoints unless /auth/login endpoint are protected. BCrypt Hashing algorithm is used as password hashing mechanism.
user can call login service with credential and after authentication receive a JWT in order to call other service.
received JWT payload is as below:
```
{
   "nationalCode":"0083747893",
   "roles":[
      "ADMIN",
      "USER"
   ],
   "iss":"localhost:8082",
   "exp":1726451622,
   "iat":1726331622,
   "userId":1
}
```
this JWT is created with HS256 symmetric algorithm and is validated on next calls when received. another claim in this JWT is 
issuer claim (iss) which is loaded from config (jwt.issuer). expiration claim is also used for defining JWT expire time
and is calculated from expiredIn config (jwt.expiresIn).
authorization is done via roles claim in payload. each service is annotated with Secured annotation in order to specify
service permitted roles. for example saveUser service can only be accessed by admin. 
```
    @PostMapping("/save")
    @Secured("ROLE_ADMIN")
    public void saveUser() {
      log.info("to save user");
    }
```
### Caching mechanism
for better performance caching mechanism is added to this project via spring-boot-starter-cache and ehcache implementation.
both programmatic and declarative type is used in order to support two types of data. programmatic cache is defined via 
[AbstractCacheableService](src/main/java/com/sample/expensetracker/cache/AbstractCacheableService.java) for caching category
data. this data can be cached once completely and then modified if any modification is done to category items.
another type of caching mechanism is used via Cacheable annotation. this type is appropriate when items are retrieved from
datasource separately. for our case on login user is retrieved from database via repository methods and cached as below:
```
    @Cacheable(cacheNames = CacheName.USER_INFO, key = "#username", unless = "#result==null")
    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

```
in USER_INFO cache username is used as key for cache and if no user exist with related username result is not cached.
below configs are defined in order to determine each cache ttl:
```
cache.categoryTimeToLive=-1 #means infinit time
cache.userTimeToLive=1800
```
for future improvement its recommended to add tti and max size for each cache. also using distributed caching providers
can be considered as improvement.

### REST API
Rest api's are defined in different controllers:
> [AuthenticationController](src/main/java/com/sample/expensetracker/controller/AuthenticationController.java) for defining authentication related services

> [CategoryController](src/main/java/com/sample/expensetracker/controller/CategoryController.java) for defining category related services

> [ExpenseController](src/main/java/com/sample/expensetracker/controller/ExpenseController.java) for defining expense related services

> [UserManagementController](src/main/java/com/sample/expensetracker/controller/UserManagementController.java) for defining user related services

> [UserSubCategoryController](src/main/java/com/sample/expensetracker/controller/UserSubCategoryController.java) for defining subCategory related services

for global exception handling in project two main exceptions are defined:
> [ExpenseTrackerException](src/main/java/com/sample/expensetracker/exception/ExpenseTrackerException.java) for checked exceptions

> [ExpenseTrackerRuntimeException](src/main/java/com/sample/expensetracker/exception/ExpenseTrackerRuntimeException.java) for unchecked exceptions

below method is defined in both exception for automatic detection of HttpStatus code:
```
public HttpStatus getHttpStatus() {
     return HttpStatus.BAD_REQUEST ;
}
```
the default HttpStatus value in ExpenseTrackerException is BAD_REQUEST and the default value in ExpenseTrackerRuntimeException
is INTERNAL_SERVER_ERROR. each child class can override this method and change HttpStatus if required.
exceptions are handled globally in exceptionControllerAdvice.

### Documentation
Documentation in this project is provided via springdoc. swagger is customized to provide different languages
customization and ability to use md files to improve exception documentation. (document.lang) config can be used
to change swagger language and it's now available in persian and english.
as we know swagger only has ability to show one description for one HttpStatus code. but sometimes we have more than
one errorType defined with one HttpStatus. to achieve this functionality md files are used and can be defined in services as below:

```
@Operation(operationId = "login", description = "${login}",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok"),
                    @ApiResponse(responseCode = "401", description = "md:login.md"),
            })
```

access to swagger is possible via below links:

http://ip:port/swagger-ui.html

http://ip:port/v3/api-docs

http://ip:port/v3/api-docs.yaml

### Logging mechanism
For providing logging mechanism [tosan-httpserver-spring-boot-starter](https://github.com/Tosan/tosan-httpserver-spring-boot-starter) is used. this starter provides facility 
to log http request and response/exception and also service logging with ability to mask critical data.
sample of login log:
```
{
  "service" : "login",
  "request" : {
    "requestDto" : {
      "username" : "*SEMI_ENCRYPTED:mi",
      "password" : "*ENCRYPTED"
    }
  }
} 
{
  "service" : "login",
  "duration" : "0.657s",
  "response" : {
    "LoginResponseDto" : {
      "jwtToken" : "eyJhbGciOiJIUzI1NiJ9.eyJuYXRpb25hbENvZGUiOiIwMDgzNzQ3ODkzIiwicm9sZXMiOlsiQURNSU4iLCJVU0VSIl0sImlzcyI6ImxvY2FsaG9zdDo4MDgyIiwiZXhwIjoxNzI2NDU5MjU0LCJpYXQiOjE3MjYzMzkyNTQsInVzZXJJZCI6MX0.Bg78qhjf8PNz1ZXAdA1_YZy43c0Rh4kn3yDvzqhCRF8",
      "userInfoDto" : {
        "name" : "mina",
        "family" : "kh",
        "nationalCode" : "*SEMI_ENCRYPTED:47893"
      },
    }
```

### Test

