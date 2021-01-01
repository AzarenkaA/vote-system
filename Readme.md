#                                            Voting System
   This API proposes to use system to vote for a restaurant where user votes for place where he wants to
  have lunch. User is able to registration and sign in to the system. System has two roles and new users will
  have '_role_user_'. User is able to show all restaurants and each restaurant's menu. Also user can vote to 
  restaurant where he wants to have lunch. User is an able vote to the restaurant before 11:00 AM. After this 
  time system won't be received any votes. 
       In this system user with 'admin_role' can create and update menu for each restaurant. Meal can be
       updated as for one of restaurant as for few restaurants. Also system writes audit to each vote of each user 
    and history menu for each restaurant.

### Content
  
- [API](https://github.com/AzarenkaA/vote-system#api)
    - [User controller](https://github.com/AzarenkaA/vote-system#usercontroller)
    - [Restaurant controller](https://github.com/AzarenkaA/vote-system#restaurantcontroller)
    - [Meal controller](https://github.com/AzarenkaA/vote-system#mealcontroller)
- [DataBase](https://github.com/AzarenkaA/vote-system#data-base)

#                                      API
  
## UserController
#### SignUp.
  The system is an able to adds new users. System will registration of new user and gives him a ROLE_USER. User with 
  admin role _ROLE_ADMIN_a already exist in the system.
  
  Http method **POST**: 
  mapping http://localhost:8080/api/auth/signup.
  
  Json request example:
    
       {
            "username": "123@mail.ru",
            "password": "123123"
       }
  
  - The system checks that username is valid email.
  - The system checks that password more 5 symbols.
  - The system checks existing user with such email in database.
  
  If all checks passed it will receive next message:
    
        {
            "message": "User registered successfully!"
        }
     
  In other case system will generate exception with a message:
            
        Unauthorized error. Message - User 123@mail.ru already exist.
  
 
#### SignIn.
Http method **POST**: 
mapping http://localhost:8080/api/auth/signin.  

   The system to login takes next request:
       
        {
            "username": "admin@mail.ru",
            "password": "admin123"
        } 
        
   Authorization in the system was created with help JWT(json web token). After user login in the system he will receive 
next message:
        
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
        
   After the login in the system that to be able swap messaging between systems requests should have header 
   'Authorization' with the parameter: 'Bearer' + space + 'token'.
   
##  RestaurantController

This controller proposes next api:
   - Gets all restaurants. System returns only restaurant without meal.
   - Gets all meal of restaurant. 
   - Saves menu to restaurant.
   - Votes for a restaurant.
   - Gets history menu of each restaurant by date.
   
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
   The system proposes to user is able to vote for the restaurant by a unique identifier.
   
Http method **POST**:
mapping http://localhost:8080/api/restaurants/{id}
   
   The system checks next values:
   - That user has role 'ROLE_ADMIN' and 'ROLE_USER'
   - That restaurant with {id} exists in the system.
   
   Example of request:
   
    http://localhost:8080/api/restaurants/1d4e58fd-6cff-46ff-832f-4be66ee7948a
    
   Example of response:
   
    {
        "message": "Voted successfully"
    }
    
#### Menu by the restaurant.
The system returns all current menu by a restaurant.

Http method **GET**:
mapping http://localhost:8080/api/restaurants/{id}/menu

   The system checks next values:
   - That user has role 'ROLE_ADMIN' and 'ROLE_USER'
    
   Example of request:
   
    http://localhost:8080/api/restaurants/1d4e58fd-6cff-46ff-832f-4be66ee7948a/menu
    
   Example of response:
   
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

   Example of request:
   
    {
        "id": "",
        "title": "newTitle3",
        "price": "50.00",
        "restaurantsIds": []  
    }
    
   Example of response:
   
    {
        "message": "Created"
    }

#### History menu of restaurant.
Http method: **GET**
mapping http://localhost:8080/api/restaurants/{id}/history/{date}

   Date should be represented in a format YYY-mm-dd (2020-12-15)  
   
   Example of response:
      
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

 Example of response:
     
     {
         "restaurantName": "Brevis",
         "countOfVotes": 2
     }
 
 ##     MealController    
 #### Save meal with many restaurants.
 Http method: **POST**:
 
 mapping http://localhost:8080/api/menus/
 
    Example of request:
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
     
        Example of request:
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
This table doesn't have addition fields.

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
This table doesn't have addition fields.

|Column       |Type            |
|------       |:------:        |
|audit_id     |varchar(256)    |
|meal_id      |varchar(256)    |