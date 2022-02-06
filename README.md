
##STOCKS API SERVICE


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

You can start the service in following ways :-
1. `mvn clean install`
2. `docker-compose up -d`

The service is live on `Port 8000`

You can also use the file postman collection file present in the /postman-collection/Stocks-API.postman_collection.json
to test different endpoints. 