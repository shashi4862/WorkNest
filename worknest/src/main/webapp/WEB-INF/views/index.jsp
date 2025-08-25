<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
 <title>Login</title>
 <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
  <div class="card shadow-sm p-4 col-md-4 mx-auto">
    <h3 class="text-center mb-3">WorkNest Login</h3>
    <form action="login" method="post">
      <div class="mb-3">
        <label>Email</label>
        <input type="email" name="email" class="form-control"/>
      </div>
      <div class="mb-3">
        <label>Password</label>
        <input type="password" name="password" class="form-control"/>
      </div>
      <button class="btn btn-primary w-100">Login</button>
    </form>
    <a href="register.jsp" class="d-block text-center mt-3">New user? Register</a>
  </div>
</div>
</body>
</html>