<%@ page import="me.mocha.minisns.model.dto.UserDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Mini SNS</title>
  <style>
    html, body {
      margin: 0;
      padding: 0;
      background: url("https://sugarchalet.files.wordpress.com/2012/11/blog-background1.jpg") no-repeat center fixed;
      background-size: cover;
    }

    a {
      text-decoration: none;
      color: #000000;
    }

    #navbar {
      position: fixed;
      display: flex;
      align-items: center;
      width: 100%;
      height: 50px;
      background-color: #AAAAAA;
    }

    .navbar-brand {
      margin-left: 30px;
      color: #333333;
      font-weight: bold;
      font-size: 1.5em;
    }

    .navbar-right {
      position: absolute;
      right: 30px;
    }

    .navbar-menu {
      margin-left: 20px;
      color: #555555;
      font-size: 1rem;
    }

    .navbar-menu:hover {
      animation-name: menu-hover;
      animation-duration: 200ms;
      animation-fill-mode: forwards;
    }

    #page-header {
      display: flex;
      align-items: center;
      justify-content: center;
      width: 100%;
      height: 100vh;
      background-color: rgba(0, 0, 0, 0.5);
    }

    .page-description {
      text-align: center;
    }
    
    .title {
      color: #FFFFFF;
      font-weight: bold;
      font-size: 3em
    }
    
    .description {
      color: #FFFFFF;
      font-style: 1em;
    }

    @keyframes menu-hover {
      from {
        font-size: 1rem;
      }
      to {
        font-size: 1.3rem;
      }
    }

  </style>
</head>
<body>
  <nav id="navbar">
    <div class="navbar-brand">
      <a href="${pageContext.request.contextPath}/">Mini SNS</a>
    </div>
    <div class="navbar-right">
      <%
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
      %>
      <a href="${pageContext.request.contextPath}/login.jsp" class="navbar-menu">login</a>
      <a href="${pageContext.request.contextPath}/register.jsp" class="navbar-menu">register</a>
      <%
        } else {
            out.println("<a href=\"#\" class=\"navbar-menu\">"+user.getNickname()+"님 환영합니다.</a>");
      %>
      <a href="${pageContext.request.contextPath}/logout" class="navbar-menu">logout</a>
      <%
        }
      %>
    </div>
  </nav>
  <header id="page-header">
    <div class="page-description">
      <p class="title">Mini SNS</p>
      <span class="description">"자바로 만든 작은 소셜 네트워크 서비스"</span>
    </div>
  </header>
</body>
</html>
