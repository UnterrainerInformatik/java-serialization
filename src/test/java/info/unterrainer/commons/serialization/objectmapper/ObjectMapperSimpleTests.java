package info.unterrainer.commons.serialization.objectmapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import info.unterrainer.commons.serialization.objectmapper.exceptions.ObjectMapperMappingException;
import info.unterrainer.commons.serialization.objectmapper.models.Car;
import info.unterrainer.commons.serialization.objectmapper.models.CarDto;
import info.unterrainer.commons.serialization.objectmapper.models.SimpleUser;

public class ObjectMapperSimpleTests {
	public ObjectMapper oMapper;

	@BeforeEach
	public void beforeEach() {
		oMapper = new ObjectMapper();
	}

	@Test
	public void mappingCarToCarDtoWorks() {
		Car car = Car.builder().build();
		car.setDoorCount(2);
		car.setMakeOfYear("1929");
		CarDto carDto = oMapper.map(Car.class, CarDto.class, car);
		assertEquals(car.getDoorCount(), carDto.getDoorCount());
		assertEquals(car.getMakeOfYear(), carDto.getMakeOfYear());
	}

	@Test
	public void mappingCarToSmallCarDtoWorks() {
		// im Target fehlt ein Feld
		// ... ev. gibt es eine Einstellung dafür...
		// ... ev. beim Erzeugen der JMapperAPI.
	}

	@Test
	public void mappingSmallCarDtoToCarWorks() {
		// im Source fehlt ein Feld
	}

	@Test
	public void mappingCarDtoToSimpleUserThrowsException() {
		assertThrows(ObjectMapperMappingException.class, () -> {
			CarDto car = new CarDto(4, "1975");
			SimpleUser u = oMapper.map(CarDto.class, SimpleUser.class, car);
		});
	}
}
