package model;

import java.security.NoSuchAlgorithmException;

public class MD5 {

    /**
     * @param str
     * @return encrypted str under MD5
     */
    public String encrypt(String str){
        try {
            java.security.MessageDigest js = java.security.MessageDigest.getInstance("MD5");

            byte[] array = js.digest(str.getBytes());
            StringBuilder strB = new StringBuilder();

            for (byte b : array) {
                strB.append(Integer.toHexString((b & 0xff) | 0x100), 1, 3);
            }
            return strB.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


}