# crud-jwt
This is simple CRUD application with JSON Web Token security. It has example entities of Product, Producer, Country and Address for primary functionality presentation. The code documentation is in polish so be aware :)

# tech stack
- JAVA 1.8
- Hibernate + H2 Database
- SpringBoot + Spring Security + JSON Web Token
- Lombok

# how to test
To test crud API we can use Postman.

To register user we need to send request to the endpoint <b>/auth/signup</b> with 3 variables: username, email and password. In response we will get message "User registered successfully!"
![REGISTER](https://raw.githubusercontent.com/longdavid2k17/crud-jwt/main/screenshots_jwt/register.PNG)

Then we can log into next endpoint <b>/auth/signin</b> with log in request (with username and password); in response we will get token which we will be validate our access to data model endpoints
![LOGUSER](https://raw.githubusercontent.com/longdavid2k17/crud-jwt/main/screenshots_jwt/log.PNG)

For example if we would like to see list of all products we can use endpoint <b>/products/all</b> without any authorization needed. But if we would like to acces one-product page for example for product with id 4 we need at least user permission. For that we need to use our token - set authorization on bearer type and paste our token
![GETALL](https://raw.githubusercontent.com/longdavid2k17/crud-jwt/main/screenshots_jwt/get_all_prod.PNG)
![GETONE](https://raw.githubusercontent.com/longdavid2k17/crud-jwt/main/screenshots_jwt/get_one_prod.PNG)
