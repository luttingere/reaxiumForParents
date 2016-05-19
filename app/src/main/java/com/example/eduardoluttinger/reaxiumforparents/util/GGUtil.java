package com.example.eduardoluttinger.reaxiumforparents.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.eduardoluttinger.reaxiumforparents.global.GGGlobalValues;

import java.util.Arrays;


/**
 * Created by Eduardo Luttinger at G&G on 13/04/2016.
 * <p/>
 * Utility methods for all android applications
 */
public class GGUtil {

    private static final String TAG = GGGlobalValues.TRACE_ID;

    /**
     * Navigate to any screen in the app
     *
     * @param context
     * @param arguments
     * @param screen
     */
    public static void goToScreen(Context context, Bundle arguments, Class screen) {
        Intent goToTheMain = new Intent(context, screen);
        if (arguments != null) {
            goToTheMain.putExtras(arguments);
        }
        context.startActivity(goToTheMain);
    }



    /**
     * validate if a number is even
     * @param number
     * @return
     */
    public static Boolean isEven(int number){
        Boolean isEven = Boolean.FALSE;
        if((number % 2) == 0){
            isEven = Boolean.TRUE;
        }
        return isEven;
    }


    /**
     * hide the softKeyboard
     *
     * @param context
     */
    public static void hideKeyboard(Context context) {
        View view = ((Activity) context).getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * Validate if the network connection is available
     *
     * @param context
     * @return TRUE or FALSE
     */
    public static boolean isNetworkAvailable(Context context) {
        boolean isNetworkAvailable = Boolean.FALSE;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            isNetworkAvailable = Boolean.TRUE;
        }
        return isNetworkAvailable;
    }


    /**
     * show a message as a toast
     *
     * @param context
     * @param message
     */
    public static void showAToast(Context context, Integer message) {
        Toast.makeText(context, context.getString(message), Toast.LENGTH_LONG).show();
    }

    /**
     * show a message as a toast
     *
     * @param context
     * @param message
     */
    public static void showAToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * Utility method to convert a byte array to a hexadecimal string.
     *
     * @param bytes Bytes to convert
     * @return String, containing hexadecimal representation.
     */
    public static String ByteArrayToHexString(byte[] bytes) {
        final char[] hexArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] hexChars = new char[bytes.length * 2]; // Each byte has two hex characters (nibbles)
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF; // Cast bytes[j] to int, treating as unsigned value
            hexChars[j * 2] = hexArray[v >>> 4]; // Select hex character from upper nibble
            hexChars[j * 2 + 1] = hexArray[v & 0x0F]; // Select hex character from lower nibble
        }
        return new String(hexChars);
    }


    /**
     * Utility method to convert a hexadecimal string to a byte string.
     * <p/>
     * <p>Behavior with input strings containing non-hexadecimal characters is undefined.
     *
     * @param s String containing hexadecimal characters to convert
     * @return Byte array generated from input
     * @throws IllegalArgumentException if input length is incorrect
     */
    public static byte[] HexStringToByteArray(String s) throws IllegalArgumentException {
        int len = s.length();
        if (len % 2 == 1) {
            throw new IllegalArgumentException("Hex string must have even number of characters");
        }
        byte[] data = new byte[len / 2]; // Allocate 1 byte per 2 hex characters
        for (int i = 0; i < len; i += 2) {
            // Convert each character into a integer (base-16), then bit-shift into place
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    /**
     * Utility method to concatenate two byte arrays.
     *
     * @param first First array
     * @param rest  Any remaining arrays
     * @return Concatenated copy of input arrays
     */
    public static byte[] ConcatArrays(byte[] first, byte[]... rest) {
        int totalLength = first.length;
        for (byte[] array : rest) {
            totalLength += array.length;
        }
        byte[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (byte[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }



}
