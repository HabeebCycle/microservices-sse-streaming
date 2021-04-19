#Microservices Using Server Sent Event and Queue Tech (RabbitMQ or Kafka)

It consists of 3 microservices that call each other with a utility project that serves all the domain payloads, server and exceptions.

Start Service-B then service-A for server-sent-events in 3 seconds interval

To use Queue/Messaging architecture, run docker-compose for any of Kafka or RabbitMQ 
(Note: Kafka also needs Zookeeper to be started) and start Service-C.
