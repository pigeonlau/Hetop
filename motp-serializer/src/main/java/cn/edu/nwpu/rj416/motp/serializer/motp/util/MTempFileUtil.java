package cn.edu.nwpu.rj416.motp.serializer.motp.util;

import java.io.File;

public class MTempFileUtil {
	public static final String TEMP_FILE_PREFIX = "macaw-temp-file";

	public static String getOriginalFileName(String fileName) {
		if (fileName != null && fileName.startsWith(TEMP_FILE_PREFIX)) {
			int index = fileName.indexOf('.');
			return getOriginalFileName(fileName.substring(index + 1));
		}
		return fileName;
	}

	public static String getOriginalFileNameOfFile(File file) {
		return file == null ? null : getOriginalFileName(file.getName());
	}

	public static boolean isTempFile(File file) {
		return file != null && file.getName().startsWith(TEMP_FILE_PREFIX);
	}
}
