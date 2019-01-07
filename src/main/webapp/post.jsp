<%@ page import="me.mocha.blog.model.dto.UserDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title><%=request.getAttribute("title")%>
  </title>
  <style>
    html, body {
      margin: 0;
      padding: 0;
    }

    .page-wrapper {
      margin: 50px auto auto;
      width: 768px;
    }

    .page-header {
      display: flex;
      align-items: center;
      word-break: break-word;
      border-bottom: 1px solid #faba75;
      padding-bottom: 10px;
    }

    .header-wrapper {
      width: 100%;
    }

    .post-title {
      font-weight: bold;
      font-size: 2em;
      margin-bottom: 30px;
      display: block;
    }

    .post-description {
      font-size: 1em;
      color: #818184;
      display: flex;
    }

    .username {
      display: block;
      flex: 0 0 auto;
    }

    .views {
      display: block;
      margin-left: auto;
    }

    .page-section {
      margin-top: 10px;
      margin-bottom: 50px;
    }

    .btn-delete {
      margin-left: 25%;
      margin-right: 25%;
      width: 50%;
      height: 50px;
      border: none;
      border-radius: 10px;
      background-color: #e10a21;
      font-size: 1.3em;
      font-weight: bold;
      color: #FFF;
    }

    .btn-delete:hover {
      background-color: #ac0010;
    }

    @media screen and (max-width: 768px) {
      .page-wrapper {
        width: 100%;
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
<div class="page-wrapper">
  <div class="page-header">
    <div class="header-wrapper">
      <span class="post-title"><%=request.getAttribute("title")%></span>
      <div class="post-description">
        <span class="username">글쓴이: <%=request.getAttribute("username")%></span>
        <span class="views">조회수: <%=request.getAttribute("views")%>회</span>
      </div>
    </div>
  </div>
  <div class="page-section">
    <div><%=request.getAttribute("content")%>
    </div>
  </div>
  <div class="page-footer">
    <form action="${pageContext.request.contextPath}/posts/delete" method="post">
      <input name="id" value="<%=request.getAttribute("id")%>" hidden="hidden">
      <%
        UserDTO user = (UserDTO) request.getSession().getAttribute("user");
        if (user != null) {
          if (request.getAttribute("username").equals(user.getUsername())) {
            out.println("<button class=\"btn-delete\" onClick=\"onDeleteClick()\">삭제</button>");
          }
        }
      %>
    </form>
  </div>
</div>
</body>
</html>
