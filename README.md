## Just an example of how to implement PubSub and Cache servers in a single process architecture
The project is in the early stages, both servers are working properly, but there are features and improvements
to add/make

### TODO
- [ ] Test, test and test
- [ ] Logging
- [ ] Support to multi processes
- [ ] Add acknowledge support
- [ ] Cache server incoming connection should be parsed with regex
- [ ] PubSub remove disconnected clients from subscribers list
- [ ] IOC/DI ?
- [ ] Enhance the build system


### Run from cli, currently all projects in single jar

**PubSub**

java -cp build/libs/cache-1.0-SNAPSHOT.jar com.opal.pubsub.PubSubServer [localhost, [port]]

**PubSub Listener**

java -cp build/libs/cache-1.0-SNAPSHOT.jar com.opal.pubsub.client.Listener [localhost, [port]]

**PubSub Publisher Test**

java -cp build/libs/cache-1.0-SNAPSHOT.jar com.opal.pubsub.client.Publisher [localhost, [port, [number of threads]]]

**Cache**

java -cp libs/cache-1.0-SNAPSHOT.jar com.opal.cache.server.CacheServer [localhost, [port]]
