[![Android Weekly](http://img.shields.io/badge/Android%20Weekly-%23133-2CB3E5.svg?style=flat)](http://androidweekly.net/issues/issue-133)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Parser-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/1255) [![Raizlabs Repository](http://img.shields.io/badge/Raizlabs%20Repository-1.3.0-blue.svg?style=flat)](https://github.com/Raizlabs/maven-releases)

# Parser

Parser is a blazing fast Key-Value-to-Model object parser that uses annotation processing to generate parsing definitions for you. It enables powerful flexibility by abstracting out the parse from ```JSONObject``` so you can create your own ```Parser``` from another JSON library without changing the core of your application's code. 

The library enables you to swap and easily move between different JSON libraries or __any__ key-value object. Extend the standard ```JsonParser``` or create your own ```@ParseInterface```/```Parser``` by extending ```BaseParser```. 

## Getting Started

```groovy
  buildscript {
    repositories {
        maven { url "https://raw.github.com/Raizlabs/maven-releases/master/releases" }
    }
    classpath 'com.raizlabs:Griddle:1.0.2'
    classpath 'com.neenbedankt.gradle.plugins:android-apt:1.4'
  }
  
  allprojects {
    repositories {
        maven { url "https://raw.github.com/Raizlabs/maven-releases/master/releases" }
    }
  }


```

Add the library to the project-level build.gradle, using the [apt plugin](https://bitbucket.org/hvisser/android-apt) and the 
[Griddle](https://github.com/Raizlabs/Griddle) plugin:

```groovy

  apply plugin: 'com.neenbedankt.android-apt'
  apply plugin: 'com.raizlabs.griddle'

  dependencies {
    apt 'com.raizlabs.android:Parser-Compiler:1.3.0'
    mod "com.raizlabs.android:Parser-Core:1.3.0"
  }

```

For using the built-in Android ```JSONObject``` library add ```Parser-JsonParser``` to the ```{}``` enclosure from the main section.

For using ```FastJSON``` [here](https://github.com/alibaba/fastjson) add ```Parser-FastJsonParser``` to the ```{}``` enclosure from the main section.

## Changelog

### 1.3.0

1. Adds ability to define custom ```ParseHandler``` either by including it in the main parse, or by overriding the parse definition creation.
2. added ```@Mergeable``` so we can merge two different sources of JSON. can configure on class or field level.

### 1.2.0

  1. Makes ```Parser``` a java library with ```Parser-Compiler```. No need to use for just Android anymore. Also, ```Parser-Core``` is now part of ```Parser``` since ```Parser``` became a java-library.
  2. Moves out ```JsonParser``` into its own Android library
  3. Creates the ```FastJsonParser``` to use with ```FastJSON```. 
  4. Doc and comment improvements

### 1.1.0
  1. Changed the ```Parser``` interface by adding ```keys()```, ```count()```, and ```getObject()```. These three methods now enable iteration of data in ```ParserUtils``` and simplify some of the implementation from now on.
  2. Support for non-```Parseable``` objects in ```List``` and arrays. It will be up to the parser to return the correct values. Reference ```JSON.get()``` for an example. 
  3. Deprecated ```FieldParseable``` to the better-named ```ParseListener```. 
  4. Fixed broken tests and sample app
  5. Added a ```BaseParser``` to eliminate need for implementing most of the methods manually.
  6. Removed restriction on ```Parseable``` classes for having one ```@Key``` field if they implement ```ParseListener```
  7. Now can subclass other ```Parser```, which means easy inclusion into your project by extending ```JsonParser``` instead of needing to implement the ```Parser``` interface!

## Usage

### Getting Started

Before creating any parse objects, you need to define a ```Parser```by adding the ```@ParseInterface``` annotation and implementing the ```Parser<ObjectType, ListType>``` interface. 

To make things simpler, all you need to do is to extend the abstract ```BaseParser``` class included in the library and add the ```@ParseInterface``` annotation. If using ```Parser-JsonParser```, extend the included ```JsonParser``` or if using ```Parser-FastJsonParser``` extend the included ```FastJsonParser```.

This will register your parser with those datatypes, so that when we call ```ParserHolder.parse()```, the ```ParserHolder``` knows how to handle the two types. You can create Parsers for other key-value data types, but they must **not** reference the same classes.

For example:

```java

@com.raizlabs.android.parser.core.ParseInterface
public class JsonParser extends com.raizlabs.android.JsonParser {
}

```

### Define a Parseable Class

Parseable classes are defined as objects that contain fields that we can parse into. You need to register each class so that 
the compiler can generate its ```$ParseDefinition```. In order to do so, you need:
  1. Add the ```@Parseable``` annotation.
  2. At least 1 ```@Key``` field or implement ```ParseListener```.
  3. Have a default constructor available so when nested, we can reference the default constructor when parsing data. 
  4. All fields **must** be package private or public in order for the ```$ParseDefinition``` of the class to have access to the fields when parsing.
  

#### Example

Simple example will look for a key named "myField" using a ```Parser``` and fill it with the ```String``` value from data passed through the ```ParserHolder```.

```java

@Parseable
public class MyParseable {


  @Key
  String myField;
}


```

### How To Parse

We wanted to make parsing as simple as possible so ```ParserHolder.parse()``` can take in any object that has a parser and turn it into the ```Parseable``` class we want. 

```java

public void someMethod(JSONObject json) {
  MyParseable parsedObject = ParserHolder.parse(MyParseable.class, json);

  // or precreated
  MyParseable modelObject = new MyParseable();
  ParserHolder.parse(modelObject, json);
}

```

## Features

Parser supports a good amount of flexible features that make this library very powerful.

### Annotations

```@Parseable``` will generate a ```$ParseDefinition``` class used in parsing the object. To list to it's own Parse events,
have the class implement ```ParseListener```. Can define its own ```ParseHandler``` to either override the generated parse, or to include it in the main parse.

```@FieldParseable``` marks a field as suscribing to the parse event data of parent ```@Parseable```. Must implement the ```FieldParsible``` interface. 

```@ParseInterface```: use in conjunction with ```Parser``` interface to define parsers for a type of data object. You can also extend ```BaseParser``` (for custom JSON or key-value libraries), ```JsonParser``` for android JSON, or ```FastJsonParser``` for FastJSON.

```@Key``` tells the **Parser*** what key to reference for a specific field. The key is defaulted to the name of the field. A ```defValue``` can be specified as a string to use if the value is missing from a parse. Custom objects can be instantiated too with as default value, however you need to use the fully-qualified class name of any custom class you use. The default for all types are: "false" for boolean, "0" for numbers, or a "null" equivalent for any non-primitive. If ```required()``` is true, will throw a ```ParseException``` when a key is not found. 

```@Mergeable```: Enables parsing multiple sources of data into a single ```@Parseable``` or field. Consequent parses to the same object will not override preset data (if that value is missing from the JSON).

### Supported Types

  1. All datatypes supported by the ```JSONObject``` class when using the ```JsonParser```
  2. Lists using ```List<T>```. Default list class will be ```ArrayList```
  3. Maps defined as ```Map<String, Value>```. The default is ```HashMap```.
  4. Array of any supported type
  5. Primitives will be boxed up to the corresponding classes before returning to its primitive type.
  6. Custom ```ParseListener``` classes declared as ```@FieldParseable```
  7. Strings
  8. Any ```@Parseable``` class in single, list, and map (as a value) form. 


### Complex Sample

#### Parseable

Here is an example showing the various supported field types and how they are declared.

```java

@Parseable
public class ComplexClass implements ParseListener {

    @Key
    String name;

    @Key
    long date;

    @Key(defValue = "0.5d") // will use this as the default value when the $ParseDefinition is created
    double math;

    @Key
    int number;

    @Key
    boolean truth;

    @Key(required = true) // will throw a ParseException if not found
    SimpleClass otherClass;

    @Key
    SimpleClass[] simpleClasses;

    @Key
    List<SimpleClass> simpleClassList;

    @Key
    Map<String, SimpleClass> simpleClassMap;

    String hidden;

    @Key
    SimpleFieldParser simpleFieldParser;
    
    @Key
    List<String> lists;

    @Override
    public void parse(Object dataInstance, Parser parser) {
        hidden = (String) parser.getValue(dataInstance, "hidden", "", false);
    }
}

```

#### ParseListener

Making a class that implements ```ParseListener``` enables it to suscribe to parse events. You can retrieve values from the parser this way and do something custom. This is called at the **end** of parsing--i.e. after all other fields are parsed.

```java

@Parseable
public class AppFeatureControl implements ParseListener {

    private String hidden;

    @Override
    public void parse(Object dataInstance, Parser parser) {
      hidden = (String) parser.getValue(dataInstance, "name", "", false);      
    }
}

```

