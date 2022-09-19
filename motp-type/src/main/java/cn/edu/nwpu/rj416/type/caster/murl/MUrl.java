package cn.edu.nwpu.rj416.type.caster.murl;


import cn.edu.nwpu.rj416.util.exception.runtime.MInvalidParameterException;
import cn.edu.nwpu.rj416.util.io.UrlUtil;

/**
 * 标准URL
 * @author MilesLiu
 *
 * 2019年10月25日 下午7:19:58
 */
public class MUrl {
	
	private MCommunicationProtocolEnum protocol;
	private String host;
	private int port;
	private String path;
	
	public static MUrl build(MCommunicationProtocolEnum protocol, String host, Integer port, String path) {
		return new MUrl(protocol, host, port, path);
	}
	
	public static MUrl build(MCommunicationProtocolEnum protocol, String host, String path) {
		return new MUrl(protocol, host, null, path);
	}
	
	public static MUrl parse(String url) {
		if (url == null || url.isEmpty()) {
			throw new MInvalidParameterException("url不能为空");
		}
		
		url = url.replace('\\', '/');
		int pos = url.indexOf("://");
		if (pos <= 0) {
			throw new MInvalidParameterException("未指定协议:%s", url);
		}
		String proptecolToken = url.substring(0, pos).toUpperCase();
		url = url.substring(pos + 3);
		
		MCommunicationProtocolEnum protocol = MCommunicationProtocolEnum.valueOf(proptecolToken);
		if (protocol == null) {
			throw new MInvalidParameterException("不支持的协议(%s):%s", proptecolToken, url);
		}
		pos = url.indexOf('/');
		if (pos < 0) {
			pos = url.length();
		}
		String host = url.substring(0, pos);
		if (pos >= url.length()) {
			url = "";
		} else {
			url = url.substring(pos + 1);
		}
		if (host.isEmpty()) {
			throw new MInvalidParameterException("缺少Host:%s", url);
		}

		int port = 0; 
		pos = host.indexOf(':');
		if (pos > 0) {
			try {
				port = Integer.parseInt(host.substring(pos + 1));
			} catch (NumberFormatException e) {
				throw new MInvalidParameterException("错误的端口定义(%s):%s", host.substring(pos + 1), url);
			}
			host = host.substring(0, pos);
		}
		
		return new MUrl(protocol, host, port, url);
	}

	public MUrl(MCommunicationProtocolEnum protocol, String host, Integer port, String path) {
		super();
		this.protocol = protocol;
		this.host = host;
		if (port != null) {
			this.port = port;
		}
		this.path = path;
	}
	
	public MUrl appendPath(String subPath) {
		String newPath = UrlUtil.joinUrl(this.path, subPath);
		MUrl url = new MUrl(this.protocol, this.host, this.port, newPath);
		return url;
	}
	
	public String getUrlStr() {
		if (this.protocol == null) {
			return "";
		}
		if (this.host == null || host.isEmpty()) {
			return "";
		}
		String path = "";
		
		if (this.path != null) {
			path = this.path;
		}
		if (!path.startsWith("/")) {
			path = "/" + path;
		}
		
		String domain = null;
		if (this.port != 0) {
			domain = String.format("%s:%d", this.host, this.port);
		} else {
			domain = this.host;
		}
		
		switch (this.protocol) {
			case HTTP:
				return String.format("http://%s%s", domain, path);
			case HTTPS:
				return String.format("https://%s%s", domain, path);
			case FTP:
				return String.format("ftp://%s%s", domain, path);
			default:
				return String.format("%s%s", domain, path);
		}
	}

	public MCommunicationProtocolEnum getProtocol() {
		return protocol;
	}
	public void setProtocol(MCommunicationProtocolEnum protocol) {
		this.protocol = protocol;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
