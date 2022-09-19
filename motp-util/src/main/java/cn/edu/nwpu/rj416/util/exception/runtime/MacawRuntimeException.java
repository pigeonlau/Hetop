package cn.edu.nwpu.rj416.util.exception.runtime;

/**
 * 
 * @author MilesLiu
 *
 * 2019年10月25日 上午1:45:53
 */
public class MacawRuntimeException extends RuntimeException { //运行时间异常

	/**
	 * 
	 */
	private static final long serialVersionUID = 3452831933237754055L;

	public MacawRuntimeException() {
		super();
	}

	public MacawRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public MacawRuntimeException(String message) {
		super(message);
	}
	public MacawRuntimeException(String formatString, Object... args) {
		super(String.format(formatString, args));
	}

	public MacawRuntimeException(Throwable cause) {
		super(cause);
	}
	
	
	
}