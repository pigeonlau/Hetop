package cn.edu.nwpu.rj416.util.exception.runtime;

/**
 * 
 * @author MilesLiu
 *
 * 2019年10月25日 上午1:46:03
 */
public class MIndexOutOfBoundException extends MacawRuntimeException { //索引超出范围异常

	/**
	 * 
	 */
	private static final long serialVersionUID = 3452831933237754055L;

	public MIndexOutOfBoundException() {
		super();
	}

	public MIndexOutOfBoundException(String message, Throwable cause) {
		super(message, cause);
	}
	public MIndexOutOfBoundException(String message) {
		super(message);
	}
}