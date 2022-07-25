package info.unterrainer.commons.serialization.objectmapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import info.unterrainer.commons.serialization.objectmapper.models.ComplexUser;
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
	}

	@Test
	public void mappingComplexUserToSimpleUserUsingMappingWorks() {
		ComplexUser cu = ComplexUser.builder().build();
		cu.setFirstName("Danijel");
		cu.setLastName("Balog");
		cu.setDateTime(LocalDate.now());
		cu.setValue(100.00d);
		SimpleUser su = SimpleUser.builder().build();
		su = oMapper.map(ComplexUser.class, SimpleUser.class, cu, su);
		assertEquals(su.getFirstName(), cu.getFirstName());
		assertEquals(su.getLastName(), cu.getLastName());
	}

	@Test
	public void mappingComplexUserToOtherSimpleUserUsingMappingWorks() {
		ComplexUser cu = ComplexUser.builder().build();
		cu.setFirstName("Danijel");
		cu.setLastName("Balog");
		cu.setDateTime(LocalDate.now());
		cu.setValue(100.00d);
		OtherSimpleUser osu = OtherSimpleUser.builder().build();
		osu = oMapper.map(ComplexUser.class, OtherSimpleUser.class, cu, osu);
		assertEquals(osu.getFirstName(), cu.getFirstName());
		assertEquals(osu.getSurName(), cu.getLastName());
	}

	@Test
	public void mappingOtherSimpleUserToComplexUserUsingMappingWorks() {
		OtherSimpleUser su = OtherSimpleUser.builder().build();
		su.setFirstName("Danijel");
		su.setSurName("Balog");
		ComplexUser cu = ComplexUser.builder().build();
		cu = oMapper.map(OtherSimpleUser.class, ComplexUser.class, su, cu);
		assertEquals(su.getFirstName(), cu.getFirstName());
		assertEquals(su.getSurName(), cu.getLastName());
	}

	@Test
	public void mappingObjectWithArrayWorks() {

	}

	@Test
	public void mappingObjectWithArrayOfObjectsWorks() {

	}

	@Test
	public void mappingObjectWithinObjectWorks() {

	}
}
