#                                            Voting System
   This API proposes to use the system to vote for a restaurant where a user votes for the place where he wants to
  have lunch. A user is able to registrate and sign in to the system. The system has two roles and new users will
  have '_ROLE_USER_'.A user is able to be shown all restaurants and each restaurant's menu. Also, a user can vote for 
  the restaurant where he wants to have lunch. A user is able to vote for the restaurant till 11:00 AM. After this 
  time the system won't receive any votes. 
  In this system a user with '_ROLE_ADMIN_' can create and update menu for each restaurant. Meal can be
  updated either for one restaurant or for a few restaurants. Also, the system writes audit for each vote of each user 
  and history of menu for each restaurant.

### Content
  
- [Start application](https://github.com/AzarenkaA/vote-system#start-application)
- [API](https://github.com/AzarenkaA/vote-system#api)
    - [User controller](https://github.com/AzarenkaA/vote-system#usercontroller)
    - [Restaurant controller](https://github.com/AzarenkaA/vote-system#restaurantcontroller)
    - [Meal controller](https://github.com/AzarenkaA/vote-system#mealcontroller)
- [DataBase](https://github.com/AzarenkaA/vote-system#data-base)
- [CURL commands for test application](https://github.com/AzarenkaA/vote-system#curl-commands)

## Start Application
   To run the application JDK8 should be installed on the computer.
   
   Enter next commands in the console:
   - mvnw clean idea:idea - download all dependencies
   - mvnw clean package - build of the project
   - mvnw spring-boot:run - start application
   
   Application will be started on port 8080. 

#                                     API
  
## UserController
#### SignUp.
  The system is able to add new users. The system will registrate a new user and give him a '_ROLE_USER_'. The user with 
  admin role '_ROLE_ADMIN_' is already exist in the system.
  
  Http method **POST**: 
  mapping http://localhost:8080/api/auth/signup.
  
  Json request example:
    
       {
            "username": "123@mail.ru",
            "password": "123123"
       }
  
  - The system checks that username is valid email.
  - The system checks that password is more than 5 symbols.
  - The system checks existing user with such email in the database.
  
  If all checks pass it will receive next message:
    
        {
            "message": "User registered successfully!"
        }
     
  In other case the system will generate an exception with the message:
            
        Unauthorized error. Message - User 123@mail.ru already exist.
  
 
#### SignIn.
Http method **POST**: 
mapping http://localhost:8080/api/auth/signin.  

   The system takes next request for log in:
       
        {
            "username": "admin@mail.ru",
            "password": "admin123"
        } 
        
   Authorization in the system was created with help of JWT(json web token). After a user logins in the system a user 
   will receive next message:
        
        {
            "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkBtYWlsLnJ1IiwiaWF0IjoxNjA3ODQ1MTg2LCJleHAiOjE2MDc5MzE1ODZ
                        9.86Hh9Sdqy_zs72TkNc-79ilvOk5vd6lpCc097yqPjGlX1gxwI1GUcIH4KGql6VHQSz8daI8SsukfIwlNnxHrsQ",
            "type": "Bearer",
            "username": "admin@mail.ru",
            "authorities": [
                {
                    "authority": "ROLE_ADMIN"
                }
            ]
        }
        
   After log into the system to be able to swap messages between systems requests should have header 
   'Authorization' with the parameter: 'Bearer' + space + 'token'.
   
##  RestaurantController

This controller proposes next api:
   - Get all restaurants. The system returns only restaurant without meal.
   - Get all meal of the restaurant. 
   - Save menu for a restaurant.
   - Vote for a restaurant.
   - Get history menu for each restaurant by date.
   
#### Get all restaurants.
The system returns all restaurants.
 
Http method **GET**:
mapping http://localhost:8080/api/restaurants/

   Example of response:
      
      [
          {
              "id": "fd7ffad2-426c-42fe-9908-f053a882a4f7",
              "name": "Brevis"
          },
          {
              "id": "0143508d-8658-4741-85cf-682a5d4bc344",
              "name": "Rebro"
          },
          {
              "id": "1d4e58fd-6cff-46ff-832f-4be66ee7948a",
              "name": "Amsterdam"
          }
      ]

#### To Vote.
   The system proposes to a user to vote for a restaurant by a unique identifier.
   
Http method **POST**:
mapping http://localhost:8080/api/restaurants/{id}
   
   The system checks next values:
   - A user has role '_ROLE_ADMIN_' and '_ROLE_USER_'
   - A restaurant with {id} exists in the system.
   
   Example of the request:
   
    http://localhost:8080/api/restaurants/1d4e58fd-6cff-46ff-832f-4be66ee7948a
    
   Example of the response:
   
    {
        "message": "Voted successfully"
    }
    
#### Menu by the restaurant.
The system returns a current menu of a restaurant.

Http method **GET**:
mapping http://localhost:8080/api/restaurants/{id}/menu

   The system checks next values:
   - A user has role '_ROLE_ADMIN_' and '_ROLE_USER_'
    
   Example of the request:
   
    http://localhost:8080/api/restaurants/1d4e58fd-6cff-46ff-832f-4be66ee7948a/menu
    
   Example of the response:
   
    [
        {
            "id": "59b80d53-7a70-4a97-835d-2154187eeebb",
            "title": "Салат из капусты",
            "price": 2.50,
            "restaurantsIds": [
                "fd7ffad2-426c-42fe-9908-f053a882a4f7"
            ]
        },
        {
            "id": "a6a84e78-c667-4346-9c44-0c44d2782f4d",
            "title": "Салат из море продуктов",
            "price": 10.30,
            "restaurantsIds": [
                "fd7ffad2-426c-42fe-9908-f053a882a4f7"
            ]
        }
    ]

#### Save meal by a restaurant id.
Http method **POST**:
mapping http://localhost:8080/api/restaurants/{id}/menu

   Example of the request:
   
    {
        "id": "",
        "title": "newTitle3",
        "price": "50.00",
        "restaurantsIds": []  
    }
    
   Example of the response:
   
    {
        "message": "Created"
    }

#### History menu of restaurant.
Http method: **GET**

mapping http://localhost:8080/api/restaurants/{id}/history/{date}

   Date should be represented in a format yyyy-mm-dd (2020-12-15)  
   
   Example of the response:
      
       {
           "restaurantTitle": "Tracktir",
           "mealTos": [
               {
                   "id": "1fa8d229-df9c-47cd-aa81-15d3c2127e9a",
                   "title": "newTitle3",
                   "price": 50.00,
                   "restaurantsIds": []
               },
               {
                   "id": "5c89185a-fb38-4c6f-ab84-670e5119135b",
                   "title": "newTitle-05",
                   "price": 45.00,
                   "restaurantsIds": []
               }
           ]
       }
       
#### Get votes of restaurant.
Http method **GET**:

mapping http://localhost:8080/api/restaurants/{id}/votes

 Example of the response:
     
     {
         "restaurantName": "Brevis",
         "countOfVotes": 2
     }
 
 ##   MealController    
 #### Save meal with many restaurants.
 Http method: **POST**:
 
 mapping http://localhost:8080/api/menus/
 
   Example of the request:
    
    {
        "id": "",
        "title": "newTitle3",
        "price": "50.00",
        "restaurantsIds": [
            "6ec18c9e-988d-45e3-b15d-0acc2e66e359",
            "230a9585-9ec4-4d3a-8bb4-b2eda3b8a2cb"
        ]  
    }
    
 #### Update meal with many restaurants.
 Http method: **PUT**:
     
 mapping http://localhost:8080/api/menus/
     
   Example of the request:
     
        {
            "id": "e476a3e6-1482-49cf-b8ca-bd4fc2e7cf6d",
            "title": "newTitle3",
            "price": "50.00",
            "restaurantsIds": [
                "6ec18c9e-988d-45e3-b15d-0acc2e66e359",
                "230a9585-9ec4-4d3a-8bb4-b2eda3b8a2cb"
            ]  
        }
        


# Data Base
  The database in this application and in integration tests represents an in-memory H2 database.
  All tables have additional columns:
  
|Column        |Type             |
|------        |:------:         |
|created_user  |varchar(256)     |
|updated_user  |varchar(256)     |
|created_date  |CURRENT_TIMESTAMP|
|updated_date  |CURRENT_TIMESTAMP|
|record_version|boolean          |
  
### Table users

|Column  |Type            |
|------  |:------:        |
|id      |varchar(256)    |
|name    |varchar(256)    |
|email   |varchar(256)    |
|password|varchar(256)    |
|enabled |boolean         |

### Table role

|Column  |Type            |
|------  |:------:        |
|user_id |varchar(256)    |
|role    |varchar(256)    |

### Table restaurant

|Column  |Type            |
|------  |:------:        |
|id      |varchar(256)    |
|title   |varchar(256)    |
|price   |decimal(10,2)   |

### Table meal_to_restaurant_map
This table doesn't have additional fields.

|Column       |Type            |
|------       |:------:        |
|meal_id      |varchar(256)    |
|restaurant_id|varchar(256)    |

### Table vote

|Column       |Type            |
|------       |:------:        |
|user_id      |varchar(256)    |
|restaurant_id|varchar(256)    |

### Table restaurant_audit

|Column       |Type            |
|------       |:------:        |
|     id      |varchar(256)    |
|menu_date    |varchar(256)    |

### Table restaurant_audit_to_meal_map
This table doesn't have additional fields.

|Column       |Type            |
|------       |:------:        |
|audit_id     |varchar(256)    |
|meal_id      |varchar(256)    |

# CURL commands

Except _signup_ and _signin_ all commands should contain a header _Authorization_ with the parameter: _Bearer' + space + 'token_.

 - sign in like admin
    - [url](https://github.com/AzarenkaA/vote-system#signin)
 - sign in like user
    - [just signup](https://github.com/AzarenkaA/vote-system#signup)
 - get all restaurants
    - GET http://localhost:8080/api/restaurants/
 - vote
    - POST http://localhost:8080/api/restaurants/fd7ffad2-426c-42fe-9908-f053a882a4f7
 - history vote
    - GET http://localhost:8080/api/restaurants/fd7ffad2-426c-42fe-9908-f053a882a4f7/history/yyyy-mm-dd
 
 Other commands you can find [here](https://github.com/AzarenkaA/vote-system#api)  
