package webside.wuqingyuan.TTS;

import java.io.Serializable;
import java.util.List;

public class VoiceTagBean implements Serializable {
    private List<String> ContentCategories;
    private List<String> VoicePersonalities;

    public List<String> getContentCategories() {
        return ContentCategories;
    }

    public void setContentCategories(List<String> ContentCategories) {
        this.ContentCategories = ContentCategories;
    }

    public List<String> getVoicePersonalities() {
        return VoicePersonalities;
    }

    public void setVoicePersonalities(List<String> VoicePersonalities) {
        this.VoicePersonalities = VoicePersonalities;
    }
}
