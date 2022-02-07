
## STOCKS API SERVICE


Stock API service provides Restful service to add, update and fetch stocks.
Following are the endpoints supported :- 
````
GET /api/stocks (get a list of stocks) 
POST /api/stocks (create a stock) 
GET /api/stocks/1 (get one stock from the list) 
PATCH /api/stocks/1 (update the price of a single stock) 
DELETE/api/stocks/1 (delete a single stock)
````

### Technical
The service is build on java 11 with spring boot framework.
For local development we are using MySql database docker image.

To start the application you should have `java 11`, `Maven` and `docker` installed on your machine.

You can start the service in following ways :-
1. `mvn clean install`
2. `docker-compose up -d`
3. `java -jar /stock-service/target/stock-service-1.0.0-SNAPSHOT.jar`

The service will be live on `Port 8000`

The other alternate way is to use the shell file `./start.sh` present in the root directory. 
Provide the execution right to the `./start.sh` and the run it. Following commands are for your rescue.
Make sure you are in root directory of the project.

1. `chmod +x start.sh`
2. `./start.sh`

the service will be live on `Port 8000`

### Test
You can also use the file postman collection file present in the /postman-collection/Stocks-API.postman_collection.json
to test different endpoints, using postman tool.

Or if you have newman installed on your machine you can also fire following commands.
1. `cd postman-collection`
2. `newman run Stocks-API.postman_collection.json`
