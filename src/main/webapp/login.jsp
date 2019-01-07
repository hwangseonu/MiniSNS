<%@ page import="me.mocha.blog.model.dto.UserDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Login</title>
  <style>
    #page-wrapper {
      width: 100%;
      height: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
    }

    #login {
      text-align: center;
      animation-name: login-slide;
      animation-duration: 1s;
      animation-fill-mode: forwards;
    }

    #login-title {
      font-size: 3em;
      font-weight: bold;
      color: #556655;
    }

    #login-form input {
      width: 100%;
      height: 50px;
      margin: 10px 0 10px 0;
      border: none;
      border-bottom: 1px solid #000000;
      border-radius: 10px;
      background-color: #EEEEEE;
    }

    .btn-login {
      width: 100%;
      height: 40px;
      margin-top: 10px;
      border: none;
      border-radius: 10px;
      color: #FFFFFF;
      background-color: #555555;
    }

    .btn-login:hover {
      background-color: #333333;
    }

    @keyframes login-slide {
      from {
        margin-top: -500%;
      }
      to {
        margin-top: -20%;
      }
    }

  </style>
</head>
<body>
<%
  String msg = (String) request.getAttribute("message");
  if (msg != null) {
      out.println("<script>alert('" + msg + "')</script>");
  }
%>

<div id="page-wrapper">
  <div id="login">
    <div id="login-title">
      <p>LOGIN</p>
    </div>
    <div>
      <form method="post" action="${pageContext.request.contextPath}/login" id="login-form">
        <input name="username" type="text" placeholder="Username">
        <input name="password" type="password" placeholder="Password">
        <button class="btn-login" type="submit">로그인</button>
      </form>
    </div>
  </div>
</div>
</body>
</html>
