It's a RESTful service responsible for run a test using selenium webdriver.

For now this application can be executed using a docker container which is used for headless browser tests, or for no headless browser testes you can use your preferred OS with the browsers supported for this service.

This application is running under Spring Boot.


## Used Technologies

**1. Java version 8.**

## Additional Technologies

**Tests:** The tests are defined as use case of the Junit. The tests of rest services have: Spring Web MVC for mock of the web infrastructure; JsonPath e hamcrest are used for access and assertions in the Json content. The tests have been made available in the structure: src/test/java.

**Spring Boot:** Technology used for create a embeded enviroment of the execution, I used this technology for simplify the use of the Spring and for controle the scope of the database. In the file src/main/resources/application.yml have properties of the Spring Boot for the project.

**Maven:** Life cycle management and project build.

## Considerations

The integration of the pages with the data occurs asynchronously, always making access to REST services available.

## Usage In Local Machine

###### Pré-requisitos
```
JDK - Java version 1.7.

Any Java IDE with support Maven.

Maven for build and dependecies.


###### After download the fonts, to install the application and test it execute the maven command:
$ mvn clean install

###### To only test the application execute the maven command:
$ mvn clean test

###### To run the application the maven command:
$ mvn spring-boot:run

###### To test the find products by id, open the browser of your preference and type it:
http://localhost:8080/product/1

###### To test the find products by catalog service, open the browser of your preference and type it:
http://localhost:8080/product/cat/Store

###### To test find list service by id service, open the browser of your preference and type it:
http://localhost:8080/order/1

###### To test the find orders by sku id service, open the browser of your preference and type it:
http://localhost:8080/order/sku/1

###### To test the find all orders service, open the browser of your preference and type it:
http://localhost:8080/order/all

###### To test the create order service, tpye it:
curl -i -H "Content-Type:application/json" -H "Accept:application/json" -X POST http://localhost:8080/order -d "{\"commerceItems\": [{\"sku\": {\"id\": 3},\"quantity\": 12,\"unitValue\": 12}],\"status\": \"SUBMITTED\",\"paymentStatus\": \"CREATED\",\"totalAmount\": 26}

##### To update the order, type it:
curl -i -H "Content-Type:application/json" -H "Accept:application/json" -X PUT -d "{\"id\":4, \"commerceItems\": [{\"sku\": {\"id\": 1},\"quantity\": 12,\"unitValue\": 12}],\"status\": \"APPROVED\",\"paymentStatus\": \"CREATED\",\"totalAmount\": 21}" http://localhost:8080/order



login:
curl -i -H "Content-Type:application/json" -H "Accept:application/json" -X POST http://localhost:8080/testing -d "{\"nameTest\": \"login\",\"urlTest\" :\"https://site.com/login\",\"formTest\":{  \"id\":\"\",\"name\": \"username\",\"cssClass\": \"\",\"value\": \"\"},\"attributesTest\":[  {    \"id\":\"\",\"name\": \"username\",\"cssClass\": \"\",\"value\": \"tomas.maiorino@site.com\"},{\"id\":\"\",\"name\": \"password\",\"cssClass\": \"\",\"value\": \"123456\"  }],\"validationAttributesTest\":[  {    \"id\":\"\",\"name\": \"\",\"cssClass\": \"userItem\",\"value\": \"\",\"validationType\": \"hasElement\",\"checkList\": \"\",\"validationContent\": \"\"}]}"

invalid login:
curl -i -H "Content-Type:application/json" -H "Accept:application/json" -X POST http://localhost:8080/testing -d "{\"nameTest\": \"invalid login\",\"urlTest\" :\"https://site.com/login\",\"formTest\":{  \"id\":\"\",\"name\": \"username\",\"cssClass\": \"\",\"value\": \"\"},\"attributesTest\":[  {    \"id\":\"\",\"name\": \"username\",\"cssClass\": \"\",\"value\": \"tomas.maiorino@site.com\"},{\"id\":\"\",\"name\": \"password\",\"cssClass\": \"\",\"value\": \"1234567\"  }],\"validationAttributesTest\":[  {    \"id\":\"\",\"name\": \"\",\"cssClass\": \"formErrorLabel\",\"value\": \"Oops, unknown username or password.\",\"validationType\": \"text\",\"checkList\": \"\",\"validationContent\": \"\"}]}"

Search find:
curl -i -H "Content-Type:application/json" -H "Accept:application/json" -X POST http://localhost:8080/testing -d "{\"nameTest\": \"search\",\"urlTest\" :\"https://site.com\",\"formTest\":{  \"id\":\"idSearchBarInput\",\"name\": \"\",\"cssClass\":\"\",\"value\": \"\"},\"attributesTest\":[  { \"id\":\"idSearchBarInput\",\"name\": \"\",\"cssClass\": \"\",\"value\":\"oil\",\"actionAttributeTest\":{\"runBeforeSetValue\":true,\"id\":\"\",\"name\": \"\",\"cssClass\": \"headerSearchIconDiv\",\"value\": \"\",\"validationType\":\"\",\"checkList\":\"\",\"validationContent\": \"\",\"action\":\"click\"}}],\"validationAttributesTest\":[{\"id\":\"\",\"name\": \"\",\"cssClass\": \"searchPageTable\",\"value\": \"\",\"validationType\":\"hasElement\",\"checkList\":\"\",\"validationContent\": \"\"}]}"

forgot password:
curl -i -H "Content-Type:application/json" -H "Accept:application/json" -X POST http://localhost:8080/testing -d "{\"nameTest\": \"search\",\"urlTest\" :\"https://site.com/account/forgot-password\",\"formTest\":{  \"id\":\"\",\"name\": \"email\",\"\":\"\",\"value\": \"\"},\"attributesTest\":[  { \"id\":\"\",\"name\": \"email\",\"cssClass\": \"\",\"value\":\"tomas.maiorino@site.com\",\"actionAttributeTest\":{\"runBeforeSetValue\":true,\"id\":\"\",\"name\": \"\",\"cssClass\": \"headerSearchIconDiv\",\"value\": \"\",\"validationType\":\"\",\"checkList\":\"\",\"validationContent\": \"\",\"action\":\"click\"}}],\"validationAttributesTest\":[{\"id\":\"\",\"name\": \"\",\"cssClass\": \"formTopP\",\"value\": \"Instructions will be sent to the email address for this account.\",\"validationType\":\"text\",\"checkList\":\"\",\"validationContent\": \"\"}]}"

attribute format:
\"actionAttributeTest\":{\"id\":\"\",\"name\": \"\",\"cssClass\": \"headerSearchIconDiv\",\"value\": \"\",\"validationType\":\"\",\"checkList\":\"\",\"validationContent\": \"\",\"action\":\"click\",\"runBeforeSetValue\":true}
