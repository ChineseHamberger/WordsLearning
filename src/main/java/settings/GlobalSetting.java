package settings;

import java.io.Serializable;

public class GlobalSetting implements Serializable {
    private boolean isUSSpeechFirst;
    private boolean isDarkTheme;
    private boolean fullScreen;

    // default settings
    public GlobalSetting() {
        setUSSpeechFirst(true);
        setDarkTheme(true);
        setFullScreen(true);
    }


    public void setUSSpeechFirst(boolean USSpeechFirst) {
        this.isUSSpeechFirst = USSpeechFirst;
    }
    public void setUKSpeechFirst(boolean UKSpeechFirst) {
        this.isUSSpeechFirst = !UKSpeechFirst;
    }

    public void setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
    }



    public void showInfo() {
    }

    public boolean getPlayUSSpeechFirst() {
        return isUSSpeechFirst;
    }
    public void setDarkTheme(boolean isDarkTheme){
        this.isDarkTheme = isDarkTheme;
    }
    public boolean getDarkTheme(){
        return isDarkTheme;
    }
    public boolean getFullScreen() {
        return fullScreen;
    }
}
