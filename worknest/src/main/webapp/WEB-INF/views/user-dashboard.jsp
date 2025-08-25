<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
 <title>User Dashboard</title>
 <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
  <h2>My Tasks</h2>
  <table class="table table-striped">
    <thead>
      <tr><th>Title</th><th>Status</th><th>Due</th><th>Action</th></tr>
    </thead>
    <tbody>
      <c:forEach var="t" items="${tasks}">
        <tr>
          <td>${t.title}</td>
          <td><span class="badge bg-info">${t.status}</span></td>
          <td>${t.dueDate}</td>
          <td><a href="taskDetails?id=${t.id}" class="btn btn-sm btn-outline-primary">View</a></td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</div>
</body>
</html>