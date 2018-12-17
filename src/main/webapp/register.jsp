<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Register</title>
  <style>
    #page-wrapper {
      width: 100%;
      height: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
    }

    #register {
      text-align: center;
      animation-name: register-slide;
      animation-duration: 1s;
      animation-fill-mode: forwards;
    }

    #register-title {
      font-size: 3em;
      font-weight: bold;
      color: #556655;
    }

    #register-form input {
      width: 100%;
      height: 50px;
      margin: 10px 0 10px 0;
      border: none;
      border-bottom: 1px solid #000000;
      border-radius: 10px;
      background-color: #EEEEEE;
    }

    .btn-register {
      width: 100%;
      height: 40px;
      margin-top: 10px;
      border: none;
      border-radius: 10px;
      color: #FFFFFF;
      background-color: #555555;
    }

    @keyframes register-slide {
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
  <div id="register">
    <div id="register-title">
      <p>REGISTER</p>
    </div>
    <div>
      <form method="post" action="${pageContext.request.contextPath}/register" id="register-form">
        <input name="username" type="text" placeholder="Username">
        <input name="password" type="password" placeholder="Password">
        <input name="nickname" type="text" placeholder="Nickname">
        <button class="btn-register" type="submit">회원가입</button>
      </form>
    </div>
  </div>
</div>
</body>
</html>
