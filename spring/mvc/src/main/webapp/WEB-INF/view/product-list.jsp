<%@ page import="ru.stoliarenko.gb.spring.mvc.entity.Product" %>
<%@ page import="java.util.Collection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Product list</title>
</head>
<body>
    <ul>
        <% //noinspection unchecked
            for (Product product : (Collection<Product>)request.getAttribute("products")) { %>
        <li>
            <%=product.getTitle()%> - <%=product.getCost()%>
        </li>
        <% } %>
    </ul>
    <form method="post" action="${pageContext.request.contextPath}/product/create">
        <label for="title-input">Title</label>
        <input id="title-input" type="text" name="title" />

        <label for="cost-input">Cost</label>
        <input id="cost-input" type="number" name="cost" />

        <button type="submit">SAVE</button>
    </form>
</body>
</html>
