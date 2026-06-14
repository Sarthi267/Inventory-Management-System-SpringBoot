## Inventory Management System (Spring Boot Web App)  
Basic inventory manager with built using Spring Boot and Spring Security
***
This project serves as a beginner API built with the purpose to further my understanding of how Spring and Spring Boot work. Specifically the logical flow the code, annotations, and the basics of Spring such as inversion of control and annotations. This API also adds some basic spring security in which the user must create an account. The local host also has a simple frontend built with HTML and thymeleaf. 
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
6. Send HTTP requests through Postman, register an account with POST, and use those account details with basic auth when making HTTP requests. The role must be either "USER" or "ADMIN," users can only use GET, whereas admins can update inventory data. After you register an account, you can log in to the local host and interact with the inventory system directly instead of using postman (localhost:8080/inventory). 
***
### Endpoints  
GET /items -retrieve all items  
POST /items - add a new item, or register and login  
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
### Account registration body format (POST)
{  
"username": "[your username]",  
"password": "[your password]",  
"role": "[ADMIN, or USER]"  
}
***
### Testing the API  
- Use a tool like Postman to send HTTP requests to the endpoints.  
- Download Postman at https://postman.com
- Register an account with POST in Postman, then you can either continue to use Postman or use the localhost (the HTML front-end is much more user-friendly and easier to use)  
- Go to localhost:8080/inventory if you want to use the interface
***
### Notes
- H2 database is created automatically on first run  
- Data persists between runs in the data/ folder
- You can access the H2 database without creating an account
