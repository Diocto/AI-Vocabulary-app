package org.andoidtown.ai_vocabulary;

public class StandardDataManager {
    static public String getMemorizedStandard()
    {
        return "correct_answer_num > incorrect_answer_num";
    }
    static public String getNotMemorizedStandard()
    {
        return "correct_answer_num < incorrect_answer_num OR correct_answer_num = 0";
    }
    static public String getPerpectWordStandard()
    {
        return "correct_answer_num > 5";
    }
}
