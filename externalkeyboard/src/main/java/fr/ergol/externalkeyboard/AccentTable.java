package fr.ergol.externalkeyboard;

import android.view.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/** Generated from ergol.xkb_symbols (levels 5/6, OneDeadKey). */
final class AccentTable {
    private static final Map<Long, String> TABLE = new HashMap<>();
    private static long key(int keyCode, boolean shift) {
        return ((long) keyCode << 1) | (shift ? 1 : 0);
    }
    static {
        TABLE.put(key(KeyEvent.KEYCODE_1, false), "\u201e");
        TABLE.put(key(KeyEvent.KEYCODE_1, true), "\u201a");
        TABLE.put(key(KeyEvent.KEYCODE_2, false), "\u201c");
        TABLE.put(key(KeyEvent.KEYCODE_2, true), "\u2018");
        TABLE.put(key(KeyEvent.KEYCODE_3, false), "\u201d");
        TABLE.put(key(KeyEvent.KEYCODE_3, true), "\u2019");
        TABLE.put(key(KeyEvent.KEYCODE_4, false), "\u00a2");
        TABLE.put(key(KeyEvent.KEYCODE_5, false), "\u2030");
        TABLE.put(key(KeyEvent.KEYCODE_8, false), "\u00a7");
        TABLE.put(key(KeyEvent.KEYCODE_9, false), "\u00b6");
        TABLE.put(key(KeyEvent.KEYCODE_0, false), "\u00b0");
        TABLE.put(key(KeyEvent.KEYCODE_MINUS, false), "\u00f7");
        TABLE.put(key(KeyEvent.KEYCODE_MINUS, true), "\u2013");
        TABLE.put(key(KeyEvent.KEYCODE_EQUALS, false), "\u2260");
        TABLE.put(key(KeyEvent.KEYCODE_EQUALS, true), "\u00b1");
        TABLE.put(key(KeyEvent.KEYCODE_Q, false), "\u00e2");
        TABLE.put(key(KeyEvent.KEYCODE_Q, true), "\u00c2");
        TABLE.put(key(KeyEvent.KEYCODE_W, false), "\u00e7");
        TABLE.put(key(KeyEvent.KEYCODE_W, true), "\u00c7");
        TABLE.put(key(KeyEvent.KEYCODE_E, false), "\u0153");
        TABLE.put(key(KeyEvent.KEYCODE_E, true), "\u0152");
        TABLE.put(key(KeyEvent.KEYCODE_R, false), "\u00f4");
        TABLE.put(key(KeyEvent.KEYCODE_R, true), "\u00d4");
        TABLE.put(key(KeyEvent.KEYCODE_U, false), "\u00b5");
        TABLE.put(key(KeyEvent.KEYCODE_I, false), "\u005f");
        TABLE.put(key(KeyEvent.KEYCODE_I, true), "\u005f");
        TABLE.put(key(KeyEvent.KEYCODE_P, false), "\u00fb");
        TABLE.put(key(KeyEvent.KEYCODE_P, true), "\u00db");
        TABLE.put(key(KeyEvent.KEYCODE_A, false), "\u00e0");
        TABLE.put(key(KeyEvent.KEYCODE_A, true), "\u00c0");
        TABLE.put(key(KeyEvent.KEYCODE_S, false), "\u00e9");
        TABLE.put(key(KeyEvent.KEYCODE_S, true), "\u00c9");
        TABLE.put(key(KeyEvent.KEYCODE_D, false), "\u00e8");
        TABLE.put(key(KeyEvent.KEYCODE_D, true), "\u00c8");
        TABLE.put(key(KeyEvent.KEYCODE_F, false), "\u00ea");
        TABLE.put(key(KeyEvent.KEYCODE_F, true), "\u00ca");
        TABLE.put(key(KeyEvent.KEYCODE_G, false), "\u00f1");
        TABLE.put(key(KeyEvent.KEYCODE_G, true), "\u00d1");
        TABLE.put(key(KeyEvent.KEYCODE_H, false), "\u0028");
        TABLE.put(key(KeyEvent.KEYCODE_J, false), "\u0029");
        TABLE.put(key(KeyEvent.KEYCODE_K, false), "\u00ee");
        TABLE.put(key(KeyEvent.KEYCODE_K, true), "\u00ce");
        TABLE.put(key(KeyEvent.KEYCODE_L, false), "\u00ef");
        TABLE.put(key(KeyEvent.KEYCODE_L, true), "\u00cf");
        TABLE.put(key(KeyEvent.KEYCODE_SEMICOLON, false), "\u00f9");
        TABLE.put(key(KeyEvent.KEYCODE_SEMICOLON, true), "\u00d9");
        TABLE.put(key(KeyEvent.KEYCODE_Z, false), "\u00e6");
        TABLE.put(key(KeyEvent.KEYCODE_X, false), "\u00df");
        TABLE.put(key(KeyEvent.KEYCODE_X, true), "\u1e9e");
        TABLE.put(key(KeyEvent.KEYCODE_C, false), "\u2011");
        TABLE.put(key(KeyEvent.KEYCODE_C, true), "\u00bf");
        TABLE.put(key(KeyEvent.KEYCODE_V, false), "\u2013");
        TABLE.put(key(KeyEvent.KEYCODE_B, false), "\u2014");
        TABLE.put(key(KeyEvent.KEYCODE_N, false), "\u2026");
        TABLE.put(key(KeyEvent.KEYCODE_PERIOD, false), "\u00b7");
        TABLE.put(key(KeyEvent.KEYCODE_PERIOD, true), "\u2022");
    }
    /** @return the composed character, or null if this key has no Ergo'L accent. */
    static String lookup(int keyCode, boolean shift) {
        return TABLE.get(key(keyCode, shift));
    }
    private AccentTable() {}
}
