package cn.edu.nwpu.rj416.motp.serializer.motp.loader;



import cn.edu.nwpu.rj416.motp.serializer.motp.util.MTempFileUtil;
import cn.edu.nwpu.rj416.util.objects.MByteBuffer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;


public class FileLoader {
	public static File readFileData(MotpLoader loader, MByteBuffer buffer) {
		try {
			return doRead(loader, buffer);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void skipFileData(MotpLoader loader, MByteBuffer buffer) {
		try {
			doRead(loader, buffer);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static File doRead(MotpLoader loader, MByteBuffer buffer) throws IOException {
		int fileNameLength = buffer.readMVLInt().castToInteger();
		byte[] fileNameBytes = buffer.readBytes(fileNameLength);
		String originalFileName = MTempFileUtil.getOriginalFileName(new String(fileNameBytes, "UTF-8"));

		int dataLength = buffer.readMVLInt().castToInteger();
		byte[] data = buffer.readBytes(dataLength);

		Path tempFilePath = Files.createTempFile(MTempFileUtil.TEMP_FILE_PREFIX, "." + originalFileName);
		Files.write(tempFilePath, data, StandardOpenOption.CREATE, StandardOpenOption.WRITE,
				StandardOpenOption.TRUNCATE_EXISTING);
		tempFilePath.toFile().deleteOnExit();

		return tempFilePath.toFile();
	}
}
