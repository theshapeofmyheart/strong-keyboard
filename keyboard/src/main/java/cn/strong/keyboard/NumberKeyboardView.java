package cn.strong.keyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.util.AttributeSet;
import android.widget.EditText;


/**
 * Created by yefei on 2019/10/20.
 */

public class NumberKeyboardView extends KeyboardView implements KeyboardView.OnKeyboardActionListener {

    private EditText editText;

    private NumberKeyboardListener numberKeyboardListener;

    private float min , max;

    private int decNumber = 2;


    public NumberKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NumberKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        // 设置软键盘按键的布局
        Keyboard keyboard = new Keyboard(context, R.xml.number_key_board);
        setKeyboard(keyboard);
        setEnabled(true);
        setPreviewEnabled(false); // 设置按键没有点击放大镜显示的效果
        setOnKeyboardActionListener(this);
    }


    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        if (editText == null) {
            return;
        }
        if (numberKeyboardListener != null){
            numberKeyboardListener.onKey();
        }
        Editable editable = editText.getText();
        int start = editText.getSelectionStart();
        switch (primaryCode){
            case Keyboard.KEYCODE_DELETE:
                // 退格
                if (editable != null && editable.length() > 0) {
                    if (start > 0) {
                        editable.delete(start - 1, start);
                    }
                }
                break;
            case Keyboard.KEYCODE_DONE:
                // OK
                if (numberKeyboardListener != null){
                    if(editable.length() > 0){
                       double value = Double.valueOf(editable.toString());
                       if (value < min || value > max){
                           numberKeyboardListener.rangeError();
                           return;
                       }
                        numberKeyboardListener.doOk(value);
                    }else {
                        numberKeyboardListener.cancel();
                    }
                }
                break;
            case Keyboard.KEYCODE_CANCEL:
                // 清除
                if (editable != null){
                    editable.clear();
                }
                break;
            case 45:
                // -号
                if (editable != null && editable.length() == 0){
                    editable.insert(start , "-");
                }
                break;
            case 46:
                // .号
                if (decNumber > 0){
                    if (editable != null && editable.length() > 0 && !editable.toString().contains(".")){
                        editable.insert(start, ".");
                    }
                }
                break;
                default:
                    // 数字
                    if (editable != null){
                        if (editable.toString().contains(".") &&
                                editable.length() >= editable.toString().indexOf(".") + 1 + decNumber){
                            return;
                        }
                        editable.insert(start, Character.toString((char) primaryCode));
                    }
                    break;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Keyboard.Key key : getKeyboard().getKeys()) {
            if (key.codes[0] == Keyboard.KEYCODE_DONE) {
                // 删除ok按键背景
                drawKeyBackground(key, canvas, getResources().getColor(R.color.skr_blue));
            }
        }
    }

    /**
     * 绘制按键的背景
     */
    private void drawKeyBackground(Keyboard.Key key, Canvas canvas, int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setTextSize(20);
        paint.setStrokeWidth(3);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawRoundRect(key.x , key.y , key.x + key.width , key.y + key.height , 10 , 10 ,
                paint);
        paint.setColor(Color.WHITE);
        int baseLineY = (int) (key.y + key.height / 2 - paint.getFontMetrics().top / 2 -
                paint.getFontMetrics().bottom / 2);
        canvas.drawText("OK",key.x + key.width / 2,baseLineY,paint);
    }

    @Override
    public void onText(CharSequence text) {
    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }

    public EditText getEditText() {
        return editText;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public void setNumberKeyboardListener(NumberKeyboardListener numberKeyboardListener) {
        this.numberKeyboardListener = numberKeyboardListener;
    }

    public void setParas(float min , float max , int decNumber){
        this.min = min;
        this.max = max;
        this.decNumber = decNumber;
        if (decNumber < 0){
            decNumber = 0;
        } else if (decNumber > 2){
            decNumber = 2;
        }
    }
}
