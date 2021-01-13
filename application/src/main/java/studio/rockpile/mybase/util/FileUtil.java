package studio.rockpile.mybase.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilterOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.codec.binary.Base64;

public class FileUtil {

	public static String readLine(File file) throws Exception {
		BufferedReader reader = null;
		String content = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			content = reader.readLine();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return content;
	}

	public static void write(File file, String text) throws Exception {
		OutputStream output = null;
		try {
			output = new FileOutputStream(file);
			output.write(text.getBytes());
		} finally {
			if (output != null) {
				output.flush();
				output.close();
			}
		}
	}

	// 文件转化base64字符串
	public static String fileToBase64(File file) throws Exception {
		InputStream input = null;
		byte[] bytes = null;
		try {
			input = new FileInputStream(file);
			bytes = new byte[input.available()];
			input.read(bytes);
		} finally {
			if (input != null) {
				input.close();
			}
		}
		return Base64.encodeBase64String(bytes);
	}

	// base64字符串转化文件
	public static void base64ToFile(String base64, String filePath) throws Exception {
		byte[] bytes = Base64.decodeBase64(base64);
		for (int i = 0; i < bytes.length; ++i) {
			if (bytes[i] < 0) {
				bytes[i] += 256; // 调整异常数据
			}
		}

		File file = new File(filePath);
		mkdir(file.getParent());

		OutputStream output = null;
		FilterOutputStream stream = null;
		try {
			output = new FileOutputStream(file);
			stream = new BufferedOutputStream(output);
			stream.write(bytes);
		} finally {
			if (stream != null) {
				stream.close();
			}
			if (output != null) {
				output.close();
			}
		}
	}

	public static void mkdir(String path) throws Exception {
		File dir = new File(path);
		if (dir.exists()) {
			if (!dir.isDirectory()) {
				throw new Exception("cannot create directory'" + path + "', file exists");
			}
		} else {
			dir.mkdirs();
		}
	}
}
