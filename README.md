## Just an example of how to implement PubSub and Cache servers in a single process architecture
The project is in the early stages, both servers are working properly, but there are features and improvements
to add/make

Cache server uses a long connection until client disconnects, this was done in purpose, again, for study purposes, next 
version should include using of nio packages to support asynchronous connections

### TODO
- [ ] Test, test and test
- [ ] Logging
- [ ] Support to multi processes
- [ ] Add acknowledge support
- [ ] PubSub remove disconnected clients from subscribers list
- [ ] IOC/DI ?
- [ ] Enhance the build system


### Run from cli, currently all projects in single jar

**PubSub**

java -cp build/libs/cache-0.1.0.jar com.opal.pubsub.PubSubServer [localhost, [port]]

**PubSub Listener**

java -cp build/libs/cache-0.1.0.jar com.opal.pubsub.client.Listener [localhost, [port]]

**PubSub Publisher Test**

java -cp build/libs/cache-0.1.0.jar com.opal.pubsub.client.Publisher [localhost, [port, [number of threads]]]

**Cache**

java -cp libs/cache-0.1.0.jar com.opal.cache.server.CacheServer [localhost, [port]]
