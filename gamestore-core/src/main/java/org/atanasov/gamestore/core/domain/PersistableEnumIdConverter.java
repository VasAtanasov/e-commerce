package org.atanasov.gamestore.core.domain;

import org.atanasov.gamestore.core.utils.ClassUtils;

import javax.persistence.AttributeConverter;
import java.util.stream.Stream;

public interface PersistableEnumIdConverter<E extends PersistableEnumId> extends AttributeConverter<E, Integer> {

  @SuppressWarnings("unchecked")
  default Class<E> getEnumClass() {
    return (Class<E>) ClassUtils.getTypeArgumentOfSuperClass(this, PersistableEnumIdConverter.class, 0);
  }

  @Override
  default Integer convertToDatabaseColumn(final E enumValue) {
    return enumValue == null ? null : enumValue.getId();
  }

  @Override
  default E convertToEntityAttribute(Integer dbData) {
    return dbData == null
        ? null
        : Stream.of(getEnumClass().getEnumConstants())
            .filter(value -> value.getId().equals(dbData))
            .findFirst()
            .orElse(null);
  }
}
