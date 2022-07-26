package info.unterrainer.commons.serialization.objectmapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import info.unterrainer.commons.serialization.objectmapper.exceptions.ObjectMapperMappingException;
import info.unterrainer.commons.serialization.objectmapper.models.Car;
import info.unterrainer.commons.serialization.objectmapper.models.CarDto;
import info.unterrainer.commons.serialization.objectmapper.models.ObjectWithObject;
import info.unterrainer.commons.serialization.objectmapper.models.OtherObjectWithObject;
import info.unterrainer.commons.serialization.objectmapper.models.SimpleUser;
import info.unterrainer.commons.serialization.objectmapper.models.SmallCarDto;

public class ObjectMapperSimpleTests {
	public ObjectMapper oMapper;

	@BeforeEach
	public void beforeEach() {
		oMapper = new ObjectMapper();
	}

	@Test
	public void mappingCarToCarDtoWorks() {
		Car car = new Car(2, "1929");
		CarDto carDto = oMapper.map(Car.class, CarDto.class, car);
		assertEquals(car.getDoorCount(), carDto.getDoorCount());
		assertEquals(car.getMakeOfYear(), carDto.getMakeOfYear());
	}

	@Test
	public void mappingCarToSmallCarDtoWorks() {
		Car car = new Car(2, "1929");
		SmallCarDto smallCarDto = oMapper.map(Car.class, SmallCarDto.class, car);
		assertEquals(car.getDoorCount(), smallCarDto.getDoorCount());
	}

	@Test
	public void mappingSmallCarDtoToCarWorks() {
		SmallCarDto smallCarDto = new SmallCarDto(2);
		Car car = oMapper.map(SmallCarDto.class, Car.class, smallCarDto);
		assertEquals(smallCarDto.getDoorCount(), car.getDoorCount());
	}

	@Test
	public void mappingCarDtoToSimpleUserThrowsException() {
		assertThrows(ObjectMapperMappingException.class, () -> {
			CarDto car = new CarDto(4, "1975");
			oMapper.map(CarDto.class, SimpleUser.class, car);
		});
	}

	@Test
	public void mappingObjectToObjectWorks() {
		ObjectWithObject oWO = new ObjectWithObject("HALLO");
		OtherObjectWithObject otherOWO = oMapper.map(ObjectWithObject.class, OtherObjectWithObject.class, oWO);
		assertEquals(oWO.getObject(), otherOWO.getObject());
	}
}
