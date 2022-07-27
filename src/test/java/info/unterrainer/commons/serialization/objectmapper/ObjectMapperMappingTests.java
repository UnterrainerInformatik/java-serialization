package info.unterrainer.commons.serialization.objectmapper;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import info.unterrainer.commons.serialization.objectmapper.models.ComplexUser;
import info.unterrainer.commons.serialization.objectmapper.models.ObjectWithArray;
import info.unterrainer.commons.serialization.objectmapper.models.ObjectWithObject;
import info.unterrainer.commons.serialization.objectmapper.models.OtherObjectWithArray;
import info.unterrainer.commons.serialization.objectmapper.models.OtherSimpleUser;
import info.unterrainer.commons.serialization.objectmapper.models.SimpleUser;

public class ObjectMapperMappingTests {
	public ObjectMapper oMapper;

	@BeforeEach
	public void beforeEach() {
		oMapper = new ObjectMapper();

		oMapper.registerMapping(ComplexUser.class, OtherSimpleUser.class, (x, y) -> {
			y.setFirstName(x.getFirstName());
			y.setSurName(x.getLastName());
		}, (y, x) -> {
			x.setFirstName(y.getFirstName());
			x.setLastName(y.getSurName());
		});
		oMapper.registerMapping(SimpleUser.class, OtherSimpleUser.class, (x, y) -> {
			y.setFirstName(x.getFirstName());
			y.setSurName(x.getLastName());
		}, (y, x) -> {
			x.setFirstName(y.getFirstName());
			x.setLastName(y.getSurName());
		});
		oMapper.registerMapping(SimpleUser.class, OtherSimpleUser.class, (x, y) -> {
			y.setFirstName(x.getFirstName());
			y.setSurName(x.getLastName());
		}, (y, x) -> {
			x.setFirstName(y.getFirstName());
			x.setLastName(y.getSurName());
		});
		oMapper.registerMapping(String[].class, ObjectWithArray.class, (x, y) -> {
			y.setObjects(x);
		}, (y, x) -> {
			x = Arrays.copyOf(y.getObjects(), y.getObjects().length, x.getClass());
		});
		oMapper.registerMapping(ObjectWithArray.class, OtherObjectWithArray.class, (x, y) -> {
			y.setObjects(Arrays.copyOf(x.getObjects(), x.getObjects().length, x.getObjects().getClass()));
		}, (y, x) -> {
			x.setObjects(Arrays.copyOf(y.getObjects(), y.getObjects().length, y.getObjects().getClass()));
		});
		oMapper.registerMapping(Integer.class, ObjectWithObject.class, (x, y) -> {
			y.setObject(x);
		}, (y, x) -> {
			x = Integer.parseInt(String.valueOf(y));
		});
	}

	@Test
	public void mappingComplexUserToSimpleUserWorks() {
		ComplexUser cu = ComplexUser.builder().build();
		cu.setFirstName("Danijel");
		cu.setLastName("Balog");
		cu.setDateTime(LocalDate.now());
		cu.setValue(100.00d);
		SimpleUser su = SimpleUser.builder().build();
		su = oMapper.map(cu, su);
		assertEquals(su.getFirstName(), cu.getFirstName());
		assertEquals(su.getLastName(), cu.getLastName());
	}

	@Test
	public void mappingComplexUserToOtherSimpleUserWorks() {
		ComplexUser cu = ComplexUser.builder().build();
		cu.setFirstName("Danijel");
		cu.setLastName("Balog");
		cu.setDateTime(LocalDate.now());
		cu.setValue(100.00d);
		OtherSimpleUser osu = OtherSimpleUser.builder().build();
		osu = oMapper.map(cu, osu);
		assertEquals(osu.getFirstName(), cu.getFirstName());
		assertEquals(osu.getSurName(), cu.getLastName());
	}

	@Test
	public void mappingOtherSimpleUserToComplexUserWorks() {
		OtherSimpleUser su = OtherSimpleUser.builder().build();
		su.setFirstName("Danijel");
		su.setSurName("Balog");
		ComplexUser cu = ComplexUser.builder().build();
		cu = oMapper.map(su, cu);
		assertEquals(su.getFirstName(), cu.getFirstName());
		assertEquals(su.getSurName(), cu.getLastName());
	}

	@Test
	public void mappingObjectWithArrayWorks() {
		String[] array = new String[] {
				"Danijel",
				"Balog",
				"Hallo"
		};
		ObjectWithArray oArray = oMapper.map(ObjectWithArray.class, array);
		assertArrayEquals(array, oArray.getObjects());
	}

	@Test
	public void mappingObjectWithArrayOfObjectsWorks() {
		ObjectWithArray array = new ObjectWithArray(new Object[] {
				"Danijel",
				500,
				"Balog",
				"Hallo"
		});
		OtherObjectWithArray oArray = oMapper.map(OtherObjectWithArray.class, array);
		assertArrayEquals(array.getObjects(), oArray.getObjects());
	}

	@Test
	public void mappingObjectWithinObjectWorks() {
		Integer i = 1234;
		ObjectWithObject oWO = oMapper.map(ObjectWithObject.class, i);
		assertEquals(i, oWO.getObject());
	}
}
