<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import= "project01.Question" contentType="text/html" pageEncoding="UTF-8"%>

<jsp:useBean id= "questions" class= "project01.Question" scope="session"></jsp:useBean>
<jsp:setProperty name="questions" property="*"/>

<%String radio = request.getParameter("radio");%>
<%String cbA = request.getParameter("check1");%>
<%String cbB = request.getParameter("check2");%>
<%String cbC = request.getParameter("check3");%>
<%String cbD = request.getParameter("check4");%>
<%String cbE = request.getParameter("check5");%>

<%questions.check(radio, cbA, cbB, cbC, cbD, cbE);%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Exercise38_19</title>
    </head>
    <body>
        <h3 id="h3style" style ="width: 500px auto; max-width: 620px; margin: 0 auto; color: white; font-family: Helvetica, sans-serif;  font-size: 100%; border-color: #6193cb; text-align: center;margin-bottom: 0.5em; background-color: #6193cb">Multiple-Choice Question: Chapter <%= questions.getChapterNo()%> Question<%= questions.getQuestionNo()%></h3>
        <table align="center">
            <tr>
                <td>
                    <fieldset>
                        <jsp:getProperty name="questions" property="questionNo" />. <jsp:getProperty name="questions" property="questionText" /><br/>
                        <c:choose>
                            <c:when test="${questions.getAnswerKey().length() < 2}">
                                <form method='post'>
                                    <c:if test="${not empty questions.getChoiceA()}">
                                        <INPUT TYPE="radio" name="command" value="0"/>
                                        <jsp:getProperty name="questions" property="choiceA" /><br>
                                    </c:if>
                                    <c:if test="${not empty questions.getChoiceB()}">
                                        <INPUT TYPE="radio" name="command" value="1"/>
                                        <jsp:getProperty name="questions" property="choiceB" /><br>
                                    </c:if>
                                    <c:if test="${not empty questions.getChoiceC()}">
                                        <INPUT TYPE="radio" name="command" value="2"/>
                                        <jsp:getProperty name="questions" property="choiceC" /><br>
                                    </c:if>
                                    <c:if test="${not empty questions.getChoiceD()}">
                                        <INPUT TYPE="radio" name="command" value="3"/>
                                        <jsp:getProperty name="questions" property="choiceD" /><br>
                                    </c:if>
                                    <c:if test="${not empty questions.getChoiceE()}">
                                        <INPUT TYPE="radio" name="command" value="4"/>
                                        <jsp:getProperty name="questions" property="choiceE" /><br>
                                    </c:if>
                                    <c:choose>
                                        <c:when test="${questions.isCorrect() == true}">
                                            <span style = "color: green"> Your answer is correct! </span><br>
                                            <div id = "a1" style = "color: green"> Click here to show an explanation</div>
                                            <script language='javascript'>$(document).ready(function() {$("#a1").click(function() {$(this).html("<div style = 'color: purple; font-family: Times New Roman;'>TEST</div>"); }); });</script> 
                                        </c:when>
                                        <c:otherwise>
                                            <span style = "color: red"> Your answer is incorrect! </span><br>
                                            <div id = "a2" style = "color: green"> Click here to show an explanation</div>
                                            <script language='javascript'>$(document).ready(function() {$("#a2").click(function() {$(this).html("<div style = 'color: purple; font-family: Times New Roman;'>TEST</div>"); }); });</script> 
                                        </c:otherwise>
                                    </c:choose>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <form method='post'>
                                    <c:if test="${not empty questions.getChoiceA()}">
                                        <INPUT TYPE="checkbox" name="command" value="0"/>
                                        <jsp:getProperty name="questions" property="choiceA" /><br>
                                    </c:if>
                                    <c:if test="${not empty questions.getChoiceB()}">
                                        <INPUT TYPE="checkbox" name="command" value="1"/>
                                        <jsp:getProperty name="questions" property="choiceB" /><br>
                                    </c:if>
                                    <c:if test="${not empty questions.getChoiceC()}">
                                        <INPUT TYPE="checkbox" name="command" value="2"/>
                                        <jsp:getProperty name="questions" property="choiceC" /><br>
                                    </c:if>
                                    <c:if test="${not empty questions.getChoiceD()}">
                                        <INPUT TYPE="checkbox" name="command" value="3"/>
                                        <jsp:getProperty name="questions" property="choiceD" /><br>
                                    </c:if>
                                    <c:if test="${not empty questions.getChoiceE()}">
                                        <INPUT TYPE="checkbox" name="command" value="4"/>
                                        <jsp:getProperty name="questions" property="choiceE" /><br>
                                    </c:if>
                                    <c:choose>
                                        <c:when test="${questions.isCorrect() == true}">
                                            <span style = "color: green"> Your answer is correct! </span><br>
                                            <div id = "a1" style = "color: green"> Click here to show an explanation</div>
                                            <script type="text/javascript">$(document).ready(function() {$("#a1").click(function() {$(this).html("<div style = 'color: purple; font-family: Times New Roman;'>TEST</div>"); }); });</script> 
                                        </c:when>
                                        <c:otherwise>
                                            <span style = "color: red"> Your answer is incorrect!</span><br>
                                            <div id = "a2" style = "color: green"> Click here to show an explanation</div>
                                            <script type="text/javascript">$(document).ready(function() {$("#a2").click(function() {$(this).html("<div style = 'color: purple; font-family: Times New Roman;'>TEST</div>"); }); });</script> 
                                        </c:otherwise>
                                    </c:choose>
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </fieldset>
                </td>
            </tr>
        </table>
    </body>
</html>
