package info.unterrainer.commons.serialization.objectmapper.key;

import lombok.Data;

@Data
public class MappingKey<S, T> {
    public MappingKey(Class<S> s, Class<T> t) {
        sourceType = s;
        targetType = t;
    }

    private Class<S> sourceType;
    private Class<T> targetType;
}