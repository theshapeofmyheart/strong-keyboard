package cn.strong.keyboard;

public interface NumberKeyboardListener {

    void doOk(double value);

    void rangeError();

    void onKey();

    void cancel();

}
