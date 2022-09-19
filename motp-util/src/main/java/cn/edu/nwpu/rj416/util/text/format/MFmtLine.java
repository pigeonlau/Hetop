package cn.edu.nwpu.rj416.util.text.format;

import java.util.ArrayList;
import java.util.List;

/**
 * 格式化文本的一行
 * @author MilesLiu
 *
 */
public class MFmtLine {
	private int indent = 0; //缩进属性
	private List<MFmtToken> tokenList = new ArrayList<>(); //已格式化的行内元素列表（一行文本的内容）
	
	
	public MFmtLine() {
		super();
	}
	public MFmtLine(int indent) {
		super();
		this.indent = indent;
	}
	
	//创建指定缩进的格式化行对象
	public static MFmtLine create(int indent) {
		return new MFmtLine(indent);
	}
	
	public int getIndent() {
		return indent;
	}
	public void setIndent(int indent) {
		this.indent = indent;
	}
	
	public List<MFmtToken> getTokenList() {
		return tokenList;
	}
	
	public void setTokenList(List<MFmtToken> tokenList) {
		this.tokenList = tokenList;
	}
	
	//从行尾添加行内元素（无id元素）
	public MFmtLine appendToken(String text) {
		this.tokenList.add(new MFmtToken(text));
		return this;
	}
	
	//从行尾添加行内元素（有id元素）
	public MFmtLine appendToken(long id, String text) {
		this.tokenList.add(new MFmtToken(id, text));
		return this;
	}
	
	//从行首添加行内元素（无id元素）
	public MFmtLine prependToken(String text) {
		this.tokenList.add(0, new MFmtToken(text));
		return this;
	}
	
	//从行首添加行内元素（有id元素）
	public MFmtLine prependToken(long id, String text) {
		this.tokenList.add(0, new MFmtToken(id, text));
		return this;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < this.indent; i++) {
			sb.append("\t"); //根据缩进值添加Tab
		}
		for (MFmtToken t : this.tokenList) {
			sb.append(t.getText());
		} //连接行内元素
		
		return sb.toString();
	}
	
	
	
}
