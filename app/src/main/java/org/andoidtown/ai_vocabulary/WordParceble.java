package org.andoidtown.ai_vocabulary;

import android.os.Parcel;
import android.os.Parcelable;

public class WordParceble implements Parcelable {
    public WordParceble(String word, String meaning)
    {
        this.word = word;
        this.meaning = meaning;
        this.correctNum = 0;
        this.incorrectNum = 0;
    }
    public WordParceble(Parcel in)
    {
        this.word = in.readString();
        this.meaning = in.readString();
        this.correctNum = in.readInt();
        this.incorrectNum = in.readInt();
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public int getCorrectNum() {
        return correctNum;
    }

    public void setCorrectNum(int correctNum) {
        this.correctNum = correctNum;
    }

    public int getIncorrectNum() {
        return incorrectNum;
    }

    public void setIncorrectNum(int incorrectNum) {
        this.incorrectNum = incorrectNum;
    }

    private String word;
    private String meaning;
    private int correctNum;
    private int incorrectNum;

    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator<WordParceble> CREATOR =
            new Parcelable.Creator<WordParceble>()
            {
                public WordParceble createFromParcel(Parcel in)
                {
                    return new WordParceble(in);
                }
                public WordParceble[] newArray(int size)
                {
                    return new WordParceble[size];
                }
            };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.word);
        parcel.writeString(this.meaning);
        parcel.writeInt(this.correctNum);
        parcel.writeInt(this.incorrectNum);
    }
}
