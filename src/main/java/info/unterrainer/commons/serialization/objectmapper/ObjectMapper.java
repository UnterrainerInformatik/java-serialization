package info.unterrainer.commons.serialization.objectmapper;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import com.googlecode.jmapper.JMapper;
import com.googlecode.jmapper.api.JMapperAPI;
import com.googlecode.jmapper.exceptions.JMapperException;

import info.unterrainer.commons.serialization.objectmapper.exceptions.ObjectMapperMappingException;
import info.unterrainer.commons.serialization.objectmapper.exceptions.ObjectMapperProcessingException;
import info.unterrainer.commons.serialization.objectmapper.key.MappingKey;

import static com.googlecode.jmapper.api.JMapperAPI.mappedClass;
import static com.googlecode.jmapper.api.JMapperAPI.global;

public class ObjectMapper {

    private Map<MappingKey<?, ?>, BiConsumer<?, ?>> mappings;
    private Map<MappingKey<?, ?>, JMapper<?, ?>> mappers;

    public ObjectMapper() {
        mappings = new HashMap<>();
        mappers = new HashMap<>();
    }

    public <S, T> T[] map(Class<S> sourceType, Class<T> targetType, S[] source) {
        return map(sourceType, targetType, source, false);
    }

    public <S, T> T[] map(Class<S> sourceType, Class<T> targetType, S[] source, boolean usejMapper) {

        @SuppressWarnings("unchecked")
        T[] targetList = (T[]) Array.newInstance(targetType, source.length);
        for (int i = 0; i < source.length; i++) {
            targetList[i] = map(sourceType, targetType, source[i], usejMapper);
        }

        return targetList;
    }

    public <S, T> T map(Class<S> sourceType, Class<T> targetType, S source) {
        return this.map(sourceType, targetType, source, false);
    }

    public <S, T> T map(Class<S> sourceType, Class<T> targetType, S source, boolean usejMapper) {
        T result = null;

        @SuppressWarnings("unchecked")
        BiConsumer<S, T> biConsumer = (BiConsumer<S, T>) mappings.get(new MappingKey<S, T>(sourceType, targetType));
        // Map fields by name...
        if (biConsumer != null && usejMapper == false) {
            try {
                result = targetType.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException
                    | ObjectMapperProcessingException e) {
                throw new info.unterrainer.commons.serialization.objectmapper.exceptions.ObjectMapperProcessingException(
                        e.getMessage(), e);
            }
            try {
                biConsumer.accept(source, result);
            } catch (ObjectMapperMappingException e) {
                throw new info.unterrainer.commons.serialization.objectmapper.exceptions.ObjectMapperMappingException(
                        e.getMessage(), e);
            }
        } else {
            @SuppressWarnings("unchecked")
            JMapper<T, S> jMapper = (JMapper<T, S>) mappers.get(new MappingKey<S, T>(sourceType, targetType));

            if (jMapper == null) {
                JMapperAPI jmapperApi = new JMapperAPI().add(mappedClass(targetType).add(global()));
                try {
                    jMapper = new JMapper<>(targetType, sourceType, jmapperApi);
                    T tmp = jMapper.getDestination(source);
                    result = tmp;
                } catch (JMapperException | ObjectMapperMappingException e) {
                    throw new info.unterrainer.commons.serialization.objectmapper.exceptions.ObjectMapperMappingException(
                            e.getMessage(), e);
                }
            }
            if (result != null) {
                mappers.put(new MappingKey<S, T>(sourceType, targetType), jMapper);
            }
        }

        return result;
    }

    // mapping function
    // Object to Object mapper
    public <A, B> void registerMapping(Class<A> sourceType, Class<B> targetType, BiConsumer<A, B> forth,
            BiConsumer<B, A> back) {
        mappings.put(new MappingKey<A, B>(sourceType, targetType), forth);
        mappings.put(new MappingKey<B, A>(targetType, sourceType), back);
    }
}