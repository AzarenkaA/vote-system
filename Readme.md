#                                            Voting System
   This API proposes to use system to vote for a restaurant where user votes for place where he wants to
  have lunch. User is able to registration and sign in to the system. System has two roles and new users will
  have '_role_user_'. User is able show all restaurants and each restaurant's menu. Also user can vote to 
  restaurant where he wants to have lunch. User is an able vote to the restaurant before 11:00 AM. After this 
  time system won't be receive any votes. 
       In this system user with 'admin_role' can create and update menu for each restaurant. Meal can be
       updated as for one of restaurant as for few restaurants. Also system writes audit to each vote of each user 
    and history menu for each restaurant.

#API
###UserController
  ### SignUp 
  The system is an able to adds new users. System will registration of new user and gives him a ROLE_USER. User with 
  admin role _ROLE_ADMIN_a already exist in the system.
  
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
  
 
### SignIn
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