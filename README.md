This stapler module creates a Guice `Injector` automatically from all the jar files in the classpath
by using [Extensibility API](https://github.com/cloudbees/extensibility-api).

This is the barebone service needed to create an extensible application that follows
the extension point / multiple implementations" paradigm.

The application should define a `WebAppMain` class that extends from `ExtensibleWebAppMain`,
which designates the Guice singleton component that gets bound to the top of the URL space.

In addition, all the `@Extension`s that implement `Startable` interface gets started.
