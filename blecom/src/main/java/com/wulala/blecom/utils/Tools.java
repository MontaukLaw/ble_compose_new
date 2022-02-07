package com.wulala.blecom.utils;

import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Locale;

public class Tools {

    private static final String TAG = Tools.class.getSimpleName();

    public static byte HexString2Byte(String str) {
        int it = Integer.parseInt(str, 16);
        // Log.d(TAG, "Hexadecimal String " + str);
        return ((Integer) it).byteValue();
    }


    public static Integer String2Int(String input) {
        if (input.equals("")) {
            return 0;
        } else {
            return Integer.parseInt(input);
        }
    }

    public static String StringNumber2HexString(String str) {
        if (str.length() > 0 && str.length() < 3) {
            String output = Integer.toHexString(Integer.parseInt(str));
            if (output.length() == 1) {
                return "0x0" + output;
            }
            return "0x" + output;
        }
        return "0x00";
    }

    public static byte DecString2Byte(String str) {
        if (str.length() > 3) {
            Log.d(TAG, "Wrong length String");
            return 0;
        } else if (str.length() == 0) {
            return 0;
        }
        int it = Integer.parseInt(str, 10);
        //  Log.d(TAG, "Hexadecimal String " + str);
        return ((Integer) it).byteValue();
    }

    public static byte getByteFromTV(final TextView textView) {
        return DecString2Byte(textView.getText().toString());
    }

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {

        char[] hexChars = new char[bytes.length * 3];

        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 3] = HEX_ARRAY[v >>> 4];
            hexChars[j * 3 + 1] = HEX_ARRAY[v & 0x0F];
            hexChars[j * 3 + 2] = ' ';
        }
        return new String(hexChars);
    }

    public static int comboUint(byte high, byte low) {
        return ((int) high & 0xff) << 8 | low & 0xff;
    }

    public static int comboInt(byte high, byte low) {
        return (int) (high << 8 | low & 0xff);
    }

    public final static int MAX_LINE = 5;

    public static byte String2Byte(String str) {
        //String str = "1D";
        int it = Integer.parseInt(str, 16);
        Log.d(TAG, "Hexadecimal String " + str);
        // Integer bigInt = it;

        // byte[] bytearray =bigInt.byteValue; (bigInt.toByteArray());
        // Log.d(TAG, "Byte Array : ");
        // for (int i = 0; i < bytearray.length; i++) {
        // Log.d(TAG, bytearray[i] + "\t");
        // }

        return ((Integer) it).byteValue();
    }

    public static String cutStringLineHead(List<String> stringList, String inputStr) {
        String rtn = "";
        if (stringList.size() > MAX_LINE) {
            for (int i = 0; i < (MAX_LINE - 1); i++) {
                stringList.set(i, stringList.get(i + 1));
            }
            stringList.set(MAX_LINE - 1, inputStr + "\r\n");
        } else {
            stringList.add(inputStr + "\r\n");
            Log.i(TAG, stringList.size() + "");
        }

        for (int i = 0; i < stringList.size(); i++) {
            rtn = rtn + stringList.get(i);
        }

        return rtn;
    }

    public static int combine2Byte(byte byteHigh, byte byteLow) {
        return ((int) byteHigh << 8) | (int) byteLow;
    }

    public static String byte2HexStr(byte b) {
        String charToHex = Integer.toHexString((char) b);
        if (charToHex.length() > 2) {
            charToHex = charToHex.substring(2);
        } else if (charToHex.length() == 1) {
            charToHex = "0" + charToHex;
        }
        return charToHex.toUpperCase(Locale.ROOT);
    }

    public static String bytes2HexString(Byte[] data) {
        String rtn = "";
        for (int i = 0; i < data.length; i++) {
            rtn = rtn + "0x" + byte2HexStr(data[i]) + " ";
        }
        return rtn;
    }
}
