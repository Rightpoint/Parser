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

### Annotations + Purpose

```@Ignore``` tells the **Parser** that we do not want to evaluate this field.

```@Key``` tells the **Parser*** what key to reference for a specific field.

This allow the field names to be separate from the data and notify the **Parser** of which fields to leave out. 

### FieldNameParsers

These allow the developer to specify a global field name "converter" to take in specific key/field name and return what the data conforms to.

### FieldParsible

This is an interface that enables a parsed object to "listen" for when parsing occurs throughout each key/field, and optionally handle the field on its own terms.

#### Example

```java

public class AppFeatureControl implements FieldParsible{

    @Getter
    private HashMap<String, AppLocalObject> localMap;

    @Override
    public boolean parseField(String inFieldName, Setter inSetter, Object inData) {
        if(!inFieldName.equals("fileDownloadControl") && !inFieldName.equals("appUpdateControl")) {
            if(localMap == null) {
                localMap = new HashMap<String, AppLocalObject>();
            }

            localMap.put(inFieldName, ObjectParser.parseJsonObject(AppLocalObject.class, inData));
            return true;
        }
        return false;
    }
}

```

### Object Mergers

Object mergers are classes that provide a nice way to merge a list of keys into some other form of data - separate from the JSON, XML, or other data types that we're parsing. The **Data** field is what the **ObjectMerger** contains and should store from the data coming back in ```merge(key,data)```. 

#### Example

```java

public class ObjectMergerDemo extends ObjectMerger<AppConfig> {

    private static final String KEY_APP_FEATURE_CONTROL = "appFeatureControl";
    private static final String KEY_FILE_DOWNLOAD_CONTROL = "fileDownloadControl";

    public ObjectMergerDemo() {
        setData(new AppConfig());
    }

    @Override
    protected void buildKeys(ArrayList<String> keys) {
        keys.add(KEY_APP_FEATURE_CONTROL);
        keys.add(KEY_FILE_DOWNLOAD_CONTROL);
    }

    @Override
    public void merge(String key, Object data) {
        // We will probably do some parsing here to convert the data into the objects as well 
        if (key.equals(KEY_APP_FEATURE_CONTROL)) {
            getData().setAppFeatureControl(data);
        } else if (key.equals(KEY_FILE_DOWNLOAD_CONTROL)) {
            getData().setFileDownloadControl(data);
        }
    }
}

```