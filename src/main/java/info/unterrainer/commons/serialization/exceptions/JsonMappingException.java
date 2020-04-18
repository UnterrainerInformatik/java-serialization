package info.unterrainer.commons.serialization.exceptions;

public class JsonMappingException extends RuntimeException {

	private static final long serialVersionUID = -2058918933463055135L;

	public JsonMappingException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
