package com.sangam.connect.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class Hashing {
    public String hashPhnNo(String phnNo){
        try{
            MessageDigest messageDigest=MessageDigest.getInstance("SHA-256");
            byte [] hash= messageDigest.digest(phnNo.getBytes());
            StringBuilder sb=new StringBuilder();
            for(byte h: hash){
                sb.append(String.format("%02x",h));
            }
            String hashString=sb.toString();
            return hashString;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
