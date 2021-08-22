/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.utils;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;

public class JWTUtils {

    public static String decoded(String JWTEncoded) throws Exception {
        try {
            String[] split = JWTEncoded.split("\\.");
            String jwtHeader = getJson(split[0]);
            String jwtBody = getJson(split[1]);
            Log.d("JWT_DECODED", "Header: " + jwtHeader);
            Log.d("JWT_DECODED", "Body: " + jwtBody);
            return jwtBody;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getJson(String strEncoded) throws UnsupportedEncodingException {
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");
    }
}