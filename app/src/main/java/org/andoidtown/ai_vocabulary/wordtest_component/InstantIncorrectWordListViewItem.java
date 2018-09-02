package org.andoidtown.ai_vocabulary.wordtest_component;

public class InstantIncorrectWordListViewItem {
    private String word;
    private String meaning;
    public InstantIncorrectWordListViewItem(String word, String meaning)
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
