package fr.ergol.externalkeyboard;

import android.inputmethodservice.InputMethodService;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputConnection;

/**
 * Minimal IME for Ergo'L: restores the "star key" (physically KEYCODE_O with
 * no modifier), which acts as a lock key to compose accented letters (Ergo'L
 * levels 5/6, table generated from ergol.xkb_symbols in AccentTable), in two
 * ways:
 *  - tap-then-release: arms composition for the next key only;
 *  - hold: while the key stays physically pressed, every key pressed is
 *    accented (like a classic modifier).
 * The rest of the layout (letters, Shift, AltGr, left Alt) continues to be
 * handled by res/raw/ergol.kcm at the system level, independently of the
 * active IME: this IME intercepts ONLY the star+letter sequence, everything
 * else is left to standard processing via super.onKeyDown().
 *
 * Replaces the former ErgolStarKeyService (AccessibilityService +
 * ACTION_SET_TEXT), which failed in apps with custom rendering (Termux,
 * games, code editors...) that don't expose a standard editable
 * accessibility node. An IME receives physical keyboard events as soon as a
 * field has an active InputConnection — which Termux provides via its
 * TerminalView (to support virtual keyboard input) — and inserts text via
 * InputConnection.commitText(), a mechanism far more universally supported
 * than accessibility actions.
 *
 * No virtual keyboard is ever shown: this IME is meant for exclusive use
 * with a physical keyboard (Sofle / other Ergo'L keyboard).
 *
 * Activation: Settings → Languages and input → Keyboards → enable "Ergo'L
 * (star key)", then select it as the active input method (keyboard icon in
 * the notification bar, or the usual IME-switch shortcut) when accent
 * composition is needed. While this IME is active, suggestions/autocorrect
 * from the usual IME (Gboard...) are unavailable: only one IME is active at
 * a time on Android, this is an accepted trade-off.
 */
public class ErgolInputMethodService extends InputMethodService {

    private static final String TAG = "ErgolInputMethodService";
    private static final long PENDING_TIMEOUT_MS = 3000; // cancels the wait if nothing follows

    private boolean pending = false;
    private boolean starHeld = false;
    private boolean usedWhileHeld = false;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable clearPending = () -> {
        pending = false;
        Log.d(TAG, "star key disarmed (timeout)");
    };

    private static boolean isStarKey(int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_O
                && !event.isShiftPressed()
                && !event.isAltPressed()
                && !event.isCtrlPressed()
                && !event.isMetaPressed();
    }

    @Override
    public boolean onEvaluateInputViewShown() {
        // never show a virtual keyboard: exclusive use with a physical keyboard
        return false;
    }

    @Override
    public View onCreateInputView() {
        // empty fallback view, in case the framework requires one despite
        // onEvaluateInputViewShown() == false
        View empty = new View(this);
        empty.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
        return empty;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isStarKey(keyCode, event)) {
            if (event.getRepeatCount() == 0) {
                starHeld = true;
                usedWhileHeld = false;
                pending = false;
                handler.removeCallbacks(clearPending);
                Log.d(TAG, "star key held");
            }
            return true; // consumed: types nothing
        }

        if (starHeld || pending) {
            String accented = AccentTable.lookup(keyCode, event.isShiftPressed());
            Log.d(TAG, "next key keyCode=" + keyCode
                    + " shift=" + event.isShiftPressed() + " accented=" + accented
                    + " starHeld=" + starHeld + " pending=" + pending);
            if (accented != null) {
                InputConnection ic = getCurrentInputConnection();
                if (ic != null) {
                    ic.commitText(accented, 1);
                    if (pending) {
                        pending = false;
                        handler.removeCallbacks(clearPending);
                    }
                    if (starHeld) {
                        usedWhileHeld = true;
                    }
                    return true; // consumed, replaced by the composed character
                }
                Log.w(TAG, "no active InputConnection, letting the key pass through");
            } else if (pending) {
                // plain tap with no accent found for this key: disarm
                pending = false;
                handler.removeCallbacks(clearPending);
            }
            // no accent defined, or no InputConnection: fall through to
            // standard processing (.kcm); if the star key is still held, it
            // stays active for the following keys
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (isStarKey(keyCode, event) && starHeld) {
            starHeld = false;
            if (!usedWhileHeld) {
                // released without composing a key: historic tap-then-release
                // behavior, arm the wait for the next key
                pending = true;
                handler.removeCallbacks(clearPending);
                handler.postDelayed(clearPending, PENDING_TIMEOUT_MS);
                Log.d(TAG, "star key released: armed for the next key");
            } else {
                Log.d(TAG, "star key released after use while held");
            }
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onFinishInput() {
        super.onFinishInput();
        pending = false;
        starHeld = false;
        usedWhileHeld = false;
        handler.removeCallbacks(clearPending);
    }
}
