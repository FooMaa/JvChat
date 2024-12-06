package org.foomaa.jvchat.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JvStructTools {
    JvStructTools() {}

    public <TYPE_KEY, TYPE_VALUE> List<Map<TYPE_KEY, TYPE_VALUE>> objectInListMaps(Object object,
                                                                                   Class<TYPE_KEY> clazzKey,
                                                                                   Class<TYPE_VALUE> clazzValue) {
        List<Map<TYPE_KEY, TYPE_VALUE>> resultList = new ArrayList<>();

        if (object instanceof List<?> objectList) {
            for (Object obj : objectList) {
                Map<TYPE_KEY, TYPE_VALUE> newMap = new HashMap<>();
                if (obj instanceof Map<?,?> map) {
                    for (Object key : map.keySet()) {
                        TYPE_KEY keyCast = clazzKey.cast(key);
                        TYPE_VALUE valueCast = clazzValue.cast(map.get(key));
                        newMap.put(keyCast, valueCast);
                    }
                }
                resultList.add(newMap);
            }
        }

        return resultList;
    }

    public <TYPE_LIST> List<TYPE_LIST> checkedCastList(Object object,
                                                       Class<TYPE_LIST> clazzType) {
        List<TYPE_LIST> resultList = new ArrayList<>();

        if (object instanceof List<?> objectList) {
            for (Object objectTmp : objectList) {
                TYPE_LIST data = clazzType.cast(objectTmp);
                resultList.add(data);
            }
        }

        return resultList;
    }

    public <TYPE_KEY, TYPE_VALUE> Map<TYPE_KEY, TYPE_VALUE> objectInMap(Object object,
                                                                        Class<TYPE_KEY> clazzKey,
                                                                        Class<TYPE_VALUE> clazzValue) {
        Map<TYPE_KEY, TYPE_VALUE> resultMap = new HashMap<>();

        if (object instanceof Map<?,?> map) {
            for (Object key : map.keySet()) {
                TYPE_KEY keyCast = clazzKey.cast(key);
                TYPE_VALUE valueCast = clazzValue.cast(map.get(key));
                resultMap.put(keyCast, valueCast);
            }
        }

        return resultMap;
    }
}