<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Post Editor</title>
  <style>
    html, body {
      margin: 0;
      padding: 2em;
    }

    .editor-wrapper {
      display: flex;
      justify-content: center;
      margin: auto;
      width: 1280px;
    }

    .flex-wrapper {
      width: 100%;
    }

    .header {
      display: flex;
      align-items: center;
      justify-content: center;
      width: 100%;
      height: 200px;
      background-color: #E0E0EE;
    }

    .page-title {
      font-size: 3em;
      font-weight: bold;
    }

    .input-form span {
      box-sizing: border-box;
      width: 20%;
      height: 100%;
      display: flex;
      float: left;
      flex: 1;
      align-items: center;
      justify-content: center;
      font-size: 1.3em;
      font-weight: bold;
      border: none;
      border-bottom: 1px solid #AAAAAA;
      border-right: 1px solid #AAAAAA;
    }

    .input-form input {
      width: 80%;
      height: 50px;
      padding: 0;
      border: none;
      border-bottom: 1px solid #AAAAAA;
      font-weight: bold;
      font-size: 1.3em;
    }

    .input-title-wrapper {
      height: 50px;
      margin-top: 20px;
    }

    .input-content-wrapper {
      height: 300px;
      margin-bottom: 20px;
    }

    .input-form textarea {
      width: 80%;
      height: 300px;
      border: none;
      border-bottom: 1px solid #AAAAAA;
      resize: none;
      font-size: 1.3em;
    }

    .btn-submit {
      width: 50%;
      height: 50px;
      margin-left: 25%;
      margin-right: 25%;
      border: none;
      border-radius: 10px;
      font-weight: bold;
      font-size: 1.3em;
      color: #FFFFFF;
      background-color: #555555;
    }

    .btn-submit:hover {
      background-color: #333333;
    }

    @media screen and (max-width: 1280px) {
      .editor-wrapper {
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
  if (request.getSession().getAttribute("user") == null) {
    out.println("<script>alert('로그인을 먼저 해주세요.');location.href = '" + request.getContextPath() + "/login.jsp'</script>");
  }
%>

<div class="editor-wrapper">
  <div class="flex-wrapper">
    <div class="header">
      <span class="page-title">게시글 쓰기</span>
    </div>
    <div class="section">
      <form method="post" action="${pageContext.request.contextPath}/posts" class="input-form">
        <div class="input-title-wrapper">
          <span>제목</span>
          <input type="text" name="title" class="input-title" maxlength="255" placeholder="제목을 입력하세요">
        </div>
        <div class="input-content-wrapper">
          <span>내용</span>
          <textarea class="input-content" placeholder="내용을 입력하세요" name="content"></textarea>
        </div>
        <button type="submit" class="btn-submit">쓰기</button>
      </form>
    </div>
  </div>
</div>

</body>
</html>
