
  
    
## Run app through docker

Build Image

`docker build --tag=rahulgangwar90/spring-boot-server:latest .`

Run Image

`docker run -p 8080:8080`
      
      
## Swagger UI      
 URL:  http://localhost:8099/swagger-ui.html#/      
      
## DB Credentials      

`root : root@1234   `   
      
## Kafka Setup   

    docker compose -f . up     
      
#### Useful Commands   
Create topic       
  
     ./kafka-topics.sh --bootstrap-server localhost:9092 --create --replication-factor 1 --partitions 1 --topic mytopic   

  View topics list        
      
     ./kafka-topics.sh --bootstrap-server=localhost:9092 --list   

  Describe a topic    

     ./kafka-topics.sh --bootstrap-server=localhost:9092 --describe --topic mytopic   

  The configs are in package : `com.example.kafka` 
  Api to send message : http://localhost:8080/swagger-ui.html#/kafka/sendMessageUsingGET
  
## Jenkins Setup

https://www.macminivault.com/installing-jenkins-on-macos/

#### Start Jenkins

`brew services start jenkins-lts`

#### Credentials

`rahulgangwar90 : rahul90`


