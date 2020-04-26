To launch : 
- AionServer.class is the gateway, it will enable all controllers and components.            
- Configure the application.properties. 

##Shop
Display list shop :

    `GET localhost:8080/listshop`

By categories :

    `GET localhost:8080/listshopcategory` 

Buy item :

    `POST localhost:8080/buy
    {
    	"idPlayer" : "1",
    	"token" : "*token*",
    	"idItem" : "1",
    	"countItem" : "2"
    }` 
    
##Paypal

Purchase shards

    `POST localhost:8080/purchase/shards
    {
    	"token" : "*token*",
    	"userId": 1,
    	"transactionAmount" : 10, 
    	"transactionId" : "*transactionId"
    }`  
    
## Login

Register

    `POST localhost:8080/register
    {
        "username" : "malo",
    	"password" : "rolfie",
    	"mail" : "tigran.slama@laposte.net"
    }
    `

Login 

     `POST localhost:8080/login
     {
         "username" : "tigran",
         "password" : "rolfie"
     }`
 
Token 

    `GET localhost:8080/valid?token=*token*`
 
 
   

