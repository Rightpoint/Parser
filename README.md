[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Parser-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/1255) [![Raizlabs Repository](http://img.shields.io/badge/Raizlabs%20Repository-1.0.0-blue.svg?style=flat)](https://github.com/Raizlabs/maven-releases)

# Parser

Parser is the fastest JSON-to-Model object parser that uses annotation processing to generate the parsing for you. It only uses reflection __one time__ and parsing is as fast as writing the code yourself. 

The library enables you to swap and easily move between different JSON libraries or __any__ key-value object. Use the standard ```JsonParser``` or create your own ```ParseInterface```! 

## Getting Started

Add the maven repo url to your build.gradle:

```groovy

  repositories {
        maven { url "https://raw.github.com/Raizlabs/maven-releases/master/releases" }
  }

```

Add the library to the project-level build.gradle, using the [apt plugin](https://bitbucket.org/hvisser/android-apt):

```groovy

  dependencies {
    apt 'com.raizlabs.android:Parser-Compiler:1.0.0'
    compile 'com.raizlabs.android:Parser-Core:1.0.0'
    compile 'com.raizlabs.android:Parser:1.0.0'
  }

```

## Usage

### Getting Started

Before creating any parse objects, you need to define a ```Parser```by adding the ```@ParseInterface``` annotation and implementing the ```Parser<ObjectType, ListType>``` interface. This will register your parser with those datatypes, so that when we call ```ParserHolder.parse()```, the ```ParserHolder``` knows how to handle the two types. You can create Parsers for other key-value data types, but they must **not** reference the same classes.

```java

@com.raizlabs.android.parser.core.ParseInterface
public class JsonParser implements Parser<JSONObject, JSONArray> {
    @Override
    public Object getValue(JSONObject object, String key, Object defValue, boolean required) {
        return JSON.getValue(object, key, defValue, required);
    }

    @Override
    public Object parse(Class returnType, JSONObject object) {
        return ParserUtils.parse(this, returnType, object);
    }

    @Override
    public void parse(Object objectToParse, JSONObject data) {
        ParserUtils.parse(this, objectToParse, data);
    }

    @Override
    public List parseList(Class returnType, JSONArray inData) {
        return JSON.parseList(returnType, ArrayList.class, inData);
    }

    @Override
    public Object[] parseArray(Class returnType, JSONArray inData) {
        return JSON.parseArray(returnType, inData);
    }

    @Override
    public Map parseMap(Class clazzType, JSONObject jsonObject) {
        return JSON.parseMap(clazzType, HashMap.class, jsonObject);
    }
}

```

### Define a Parseable Class

In order to register a class to generate its ```$ParseDefinition``` you need:
  1. Add the ```@Parseable``` annotation.
  2. At least 1 ```@Key``` field.
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

```@Key``` tells the **Parser*** what key to reference for a specific field. The key is defaulted to the name of the field. A ```defValue``` can be specified as a string to use if the value is missing from a parse. Custom objects can be instantiated too with as default value, however you need to use the fully-qualified class name of any custom class you use. The default for primitive types is there "false", "0", or "null" equivalent.

### Supported Types

List:  must be ```List<T>```. The ```Parser``` can pick what kind of list to use for the specified field.
Map: must be ```Map<String, Value>```. The ```Parser``` can pick what kind of map to use.
Array: class equivalent of primitives or ```@Parseable```
Primitives: These will be boxed up to the corresponding classes before returning to its primitive type.
String
Any ```@Parseable``` class in single, list, and map (as a value) form. 


### Complex Sample

#### Parseable

```java

@Parseable
public class ComplexClass implements FieldParseable {

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

    @Override
    public void parse(Object dataInstance, Parser parser) {
        hidden = (String) parser.getValue(dataInstance, "hidden", "", false);
    }
}

```

#### FieldParsible

```java

@FieldParseable
public class AppFeatureControl implements FieldParseable{

    private String hidden;

    @Override
    public void parse(Object dataInstance, Parser parser) {
      hidden = (String) parser.getValue(dataInstance, "name", "", false);      
    }
}

```

