<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>meal list</title>
    <style>
        .normal {
            color: green;
        }
        .exceeded {
            color: red;
        }
    </style>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>meal list</h2>


<table border="1" cellpadding="8" cellspacing="0">
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    </thead>
    <c:forEach items="${mealList}" var="meal">
    <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
        <c:set var="cleanedDateTime" value="${fn:replace(meal.dateTime, 'T', ' ')}" />
        <fmt:parseDate value="${cleanedDateTime}" pattern="yyyy-MM-dd HH:mm"
                       var="parsedDate" type="date" />

        <fmt:formatDate value="${parsedDate}" var="stdDatum"
                        type="date" pattern="yyyy-MM-dd HH:mm" />
        <td>${stdDatum}</td>
        <td>${meal.description}</td>
        <td>${meal.calories}</td>
    </tr>
    </c:forEach>
</body>
</html>