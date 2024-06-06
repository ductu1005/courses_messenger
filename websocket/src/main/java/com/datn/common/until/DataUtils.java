package com.datn.common.until;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class DataUtils {

    private static Logger log = LoggerFactory.getLogger(DataUtils.class);

    public static boolean isNullOrEmpty(final Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }


    public static boolean isNullOrEmpty(Object o) {
        return o == null;
    }

    public static boolean isNull(Object o) {
        return o == null;
    }

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.trim().isEmpty();
    }

    public static <T> List<T> convertListObjectsToClass(List<String> attConvert, List<Object[]> objects, Class<?> aClass) throws Exception {
        List<T> lsResult = new ArrayList<>();
        if (DataUtils.isNullOrEmpty(objects)) {
            return lsResult;
        } else {
            for (Object[] item : objects) {
                lsResult.add(convertObjectsToClass(attConvert, item, aClass));
            }
        }
        return lsResult;
    }

    public static BigDecimal safeToBigDecimal(Object obj) {
        if (obj instanceof BigDecimal) {
            return (BigDecimal) obj;
        }
        return BigDecimal.ZERO;
    }

    public static String safeToString(Object obj) {
        if (obj == null) {
            return "";
        }
        return obj.toString();
    }

    public static String setNullIfEmptyString(Object obj) {
        if (DataUtils.isNullOrEmpty(obj)) {
            return null;
        }
        return obj.toString();
    }

    public static Timestamp safeToTimestamp(Object obj) {
        if (obj == null) {
            return null;
        }
        return (Timestamp) obj;
    }

    public static Integer safeToInteger(Object obj) {
        if (obj == null) {
            return 0;
        }
        return (Integer) obj;
    }

    public static Long safeToLong(Object obj1) {
        long result = 0L;
        if (obj1 != null) {
            if (obj1 instanceof BigDecimal) {
                return ((BigDecimal) obj1).longValue();
            }
            if (obj1 instanceof BigInteger) {
                return ((BigInteger) obj1).longValue();
            }
            try {
                result = Long.parseLong(obj1.toString());
            } catch (Exception ignored) {
            }
        }

        return result;
    }

    public static Float safeToFloat(Object obj1) {
        float result = 0.0f;
        if (obj1 != null) {
            if (obj1 instanceof BigDecimal) {
                return ((BigDecimal) obj1).floatValue();
            }
            if (obj1 instanceof BigInteger) {
                return ((BigInteger) obj1).floatValue();
            }
            try {
                result = Float.parseFloat(obj1.toString());
            } catch (Exception ignored) {
            }
        }

        return result;
    }


    public static BigInteger safeToBigInteger(Object obj) {
        if (obj instanceof BigInteger) {
            return (BigInteger) obj;
        }
        return BigInteger.ZERO;
    }

    public static String timestampToString(Timestamp fromDate, String pattern) {
        if (fromDate == null)
            return "";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(fromDate);
    }

    public static String formatNumberWithComma(Double number, String pattern) {
        if (number == null)
            return "";
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(number);
    }

    public static String dateToString(Date fromDate, String pattern) {
        if (fromDate == null)
            return "";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(fromDate);
    }

    public static Double safeToDouble(Object obj1, Double defaultValue) {
        Double result = defaultValue;
        if (obj1 != null) {
            try {
                result = Double.parseDouble(obj1.toString());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return result;
    }

    public static Double safeToDouble(Object obj1) {
        return safeToDouble(obj1, 0.0);
    }

    public static Date safeToDate(Object obj) {
        if (obj instanceof Date) {
            return (Date) obj;
        }
        return null;
    }

    public static LocalDate safeToLocalDate(Object obj) {
        if (obj instanceof LocalDate) {
            return (LocalDate) obj;
        }
        return null;
    }

    public static String date2StringByPattern(Date date, String pattern) {
        if (date == null || DataUtils.isNullOrEmpty(pattern)) {
            return null;
        }

        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    public static <T> T convertObjectsToClass(List<String> attConvert, Object[] objects, Class<?> aClass) throws Exception {
        Object object = aClass.newInstance();
        Field[] fields = aClass.getDeclaredFields();

        for (int i = 0; i < attConvert.size(); i++) {

            Field f;
            int finalIndex = i;
            f = Arrays.stream(fields).filter(item -> attConvert.get(finalIndex).equals(item.getName())).findFirst().orElse(null);
            if (f != null) {
                f.setAccessible(true);
                Class<?> t = f.getType();
                if (objects[i] == null)
                    continue;
                switch (t.getName()) {
                    case "java.lang.String":
                        if (objects[i] instanceof String || objects[i] instanceof Long || objects[i] instanceof BigInteger ||
                                objects[i] instanceof Integer || objects[i] instanceof BigDecimal) {
                            f.set(object, DataUtils.safeToString(objects[i]));
                        } else if (objects[i] instanceof java.sql.Date || objects[i] instanceof Date
                                || objects[i] instanceof Timestamp
                        ) {
                            f.set(object, date2StringByPattern(DataUtils.safeToDate(objects[i]), "dd/MM/yyyy HH:mm:ss"));
                        }
                        break;
                    case "java.lang.Long":
                    case "long":
                        f.set(object, DataUtils.safeToLong(objects[i]));
                        break;
                    case "java.lang.Float":
                    case "float":
                        f.set(object, DataUtils.safeToFloat(objects[i]));
                        break;
                    case "java.lang.Double":
                    case "double":
                        f.set(object, DataUtils.safeToDouble(objects[i]));
                        break;
                    case "java.lang.Boolean":
                    case "boolean":
                        f.set(object, objects[i]);
                        break;
                    case "java.util.Date":
                        f.set(object, DataUtils.safeToDate(objects[i]));
                        break;
                    case "java.time.LocalDate":
                        f.set(object, DataUtils.safeToLocalDate(objects[i]));
                        break;
                    case "java.sql.Timestamp":
                        f.set(object, DataUtils.safeToTimestamp(objects[i]));
                        break;
                    case "java.lang.Integer":
                    case "int":
                        f.set(object, DataUtils.safeToInteger(objects[i]));
                        break;
                    case "java.math.BigInteger":
                        f.set(object, DataUtils.safeToBigInteger(objects[i]));
                        break;
                }
            }
        }
        return (T) object;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
