package cn.edu.nwpu.rj416.type.caster.murl;

public enum MCommunicationProtocolEnum {
	HTTP("http"),
	HTTPS("https"),
	FTP("ftp"),
	TCP("tcp"),
	UDP("udp");

	private String desc;

	private MCommunicationProtocolEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
