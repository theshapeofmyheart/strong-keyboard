package cn.strong.keyboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import static android.inputmethodservice.Keyboard.KEYCODE_MODE_CHANGE;

public class LetterKeyboardView extends KeyboardView implements KeyboardView.OnKeyboardActionListener {

    private EditText editText;

    private LetterKeyboardListener letterKeyboardListener;

    private Paint paint;

    private boolean isUpperCase;

    private int maxLength;


    public LetterKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LetterKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        paint = new Paint();
        paint.setTextSize(20);
        paint.setStrokeWidth(3);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        // 设置软键盘按键的布局
        Keyboard keyboard = new Keyboard(context, R.xml.letter_key_board);
        setKeyboard(keyboard);
        setEnabled(true);
        setPreviewEnabled(false); // 设置按键没有点击放大镜显示的效果
        setOnKeyboardActionListener(this);
    }


    public EditText getEditText() {
        return editText;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Keyboard.Key key : getKeyboard().getKeys()) {
            if (key.codes[0] == Keyboard.KEYCODE_DONE) {
                drawKeyBackground(key, canvas, getResources().getColor(R.color.skr_blue), Color.WHITE,
                        "OK");
            }
            if (key.codes[0] == 32) {
                drawKeyBackground(key, canvas, Color.WHITE, Color.BLACK, null);
                drawSpaceIcon(key, canvas);
            }

            if (key.codes[0] >= 97 && key.codes[0] <= 122) {
                if (isUpperCase) {
                    drawLetter(key, canvas, Character.toString((char) (key.codes[0] - 32)));
                } else {
                    drawLetter(key, canvas, Character.toString((char) key.codes[0]));
                }
            }
        }
    }

    /**
     * 绘制按键的背景
     */
    private void drawKeyBackground(Keyboard.Key key, Canvas canvas, int bgColor, int textColor, String text) {
        paint.setColor(bgColor);
        paint.setStrokeWidth(3);
        canvas.drawRoundRect(key.x, key.y, key.x + key.width, key.y + key.height, 10, 10,
                paint);
        paint.setColor(textColor);
        if (text != null) {
            int baseLineY = (int) (key.y + key.height / 2 - paint.getFontMetrics().top / 2 -
                    paint.getFontMetrics().bottom / 2);
            canvas.drawText(text, key.x + key.width / 2, baseLineY, paint);
        }
    }

    private void drawSpaceIcon(Keyboard.Key key, Canvas canvas){
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(2);
        canvas.drawLine(key.x + key.width / 2 - 16 , key.y + key.height / 2 - 4 ,
                key.x + key.width / 2 - 16 ,key.y + key.height / 2 + 5 , paint);
        canvas.drawLine(key.x + key.width / 2 - 16 ,key.y + key.height / 2 + 4 ,
                key.x + key.width / 2 + 16 ,key.y + key.height / 2 + 4 , paint);
        canvas.drawLine(key.x + key.width / 2 + 16 ,key.y + key.height / 2 + 5 ,
                key.x + key.width / 2 + 16 ,key.y + key.height / 2 - 4 , paint);
    }


    private void drawLetter(Keyboard.Key key, Canvas canvas, String letter) {
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);
        int baseLineY = (int) (key.y + key.height / 2 - paint.getFontMetrics().top / 2 -
                paint.getFontMetrics().bottom / 2);
        canvas.drawText(letter, key.x + key.width / 2, baseLineY, paint);
    }


    @Override
    public void onPress(int i) {

    }

    @Override
    public void onRelease(int i) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        if (editText == null) {
            return;
        }
        Editable editable = editText.getText();
        int start = editText.getSelectionStart();
        switch (primaryCode) {
            case Keyboard.KEYCODE_CANCEL:
                // 清除
                if (editable != null){
                    editable.clear();
                    if (letterKeyboardListener != null){
                        letterKeyboardListener.dismissError();
                    }
                }
                break;
            case Keyboard.KEYCODE_MODE_CHANGE:
                isUpperCase = !isUpperCase;
                invalidate();
                break;
            case Keyboard.KEYCODE_DELETE:
                // 退格
                if (editable != null && editable.length() > 0) {
                    if (start > 0) {
                        editable.delete(start - 1, start);
                    }
                    if (letterKeyboardListener != null){
                        letterKeyboardListener.dismissError();
                    }
                }
                break;
            case Keyboard.KEYCODE_DONE:
                // OK
                if (letterKeyboardListener != null){
                    if(editable.length() > 0){
                        if (editable.length() <= maxLength){
                            letterKeyboardListener.doOk(editable.toString());
                        }
                    }else {
                        letterKeyboardListener.cancel();
                    }
                }
                break;
            default:
                // 数字
                if (editable != null){
                    if (editable.length() >= maxLength){
                        letterKeyboardListener.rangeError();
                    }else {
                        if (primaryCode >= 97 && primaryCode <= 122) {
                            if (isUpperCase){
                                editable.insert(start, Character.toString((char) (primaryCode - 32)));
                            }else {
                                editable.insert(start, Character.toString((char) primaryCode));
                            }
                        }else {
                            editable.insert(start, Character.toString((char) primaryCode));
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onText(CharSequence charSequence) {

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

    public void setLetterKeyboardListener(LetterKeyboardListener letterKeyboardListener) {
        this.letterKeyboardListener = letterKeyboardListener;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }
}
