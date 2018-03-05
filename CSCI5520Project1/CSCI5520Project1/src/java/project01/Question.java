
package project01;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Question {

    private PreparedStatement pstmt;
    private Connection conn;

    static String a = "";
    static String b = "";
    static String c = "";
    static String d = "";
    static String e = "";
    static String hit = "";
    static String chapter = "";
    static String question = "";
    static String questiontext = "";
    static String key = "";

    String chapterNo;
    String questionNo;
    String questionText;
    String choiceA;
    String choiceB;
    String choiceC;
    String choiceD;
    String choiceE;
    String answerKey;
    String hint;
    String label;
    String text = "";

    boolean crct = false;
    public boolean isCrct = false;

    public Question() throws SQLException {
        initializeJdbc();

    }

    /**
     * Initialize database connection
     */
    private void initializeJdbc() {
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded");

            // Establish a connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost/javabook", "scott", "tiger");
            System.out.println("Database connected");

            createQuizTable();
            createAnsTable();
            populateDB();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void createQuizTable() throws SQLException {
        Statement statement = conn.createStatement();
        statement.execute("drop table if exists intro11equiz;");
        statement.execute("create table if not exists intro11equiz (chapterNo "
                + "int(11), questionNo int(11), questionText text, choiceA "
                + "varchar(1000), choiceB varchar(1000), choiceC varchar(1000),"
                + " choiceD varchar(1000), choiceE varchar(1000), answerKey "
                + "varchar(5), hint text);");
    }

    private void createAnsTable() throws SQLException {
        Statement statement = conn.createStatement();
        statement.execute("drop table if exists intro11e;");

        statement.execute("create table if not exists intro11e (chapterNo "
                + "int(11), questionNo int(11), isCorrect bit(1) default 0, time "
                + "timestamp default current_timestamp, hostname varchar(100), answerA bit(1) "
                + "default 0, answerB bit(1) default 0, answerC bit(1) default 0, "
                + "answerD bit(1) default 0, answerE bit(1) default 0, username varchar(100));");
    }

    /**
     * Parse through the chapter files and populate the database
     */
    private void populateDB() {

        /**
         * Parse through files and collect data
         */
        String dirPath = "C:\\selftest\\selftest11e";

        File dir = new File(dirPath);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                //read file into stream, and parse through each line of each file
                try (Stream<String> streams = Files.lines(Paths.get(child.toString()), Charset.forName("windows-1252"))
                        .filter(stream -> !stream.startsWith("Section"))
                        .filter(stream -> !stream.isEmpty())) {

                    streams.forEach((stream) -> {
                        parse(stream);
                    });
                    insert();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("something broke");
        }
    }

    private void parse(String stream) {

        Pattern pattern;
        Matcher matcher;
        boolean found;

        if (stream.startsWith("Section")) {
            String trash = stream; // trash it
        } else if (stream.startsWith("Chapter") || stream.startsWith("chapter")) {
            chapterNo = stream.substring(8, 10);
            setChapterNo(chapterNo);
        } else if (Character.isDigit(stream.charAt(0))) {
            setQuestionNo(stream.substring(0, 2));
            text = stream.substring(3) + "<br/><br/>";
        } else if (stream.startsWith("A.") || stream.startsWith("a.")) {
            a = "A. " + stream.substring(3);
            setChoiceA(a);
        } else if (stream.startsWith("B.") || stream.startsWith("b.")) {
            b = "B. " + stream.substring(3);
            setChoiceB(b);
        } else if (stream.startsWith("C.") || stream.startsWith("c.")) {
            c = "C. " + stream.substring(3);
            setChoiceC(c);
        } else if (stream.startsWith("D.") || stream.startsWith("d.")) {
            d = "D. " + stream.substring(3);
            setChoiceD(d);
        } else if (stream.startsWith("E.") || stream.startsWith("e.")) {
            e = "E. " + stream.substring(3);
            setChoiceE(e);
        } else if (stream.startsWith("Key:") || stream.startsWith("key:")) {
            pattern = Pattern.compile("\\s");
            matcher = pattern.matcher(stream);
            found = matcher.find();

            if (found) {
                int whitespace = stream.indexOf(" ");
                hint = stream.substring(whitespace + 1); // start one index after the whitespace char
                key = stream.substring(4, 6);
                setHint(hint);
                setAnswerKey(key);
            } else {
                key = stream.substring(4);
                setAnswerKey(key);

                hint = null;
                setHint(hint);
            }
        } else if (stream.startsWith("#")) {
            setQuestionText(text);
            insert();
        } else {
            if (stream.contains("a.") || stream.contains("b.") || stream.contains("c.") || stream.contains("d.")) {
                String trash = stream;
            } else {
                text += stream + "<br/>";
            }
        }
    }

    public void insert() {
        String query = "insert ignore into intro11equiz values(?,?,?,?,?,?,?,?,?,?)";

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, getChapterNo());
            pstmt.setString(2, getQuestionNo());
            pstmt.setString(3, getQuestionText());
            pstmt.setString(4, getChoiceA());
            pstmt.setString(5, getChoiceB());
            pstmt.setString(6, getChoiceC());
            pstmt.setString(7, getChoiceD());
            pstmt.setString(8, getChoiceE());
            pstmt.setString(9, getAnswerKey());
            pstmt.setString(10, getHint());
            pstmt.executeUpdate();
            label = "Entry successfully inserted!";
            setLabel(label);

        } catch (SQLException ex) {
            ex.printStackTrace();
            label = "There was an error with that entry: " + ex.getMessage();
            setLabel(label);
        }
    }

    public String getChapterNo() {
        return chapterNo;
    }

    public void setChapterNo(String chapterNo) {
        this.chapterNo = chapterNo;
    }

    public String getQuestionNo() {
        return questionNo;
    }

    public void setQuestionNo(String questionNo) {
        this.questionNo = questionNo;
    }

    public String getQuestionText() {
        String queryString = "select questionText from intro11equiz where chapterNo = ? and questionNo = ?;";

        try {
            PreparedStatement pstate = conn.prepareStatement(queryString);
            pstate.setString(1, getChapterNo());
            pstate.setString(2, getQuestionNo());

            ResultSet rset = pstate.executeQuery();

            if (rset.next()) {
                questionText = rset.getString(1);
                if (questionText == null) {
                    setLabel("QUESTION OR CHAPTER DOES NOT EXIST");
                }
            }
        } catch (Exception ex) {

        }

        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getChoiceA() {
        String queryString = "select choiceA from intro11equiz where chapterNo = ? and questionNo = ?;";

        try {
            PreparedStatement pstate = conn.prepareStatement(queryString);
            pstate.setString(1, getChapterNo());
            pstate.setString(2, getQuestionNo());

            ResultSet rset = pstate.executeQuery();

            if (rset.next()) {
                choiceA = rset.getString(1);
            }
        } catch (Exception ex) {

        }

        return choiceA;
    }

    public void setChoiceA(String choiceA) {
        this.choiceA = choiceA;
    }

    public String getChoiceB() {
        String queryString = "select choiceB from intro11equiz where chapterNo = ? and questionNo = ?;";

        try {
            PreparedStatement pstate = conn.prepareStatement(queryString);
            pstate.setString(1, getChapterNo());
            pstate.setString(2, getQuestionNo());

            ResultSet rset = pstate.executeQuery();

            if (rset.next()) {
                choiceB = rset.getString(1);
            }
        } catch (Exception ex) {

        }

        return choiceB;
    }

    public void setChoiceB(String choiceB) {
        this.choiceB = choiceB;
    }

    public String getChoiceC() {
        String queryString = "select choiceC from intro11equiz where chapterNo = ? and questionNo = ?;";

        try {
            PreparedStatement pstate = conn.prepareStatement(queryString);
            pstate.setString(1, getChapterNo());
            pstate.setString(2, getQuestionNo());

            ResultSet rset = pstate.executeQuery();

            if (rset.next()) {
                choiceC = rset.getString(1);
            }
        } catch (Exception ex) {

        }

        return choiceC;
    }

    public void setChoiceC(String choiceC) {
        this.choiceC = choiceC;
    }

    public String getChoiceD() {
        String queryString = "select choiceD from intro11equiz where chapterNo = ? and questionNo = ?;";

        try {
            PreparedStatement pstate = conn.prepareStatement(queryString);
            pstate.setString(1, getChapterNo());
            pstate.setString(2, getQuestionNo());

            ResultSet rset = pstate.executeQuery();

            if (rset.next()) {
                choiceD = rset.getString(1);
            }
        } catch (Exception ex) {

        }

        return choiceD;
    }

    public void setChoiceD(String choiceD) {
        this.choiceD = choiceD;
    }

    public String getChoiceE() {
        String queryString = "select choiceE from intro11equiz where chapterNo = ? and questionNo = ?;";

        try {
            PreparedStatement pstate = conn.prepareStatement(queryString);
            pstate.setString(1, getChapterNo());
            pstate.setString(2, getQuestionNo());

            ResultSet rset = pstate.executeQuery();

            if (rset.next()) {
                choiceE = rset.getString(1);
            }
        } catch (Exception ex) {

        }

        return choiceE;
    }

    public void setChoiceE(String choiceE) {
        this.choiceE = choiceE;
    }

    public String getAnswerKey() {
        String queryString = "select answerKey from intro11equiz where chapterNo = ? and questionNo = ?;";

        try {
            PreparedStatement pstate = conn.prepareStatement(queryString);
            pstate.setString(1, getChapterNo());
            pstate.setString(2, getQuestionNo());

            ResultSet rset = pstate.executeQuery();

            if (rset.next()) {
                answerKey = rset.getString(1);
            }
        } catch (Exception ex) {

        }

        return answerKey;
    }

    public void setAnswerKey(String answerKey) {
        this.answerKey = answerKey;
    }

    public String getHint() {
        String queryString = "select hint from intro11equiz where chapterNo = ? and questionNo = ?;";

        try {
            PreparedStatement pstate = conn.prepareStatement(queryString);
            pstate.setString(1, getChapterNo());
            pstate.setString(2, getQuestionNo());

            ResultSet rset = pstate.executeQuery();

            if (rset.next()) {
                hint = rset.getString(1);
            }
        } catch (Exception ex) {

        }

        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void insertInfo(boolean correct, boolean aint, boolean bint, boolean cint, boolean dint, boolean eint) {
        String query = "insert ignore into intro11e values(?,?,?,?,?,?,?,?,?,?,?)";

        try {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, getChapterNo());
            pstmt.setString(2, getQuestionNo());
            pstmt.setBoolean(3, correct);
            pstmt.setTimestamp(4, timestamp);
            pstmt.setString(5, "HOSTNAME");
            pstmt.setBoolean(6, aint);
            pstmt.setBoolean(7, bint);
            pstmt.setBoolean(8, cint);
            pstmt.setBoolean(9, dint);
            pstmt.setBoolean(10, eint);
            pstmt.setString(11, "USERNAME");
            pstmt.executeUpdate();
            label = getAnswerKey() + "    " + correct;
            setLabel(label);

        } catch (SQLException ex) {
            ex.printStackTrace();
            label = "There was an error with that entry: " + ex.getMessage();
            setLabel(label);
        }
    }

    public void check(String radio, String cbA, String cbB, String cbC, String cbD, String cbE) {
        String checker = "";
        boolean aint = false;
        boolean bint = false;
        boolean cint = false;
        boolean dint = false;
        boolean eint = false;
        boolean correct = false;

        try {
            if (radio != null) {
                if (radio.equals("rbA")) {
                    aint = true;
                    if (getAnswerKey().contains("a") || getAnswerKey().contains("A")) {
                        correct = true;
                        isCrct = true;
                    }
                }
                if (radio.equals("rbB")) {
                    bint = true;
                    if (getAnswerKey().contains("b") || getAnswerKey().contains("B")) {
                        correct = true;
                        isCrct = true;
                    }
                }
                if (radio.equals("rbC")) {
                    cint = true;
                    if (getAnswerKey().contains("c") || getAnswerKey().contains("C")) {
                        correct = true;
                        isCrct = true;
                    }
                }
                if (radio.equals("rbD")) {
                    dint = true;
                    if (getAnswerKey().contains("D") || getAnswerKey().contains("d")) {
                        correct = true;
                        isCrct = true;
                    }
                }
                if (radio.equals("rbE")) {
                    eint = true;
                    if (getAnswerKey().contains("E") || getAnswerKey().contains("e")) {
                        correct = true;
                        isCrct = true;
                    }
                }
            }
            if (radio == null) {
                if (cbA != null) {
                    if (cbA.equals("cbA")) {
                        aint = true;
                        checker += "a";
                    }
                }
                if (cbB != null) {
                    if (cbB.equals("cbB")) {
                        bint = true;
                        checker += "b";
                    }
                }
                if (cbC != null) {
                    if (cbC.equals("cbC")) {
                        cint = true;
                        checker += "c";
                    }
                }
                if (cbD != null) {
                    if (cbD.equals("cbD")) {
                        dint = true;
                        checker += "d";
                    }
                }
                if (cbE != null) {
                    if (cbE.equals("cbE")) {
                        eint = true;
                        checker += "e";
                    }
                }
                System.out.println(aint);
                System.out.println(bint);
                System.out.println(cint);
                System.out.println(dint);
                System.out.println(eint);
                System.out.println(correct);
                if (checker.matches(getAnswerKey())) {
                    correct = true;
                    isCrct = true;
                }
            }

            insertInfo(correct, aint, bint, cint, dint, eint);
        } catch (Exception e) {

        }
    }

    public boolean isCorrect() {
        return isCrct;
    }
    
    public void setFalse() {
        isCrct = false;
    }
}
