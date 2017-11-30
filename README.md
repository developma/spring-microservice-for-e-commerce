# spring-microservice-for-e-commerce
The example application is an e-commerce application which is based on Micro Service and Spring Cloud.

## Reference
http://microservices.io/patterns/microservices.html

## About the example application
This example application provides REST API for viewing and getting inventory item and saving shipping information.

## REST API specification

### inventory application

- /inventory/items/
 - Gets all items.
- /inventory/items/category/{categoryId}
 - Gets items which belong to specify category.
- /inventory/items/{id}
 - Gets specify item.

If you specified invalid path (e.g. /inventory/iiiite, /inventory/items, /inventory/item/aaaa/) on browser or REST access,
this example application will be returned JSON message which includes an error infomation.

### shipping application.

TODO

### storefrontend application.

TODO
