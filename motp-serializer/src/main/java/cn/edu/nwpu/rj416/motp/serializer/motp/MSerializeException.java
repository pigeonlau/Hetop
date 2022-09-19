package cn.edu.nwpu.rj416.motp.serializer.motp;


import cn.edu.nwpu.rj416.util.exception.runtime.MacawRuntimeException;

/**
 * 序列化异常
 * 
 * @author MilesLiu
 *
 * 2019年10月25日 上午1:46:09
 */
public class MSerializeException extends MacawRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3452831933237754055L;

	public MSerializeException() {
		super();
	}

	public MSerializeException(String message, Throwable cause) {
		super(message, cause);
	}

	public MSerializeException(String message) {
		super(message);
	}
	public MSerializeException(String formatString, Object... args) {
		super(String.format(formatString, args));
	}

	public MSerializeException(Throwable cause) {
		super(cause);
	}
	
	
	
}