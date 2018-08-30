package org.andoidtown.ai_vocabulary.word_listview_component;

public class WordListViewItem {
    public void setValue(String value) {
        this.value = value;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public void setCaNum(int caNum) {
        this.caNum = caNum;
    }

    public void setIncaNum(int incaNum) {
        this.incaNum = incaNum;
    }

    public String getValue() {
        return value;
    }

    public String getMeaning() {
        return meaning;
    }

    public int getCaNum() {
        return caNum;
    }

    public int getIncaNum() {
        return incaNum;
    }

    private String value;
    private String meaning;
    private int caNum;
    private int incaNum;


}
