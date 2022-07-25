package info.unterrainer.commons.serialization;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import info.unterrainer.commons.serialization.models.Car;
import info.unterrainer.commons.serialization.models.CarDTO;
import info.unterrainer.commons.serialization.models.ComplexUser;
import info.unterrainer.commons.serialization.models.SimpleUser;
import info.unterrainer.commons.serialization.models.SimpleUserWithOtherPropertyNames;
import info.unterrainer.commons.serialization.objectmapper.ObjectMapper;
import info.unterrainer.commons.serialization.objectmapper.exceptions.ObjectMapperMappingException;

public class ObjectMapperTests {
    public ObjectMapper oMapper;

    @BeforeEach
    public void beforeEach() {
        oMapper = new ObjectMapper();

        oMapper.registerMapping(ComplexUser.class, SimpleUserWithOtherPropertyNames.class, (x, y) -> {
            y.setFirstName(x.getFirstName());
            y.setSurName(x.getLastName());
        }, (y, x) -> {
            x.setFirstName(y.getFirstName());
            x.setLastName(y.getSurName());
        });
        oMapper.registerMapping(SimpleUser.class, SimpleUserWithOtherPropertyNames.class, (x, y) -> {
            y.setFirstName(x.getFirstName());
            y.setSurName(x.getLastName());
        }, (y, x) -> {
            x.setFirstName(y.getFirstName());
            x.setLastName(y.getSurName());
        });
    }

    @Test
    public void mappingCarToCarDtoWorks() {
        Car car = new Car();
        car.setDoorCount(2);
        car.setMakeOfYear("1929");
        CarDTO carDto = new CarDTO();
        carDto = oMapper.map(Car.class, CarDTO.class, car);
        assertEquals(car.getDoorCount(), carDto.getDoorCount());
        assertEquals(car.getMakeOfYear(), carDto.getMakeOfYear());
    }

    @Test
    public void mappingComplexUserToSimpleUseWorks() {
        ComplexUser cU = new ComplexUser();
        cU.setFirstName("Danijel");
        cU.setLastName("Balog");
        cU.setDateTime(LocalDate.now());
        cU.setValue(100.00d);
        SimpleUser sU = oMapper.map(ComplexUser.class, SimpleUser.class, cU);
        assertEquals(sU.getFirstName(), cU.getFirstName());
        assertEquals(sU.getLastName(), cU.getLastName());
    }

    @Test
    public void mappingComplexUserToSimpleUserWithOtherPropertyNamesWorks() {
        ComplexUser cU = new ComplexUser();
        cU.setFirstName("Danijel");
        cU.setLastName("Balog");
        cU.setDateTime(LocalDate.now());
        cU.setValue(100.00d);
        SimpleUserWithOtherPropertyNames sU = oMapper.map(ComplexUser.class, SimpleUserWithOtherPropertyNames.class,
                cU);
        assertEquals(sU.getFirstName(), cU.getFirstName());
        assertEquals(sU.getSurName(), cU.getLastName());
    }

    @Test
    public void mappingSimpleUserWithOtherPropertyNamesToComplexUserWorks() {
        SimpleUserWithOtherPropertyNames sU = new SimpleUserWithOtherPropertyNames();
        sU.setFirstName("Danijel");
        sU.setSurName("Balog");
        ComplexUser cU = oMapper.map(SimpleUserWithOtherPropertyNames.class, ComplexUser.class,
                sU);
        assertEquals(sU.getFirstName(), cU.getFirstName());
        assertEquals(sU.getSurName(), cU.getLastName());
    }

    @Test
    public void mappingArrayOfSimpleUserWithOtherPropertyNamesToArrayOfComplexUsersWorks() {
        SimpleUserWithOtherPropertyNames[] sUArray = new SimpleUserWithOtherPropertyNames[3];
        SimpleUserWithOtherPropertyNames sU = new SimpleUserWithOtherPropertyNames();
        sU.setFirstName("Danijel");
        sU.setSurName("Balog");
        sUArray[0] = sU;
        sU.setFirstName("Max");
        sU.setSurName("Mustermann");
        sUArray[1] = sU;
        sU.setFirstName("Johanna");
        sU.setSurName("Musterfrau");
        sUArray[2] = sU;
        ComplexUser[] cUArray = oMapper.map(SimpleUserWithOtherPropertyNames.class, ComplexUser.class, sUArray);
        for (int i = 0; i < cUArray.length; i++) {
            assertEquals(sUArray[i].getFirstName(), cUArray[i].getFirstName());
            assertEquals(sUArray[i].getSurName(), cUArray[i].getLastName());
        }
    }

    @Test
    public void mappingStringToIntException() throws ObjectMapperMappingException {
        Integer i = oMapper.map(String.class, Integer.class, "11");
    }
}
