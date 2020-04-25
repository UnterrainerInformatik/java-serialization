package info.unterrainer.commons.serialization.jsons;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class DateTimeJson {

	private LocalDate localDate;
	private LocalDateTime localDateTime;
	private ZonedDateTime zonedDateTime;
}
