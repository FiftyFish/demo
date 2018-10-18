package com.imooc.demo.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import sun.misc.BASE64Decoder;

public class EncryptUtil {
	/**
	 * 加密和解密秘钥
	 */
	public static final String KEY = "abcdefgabcdefg12";

	private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

	/**
	 * AES加密为base 64 code
	 * 
	 * @param content    待加密的内容
	 * @param encryptKey 加密密钥
	 * @return 加密后的base 64 code
	 * @throws Exception
	 */
	public static String base64Encode(byte[] bytes) {
		return Base64.encodeBase64String(bytes);
	}

	public static byte[] base64Decode(String base64Code) throws Exception {
		return new BASE64Decoder().decodeBuffer(base64Code);
	}

	/**
	 * ASE加密
	 * 
	 * @param content    原密码
	 * @param encryptKey 加密秘钥
	 * @return 加密后的byte
	 * @throws Exception
	 */
	public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
		// 实例化一个用AES加密算法的密钥生成器
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		// 初始化此密钥生成器，使其具有确定的密钥大小128字节长。
		kgen.init(128);
		// 创建密码器
		Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
		// 根据给定的encryptKey字节数组构造一个用AES算法加密的密钥;以加密的方式用密钥初始化此 Cipher
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));
		// 使用给定的UTF-8编码将此String编码到byte序列;按单部分操作加密指定的加密;返回加密过后的
		return cipher.doFinal(content.getBytes("utf-8"));
	}

	public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128);

		Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
		byte[] decryptBytes = cipher.doFinal(encryptBytes);

		return new String(decryptBytes);
	}

	/**
	 * 字符串加密
	 * 
	 * @param content    原密码
	 * @param encryptKey 加密秘钥
	 * @return
	 * @throws Exception
	 */
	public static String aesEncrypt(String content, String encryptKey) throws Exception {
		return base64Encode(aesEncryptToBytes(content, encryptKey));
	}

	public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
		return aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
	}

	/**
	 * 测试
	 * 
	 */
	public static void main(String[] args) throws Exception {

		String content = "111111";
		System.out.println("加密前：" + content);

		System.out.println("加密密钥和解密密钥：" + KEY);

		String pwd = aesEncrypt(content, KEY);
		System.out.println(pwd.length() + ":加密后：" + pwd);

		String decrypt = aesDecrypt(pwd, KEY);
		System.out.println("解密后：" + decrypt);
	}
}