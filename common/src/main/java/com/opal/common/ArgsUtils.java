package com.opal.common;

import java.util.HashMap;
import java.util.Map;

public class ArgsUtils {

    private Map<String, String> result;


    public ArgsUtils(String[] args) {
        result = new HashMap<>();
        parse(args);
    }

    public Map<String, String> parse(String[] args) {
        if (args.length==0) {
            return null;
        }

        for (int i=0; i<args.length-1;) {
            switch (args[i]) {
                case "-i":
                    result.put("numberOfIterations", args[i+1]);
                    i+=2;
                    break;
                case "-w":
                    result.put("numberOfWorkers", args[i+1]);
                    i+=2;
                    break;
                case "-t":
                    result.put("timeToRun", args[i+1]);
                    i+=2;
                    break;
            }
        }

        return result;
    }

    public String getString(String key, String defVal) {
        if(!result.containsKey(key)) {
            return defVal;
        }

        return String.valueOf(result.get(key));
    }

    public int getInteger(String key, int defVal) {
        if(!result.containsKey(key)) {
            return defVal;
        }

        return Integer.parseInt(result.get(key));
    }

}
