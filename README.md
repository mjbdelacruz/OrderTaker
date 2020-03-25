# OrderTaker
A Spring Boot RESTful Application that implements three endpoints that place/take/list orders.

## Prerequisites
In order to be able to run the app, the host machine must have these packages installed:
1. Maven
2. Docker
3. Docker Compose
4. Google API key

**Note:** Make that the machine can manage docker as a non-root user. If not, please follow this [article](https://docs.docker.com/install/linux/linux-postinstall/) to run Docker in rootless mode.

## Steps to run the application
1. Clone the project
2. Place the Google API key in the property file `src/main/resources/application.properties`
```
google.api.key=
```
3. Go to the project root directory, then run `start.sh`. Once the Docker executes the JAR, the endpoint can be accessed in `http://localhost:8080` of host machine

## API Interface
#### Place Order
  - Method: `POST`
  - URL path: `/orders`
  - Request body:

    ```
    {
        "origin": ["START_LATITUDE", "START_LONGITUDE"],
        "destination": ["END_LATITUDE", "END_LONGITUDE"]
    }
    ```

  - Response:

    Header: `HTTP 200`
    Body:
      ```
      {
          "id": <order_id>,
          "distance": <total_distance>,
          "status": "UNASSIGNED"
      }
      ```
    or

    Header: `HTTP <HTTP_CODE>`
    Body:

      ```
      {
          "error": "ERROR_DESCRIPTION"
      }
      ```
   - Example cUrl command:
     ```
     curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"origin": ["14.6180", "121.1572"], "destination": ["14.7216", "121.0521"]}' http://localhost:8080/orders
     ```
      
#### Take Order
  - Method: `PATCH`
  - URL path: `/orders/:id`
  - Request body:
    ```
    {
        "status": "TAKEN"
    }
    ```
  - Response:
    Header: `HTTP 200`
    Body:
      ```
      {
          "status": "SUCCESS"
      }
      ```
    or

    Header: `HTTP <HTTP_CODE>`
    Body:
      ```
      {
          "error": "ERROR_DESCRIPTION"
      }
      ```
  - Example curl command
    ```
    curl -H "Accept: application/json" -H "Content-type: application/json" -X PATCH -d '{"status": "TAKEN"}' http://localhost:8080/orders/1
    ```

#### List the Orders
  - Method: `GET`
  - Url path: `/orders?page=:page&limit=:limit`
  - Response:
    Header: `HTTP 200`
    Body:
      ```
      [
          {
              "id": <order_id>,
              "distance": <total_distance>,
              "status": <ORDER_STATUS>
          },
          ...
      ]
      ```

    or

    Header: `HTTP <HTTP_CODE>` Body:

    ```
    {
        "error": "ERROR_DESCRIPTION"
    }
    
  - Example curl command
    ```
    curl -X GET http://localhost:8080/orders?page=1&limit=5
    ```
