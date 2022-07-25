package info.unterrainer.commons.serialization.objectmapper.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {
	private int doorCount;
	private String makeOfYear;
}
