package cn.edu.nwpu.rj416.util.exception.runtime;

/**
 * 
 * @author MilesLiu
 *
 * 2019年10月25日 上午1:46:03
 */
public class MInvalidValueException extends MacawRuntimeException { //无效值异常

	/**
	 * 
	 */
	private static final long serialVersionUID = 3452831933237754055L;

	public MInvalidValueException() {
		super();
	}

	public MInvalidValueException(String message, Throwable cause) {
		super(message, cause);
	}
	public MInvalidValueException(String message) {
		super(message);
	}
	
	public MInvalidValueException(String formatString, Object... args) {
		super(String.format(formatString, args));
	}
}