package cn.edu.nwpu.rj416.util.exception.runtime;

/**
 * 
 * @author MilesLiu
 *
 * 2019年10月25日 上午1:46:03
 */
public class MOverflowException extends MacawRuntimeException { //溢出异常

	/**
	 * 
	 */
	private static final long serialVersionUID = 3452831933237754055L;

	public MOverflowException() {
		super();
	}

	public MOverflowException(String message, Throwable cause) {
		super(message, cause);
	}
	public MOverflowException(String message) {
		super(message);
	}

	public MOverflowException(String formatString, Object... args) {
		super(String.format(formatString, args));
	}
}