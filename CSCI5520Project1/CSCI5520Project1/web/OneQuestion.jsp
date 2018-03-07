<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import= "project01.Question" contentType="text/html" pageEncoding="UTF-8"%>

<jsp:useBean id= "questions" class= "project01.Question" scope="session"></jsp:useBean>
<jsp:setProperty name="questions" property="*"/>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Rapid Project 1</title>
    </head>
    <body>
        <h3 id="h3style" style ="width: 500px auto; max-width: 620px; margin: 0 auto; color: white; font-family: Helvetica, sans-serif;  font-size: 100%; border-color: #6193cb; text-align: center;margin-bottom: 0.5em; background-color: #6193cb">Multiple-Choice Question: Chapter <%= questions.getChapterNo()%> Question <%= questions.getQuestionNo()%></h3>
        <table align="center">
            <tr>
                <td>
                    <fieldset>
                        <jsp:getProperty name="questions" property="questionNo" />. <jsp:getProperty name="questions" property="questionText" /><br/>
                        <c:choose>
                            <c:when test="${questions.getAnswerKey().length() < 2}">
                                <form method='post' action="GradeOneQuestion.jsp">
                                    <c:if test="${not empty questions.getChoiceA()}">
                                        <INPUT TYPE="radio" name="radio" value="rbA"/>
                                        <jsp:getProperty name="questions" property="choiceA" /><br>
                                    </c:if>
                                    <c:if test="${not empty questions.getChoiceB()}">
                                        <INPUT TYPE="radio" name="radio" value="rbB"/>
                                        <jsp:getProperty name="questions" property="choiceB" /><br>
                                    </c:if>
                                    <c:if test="${not empty questions.getChoiceC()}">
                                        <INPUT TYPE="radio" name="radio" value="rbC"/>
                                        <jsp:getProperty name="questions" property="choiceC" /><br>
                                    </c:if>
                                    <c:if test="${not empty questions.getChoiceD()}">
                                        <INPUT TYPE="radio" name="radio" value="rbD"/>
                                        <jsp:getProperty name="questions" property="choiceD" /><br>
                                    </c:if>
                                    <c:if test="${not empty questions.getChoiceE()}">
                                        <INPUT TYPE="radio" name="radio" value="rbE"/>
                                        <jsp:getProperty name="questions" property="choiceE" /><br>
                                    </c:if>
                                    <tr align="left">
                                        <td>
                                            <button name='action' value='check' type='submit' style="margin-bottom: 0px; margin-top: 10px; margin-left: 5px;border: 0px; font-family: Helvetica, monospace; font-size: 85%;background-color: rgba(0, 128, 0, 0.7); border-radius: 0px; color:black;">Check My Answer</button>
                                        </td>
                                    </tr>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <form method='post' action="GradeOneQuestion.jsp">
                                    <c:if test="${not empty questions.getChoiceA()}">
                                        <INPUT TYPE="checkbox" name="check1" value="cbA"/>
                                        <jsp:getProperty name="questions" property="choiceA" /><br>
                                    </c:if>
                                    <c:if test="${not empty questions.getChoiceB()}">
                                        <INPUT TYPE="checkbox" name="check2" value="cbB"/>
                                        <jsp:getProperty name="questions" property="choiceB" /><br>
                                    </c:if>
                                    <c:if test="${not empty questions.getChoiceC()}">
                                        <INPUT TYPE="checkbox" name="check3" value="cbC"/>
                                        <jsp:getProperty name="questions" property="choiceC" /><br>
                                    </c:if>
                                    <c:if test="${not empty questions.getChoiceD()}">
                                        <INPUT TYPE="checkbox" name="check4" value="cbD"/>
                                        <jsp:getProperty name="questions" property="choiceD" /><br>
                                    </c:if>
                                    <c:if test="${not empty questions.getChoiceE()}">
                                        <INPUT TYPE="checkbox" name="check5" value="cbE"/>
                                        <jsp:getProperty name="questions" property="choiceE" /><br>
                                    </c:if>
                                    <tr align="left">
                                        <td>
                                            <button name='action' value='check' type='submit' style="margin-bottom: 0px; margin-top: 10px; margin-left: 5px;border: 0px; font-family: Helvetica, monospace; font-size: 85%;background-color: rgba(0, 128, 0, 0.7); border-radius: 0px; color:black;">Check My Answer</button>
                                        </td>
                                    </tr>
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </fieldset>
                </td>
            </tr>
        </table>
    </body>
</html>
