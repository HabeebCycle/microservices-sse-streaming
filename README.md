## Microservices Using Server Sent Event and Queue Tech (RabbitMQ or Kafka)

It consists of 3 microservices that call each other with a utility project that serves all the domain payloads, server and exceptions.

Start Service-B then service-A for server-sent-events in 3 seconds interval

Standalone App/JS -> http://127.0.0.1:7000

Stream API response -> http://127.0.0.1:7000/message/stream/interval OR (http://127.0.0.1:7001/message/stream/interval)

Stream API router -> http://127.0.0.1:7000/message/stream/router OR (http://127.0.0.1:7001/message/stream/router)

To use Queue/Messaging architecture, run docker-compose for any of Kafka or RabbitMQ 
(Note: Kafka also needs Zookeeper to be started) and start Service-C.

Request for response at http://localhost:7000/message/stream/api/{messageId} routes call to http://localhost:7001/message/stream/api/{messageId}
which in turn initiate call to http://localhost:7002/produce/{messageId} at Service C.

Service C writes response for 5 seconds to Kafka or RabbitMQ topics and then consumed by Service B which then return response back to service A at port 7000.
