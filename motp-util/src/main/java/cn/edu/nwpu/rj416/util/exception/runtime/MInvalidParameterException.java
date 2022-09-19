package cn.edu.nwpu.rj416.util.exception.runtime;

/**
 * 
 * @author MilesLiu
 *
 * 2019年10月25日 上午1:46:03
 */
public class MInvalidParameterException extends MacawRuntimeException { //无效参数异常

	/**
	 * 
	 */
	private static final long serialVersionUID = 3452831933237754055L;

	public MInvalidParameterException() {
		super();
	}

	public MInvalidParameterException(String message, Throwable cause) {
		super(message, cause);
	}
	public MInvalidParameterException(String message) {
		super(message);
	}
	
	public MInvalidParameterException(String formatString, Object... args) {
		super(String.format(formatString, args));
	}
}