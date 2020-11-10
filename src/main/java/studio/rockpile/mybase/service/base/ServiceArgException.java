package studio.rockpile.mybase.service.base;

public class ServiceArgException extends Exception {

	private static final long serialVersionUID = 1L;

	public ServiceArgException() {
	}

	public ServiceArgException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public ServiceArgException(String msg) {
		super(msg);
	}

	public ServiceArgException(Throwable cause) {
		super(cause);
	}
}
