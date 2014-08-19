package com.raizlabs.android.parser;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClassFieldMap {

    private static HashMap<Class, HashMap<String, Field>> sClassFieldMap;

    private static HashMap<Class, FieldNameParser> sFielNameParserMap;

    private static final FieldNameParser DEFAULT_PARSER = new FieldNameParser() {
        @Override
        public String getJsonKey(String fieldName) {
            return fieldName;
        }
    };

    public static HashMap<Class, HashMap<String, Field>> getSharedInstance() {
        if (sClassFieldMap == null) {
            sClassFieldMap = new HashMap<Class, HashMap<String, Field>>();
        }
        return sClassFieldMap;
    }

    public static HashMap<Class, FieldNameParser> getSharedFieldNameParserInstance() {
        if (sFielNameParserMap == null) {
            sFielNameParserMap = new HashMap<Class, FieldNameParser>();
        }
        return sFielNameParserMap;
    }

    public static HashMap<String, Field> getFieldMap(Class inDataObject) {
        HashMap<String, Field> fieldList = ClassFieldMap.getSharedInstance().get(inDataObject);
        if (fieldList == null) {
            List<Field> list = ReflectionUtils.getAllFields(new ArrayList<Field>(), inDataObject);
            fieldList = new HashMap<String, Field>();
            for (int i = 0; i < list.size(); i++) {
                Field curField = list.get(i);
                Key keyName = curField.getAnnotation(Key.class);
                String name = curField.getName();
                if (keyName != null && keyName.name() != null && !keyName.name().equals("")) {
                    name = keyName.name();
                }
                fieldList.put(name, curField);
            }
            ClassFieldMap.getSharedInstance().put(inDataObject, fieldList);
        }
        return fieldList;

    }

    public static HashMap<String, Field> getFieldMap(Object object) {
        return getFieldMap(object.getClass());
    }

    public static FieldNameParser getFieldNameParser(Class clazz) {
        FieldNameParser fieldNameParser = getSharedFieldNameParserInstance().get(clazz);
        if (fieldNameParser == null) {
            fieldNameParser = DEFAULT_PARSER;
        }
        return fieldNameParser;
    }

    public static FieldNameParser getFieldNameParser(Object inDataObject) {
        return getFieldNameParser(inDataObject.getClass());
    }
}