<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>

<head>
    <title>Home</title>
    <meta charset="UTF-8">
</head>

<body>

	<%
		String contextPath = request.getContextPath().replace("/", "");
	%>

		<p> <%= contextPath %>  </p>
	<fmt:bundle basename="messages">
		<legend><fmt:message key="project"/></legend>
		<legend><fmt:message key="welcome"/></legend>
		<p>${sessionScope.clienteLogado.nome}</p>
		
		<a href="/<%= contextPath%>/consultas/agendar">Agende uma consulta</a>
			
		

    </fmt:bundle>
</body>

</html>