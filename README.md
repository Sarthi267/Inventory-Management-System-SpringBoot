## Inventory Management System (Spring Boot Web App)  
Basic inventory manager built using Spring Boot
***
This project serves as a beginner API built with the purpose to further my understanding of how Spring and Spring Boot work. Specifically the logical flow the code, annotations, and the basics of Spring such as inversion of control and annotations.  
***
### Prerequisites  
- Java 21
- Maven  
***
### How to Run  
1. Clone the repository:  
  git clone https://github.com/Sarthi267/Inventory-Management-System-SpringBoot.git
2. Move into the project directory:  
cd Inventory-Management-System-SpringBoot
3. Build the project:  
mvn clean install  
4. Run the application:  
mvn spring-boot:run  
5. API is available at:  
http://localhost:8080/items  
***
### Endpoints  
GET /items -retrieve all items  
POST /items - add a new item  
PUT /items/{id} - update an item by id  
DELETE /items/{id} - delete an item by id
***
### Request body format (POST and PUT)  
{  
    "name": "laptop",  
    "quantity" : 5,  
    "price": 999.99  
}
***
### Testing the API  
Use a tool like Postman to send HTTP requests to the endpoints.  
Download Postman at https://postman.com
***
### Notes
- H2 database is created automatically on first run  
- Data persists between runs in the data/ folder
