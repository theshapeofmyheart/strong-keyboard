package cn.strong.keyboard;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class LetterKeyboardDialog extends Dialog implements LetterKeyboardListener{

    private LetterKeyboardView letterKeyboardView;

    private EditText editText;

    private TextView promptTv , titleTv;

    private int x = 100, y = 100;

    private int code;

    private ImageView pswIv;

    private boolean isPsw;

    private LetterKeyboardDgListener letterKeyboardDgListener;



    public LetterKeyboardDialog(@NonNull Context context) {
        super(context,R.style.Dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.letter_keyboard_dg);
        setWindow();
        initView();

    }

    private void setWindow(){
        Window window = getWindow();
        if (window != null){
            WindowManager.LayoutParams p = getWindow().getAttributes();               //获取对话框当前的参数值
            p.gravity = Gravity.START | Gravity.TOP;
            p.x = x;
            p.y = y;
            window.setAttributes(p);
        }
    }

    private void initView(){
        letterKeyboardView = findViewById(R.id.letter_keyboard_view);
        editText = findViewById(R.id.letter_keyboard_dg_edt);
        promptTv = findViewById(R.id.letter_keyboard_prompt_tv);
        titleTv = findViewById(R.id.letter_keyboard_title_tv);
        pswIv = findViewById(R.id.letter_keyboard_psw_iv);
        if (getWindow() != null){
            FuncUtil.setNoDefaultSoftKeyboard(getWindow() , editText);
        }
        letterKeyboardView.setEditText(editText);
        letterKeyboardView.setLetterKeyboardListener(this);
        pswIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPsw){
                    editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    pswIv.setImageResource(R.drawable.psw_visible);
                }else {
                    editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    pswIv.setImageResource(R.drawable.psw_invisible);
                }
                editText.setSelection(editText.getText().toString().length());
                isPsw = !isPsw;
            }
        });
    }


    @Override
    public void doOk(String value) {
        cancel();
        if (letterKeyboardDgListener != null){
            letterKeyboardDgListener.result(value , code);
        }
    }

    @Override
    public void rangeError() {
        promptTv.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissError() {
        promptTv.setVisibility(View.INVISIBLE);
    }


    public void setPosition(int x , int y){
        this.x = x;
        this.y = y;
    }

    /**
     * 打开键盘
     * @param maxLength
     * @param code
     * @param title
     */
    public void openKeyboard(int maxLength , int code , String title , boolean isPassword){
        show();
        titleTv.setText(title);
        letterKeyboardView.setMaxLength(maxLength);
        this.isPsw = isPassword;
        if (isPassword){
            pswIv.setVisibility(View.VISIBLE);
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }else {
            pswIv.setVisibility(View.GONE);
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        editText.setText("");
        promptTv.setVisibility(View.INVISIBLE);
        this.code = code;
    }


    public void setLetterKeyboardDgListener(LetterKeyboardDgListener letterKeyboardDgListener) {
        this.letterKeyboardDgListener = letterKeyboardDgListener;
    }
}
