package studio.rockpile.mybase.service;

import java.io.File;
import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import studio.rockpile.mybase.service.base.BaseServiceHandler;
import studio.rockpile.mybase.service.base.ServiceArgException;
import studio.rockpile.mybase.util.AES128Encryptor;
import studio.rockpile.mybase.util.FileUtil;

public class FileEncryptor extends BaseServiceHandler {
	private static final Logger logger = LoggerFactory.getLogger(FileEncryptor.class);
	private final static String ENCRYPT_FILE_SUFFIX = ".enc";
	private String aesKey = null;
	private String ivParam = null;

	@Override
	public void perform(String[] args) throws Exception {
		if (args.length < 2) {
			throw new ServiceArgException("运行参数未指定文件路径!");
		}

		File file = new File(args[1]);
		if (!file.isFile()) {
			throw new ServiceArgException("文件(" + args[1] + ")不存在!");
		}
		aesKey = (args.length >= 3) ? args[2] : AES128Encryptor.DEFAULT_AES_KEY;
		ivParam = (args.length >= 4) ? args[3] : AES128Encryptor.DEFAULT_IV_PARAM;

		String fileName = file.getName();
		if (fileName.startsWith("_")) {
			encoding(file);
			file.delete();
		} else {
			int idx = fileName.lastIndexOf(".");
			if (idx != -1 && ENCRYPT_FILE_SUFFIX.equals(fileName.substring(idx))) {
				decoding(file, fileName.substring(0, idx));
				file.delete();
			}
		}

	}

	public void encoding(File file) throws Exception {
		String base64 = FileUtil.fileToBase64(file);
		String encrypt = AES128Encryptor.encrypt(base64, aesKey, ivParam);
		String path = file.getParent() + "/"
				+ Base64.encodeBase64String(file.getName().getBytes(StandardCharsets.UTF_8));
		FileUtil.write(new File(path + ENCRYPT_FILE_SUFFIX), encrypt);
	}

	public void decoding(File file, String nameBase64) throws Exception {
		String base64 = AES128Encryptor.decrypt(FileUtil.readLine(file), aesKey, ivParam);
		String fileName = new String(Base64.decodeBase64(nameBase64), StandardCharsets.UTF_8);
		FileUtil.base64ToFile(base64, file.getParent() + "/" + fileName);
	}

	@Override
	public String usage() {
		return "Usage: java -jar mybase.jar file-encrypt {file-path} [aes] [iv]";
	}
}
