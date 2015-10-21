package com.tools.utils;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * AES工具类
 * 
 * @author LeeJEric
 * 
 */
public class AESUtil {

	// 密钥算法
	public static final String KEY_ALGORITHM = "AES";

	// 加解密算法/工作模式/填充方式,Java6.0支持PKCS5Padding填充方式,BouncyCastle支持PKCS7Padding填充方式
	public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

	/**
	 * 生成随机密钥 LeeJEric 2013-12-5 上午10:38:47
	 * 
	 * @return
	 * @throws Exception
	 */
	public String randomkey() throws Exception {
		BASE64Encoder encoder = new BASE64Encoder();
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM); // 实例化密钥生成器
		kg.init(128); // 初始化密钥生成器:AES要求密钥长度为128,192,256位
		SecretKey secretKey = kg.generateKey(); // 生成密钥
		return encoder.encode(secretKey.getEncoded()); // 获取二进制密钥编码形式
	}

	/**
	 * 根据指定key生成密钥 LeeJEric 2013-12-5 上午11:06:20
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String initkey(String key) throws Exception {
		return MD5Util.md5For3Encrypt(key).substring(0, 22) + "==";
	}

	/**
	 * 转换密钥 LeeJEric 2013-12-5 上午10:38:58
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public Key toKey(byte[] key) throws Exception {
		return new SecretKeySpec(key, KEY_ALGORITHM);
	}

	/**
	 * 加密数据
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            密钥
	 * @return 加密后的数据
	 * */
	public String encrypt(String data, String key) throws Exception {
		BASE64Encoder encoder = new BASE64Encoder();
		BASE64Decoder decoder = new BASE64Decoder();
		Key k = toKey(decoder.decodeBuffer(key)); // 还原密钥
		// 使用PKCS7Padding填充方式,这里就得这么写了(即调用BouncyCastle组件实现)
		// Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM); // 实例化Cipher对象，它用于完成实际的加密操作
		cipher.init(Cipher.ENCRYPT_MODE, k); // 初始化Cipher对象，设置为加密模式
		return encoder.encode(cipher.doFinal(data.getBytes())); // 执行加密操作。加密后的结果通常都会用Base64编码进行传输
	}

	/**
	 * 解密数据
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            密钥
	 * @return 解密后的数据
	 * */
	public String decrypt(String data, String key) throws Exception {
		BASE64Decoder decoder = new BASE64Decoder();
		Key k = toKey(decoder.decodeBuffer(key));
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, k); // 初始化Cipher对象，设置为解密模式
		return new String(cipher.doFinal(decoder.decodeBuffer(data))); // 执行解密操作
	}

	/*
	 * public static void main(String[] args) throws Exception { AESUtil aesUtil
	 * = new AESUtil(); String source = "☆→■△▲※"; System.out.println("原文：" +
	 * source);
	 * 
	 * String pwd = "eric"; String key = aesUtil.initkey(pwd);
	 * System.out.println("密钥：" + key);
	 * 
	 * String encryptData = aesUtil.encrypt(source, key);
	 * System.out.println("密文：" + encryptData);
	 * 
	 * String decryptData = aesUtil.decrypt(encryptData, key);
	 * System.out.println("明文: " + decryptData); }
	 */
}
