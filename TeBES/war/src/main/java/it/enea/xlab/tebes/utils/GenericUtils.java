package it.enea.xlab.tebes.utils;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class GenericUtils {

	/**
     * Get the actual type arguments a child class has used to extend a generic
     * base class. (Taken from http://www.artima.com/weblogs/viewpost.jsp?thread=208860. Thanks
     * mathieu.grenonville for finding this solution!)
     * 
     * @param baseClass
     *            the base class
     * @param childClass
     *            the child class
     * @return a list of the raw classes for the actual type arguments.
     */
    public static <T> List<Class<?>> getTypeArguments(Class<T> baseClass,
                    Class<? extends T> childClass) {
            Map<Type, Type> resolvedTypes = new HashMap<Type, Type>();
            Type type = childClass;
            // start walking up the inheritance hierarchy until we hit baseClass
            while (!getClass(type).equals(baseClass)) {
                    if (type instanceof Class) {
                            // there is no useful information for us in raw types, so just
                            // keep going.
                            type = ((Class) type).getGenericSuperclass();
                    } else {
                            ParameterizedType parameterizedType = (ParameterizedType) type;
                            Class<?> rawType = (Class) parameterizedType.getRawType();

                            Type[] actualTypeArguments = parameterizedType
                                            .getActualTypeArguments();
                            TypeVariable<?>[] typeParameters = rawType.getTypeParameters();
                            for (int i = 0; i < actualTypeArguments.length; i++) {
                                    resolvedTypes
                                                    .put(typeParameters[i], actualTypeArguments[i]);
                            }

                            if (!rawType.equals(baseClass)) {
                                    type = rawType.getGenericSuperclass();
                            }
                    }
            }

            // finally, for each actual type argument provided to baseClass,
            // determine (if possible)
            // the raw class for that type argument.
            Type[] actualTypeArguments;
            if (type instanceof Class) {
                    actualTypeArguments = ((Class) type).getTypeParameters();
            } else {
                    actualTypeArguments = ((ParameterizedType) type)
                                    .getActualTypeArguments();
            }
            List<Class<?>> typeArgumentsAsClasses = new ArrayList<Class<?>>();
            // resolve types by chasing down type variables.
            for (Type baseType : actualTypeArguments) {
                    while (resolvedTypes.containsKey(baseType)) {
                            baseType = resolvedTypes.get(baseType);
                    }
                    typeArgumentsAsClasses.add(getClass(baseType));
            }
            return typeArgumentsAsClasses;
    }
    
    
    /**
     * Get the underlying class for a type, or null if the type is a variable
     * type.
     * 
     * @param type
     *            the type
     * @return the underlying class
     */
    private static Class<?> getClass(Type type) {
            if (type instanceof Class) {
                    return (Class) type;
            } else if (type instanceof ParameterizedType) {
                    return getClass(((ParameterizedType) type).getRawType());
            } else if (type instanceof GenericArrayType) {
                    Type componentType = ((GenericArrayType) type)
                                    .getGenericComponentType();
                    Class<?> componentClass = getClass(componentType);
                    if (componentClass != null) {
                            return Array.newInstance(componentClass, 0).getClass();
                    } else {
                            return null;
                    }
            } else {
                    return null;
            }
    }
    
    
    
    public static <K extends Comparable,V extends Comparable> Map<K,V> sortByValues(Map<K,V> map){
        List<Map.Entry<K,V>> entries = new LinkedList<Map.Entry<K,V>>(map.entrySet());
      
        Collections.sort(entries, new Comparator<Map.Entry<K,V>>() {

            public int compare(Entry<K, V> o1, Entry<K, V> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
      
        //LinkedHashMap will keep the keys in the order they are inserted
        //which is currently sorted on natural ordering
        Map<K,V> sortedMap = new LinkedHashMap<K,V>();
      
        for(Map.Entry<K,V> entry: entries){
            sortedMap.put(entry.getKey(), entry.getValue());
        }
      
        return sortedMap;
    }
   

}

