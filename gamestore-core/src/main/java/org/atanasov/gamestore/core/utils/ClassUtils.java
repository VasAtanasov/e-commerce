package org.atanasov.gamestore.core.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public final class ClassUtils {

  public static Class<?> getTypeArgumentOfSuperClass(
      final Object object, final Class<?> superClass, final int typeArgumentIndex) {

    Objects.requireNonNull(object, "object is null");
    Objects.requireNonNull(superClass, "superClass is null");
    if (typeArgumentIndex < 0) {
      throw new IllegalArgumentException("typeArgumentIndex must not be negative");
    }

    final Optional<ParameterizedType> paramType =
        findParameterizedParentTypeOfClass(object.getClass(), superClass);

    if (paramType.isEmpty()) {
      throw new IllegalArgumentException(
          String.format(
              "Did not find parameterized interface of class %s for object of class %s",
              superClass.getName(), object.getClass().getName()));
    }

    final Type[] typeArguments = paramType.get().getActualTypeArguments();

    if (typeArguments.length == 0) {
      throw new IllegalArgumentException(
          "The interface does not have type arguments: " + superClass.getName());
    }

    return (Class<?>) typeArguments[typeArgumentIndex];
  }

  public static Optional<ParameterizedType> findParameterizedParentTypeOfClass(
      final Class<?> clazz, final Class<?> expectedType) {

    Objects.requireNonNull(clazz, "clazz is null");
    Objects.requireNonNull(expectedType, "expectedType is null");

    final Type genericSuperclass = clazz.getGenericSuperclass();
    final Type[] genericInterfaces = clazz.getGenericInterfaces();

    List<Type> types = new ArrayList<>();
    types.add(genericSuperclass);
    types.addAll(Arrays.asList(genericInterfaces));

    for (final Type type : types) {
      if (type != null) {
        if (type instanceof ParameterizedType) {
          final ParameterizedType parameterizedType = (ParameterizedType) type;
          final Type rawType = parameterizedType.getRawType();

          if (rawType instanceof Class) {
            final Class<?> rawClass = (Class<?>) rawType;

            if (expectedType.equals(rawClass)) {
              return Optional.of(parameterizedType);
            }

            final Optional<ParameterizedType> match =
                findParameterizedParentTypeOfClass(rawClass, expectedType);
            if (match.isPresent()) {
              return match;
            }
          }
        } else if (type instanceof Class) {
          final Optional<ParameterizedType> match =
              findParameterizedParentTypeOfClass((Class<?>) type, expectedType);
          if (match.isPresent()) {
            return match;
          }
        }
      }
    }

    return Optional.empty();
  }

  private ClassUtils() {
    throw new AssertionError();
  }
}
