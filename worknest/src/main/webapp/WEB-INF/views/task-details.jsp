<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
 <title>Task Details</title>
 <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
  <h3>${task.title}</h3>
  <p>${task.description}</p>
  <p><b>Status:</b> ${task.status}</p>

  <form action="updateTaskStatus" method="post">
    <input type="hidden" name="id" value="${task.id}"/>
    <select name="status" class="form-select mb-2">
      <option>Pending</option>
      <option>In Progress</option>
      <option>Completed</option>
    </select>
    <button class="btn btn-success">Update Status</button>
  </form>

  <h4 class="mt-4">Comments</h4>
  <ul class="list-group">
    <c:forEach var="c" items="${task.comments}">
      <li class="list-group-item">
        <b>${c.user.name}</b>: ${c.content} <span class="text-muted small">${c.createdAt}</span>
      </li>
    </c:forEach>
  </ul>

  <form action="addComment" method="post" class="mt-3">
    <input type="hidden" name="taskId" value="${task.id}"/>
    <textarea name="content" class="form-control mb-2"></textarea>
    <button class="btn btn-primary">Add Comment</button>
  </form>
</div>
</body>
</html>