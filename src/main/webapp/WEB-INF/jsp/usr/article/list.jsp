<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import = "com.example.demo.vo.Article"%>
<%@page import = "java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 목록</title>
</head>
<body>
    <h1>게시물 목록</h1>

    <a href="../home/main">메인 페이지로 </a>

    <table style="border-collapse: collapse; border-color: green;" border="1px">
        <thead>
            <tr>
                <th>번호</th>
                <th>날짜</th>
                <th>제목</th>
                <th>내용</th>
                <th>닉네임</th>
                <th>수정</th>
                <th>삭제</th>
            </tr>
        </thead>
        <tbody>
            <%
            List<Article> articles = (List<Article>) request.getAttribute("articles");
            for (Article article : articles) {
            %>
            <tr style="text-align: center;">
                <td><%= article.getId() %></td>
                <td><%= article.getRegDate() %></td>
                <td><%= article.getTitle() %></td>
                <td><%= article.getBody() %></td>
                <td><%= article.getNickName()%></td>
                <td><a href="modify?id=<%= article.getId() %>">수정</a></td>
                <td><a href="doDelete?id=<%= article.getId() %>">삭제</a></td>
            </tr>
            <%
            }
            %>
        </tbody>
    </table>
</body>
</html>