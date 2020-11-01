package app1.mycipher;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MyCipher {
	private static final String ALGORITHM = "DES"; // 加密算法
	private static final String KEY = "12345678"; // 秘钥
	private static final Base64.Encoder encoder = Base64.getEncoder(); // Base64编码器
	private static final Base64.Decoder decoder = Base64.getDecoder(); // Base64解码器

	/**
	 * 加密字符串
	 * @param clearText 明文
	 * @return 密文
	 */
	public static String encryptString(String clearText)
	{
		SecretKey skey = new SecretKeySpec(KEY.getBytes(),ALGORITHM);
		Cipher cipher;
		try {
			cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE,skey);
			//1.加密
			byte[] dofinal = cipher.doFinal(clearText.getBytes());
			//2.对加密后的字节进行Base64编码，避免出现乱码
			String encode = encoder.encodeToString(dofinal);
			return encode;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 解密字符串
	 * @param cipherText 密文
	 * @return 明文
	 */
	public static String decryptString(String cipherText)
	{
		SecretKey skey = new SecretKeySpec(KEY.getBytes(),ALGORITHM);
		Cipher cipher;
		try {
			cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE,skey);
			//1.对密文线进行Base64解码，获取字节
			byte[] decode = decoder.decode(cipherText);
			//2.解密
			byte[] dofinal = cipher.doFinal(decode);
			return new String(dofinal);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static void main(String[] args)
	{
		System.out.println(MyCipher.decryptString("/7wsStZJAwM="));
	}
}
