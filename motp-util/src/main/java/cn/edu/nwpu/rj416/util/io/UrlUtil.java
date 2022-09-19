package cn.edu.nwpu.rj416.util.io;

import cn.edu.nwpu.rj416.util.types.StringUtil;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class UrlUtil { //URL工具
	
	/** Pseudo URL prefix for loading from the class path: "classpath:". */
	public static final String CLASSPATH_URL_PREFIX = "classpath:";

	/** URL prefix for loading from the file system: "file:". */
	public static final String FILE_URL_PREFIX = "file:";

	/** URL prefix for loading from a jar file: "jar:". */
	public static final String JAR_URL_PREFIX = "jar:";

	/** URL prefix for loading from a war file on Tomcat: "war:". */
	public static final String WAR_URL_PREFIX = "war:";

	/** URL protocol for a file in the file system: "file". */
	public static final String URL_PROTOCOL_FILE = "file";

	/** URL protocol for an entry from a jar file: "jar". */
	public static final String URL_PROTOCOL_JAR = "jar";

	/** URL protocol for an entry from a war file: "war". */
	public static final String URL_PROTOCOL_WAR = "war";

	/** URL protocol for an entry from a zip file: "zip". */
	public static final String URL_PROTOCOL_ZIP = "zip";

	/** URL protocol for an entry from a WebSphere jar file: "wsjar". */
	public static final String URL_PROTOCOL_WSJAR = "wsjar";

	/** URL protocol for an entry from a JBoss jar file: "vfszip". */
	public static final String URL_PROTOCOL_VFSZIP = "vfszip";

	/** URL protocol for a JBoss file system resource: "vfsfile". */
	public static final String URL_PROTOCOL_VFSFILE = "vfsfile";

	/** URL protocol for a general JBoss VFS resource: "vfs". */
	public static final String URL_PROTOCOL_VFS = "vfs";

	/** File extension for a regular jar file: ".jar". */
	public static final String JAR_FILE_EXTENSION = ".jar";

	/** Separator between JAR URL and file path within the JAR: "!/". */
	public static final String JAR_URL_SEPARATOR = "!/";

	/** Special separator between WAR URL and jar part on Tomcat. */
	public static final String WAR_URL_SEPARATOR = "*/";
	
	/**
	 * Determine whether the given URL points to a resource in a jar file.
	 * i.e. has protocol "jar", "war, ""zip", "vfszip" or "wsjar".
	 * @param url the URL to check
	 * @return whether the URL has been identified as a JAR URL
	 */
	public static boolean isJarURL(URL url) { //确定给定的URL是否指向jar文件中的资源
		String protocol = url.getProtocol(); //获取URL的协议
		return (URL_PROTOCOL_JAR.equals(protocol) || URL_PROTOCOL_WAR.equals(protocol) ||
				URL_PROTOCOL_ZIP.equals(protocol) || URL_PROTOCOL_VFSZIP.equals(protocol) ||
				URL_PROTOCOL_WSJAR.equals(protocol)); //属于给定协议常量即返回true
	}
	
	/**
	 * Determine whether the given URL points to a jar file itself,
	 * that is, has protocol "file" and ends with the ".jar" extension.
	 * @param url the URL to check
	 * @return whether the URL has been identified as a JAR file URL
	 * @since 4.1
	 */
	public static boolean isJarFileURL(URL url) { //确定给定的URL是否指向jar文件本身
		return (URL_PROTOCOL_FILE.equals(url.getProtocol()) &&
				url.getPath().toLowerCase().endsWith(JAR_FILE_EXTENSION));
	}//URL用file协议并且路径以.jar结尾即返回真

	/**
	 * Extract the URL for the actual jar file from the given URL
	 * (which may point to a resource in a jar file or to a jar file itself).
	 * @param jarUrl the original URL
	 * @return the URL for the actual jar file
	 * @throws MalformedURLException if no valid jar file URL could be extracted
	 */
	//从给定的jar URL提取实际jar文件的URL
	public static URL extractJarFileURL(URL jarUrl) throws MalformedURLException {
		String urlFile = jarUrl.getFile(); //获取URL的文件名
		int separatorIndex = urlFile.indexOf(JAR_URL_SEPARATOR); //提取jar URL和文件路径分隔符的位置索引
		if (separatorIndex != -1) { //分隔符不是文件名的最后一位
			String jarFile = urlFile.substring(0, separatorIndex); //提取jar URL
			try {
				return new URL(jarFile); //根据提取的jar URL字符串尝试创建URL对象
			}
			catch (MalformedURLException ex) {
				// Probably no protocol in original jar URL, like "jar:C:/mypath/myjar.jar".
				// This usually indicates that the jar file resides in the file system.
				if (!jarFile.startsWith("/")) {
					jarFile = "/" + jarFile;
				} //创建失败通常是因为jar在文件系统中却没有加'file:/'表明其使用的协议
				return new URL(FILE_URL_PREFIX + jarFile); //加上'file:/'再创建URL对象
			}
		}
		else {
			return jarUrl;
		}
	}

	/**
	 * Extract the URL for the outermost archive from the given jar/war URL
	 * (which may point to a resource in a jar file or to a jar file itself).
	 * <p>In the case of a jar file nested within a war file, this will return
	 * a URL to the war file since that is the one resolvable in the file system.
	 * @param jarUrl the original URL
	 * @return the URL for the actual jar file
	 * @throws MalformedURLException if no valid jar file URL could be extracted
	 * @since 4.1.8
	 * @see #extractJarFileURL(URL)
	 */
	//从给定的jar/war URL提取最外层存档的URL（可能指向jar包自身，也可能指向jar包中的资源）
	public static URL extractArchiveURL(URL jarUrl) throws MalformedURLException {
		String urlFile = jarUrl.getFile(); //获取URL字符串

		int endIndex = urlFile.indexOf(WAR_URL_SEPARATOR); //提取war URL和jar URL之间分隔符在给定URL中的索引
		if (endIndex != -1) {
			// Tomcat's "war:file:...mywar.war*/WEB-INF/lib/myjar.jar!/myentry.txt"
			String warFile = urlFile.substring(0, endIndex); //分隔符前是war URL
			if (URL_PROTOCOL_WAR.equals(jarUrl.getProtocol())) {
				return new URL(warFile); //给定URL的协议要是war，返回war URL创建的对象
			}
			int startIndex = warFile.indexOf(WAR_URL_PREFIX); //协议不是war，提取war前缀在给定URL中的位置索引
			if (startIndex != -1) {
				return new URL(warFile.substring(startIndex + WAR_URL_PREFIX.length()));
			}//截取war前缀之后的字符串创建URL对象返回
		}

		// Regular "jar:file:...myjar.jar!/myentry.txt"
		return extractJarFileURL(jarUrl); //不是war URL，按jar URL处理即可
	}

	/**
	 * Create a URI instance for the given URL,
	 * replacing spaces with "%20" URI encoding first.
	 * @param url the URL to convert into a URI instance
	 * @return the URI instance
	 * @throws URISyntaxException if the URL wasn't a valid URI
	 * @see URL#toURI()
	 */
	//为给定的URL（统一资源定位符）创建URI（统一资源标识符）实例
	public static URI toURI(URL url) throws URISyntaxException {
		return toURI(url.toString());
	}

	/**
	 * Create a URI instance for the given location String,
	 * replacing spaces with "%20" URI encoding first.
	 * @param location the location String to convert into a URI instance
	 * @return the URI instance
	 * @throws URISyntaxException if the location wasn't a valid URI
	 */
	//为给定的位置字符串创建URI实例
	public static URI toURI(String location) throws URISyntaxException {
		return new URI(StringUtil.replace(location, " ", "%20"));//将给定字符串的空格用"%20"替换
	}
	
	//连接给定的路径和基础URL字符串（“掐头去尾”）
	public static String joinUrl(String baseUrl, String path) {
		if (StringUtil.isEmpty(baseUrl)) {
			return null;
		} //URL不能为空
		
		if (StringUtil.isEmpty(path)) {
			return baseUrl;
		} //要连接的路径为空则直接返回基础URL字符串
		baseUrl = baseUrl.replace('\\', '/');
		baseUrl = StringUtil.removeEnd(baseUrl, "/"); //去掉base URL最后的分隔符之后的内容
		
		path = path.replace('\\', '/');
		path = StringUtil.removeStart(path, "/"); //去掉path第一个分隔符之前的内容
		
		return String.format("%s/%s", baseUrl, path); //组合“掐头去尾”的两部分返回
	}
}
