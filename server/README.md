To launch : 
- AionServer.class is the gateway, it will enable all controllers and components.            
- Configure the application.properties. 

##Shop
Display list shop :

    GET localhost:8080/list-shop

By categories :

    GET localhost:8080/list-shop/category

Buy item :

    POST localhost:8080/buy
    {
    	"token" : "*token*",
    	"idItem" : "1",
    	"countItem" : "2",
    	"recipient" : "toGift"
    } 
    
##Paypal

Purchase shards

    POST localhost:8080/purchase/shards
    {
    	"token" : "*token*",
    	"transactionId" : "*transactionId"
    } 
    
## Login

Register

    POST localhost:8080/register
    {
        "username" : "malo",
    	"password" : "rolfie",
    	"mail" : "example@mail.com"
    }
    

Login 

     POST localhost:8080/login
     {
         "username" : "tigran",
         "password" : "rolfie"
     }
     
 OR

    POST localhost:8080/login
    {
        "token" : "*token*"
    }
         
Token 

    GET localhost:8080/valid?token=*token*
 
User informations
 
    GET localhost:8080/list-players?idUser=*idUser*
 
Check user exist
   
    GET localhost:8080/check-players-exist?name=*name*

Factions

    GET localhost:8080/factions/exist
    
##Mail 
 
 Send mail to recipients 
 
    POST localhost:8080/send-mail
    {
        "token": : "*token*",
    	"to" : ["rolfie@mail.com", "example@mail.com"],
    	"subject" : "Test",
    	"content" : "Bonjour, \n Ceci est un test!"
    }
