<%@ page import="java.util.List" %>
<%@ page import="me.mocha.minisns.model.dto.PostDTO" %>
<%@ page import="me.mocha.minisns.model.dto.UserDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Post List</title>
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
      font-weight: bold;
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
      height: 500px;
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

    #page-section {
      width: 100%;
      min-height: 70vh;
      background-color: #FFF;
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
    <a href="${pageContext.request.contextPath}/">작은 블로그</a>
  </div>
  <div class="navbar-right">
    <%
      UserDTO user = (UserDTO) session.getAttribute("user");
      if (user == null) {
    %>
    <a href="${pageContext.request.contextPath}/login.jsp" class="navbar-menu">로그인</a>
    <a href="${pageContext.request.contextPath}/register.jsp" class="navbar-menu">회원가입</a>
    <%
    } else {
      out.println("<a href=\"#\" class=\"navbar-menu\">" + user.getNickname() + "님 환영합니다.</a>");
    %>
    <a href="${pageContext.request.contextPath}/logout" class="navbar-menu">로그아웃</a>
    <%
      }
    %>
  </div>
</nav>
<header id="page-header">
  <div class="page-description">
    <p class="title">작은 블로그</p>
  </div>
</header>
<section id="page-section">
  <%
    List posts = (List) request.getAttribute("posts");
    for (Object p : posts) {
      PostDTO post = (PostDTO) p;
    }
  %>
</section>
</body>
</html>
