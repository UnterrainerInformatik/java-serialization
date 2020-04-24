package info.unterrainer.commons.serialization.jsons;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DateTimeJson {

	private LocalDate localDate;
	private LocalDateTime localDateTime;
	private ZonedDateTime zonedDateTime;
}
