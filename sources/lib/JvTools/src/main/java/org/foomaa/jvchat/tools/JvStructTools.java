package org.foomaa.jvchat.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JvStructTools {
    private static JvStructTools instance;

    private JvStructTools() {}

    public static JvStructTools getInstance() {
        if (instance == null) {
            instance = new JvStructTools();
        }
        return instance;
    }

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
}