    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
        <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
        <%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
        <!DOCTYPE html>
        <html>
        <head>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
        integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"
        integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
        crossorigin="anonymous"></script>
        <title>
        <tiles:getAsString name="title"/>
        </title>

        <style>
        .error{
        color: red;
        }
        </style>
        </head>
        <body>
        <%@taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
        <tilesx:useAttribute name="current"/>

        <nav class="navbar navbar-expand-md navbar-dark bg-dark mb-4">
        <a class="navbar-brand" href='<spring:url value="/index.html"/>'>Danny</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse"
        aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">
        <ul class="navbar-nav mr-auto">
        <li class="nav-item ${current == 'index' ? 'active' : ''}">
        <a class="nav-link" href='<spring:url value="/index.html"/>'>Home <span class="sr-only">(current)</span></a>
        </li>
        <security:authorize access="hasRole('ROLE_ADMIN')">
            <li class="nav-item ${current == 'users' ? 'active' : ''}">
            <a class="nav-link" href='
            <spring:url value="/users.html"/>
            '>Users</a>
            </li>
        </security:authorize>
        <security:authorize access="!isAuthenticated()">
            <li class="nav-item ${current == 'register' ? 'active' : ''}">
            <a class="nav-link" href='
            <spring:url value="/register.html"/>
            '>Register</a>
            </li>
            <li class="nav-item ${current == 'login' ? 'active' : ''}">
            <a class="nav-link" href='
            <spring:url value="/login.html"/>
            '>Login</a>
            </li>
        </security:authorize>
        <security:authorize access="isAuthenticated()">
            <li class="nav-item ${current == 'account' ? 'active' : ''}">
            <a class="nav-link" href='
            <spring:url value="/account.html"/>
            '>My Account</a>
            </li>
            <li class="nav-item">
            <form:form id="logout" action="/logout" method="post">
                <a class="nav-link" href=""
                onclick="document.getElementById('logout').submit();return false">
                Logout</a>
            </form:form>
            </li>
        </security:authorize>
        </ul>
        </div>
        </nav>
        <tiles:insertAttribute name="body"/>
        <br /><br />
        <div style="text-align: center;">
        <tiles:insertAttribute name="footer"/>
        </div>
        <!-- jQuery first, then Popper.js, then Bootstrap JS -->
        <%--        <script src="https://code.jquery.com/jquery-3.4.1.min.js"--%>
        <%--                integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="--%>
        <%--                crossorigin="anonymous"></script>--%>
        <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.1/dist/jquery.validate.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
        <script src='<c:url value="resources/js/custom.js"/>'></script>
        </body>
        </html>