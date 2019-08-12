<%--
  Created by IntelliJ IDEA.
  User: Danish
  Date: 6/29/2019
  Time: 1:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="../layout/taglib.jsp" %>

<h1>Latest news from Java World:</h1>
<h3><spring:message code="label.welcome"/></h3>

<table class="table table-bordered table-hover table-striped">
    <thead>
        <tr>
            <th>Date</th>
            <th>Item</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${items}" var="item">
            <tr>
                <td>
                    <c:out value="${item.publishedDate}"/>
                    <br/>
                    <c:out value="${item.blog.name}"/>
                </td>
                <td>
                    <strong>
                        <a href='<c:out value="${item.link}" />' target="_blank">
                            <c:out value="${item.title}"/>
                        </a>
                    </strong>
                    <br/>
                    <c:out value="${item.description}"/>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>