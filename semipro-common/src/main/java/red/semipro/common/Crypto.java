package red.semipro.common;

import java.io.Serializable;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 * AES暗号/CBCモードによる暗号処理クラス
 * @author 川場隆　kawaba@tk-webs.com 
 */
public class Crypto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Cipher encrypter; // 暗号化用の暗号器
    private Cipher decrypter; // 復号化用の暗号器
    private static String DEFAULT_IVS = "[@3etn*m5rt00[@;";
    private static String DEFAULT_KEY = "^k4orpbglng[r4k+";
    
    public Crypto() {

        IvParameterSpec iv = new IvParameterSpec(DEFAULT_IVS.getBytes());    // 暗号化時のスタートブロック用の初期値を作成

        SecretKeySpec key = new SecretKeySpec(DEFAULT_KEY.getBytes(), "AES"); // 暗号方式＋解読キーのセットを作成

        try {
            encrypter = Cipher.getInstance("AES/CBC/PKCS5Padding");  // 暗号方式と生成方式などを指定して暗号器を作成
            encrypter.init(Cipher.ENCRYPT_MODE, key, iv);            // 暗号器を暗号化モードにセットする

            decrypter = Cipher.getInstance("AES/CBC/PKCS5Padding");  // もうひとつ、暗号器を作成しておく
            decrypter.init(Cipher.DECRYPT_MODE, key, iv);            // 暗号器を復号モードにセットする
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
    
    /* コンストラクタ（引数は暗号解読キーとIV）
     *   引数は、どちらもStringからbyte[]に変換したものを指定する
     */
    public Crypto(byte[] secretKey, byte[] ivs) {
        IvParameterSpec iv = new IvParameterSpec(ivs);    // 暗号化時のスタートブロック用の初期値を作成

        SecretKeySpec key = new SecretKeySpec(secretKey, "AES"); // 暗号方式＋解読キーのセットを作成
        try {
            encrypter = Cipher.getInstance("AES/CBC/PKCS5Padding");  // 暗号方式と生成方式などを指定して暗号器を作成
            encrypter.init(Cipher.ENCRYPT_MODE, key, iv);            // 暗号器を暗号化モードにセットする

            decrypter = Cipher.getInstance("AES/CBC/PKCS5Padding");  // もうひとつ、暗号器を作成しておく
            decrypter.init(Cipher.DECRYPT_MODE, key, iv);            // 暗号器を復号モードにセットする
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    /* 暗号化処理を実行するメソッド */
    public String encrypto(String text) {
        try {
            byte[] crypto = encrypter.doFinal(text.getBytes()); // 暗号化する
            byte[] str64 = Base64.getEncoder().encode(crypto);  // 表示できるように文字の配列に変換する
            return DatatypeConverter.printHexBinary(str64);     // さらに文字列にしておく
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
    /* 復号化処理を実行するメソッド */
    public String decrypto(String hexStr) {
        try {
            byte[] str64 = DatatypeConverter.parseHexBinary(hexStr);
            byte[] str = Base64.getDecoder().decode(str64);     // 暗号文字列を元のバイナリに戻す
            byte[] text = decrypter.doFinal(str);               // 復号化する
            return new String(text);                            // 文字列に変換して返す
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}

