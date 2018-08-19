package org.andoidtown.ai_vocabulary.wordtestcomponent;

public class IncorrectWordListViewItem {
    private String word;
    private String meaning;
    public IncorrectWordListViewItem(String word, String meaning)
    {
        this.word = word;
        this.meaning = meaning;
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
}
