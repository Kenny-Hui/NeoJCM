package com.lx862.jcm.mod.scripting.mtr.util;

import mtr.MTRClient;
import mtr.data.IGui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextUtil {
    private static final int SWITCH_LANG_DURATION = 60;

    public static String getCjkParts(String src) {
        return getCjkMatching(src, true);
    }

    public static String getNonCjkParts(String src) {
        return getCjkMatching(src, false);
    }

    public static String getExtraParts(String src) {
        return getExtraMatching(src, true);
    }

    public static String getNonExtraParts(String src) {
        return getExtraMatching(src, false);
    }

    public static String getNonCjkAndExtraParts(String src) {
        String extraParts = getExtraMatching(src, false).trim();
        String var10000 = getCjkMatching(src, false).trim();
        return var10000 + (extraParts.isEmpty() ? "" : "|" + extraParts);
    }

    public static boolean isCjk(String src) {
        return IGui.isCjk(src);
    }

    public static String cycleString(String mtrString) {
        return cycleString(mtrString, SWITCH_LANG_DURATION);
    }

    public static String cycleString(String mtrString, int switchDuration) {
        if(mtrString == null) return "";

        List<String> split = new ArrayList<>(Arrays.asList(mtrString.split("\\|")));
        if(split.isEmpty()) return "";

        return split.get(((int) MTRClient.getGameTick() / switchDuration) % split.size());
    }

    private static String getExtraMatching(String src, boolean extra) {
        return src.contains("||") ? src.split("\\|\\|", 2)[extra ? 1 : 0].trim() : "";
    }

    private static String getCjkMatching(String src, boolean isCJK) {
        if (src.contains("||")) {
            src = src.split("\\|\\|", 2)[0];
        }

        String[] stringSplit = src.split("\\|");
        StringBuilder result = new StringBuilder();

        for(String stringSplitPart : stringSplit) {
            if (IGui.isCjk(stringSplitPart) == isCJK) {
                if (result.length() > 0) {
                    result.append(' ');
                }

                result.append(stringSplitPart);
            }
        }

        return result.toString().trim();
    }
}