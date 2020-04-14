package info.unterrainer.commons.udpobserver;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MediolaDatagram {
	private String type;
	@JsonProperty("adr")
	private String adress;
	private String data;
	private String vendor;
	private MediolaDatagramState state;
}
