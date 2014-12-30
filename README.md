[![Android Weekly](http://img.shields.io/badge/Android%20Weekly-%23133-2CB3E5.svg?style=flat)](http://androidweekly.net/issues/issue-133)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Parser-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/1255) [![Raizlabs Repository](http://img.shields.io/badge/Raizlabs%20Repository-1.1.0-blue.svg?style=flat)](https://github.com/Raizlabs/maven-releases)

# Parser

Parser is the fastest Key-Value-to-Model object parser that uses annotation processing to generate the parsing for you. It only uses reflection __one time__ and parsing is as fast as writing the code yourself. 

The library enables you to swap and easily move between different JSON libraries or __any__ key-value object. Create the standard ```JsonParser``` or create your own ```ParseInterface```. 

## Getting Started

Add the maven repo url to your build.gradle:

```groovy

  repositories {
        maven { url "https://raw.github.com/Raizlabs/maven-releases/master/releases" }
  }

```

Add the library to the project-level build.gradle, using the [apt plugin](https://bitbucket.org/hvisser/android-apt) and the 
[AARLinkSources](https://github.com/xujiaao/AARLinkSources) plugin::

```groovy

  dependencies {
    apt 'com.raizlabs.android:Parser-Compiler:1.1.0'
    aarLinkSources 'com.raizlabs.android:Parser-Compiler:1.1.0:sources@jar'
    compile 'com.raizlabs.android:Parser-Core:1.1.0'
    aarLinkSources 'com.raizlabs.android:Parser-Core:1.1.0:sources@jar'
    compile 'com.raizlabs.android:Parser:1.1.0'
    aarLinkSources 'com.raizlabs.android:Parser:1.1.0:sources@jar'
  }

```

## Changelog

### 1.1.0
  1. Changed the ```Parser``` interface by adding ```keys()```, ```count()```, and ```getObject()```. These three methods now enable iteration of data in ```ParserUtils``` and simplify some of the implementation from now on.
  2. Support for non-```Parseable``` objects in ```List``` and arrays. It will be up to the parser to return the correct values. Reference ```JSON.get()``` for an example. 
  3. Deprecated ```FieldParseable``` to the better-named ```ParseListener```. 
  4. Fixed broken tests and sample app
  5. Added a ```BaseParser``` to eliminate need for implementing most of the methods manually.
  6. Removed restriction on ```Parseable``` classes for having one ```@Key``` field if they implement ```ParseListener```

## Usage

### Getting Started

Before creating any parse objects, you need to define a ```Parser```by adding the ```@ParseInterface``` annotation and implementing the ```Parser<ObjectType, ListType>``` interface. 

To make things simpler, all you need to do is to extend the ```JsonParser``` class included in the library and add the ```@ParseInterface``` annotation.

This will register your parser with those datatypes, so that when we call ```ParserHolder.parse()```, the ```ParserHolder``` knows how to handle the two types. You can create Parsers for other key-value data types, but they must **not** reference the same classes.

```java

@com.raizlabs.android.parser.core.ParseInterface
public class JsonParser extends com.raizlabs.android.JsonParser {
}

```

### Define a Parseable Class

In order to register a class to generate its ```$ParseDefinition``` you need:
  1. Add the ```@Parseable``` annotation.
  2. At least 1 ```@Key``` field or implement ```ParseListener```.
  3. Have a default constructor available so when nested, we can reference the default constructor. 
  4. All fields **must** be package private or public in order for the ```$ParseDefinition``` of the class to have access to the fields when parsing.


```java

@Parseable
public class MyParseable {


  @Key
  String myField;
}


```

### How To Parse

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
have the class implement ```FieldParseable```

```@FieldParseable``` marks a field as suscribing to the parse event data of parent ```@Parseable```. Must implement the ```FieldParsible``` interface. 

```@ParseInterface``` used in conjunction with ```Parser``` interface to define parsers for a type of data object.

```@Key``` tells the **Parser*** what key to reference for a specific field. The key is defaulted to the name of the field. A ```defValue``` can be specified as a string to use if the value is missing from a parse. Custom objects can be instantiated too with as default value, however you need to use the fully-qualified class name of any custom class you use. The default for primitive types is there "false", "0", or "null" equivalent. ```required``` if true, will throw a ```ParseException``` when not found. 

### Supported Types

  1. All datatypes supported by the ```JSONObject``` class when using the ```JsonParser```
  2. Lists using ```List<T>```. Default will be ```ArrayList```
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

