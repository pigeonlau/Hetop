package cn.edu.nwpu.rj416.type.astype.cast;


import cn.edu.nwpu.rj416.util.exception.runtime.MacawRuntimeException;

public class MTypeCastException extends MacawRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9206136835273753206L;

	public MTypeCastException() {
		super();
	}

	public MTypeCastException(String message, Throwable cause) {
		super(message, cause);
	}

	public MTypeCastException(String message) {
		super(message);
	}
	public MTypeCastException(String formatString, Object... args) {
		super(String.format(formatString, args));
	}

	public MTypeCastException(Throwable cause) {
		super(cause);
	}
	
	
	
}