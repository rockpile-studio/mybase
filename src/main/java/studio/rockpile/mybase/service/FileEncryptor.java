package studio.rockpile.mybase.service;

import java.io.File;

import studio.rockpile.mybase.service.base.BaseHandler;
import studio.rockpile.mybase.service.base.ServiceArgException;
import studio.rockpile.mybase.util.AES128Encryptor;
import studio.rockpile.mybase.util.FileUtil;

public class FileEncryptor extends BaseHandler {

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

		String path = file.getPath();
		int idx = path.lastIndexOf(".");

		if (ENCRYPT_FILE_SUFFIX.equals(path.substring(idx))) {
			decoding(file, path.substring(0, idx));
		} else {
			encoding(file);
		}
		file.delete();
	}

	public void encoding(File file) throws Exception {
		String base64 = FileUtil.fileToBase64(file);
		String encrypt = AES128Encryptor.encrypt(base64, aesKey, ivParam);
		FileUtil.write(new File(file.getPath() + ENCRYPT_FILE_SUFFIX), encrypt);
	}

	public void decoding(File file, String destFile) throws Exception {
		String base64 = AES128Encryptor.decrypt(FileUtil.readLine(file), aesKey, ivParam);
		FileUtil.base64ToFile(base64, destFile);
	}

	@Override
	public String usage() {
		return "Usage: java -jar mybase.jar file-encrypt {file-path} [aes] [iv]";
	}
}
