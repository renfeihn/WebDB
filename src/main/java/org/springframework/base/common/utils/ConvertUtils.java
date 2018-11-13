//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.base.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.base.common.utils.Reflections;

public class ConvertUtils {
    static {
        registerDateConverter();
    }

    public ConvertUtils() {
    }

    public static List convertElementPropertyToList(Collection collection, String propertyName) {
        ArrayList list = new ArrayList();

        try {
            Iterator var4 = collection.iterator();

            while(var4.hasNext()) {
                Object e = var4.next();
                list.add(PropertyUtils.getProperty(e, propertyName));
            }

            return list;
        } catch (Exception var5) {
            throw Reflections.convertReflectionExceptionToUnchecked(var5);
        }
    }

    public static String convertElementPropertyToString(Collection collection, String propertyName, String separator) {
        List list = convertElementPropertyToList(collection, propertyName);
        return StringUtils.join(list, separator);
    }

    public static Object convertStringToObject(String value, Class<?> toType) {
        try {
            return org.apache.commons.beanutils.ConvertUtils.convert(value, toType);
        } catch (Exception var3) {
            throw Reflections.convertReflectionExceptionToUnchecked(var3);
        }
    }

    private static void registerDateConverter() {
        DateConverter dc = new DateConverter();
        dc.setUseLocaleFormat(true);
        dc.setPatterns(new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"});
        org.apache.commons.beanutils.ConvertUtils.register(dc, Date.class);
    }
}
