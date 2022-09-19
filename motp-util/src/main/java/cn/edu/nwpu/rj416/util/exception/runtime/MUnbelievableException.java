package cn.edu.nwpu.rj416.util.exception.runtime;

/**
 * <b>一切皆有可能 &gt; 绝对不可能 </b></br>
 * </br>
 * 所以当代码中的某处理论上说不可能到达时，为了以防万一可以抛出这个异常</br>
 * 
 * @author MilesLiu
 */
public class MUnbelievableException extends MacawRuntimeException {
	
	private static final long serialVersionUID = -8057371013441980579L;

	public MUnbelievableException() {
		super();
	}

	public MUnbelievableException(String message, Throwable cause) {
		super(message, cause);
	}
	public MUnbelievableException(String message) {
		super(message);
	}
	
	public MUnbelievableException(String formatString, Object... args) {
		super(String.format(formatString, args));
	}
}