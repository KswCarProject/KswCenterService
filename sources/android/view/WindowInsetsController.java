package android.view;

import android.view.WindowInsets;

public interface WindowInsetsController {
    void controlWindowInsetsAnimation(int i, WindowInsetsAnimationControlListener windowInsetsAnimationControlListener);

    void hide(int i);

    void show(int i);

    void controlInputMethodAnimation(WindowInsetsAnimationControlListener listener) {
        controlWindowInsetsAnimation(WindowInsets.Type.ime(), listener);
    }

    void showInputMethod() {
        show(WindowInsets.Type.ime());
    }

    void hideInputMethod() {
        hide(WindowInsets.Type.ime());
    }
}
