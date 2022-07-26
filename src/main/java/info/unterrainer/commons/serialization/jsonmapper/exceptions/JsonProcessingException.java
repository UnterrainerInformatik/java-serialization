package info.unterrainer.commons.serialization.jsonmapper.exceptions;

public class JsonProcessingException extends RuntimeException {

	private static final long serialVersionUID = 6847139397800653190L;

	public JsonProcessingException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
