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

Given `api.properties` has the following content:

```properties
testkey=CHANGE_ME
```

Then you can access `testkey` via project extras. In Groovy DSL:

```groovy
ext.testkey
// or for task context
project.ext.testkey
```

For Kotlin DSL:

```kotlin
val testkey : String by extra
// or for task context
val testkey = project.extensions.extraProperties.get("testkey")
```

## Examples

To see an example of how to use this plugin, see [functional tests](src/functionalTest/kotlin/io/github/fobo66/PropertiesLoaderPluginFunctionalTest.kt). There are all the needed properties defined in test cases in `build.gradle` "files".
