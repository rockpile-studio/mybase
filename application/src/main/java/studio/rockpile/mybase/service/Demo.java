package studio.rockpile.mybase.service;

import studio.rockpile.mybase.util.AES128Encryptor;
import studio.rockpile.mybase.util.FileUtil;

import java.io.File;

public class Demo {
    private String aesKey = null;
    private String ivParam = null;

    public void encoding(File file) throws Exception {
        String base64 = FileUtil.fileToBase64(file);
        String encrypt = AES128Encryptor.encrypt(base64, aesKey, ivParam);
        String path = file.getParent() + "/" + file.getName() + ".enc";
        FileUtil.write(new File(path), encrypt);
    }

    public void decoding(File file) throws Exception {
        String base64 = AES128Encryptor.decrypt(FileUtil.readLine(file), aesKey, ivParam);
        String fileName = file.getName() + ".dec";
        FileUtil.base64ToFile(base64, file.getParent() + "/" + fileName);
    }

    public static void main(String[] args) {
        try {
            Demo demo = new Demo();
            String fileName = "D:/temporary/meta.dat";
            demo.setAesKey(AES128Encryptor.DEFAULT_AES_KEY);
            demo.setIvParam(AES128Encryptor.DEFAULT_IV_PARAM);
            demo.encoding(new File(fileName));

            String fileName2 = fileName + ".enc";
            demo.decoding(new File(fileName2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    public String getIvParam() {
        return ivParam;
    }

    public void setIvParam(String ivParam) {
        this.ivParam = ivParam;
    }
}
