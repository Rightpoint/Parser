# Parser

## Provides a standard way to parse data into model objects.

## Getting Started


### Using Standard Gradle

```groovy

  dependencies {
    apt project(':Libraries:Parser-Compiler')
    compile project(':Libraries:Parser:Parser')
  }

```

## Usage


### Example

```java

public void someMethod(JSONObject json) {
  MyModelObject parsedObject = ParserHolder.parse(MyModelObject.class, json);

  // or precreated
  MyModelObject modelObject = new MyModelObject();
  ParserHolder.parse(modelObject, json);
}

```

## Features

Parser supports a good amount of flexible features that make this library very powerful.

### Annotations

```@Parseable``` will generate a ```$ParseDefinition``` class used in parsing the object

```@FieldParseable``` marks a field as suscribing to the parse event data of parent ```@Parseable```. Must implement the ```FieldParsible``` interface. 

```@ParseInterface``` used in conjunction with ```Parser``` interface to define parsers for a type of data object.

```@Key``` tells the **Parser*** what key to reference for a specific field. The key is defaulted to the name of the field.

### Example

#### Parseable

```java

@Parseable
public class ComplexClass implements FieldParseable {

    @Key
    String name;

    @Key
    long date;

    @Key
    double math;

    @Key
    int number;

    @Key
    boolean truth;

    @Key
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
        hidden = (String) parser.getValue(dataInstance, "hidden");
    }
}

```

#### FieldParsible

```java

@FieldParsible
public class AppFeatureControl implements FieldParsible{

    private String hidden;

    @Override
    public void parse(Object dataInstance, Parser parser) {
      hidden = parser.getValue(dataInstance, "name");      
    }
}

```

#### Json Parser Example

```java

@com.raizlabs.android.parser.core.ParseInterface
public class JsonParser implements Parser<JSONObject, JSONArray> {
    @Override
    public Object getValue(JSONObject object, String key) {
        return object.opt(key);
    }

    @Override
    public Object parse(Class returnType, JSONObject object) {
        return JSON.parse(this, returnType, object);
    }

    @Override
    public void parse(Object objectToParse, JSONObject data) {
        JSON.parse(this, objectToParse, data);
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
