package cn.edu.nwpu.rj416.util.text.format;

/**
 * 格式化文本中的一个行内元素
 * @author MilesLiu
 *
 */
public class MFmtToken {
	private long id; //元素id
	private String text; //元素内容
	
	
	public MFmtToken() {
		super();
	}
	
	public MFmtToken(long id, String text) { //指定id和内容创建元素
		super();
		this.id = id;
		this.text = text;
	}

	
	public MFmtToken(String text) { //创建无id元素
		super();
		this.text = text;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
}
