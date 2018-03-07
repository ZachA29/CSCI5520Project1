

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javax.servlet.ServletException;
import static project01.DBFields.getAnswerKey;
import static project01.DBFields.getChapterNo;
import static project01.DBFields.getChoiceA;
import static project01.DBFields.getChoiceB;
import static project01.DBFields.getChoiceC;
import static project01.DBFields.getChoiceD;
import static project01.DBFields.getChoiceE;
import static project01.DBFields.getHint;
import static project01.DBFields.getQuestionNo;
import static project01.DBFields.getQuestionText;
import static project01.DBFields.label;

public class TestParse {

    private PreparedStatement pstmt;
    private Connection conn;

    String a = "";
    String b = "";
    String c = "";
    String d = "";
    String e = "";
    String hint = "";
    String chapterNo = "";
    String questionNo = "";
    String question = "";
    String key = "";

    private void initializeJdbc() {
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded");

            // Establish a connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost/javabook", "scott", "tiger");
            System.out.println("Database connected");

            createTable();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void createTable() throws SQLException {
        Statement statement = conn.createStatement();
        statement.execute("create table if not exists intro11equiz (\n"
                + "  chapterNo int(11), \n"
                + "  questionNo int(11), \n"
                + "  questionText, \n"
                + "  choiceA varchar(1000),\n"
                + "  choiceB varchar(1000),\n"
                + "  choiceC varchar(1000),\n"
                + "  choiceD varchar(1000),\n"
                + "  choiceE varchar(1000),\n"
                + "  answerKey varchar(5),\n"
                + "  hint text\n"
                + ");");
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
            for (File child : directoryListing) 
                try (Stream<String> streams = Files.lines(Paths.get(child.toString()), Charset.forName("windows-1252"))
                        .filter(stream -> !stream.startsWith("Section"))
                        .filter(stream -> !stream.isEmpty())) {

                    streams.forEach((stream) -> {
                        parse(stream);
                    });
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

        if (stream.startsWith("Chapter") || stream.startsWith("chapter")) {
            chapterNo = stream;
            DBFields.setChapterNo(chapterNo);
        } else if (Character.isDigit(stream.charAt(0))) {
            DBFields.setQuestionNo(stream);
        } else if (stream.startsWith("A. ") || stream.startsWith("a. ")) {
            a = stream.substring(3);
            DBFields.setChoiceA(a);
        } else if (stream.startsWith("B. ") || stream.startsWith("b. ")) {
            b = stream.substring(3);
            DBFields.setChoiceB(b);
        } else if (stream.startsWith("C. ") || stream.startsWith("c. ")) {
            c = stream.substring(3);
            DBFields.setChoiceC(c);
        } else if (stream.startsWith("D. ") || stream.startsWith("d. ")) {
            d = stream.substring(3);
            DBFields.setChoiceD(d);
        } else if (stream.startsWith("E. ") || stream.startsWith("e. ")) {
            e = stream.substring(3);
            DBFields.setChoiceE(e);
        } else if (stream.startsWith("Key:") || stream.startsWith("key:")) {
            pattern = Pattern.compile("\\s");
            matcher = pattern.matcher(stream);
            found = matcher.find();

            if (found) {
                int whitespace = stream.indexOf(" ");
                hint = stream.substring(whitespace + 1); // start one index after the whitespace char
            } else {
                key = stream.substring(4);
                DBFields.setAnswerKey(key);

                hint = null;
                DBFields.setHint(hint);
            }
        } else if (stream.startsWith("#")) {
            insert();
        }
    }

    public void insert() {
        String query = "insert into intro11equiz (chapterNo, questionNo, questionText, choiceA, "
                + "choiceB, choiceC, choiceD, choiceE, answerKey, hint) values (?, ?, ?, ?, "
                + "?, ?, ?, ?, ?, ?);";

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
            DBFields.setLabel(label);

        } catch (SQLException ex) {
            ex.printStackTrace();
            label = "There was an error with that entry: " + ex.getMessage();
            DBFields.setLabel(label);
        }
    
}
}
