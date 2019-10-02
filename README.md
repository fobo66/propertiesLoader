# PropertiesLoader Gradle plugin

This simple plugin allows you to load properties (such as API keys)
from `.properties` files into project extras.

## Usage

First of all, load plugin in your root project's `build.gradle` file:

```groovy
plugins {
    id "io.github.fobo66.propertiesloader" version "1.0"
}
```

For Kotlin DSL:

```kotlin
plugins {
    id("io.github.fobo66.propertiesloader") version "1.0"
}
```

Then you need to specify `.properties` files for the plugin:

```groovy
propertiesLoader {
    propertiesFiles.from(project.file("api.properties"))
}
```