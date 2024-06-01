package settings;

import java.io.Serializable;

public class GlobalSetting implements Serializable {
    private boolean isUSSpeechFirst;

    // default settings
    public GlobalSetting() {
        setUSSpeechFirst();
    }


    public void setUSSpeechFirst() {
        this.isUSSpeechFirst = true;
    }
    public void setUKSpeechFirst() {
        this.isUSSpeechFirst = false;
    }
    public void showInfo() {
    }

    public boolean getPlayUSSpeechFirst() {
        return isUSSpeechFirst;
    }
}
