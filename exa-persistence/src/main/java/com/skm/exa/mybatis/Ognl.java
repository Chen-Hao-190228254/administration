package com.skm.exa.mybatis;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * @author dhc
 * 2019-03-06 11:53
 */
public class Ognl {
    public static boolean isEmpty(Object obj) {
        if (obj == null) return true;

        if (obj instanceof String) {
            return ((String) obj).length() == 0;
        } else if (obj instanceof Collection) {
            return ((Collection<?>) obj).isEmpty();
        } else if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        } else if (obj instanceof Map) {
            return ((Map<?, ?>) obj).isEmpty();
        } else {
            return false;
        }
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    public static boolean isDynamicSearch(Object obj) {
        return obj instanceof DynamicSearchable;
    }
}
