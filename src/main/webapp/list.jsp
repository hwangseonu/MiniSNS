<%@ page import="java.util.List" %>
<%@ page import="me.mocha.blog.model.dto.PostDTO" %>
<%@ page import="me.mocha.blog.model.dto.UserDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Java Blog</title>
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
      min-height: 70vh;
      background-color: #FFF;
      padding: 50px 20vw 0 20vw;
    }

    .article {
      padding: 10px;
      min-height: 10vh;
      border-bottom: 1px solid #faba75;
    }

    .article-title {
      font-size: 2em;
      font-weight: bold;
    }

    .article-section {
      padding-top: 20px;
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
      out.println("<a href=\""+request.getContextPath()+"/posts?id="+post.getId()+"\">");
      out.println("<article class=\"article\">");
      out.println("<header class=\"article-header\">");
      out.println("<span class=\"article-title\">"+post.getTitle()+"</span>");
      out.println("</header>");
      out.println("<section class=\"article-section\">");
      out.println("<div>"+post.getContent()+"</div>");
      out.println("<section>");
      out.println("</article>");
      out.println("</a>");
    }
  %>
</section>
</body>
</html>
