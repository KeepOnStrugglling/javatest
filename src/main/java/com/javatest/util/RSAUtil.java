package com.javatest.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hjw
 * @date 2020-3-24
 */
public class RSAUtil {

    private static String RSA = "RSA"; //指定加密类型

    private static String charset = "UTF-8";    //指定码表

    private static final int MAX_ENCRYPT_BLOCK = 256;   //指定RSA最大加密明文大小

    // 生成RSA密钥对（默认密钥长度2048）
    public static KeyPair generateRSAKeyPair(){
        return generateRSAKeyPair(2048);
    }

    // 生成RSA密钥对（密钥长度512~2048）
    public static KeyPair generateRSAKeyPair(int keyLength){
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);
            kpg.initialize(keyLength);
            return kpg.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 返回密钥对（BASE64编码后）——使用默认密钥长度
    public static Map<String,String> getRSAKeys() {
        Map<String,String> map = new HashMap<>();
        KeyPair keyPair = generateRSAKeyPair();
        String publicKey = Base64.encodeBase64URLSafeString(keyPair.getPublic().getEncoded());
        String privateKey = Base64.encodeBase64URLSafeString(keyPair.getPrivate().getEncoded());
        map.put("publicKey", publicKey);
        map.put("privateKey", privateKey);
        return map;
    }

    // 返回密钥对（BASE64编码后）——指定密钥长度
    public static Map<String,String> getRSAKeys(int keyLength) {
        Map<String,String> map = new HashMap<>();
        KeyPair keyPair = generateRSAKeyPair(keyLength);
        String publicKey = Base64.encodeBase64URLSafeString(keyPair.getPublic().getEncoded());
        String privateKey = Base64.encodeBase64URLSafeString(keyPair.getPrivate().getEncoded());
        map.put("publicKey", publicKey);
        map.put("privateKey", privateKey);
        return map;
    }

    /**
     * 根据公钥字符串获取公钥对象
     * @param publicKey 公钥字符串
     * @return 公钥对象
     */
    public static RSAPublicKey getPublicKey(String publicKey) throws Exception{
        //通过X509编码的Key指令获得公钥对象
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
            return (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }

    /**
     * 根据私钥字符串获取私钥对象
     * @param privateKey 私钥字符串
     * @return 私钥对象
     */
    public static RSAPrivateKey getPrivateKey(String privateKey) throws Exception {
        //通过PKCS#8编码的Key指令获得私钥对象
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
            return (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }

    /**
     * 根据公钥加密数据
     * @param data  需加密的byte数据
     * @param publicKey BASE64解码后的公钥
     * @return  加密后的byte数据
     */
    public static byte[] encryptByPublic(byte[] data, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding"); // 参数含义：ECB指对需加密数据分组处理，PKCS1Padding指填充模式
            cipher.init(Cipher.ENCRYPT_MODE,publicKey); //设置编码方式及密钥
            return rsaCodec(cipher,Cipher.ENCRYPT_MODE,data,publicKey.getModulus().bitLength());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据私钥解密
     * @param encryptedData 被加密的byte数据
     * @param privateKey BASE64解码后的私钥
     * @return 原文的byte数据
     */
    public static byte[] decryptByPrivate(byte[] encryptedData, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE,privateKey);
            return rsaCodec(cipher,Cipher.DECRYPT_MODE,encryptedData,privateKey.getModulus().bitLength());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据私钥加密
     * @param data 需加密的byte数据
     * @param privateKey BASE64解码后的私钥
     * @return 加密后的byte数据
     */
    public static byte[] encryptByPrivate(byte[] data, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding"); // 参数含义：ECB指对需加密数据分组处理，PKCS1Padding指填充模式
            cipher.init(Cipher.ENCRYPT_MODE,privateKey); //设置编码方式及密钥
            return rsaCodec(cipher,Cipher.ENCRYPT_MODE,data,privateKey.getModulus().bitLength());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param encryptedData 被加密的byte数据
     * @param publicKey BASE64解码后的公钥
     * @return 原文的byte数据
     */
    public static byte[] decryptByPublic(byte[] encryptedData, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE,publicKey);
            return rsaCodec(cipher,Cipher.DECRYPT_MODE,encryptedData,publicKey.getModulus().bitLength());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * RSA处理
     */
    private static byte[] rsaCodec(Cipher cipher, int optionMode, byte[] data, int keyLength) {
        int maxBlock = 0;
        if (optionMode == Cipher.DECRYPT_MODE) {
            maxBlock = keyLength / 8;
        } else {
            maxBlock = keyLength / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // 对数据进行分段加/解密
        int offSet = 0;
        byte[] cache;
        int i = 0;
        try {
            while (data.length - offSet > 0) {
                if (data.length - offSet > maxBlock) {
                    cache = cipher.doFinal(data, offSet, maxBlock);
                } else {
                    cache = cipher.doFinal(data, offSet, data.length - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * maxBlock;
            }
            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Map<String, String> rsaKeys = RSAUtil.getRSAKeys();
        String publicKey = rsaKeys.get("publicKey");
        String privateKey = rsaKeys.get("privateKey");
        System.out.println("公钥：" + publicKey);
        System.out.println("私钥：" + privateKey);

        System.out.println("========使用公钥加密私钥解密========");
        String str1 = "token159357";
        System.out.println("明文：" + str1);
        long time1 = System.currentTimeMillis();
        byte[] ebytes = RSAUtil.encryptByPublic(str1.getBytes(), RSAUtil.getPublicKey(publicKey));
        long time2 = System.currentTimeMillis();
        System.out.println("密文：" + Base64.encodeBase64URLSafeString(ebytes));
        long time3 = System.currentTimeMillis();
        byte[] dbytes = RSAUtil.decryptByPrivate(ebytes, RSAUtil.getPrivateKey(privateKey));
        long time4 = System.currentTimeMillis();
        System.out.println("解密后文字：" + new String(dbytes,charset));
        System.out.println("加密耗时：" + (time2-time1));
        System.out.println("BASE64处理耗时：" + (time3-time2));
        System.out.println("解密耗时：" + (time4-time3));

        System.out.println("========结束=========");

//        System.out.println("++++++++使用私钥加密公钥解密++++++++");
//        String str2 = "东风夜放花千树，更吹落，星如雨。";
//        System.out.println("明文：" + str2);
//        byte[] ebytes1 = RSAUtil.encryptByPrivate(str2.getBytes(), RSAUtil.getPrivateKey(privateKey));
//        System.out.println("密文：" + Base64.encodeBase64URLSafeString(ebytes1));
//        byte[] dbytes2 = RSAUtil.decryptByPublic(ebytes1, RSAUtil.getPublicKey(publicKey));
//        System.out.println("解密后文字：" + new String(dbytes2,charset));
//        System.out.println("++++++++结束++++++++");

        // 结论：不管是公钥加密私钥解密，还是私钥加密公钥解密，单次处理耗时基本一致。但如果同时处理，则第二次处理时间会加快
    }
}
