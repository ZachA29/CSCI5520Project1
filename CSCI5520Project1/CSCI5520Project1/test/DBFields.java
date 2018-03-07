


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBFields {
    static String chapterNo;
    static String questionNo;
    static String questionText;
    static String choiceA;
    static String choiceB;
    static String choiceC;
    static String choiceD;
    static String choiceE;
    static String answerKey;
    static String hint;
    static String label;

    public DBFields() {
        chapterNo = questionNo = questionText = choiceA = choiceB = choiceC = choiceD = choiceE = answerKey = hint = label = "";
    }

    public static String getChapterNo() {
        return chapterNo;
    }

    public static void setChapterNo(String chapterNo) {
        DBFields.chapterNo = chapterNo;
    }

    public static String getQuestionNo() {
        return questionNo;
    }

    public static void setQuestionNo(String questionNo) {
        DBFields.questionNo = questionNo;
    }

    public static String getQuestionText() {
        return questionText;
    }

    public static void setQuestionText(String questionText) {
        DBFields.questionText = questionText;
    }

    public static String getChoiceA() {
        return choiceA;
    }

    public static void setChoiceA(String choiceA) {
        DBFields.choiceA = choiceA;
    }

    public static String getChoiceB() {
        return choiceB;
    }

    public static void setChoiceB(String choiceB) {
        DBFields.choiceB = choiceB;
    }

    public static String getChoiceC() {
        return choiceC;
    }

    public static void setChoiceC(String choiceC) {
        DBFields.choiceC = choiceC;
    }

    public static String getChoiceD() {
        return choiceD;
    }

    public static void setChoiceD(String choiceD) {
        DBFields.choiceD = choiceD;
    }

    public static String getChoiceE() {
        return choiceE;
    }

    public static void setChoiceE(String choiceE) {
        DBFields.choiceE = choiceE;
    }

    public static String getAnswerKey() {
        return answerKey;
    }

    public static void setAnswerKey(String answerKey) {
        DBFields.answerKey = answerKey;
    }

    public static String getHint() {
        return hint;
    }

    public static void setHint(String hint) {
        DBFields.hint = hint;
    }
    
    public String getLabel() {
        return label;
    }
    
    public static void setLabel(String label) {
        DBFields.label = label;
    }

}
