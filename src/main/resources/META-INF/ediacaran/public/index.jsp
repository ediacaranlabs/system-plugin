<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>
<html>
<body>
<h1>Plugin</h1>
<%=this.getClass().getClassLoader().toString()%>
<% Object o = new FileInputStream("C:/etc/autoblock/access.txt"); %>
</body>
</html>