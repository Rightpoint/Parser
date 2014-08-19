# Parser

## Provides a standard way to parse data into model objects.

## Getting Started

Including in your project: 

1. Clone this repo into /ProjectRoot/Libraries/
2. Add this line to your settings.gradle:

```groovy

  include ':Libraries:Parser'

```

3. Add this line to your build.gradle:

```groovy

  dependencies {
    compile project(':Libraries:Parser');
  }

```

## Usage

Define your parser statically for memory and performance reasons. 

### Example using JSONParser

```java

private static Parser<MyModelObject, JSONObject, JSONArray> parser = new JSONParser<>();

public void someMethod(JSONObject json) {
  MyModelObject parsedObject = parser.parse(MyModelObject.class, json);

  // or precreated
  MyModelObject modelObject = new MyModelObject();
  parser.parse(modelObject, json);
}

```

## Features

Parser supports a good amount of flexible features that make this library very powerful. 
