package com.aion.server.service;

import com.aion.server.service.infra.exception.EncodeException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;


@Slf4j
@UtilityClass
public class EncryptionService {

    private static final char[] CA = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

    public static String toEncode(String toEncode) throws EncodeException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(toEncode.getBytes("UTF-8"));
            return encodeToString(messageDigest.digest(), false);
        } catch (NoSuchAlgorithmException e) {
            log.error("Failed to get ");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        throw new EncodeException();
    }

    public static String toDecode(String toDecode) {
        return Arrays.toString(Base64.getDecoder().decode(toDecode.getBytes()));
    }


    /**
     * Encodes a raw byte array into a BASE64 <code>char[]</code> representation
     * i accordance with RFC 2045.
     *
     * @param sArr    The bytes to convert. If <code>null</code> or length 0 an
     *                empty array will be returned.
     * @param lineSep Optional "\r\n" after 76 characters, unless end of
     *                file.<br>
     *                No line separator will be in breach of RFC 2045 which specifies max 76
     *                per line but will be a little faster.
     * @return A BASE64 encoded array. Never <code>null</code>.
     */
    public static char[] encodeToChar(byte[] sArr, boolean lineSep) {
        // Check special case
        int sLen = sArr != null ? sArr.length : 0;
        if (sLen == 0) {
            return new char[0];
        }

        int eLen = (sLen / 3) * 3; // Length of even 24-bits.
        int cCnt = ((sLen - 1) / 3 + 1) << 2; // Returned character count
        int dLen = cCnt + (lineSep ? (cCnt - 1) / 76 << 1 : 0); // Length of returned array
        char[] dArr = new char[dLen];

        // Encode even 24-bits
        for (int s = 0, d = 0, cc = 0; s < eLen; ) {
            // Copy next three bytes into lower 24 bits of int, paying attension to sign.
            int i = (sArr[s++] & 0xff) << 16 | (sArr[s++] & 0xff) << 8 | (sArr[s++] & 0xff);

            // Encode the int into four chars
            dArr[d++] = CA[(i >>> 18) & 0x3f];
            dArr[d++] = CA[(i >>> 12) & 0x3f];
            dArr[d++] = CA[(i >>> 6) & 0x3f];
            dArr[d++] = CA[i & 0x3f];

            // Add optional line separator
            if (lineSep && ++cc == 19 && d < dLen - 2) {
                dArr[d++] = '\r';
                dArr[d++] = '\n';
                cc = 0;
            }
        }

        // Pad and encode last bits if source isn't even 24 bits.
        int left = sLen - eLen; // 0 - 2.
        if (left > 0) {
            // Prepare the int
            int i = ((sArr[eLen] & 0xff) << 10) | (left == 2 ? ((sArr[sLen - 1] & 0xff) << 2) : 0);

            // Set last four chars
            dArr[dLen - 4] = CA[i >> 12];
            dArr[dLen - 3] = CA[(i >>> 6) & 0x3f];
            dArr[dLen - 2] = left == 2 ? CA[i & 0x3f] : '=';
            dArr[dLen - 1] = '=';
        }
        return dArr;
    }

    public static String encodeToString(byte[] sArr, boolean lineSep) {
        // Reuse char[] since we can't create a String incrementally anyway and StringBuffer/Builder would be slower.
        return new String(encodeToChar(sArr, lineSep));
    }
}
