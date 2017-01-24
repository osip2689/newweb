<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Города РФ</title>
</head>
<body>
<h1>Города РФ</h1>
<hr>
<form action="/abs" method="GET">
    <select name="filter" size="1">
        <option value="defaultFilter">Выберите фильтр</option>
        <option value="id">Номер</option>
        <option value="cityName">Название</option>
        <option value="region">Регион</option>
        <option value="popup">Население</option>
        <option value="found">Основан</option>
    </select>
    <input type="text" value="" name="mask">
    <input type="submit" value="Show"/>
</form>
<form action="/abs" method="GET">
    <table>
        <tr>
            <td><input type="submit" value="Edit" name="Edit"/></td>
            <td><input type="submit" value="Add" name="Add"/></td>
            <td><input type="submit" value="Delete" name="Delete"/></td>
        </tr>
    </table>
    <table border="1">
        <tr>
            <th></th>
            <th>Номер</th>
            <th>Название</th>
            <th>Регион</th>
            <th>Население</th>
            <th>Основан</th>
        </tr>
        <c:forEach var="city" items="${cities}">
            <tr>
                <td><input type="radio" name="check" value="${city.id}"></td>
                <td><c:out value="${city.id}"/></td>
                <td><c:out value="${city.cityName}"/></td>
                <td><c:out value="${city.region}"/></td>
                <td><c:out value="${city.popup}"/></td>
                <td><c:out value="${city.found}"/></td>
            </tr>
        </c:forEach>
    </table>
    <hr>
    <h2>Редактор</h2>
    <input type="text" value="${cityEdit.id}" disabled>
    <input type="text" value="${cityEdit.cityName}" name="cityName">
    <input type="text" value="${cityEdit.region}" name="region">
    <input type="text" value="${cityEdit.popup}" name="popup">
    <input type="text" value="${cityEdit.found}" name="found">
</form>
</body>
</html>
