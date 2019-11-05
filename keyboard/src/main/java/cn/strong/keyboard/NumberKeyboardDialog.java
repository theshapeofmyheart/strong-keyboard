package cn.strong.keyboard;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Method;


public class NumberKeyboardDialog extends Dialog implements NumberKeyboardListener{

    private NumberKeyboardView numberKeyboardView;

    private EditText editText;

    private int x = 500, y = 120;

    private TextView rangeTv , promptTv , titleTv;

    private Context context;

    private int code;

    private NumberKeyboardDgListener numberKeyboardDgListener;


    public NumberKeyboardDialog(@NonNull Context context) {
        super(context,R.style.Dialog);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.number_keyboard_dg);
        setWindow();
        initView();
    }

    private void initView(){
        numberKeyboardView = findViewById(R.id.number_keyboard_view);
        editText = findViewById(R.id.keyboard_dg_edt);
        rangeTv = findViewById(R.id.keyboard_range_tv);
        promptTv = findViewById(R.id.keyboard_prompt_tv);
        titleTv = findViewById(R.id.number_keyboard_title_tv);
        if (getWindow() != null){
            FuncUtil.setNoDefaultSoftKeyboard(getWindow() , editText);
        }
        numberKeyboardView.setEditText(editText);
        numberKeyboardView.setNumberKeyboardListener(this);
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


    public void setPosition(int x , int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public void doOk(double value) {
        cancel();
        if (numberKeyboardDgListener != null){
            numberKeyboardDgListener.result(value , code);
        }
    }


    @Override
    public void rangeError() {
        promptTv.setVisibility(View.VISIBLE);
    }

    @Override
    public void onKey() {
        if (promptTv.getVisibility() == View.VISIBLE){
            promptTv.setVisibility(View.INVISIBLE);
        }
    }


    public void openKeyboard(float min , float max ,
                             int decNumber , int code , String title){
        show();
        if (numberKeyboardView != null){
            numberKeyboardView.setParas(min , max ,  decNumber);
            titleTv.setText(title);
            rangeTv.setText(context.getResources().getString(R.string.range)+
                    (char)58 +min+" - "+max);
        }
        editText.setText("");
        this.code = code;
    }


    public void setNumberKeyboardDgListener(NumberKeyboardDgListener numberKeyboardDgListener) {
        this.numberKeyboardDgListener = numberKeyboardDgListener;
    }
}
