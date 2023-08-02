package webside.wuqingyuan.TTS;

import java.io.Serializable;

public class ETTS implements Serializable {

    /**
     * Name : Microsoft Server Speech Text to Speech Voice (af-ZA, AdriNeural)
     * ShortName : af-ZA-AdriNeural
     * Gender : Female
     * Locale : af-ZA
     * SuggestedCodec : audio-24khz-48kbitrate-mono-mp3
     * FriendlyName : Microsoft Adri Online (Natural) - Afrikaans (South Africa)
     * Status : GA
     * VoiceTag : {"ContentCategories":["General"],"VoicePersonalities":["Friendly","Positive"]}
     */

    private String Name;
    private String ShortName;
    private String Gender;
    private String Locale;
    private String SuggestedCodec;
    private String FriendlyName;
    private String Status;
    private VoiceTagBean VoiceTag;

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getShortName() {
        return ShortName;
    }

    public void setShortName(String ShortName) {
        this.ShortName = ShortName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public String getLocale() {
        return Locale;
    }

    public void setLocale(String Locale) {
        this.Locale = Locale;
    }

    public String getSuggestedCodec() {
        return SuggestedCodec;
    }

    public void setSuggestedCodec(String SuggestedCodec) {
        this.SuggestedCodec = SuggestedCodec;
    }

    public String getFriendlyName() {
        return FriendlyName;
    }

    public void setFriendlyName(String FriendlyName) {
        this.FriendlyName = FriendlyName;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public VoiceTagBean getVoiceTag() {
        return VoiceTag;
    }

    public void setVoiceTag(VoiceTagBean VoiceTag) {
        this.VoiceTag = VoiceTag;
    }
}
