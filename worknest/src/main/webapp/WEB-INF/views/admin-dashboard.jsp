<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
 <title>Admin Dashboard</title>
 <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
  <h2>Admin Dashboard</h2>
  <a href="addUser.jsp" class="btn btn-success mb-3">Add User</a>
  <a href="addTask.jsp" class="btn btn-primary mb-3">Assign Task</a>

  <h4 class="mt-4">All Tasks</h4>
  <table class="table table-bordered">
    <thead>
      <tr><th>ID</th><th>Title</th><th>Status</th><th>User</th></tr>
    </thead>
    <tbody>
      <c:forEach var="t" items="${tasks}">
        <tr>
          <td>${t.id}</td>
          <td>${t.title}</td>
          <td>
            <span class="badge bg-secondary">${t.status}</span>
          </td>
          <td>${t.assignedUser.name}</td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</div>
</body>
</html>