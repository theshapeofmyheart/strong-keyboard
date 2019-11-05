package cn.strong.keyboard;

import android.content.Context;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import java.lang.reflect.Method;

public class FuncUtil {

    public static void setNoDefaultSoftKeyboard(Window window , EditText editText){
        window.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        try {
            Method setShowSoftInputOnFocus;
            setShowSoftInputOnFocus = EditText.class.getMethod("setShowSoftInputOnFocus",
                    boolean.class);
            setShowSoftInputOnFocus.setAccessible(true);
            setShowSoftInputOnFocus.invoke(editText, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
