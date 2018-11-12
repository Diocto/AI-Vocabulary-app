package org.andoidtown.ai_vocabulary.Manager;

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
    static public String getCreateWordGroupTableSQL()
    {
        return "CREATE TABLE word_group (" +
                "group_name text PRIMARY KEY," +
                "registered_date datetime," +
                "num_of_test integer," +
                "next_test_date datetime)";
    }
    static public String getCreateWordTestTableSQL()
    {
        return "CREATE TABLE word_test (" +
                "test_date date," +
                "correct_answer_num integer," +
                "incorrect_answer_num integer," +
                "test_time datetime," +
                "group_name text" +
                ")";
    }
    static public String getCreateWordTableSQL()
    {
        return "create table word(" +
                "value text," +
                "meaning text," +
                "correct_answer_num integer," +
                "incorrect_answer_num integer," +
                "group_name text)";
    }
}
