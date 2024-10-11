<!-- WebContent/login.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<style>
    .login-container {
        width: 300px;
        margin: 100px auto;
        border: 1px solid #999;
        padding: 20px;
        border-radius: 5px;
    }

    .login-container h2 {
        text-align: center;
    }

    .login-container input[type="text"],
    .login-container input[type="password"] {
        width: 100%;
        padding: 8px;
        margin: 8px 0;
        box-sizing: border-box;
    }

    .login-container input[type="submit"] {
        width: 100%;
        padding: 8px;
        background-color: #4CAF50;
        border: none;
        color: white;
        cursor: pointer;
    }

    .login-container input[type="submit"]:hover {
        background-color: #45a049;
    }

    .error {
        color: red;
        text-align: center;
    }
</style>
</head>
<body>
    <div class="login-container">
    <h3>'kimcs@example.com', 'password123'</h3>
        <h2>로그인</h2>
        <%
            String error = request.getParameter("error");
            if ("invalid".equals(error)) {
        %>
            <div class="error">잘못된 이메일 또는 비밀번호입니다.</div>
        <%
            }
        %>
        <form action="login" method="post">
            <label for="email">이메일:</label>
            <input type="text" id="email" name="email" required>

            <label for="password">비밀번호:</label>
            <input type="password" id="password" name="password" required>

            <input type="submit" value="로그인">
        </form>
    </div>
</body>
</html>
