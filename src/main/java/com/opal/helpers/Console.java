package com.opal.helpers;

public class Console {

    public static String getArgument(String[] args, int index, String defaultVal) {
        if(args.length > index) {
            return args[index];
        }

        return defaultVal;
    }

    public static int getInt(String[] args, int index, String defaultVal) {
        return Integer.parseInt(getArgument(args, index, defaultVal));
    }
}
