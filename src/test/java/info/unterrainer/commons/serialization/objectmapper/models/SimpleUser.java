package info.unterrainer.commons.serialization.objectmapper.models;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class SimpleUser {
	String firstName;
	String lastName;
}
