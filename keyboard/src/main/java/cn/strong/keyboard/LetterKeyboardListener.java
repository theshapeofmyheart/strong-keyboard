package cn.strong.keyboard;

public interface LetterKeyboardListener {

    void doOk(String value);

    void rangeError();

    void cancel();

    void dismissError();
}
