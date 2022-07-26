package info.unterrainer.commons.serialization.objectmapper.models;

import java.time.LocalDate;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class ComplexUser {
	String firstName;
	String lastName;
	LocalDate dateTime;
	Double value;
}
