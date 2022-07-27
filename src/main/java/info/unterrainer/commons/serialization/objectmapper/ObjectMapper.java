package info.unterrainer.commons.serialization.objectmapper;

import static com.googlecode.jmapper.api.JMapperAPI.global;
import static com.googlecode.jmapper.api.JMapperAPI.mappedClass;

import java.awt.geom.Arc2D.Float;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import com.googlecode.jmapper.JMapper;
import com.googlecode.jmapper.api.JMapperAPI;
import com.googlecode.jmapper.exceptions.JMapperException;

import info.unterrainer.commons.serialization.objectmapper.exceptions.ObjectMapperMappingException;
import info.unterrainer.commons.serialization.objectmapper.exceptions.ObjectMapperProcessingException;
import info.unterrainer.commons.serialization.objectmapper.key.MappingKey;
import lombok.NonNull;

public class ObjectMapper {

	private Map<MappingKey<?, ?>, JMapper<?, ?>> mappers;
	private Map<MappingKey<?, ?>, BiConsumer<?, ?>> mappings;

	public ObjectMapper() {
		mappings = new HashMap<>();
		mappers = new HashMap<>();
	}

	public <A, B> void registerMapping(@NonNull final Class<A> sourceType, @NonNull final Class<B> targetType,
			final BiConsumer<A, B> forth, final BiConsumer<B, A> back) {
		if (forth != null)
			mappings.put(new MappingKey<>(sourceType, targetType), forth);
		if (back != null)
			mappings.put(new MappingKey<>(targetType, sourceType), back);
	}

	public <S, T> T map(@NonNull final Class<T> targetType,
			@NonNull final S source) {
		return map(targetType, source, null);
	}

	@SuppressWarnings("unchecked")
	public <S, T> T map(@NonNull final Class<T> targetType, @NonNull final S source,
			final T target) {
		final Class<S> sourceType = (Class<S>) source.getClass();
		T result = mapWithJMapper(sourceType, targetType, source, target);
		return mapWithBiConsumer(sourceType, targetType, source, result);
	}

	private <T, S> T mapWithBiConsumer(@NonNull final Class<S> sourceType, @NonNull final Class<T> targetType,
			@NonNull final S source, @NonNull final T target) {
		@SuppressWarnings("unchecked")
		BiConsumer<S, T> biConsumer = (BiConsumer<S, T>) mappings.get(new MappingKey<>(sourceType, targetType));

		if (biConsumer != null)
			biConsumer.accept(source, target);
		return target;
	}

	private <T> T constructObjectWithObjectType(final Class<T> targetType) {
		try {
			return targetType.getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new ObjectMapperProcessingException("Error while instantiating target object", e);
		}
	}

	private <T, S> T mapWithJMapper(@NonNull final Class<S> sourceType, @NonNull final Class<T> targetType,
			@NonNull final S source, final T target) {
		if (IsPrimitveIncludingWrapper(sourceType) || IsPrimitveIncludingWrapper(targetType)
				|| IsPrimitiveCollectionOrArrayIncludingWrapper(sourceType)
				|| IsPrimitiveCollectionOrArrayIncludingWrapper(targetType)) {
			return constructObjectWithObjectType(targetType);
		}

		@SuppressWarnings("unchecked")
		JMapper<T, S> jMapper = (JMapper<T, S>) mappers.get(new MappingKey<>(sourceType, targetType));
		if (jMapper == null) {
			JMapperAPI jmapperApi = new JMapperAPI().add(mappedClass(targetType).add(global()));
			try {
				jMapper = new JMapper<>(targetType, sourceType, jmapperApi);
			} catch (JMapperException e) {
				throw new ObjectMapperMappingException("Error creating mapper.", e);
			}
			mappers.put(new MappingKey<>(sourceType, targetType), jMapper);
		}
		try {
			if (target == null)
				return jMapper.getDestination(source);
			return jMapper.getDestination(target, source);
		} catch (JMapperException e) {
			throw new ObjectMapperMappingException("Error mapping objects.", e);
		}

	}

	private boolean IsPrimitveIncludingWrapper(final Class<?> t) {
		return t.isPrimitive() || t == String.class || t == Short.class || t == Integer.class || t == Long.class
				|| t == Float.class || t == Double.class || t == Character.class || t == Boolean.class;
	}

	private boolean IsPrimitiveCollectionOrArrayIncludingWrapper(final Class<?> t) {
		return t.isArray() && IsPrimitveIncludingWrapper(t.getComponentType()) || Collection.class.isAssignableFrom(t)
				|| Map.class.isAssignableFrom(t);
	}
}