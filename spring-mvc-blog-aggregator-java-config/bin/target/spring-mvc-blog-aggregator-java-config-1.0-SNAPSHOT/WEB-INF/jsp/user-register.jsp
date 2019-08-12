<%--
  Created by IntelliJ IDEA.
  User: Danish
  Date: 6/29/2019
  Time: 1:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@include file="../layout/taglib.jsp" %>

<%--commandName is before Spring 5--%>
<%--<form:form commandName="user" cssClass="form-horizontal">--%>
<form:form modelAttribute="user" cssClass="form-horizontal registrationForm">

    <c:if test="${success eq true}">
        <div class="alert alert-success">Registration successful!</div>
    </c:if>

    <div class="form-group">
        <label for="username" class="col-sm-2 control-label">Name:</label>
        <div class="col-sm-10">
            <form:input path="username" cssClass="form-control"/>
            <form:errors path="username" cssClass="error"/>
        </div>
    </div>
    <div class="form-group">
        <label for="email" class="col-sm-2 control-label">Email:</label>
        <div class="col-sm-10">
            <form:input path="email" cssClass="form-control"/>
            <form:errors path="email" cssClass="error"/>
        </div>
    </div>
    <div class="form-group">
        <label for="password" class="col-sm-2 control-label">Password:</label>
        <div class="col-sm-10">
            <form:password path="password" cssClass="form-control"/>
            <form:errors path="password" cssClass="error"/>
        </div>
    </div>
    <div class="form-group">
        <label for="confirm_password" class="col-sm-2 control-label">Confirm Password:</label>
        <div class="col-sm-10">
            <input type="password" name="confirm_password" id="confirm_password" class="form-control"/>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-2">
            <input type="submit" value="Save" class="btn btn-lg btn-primary"/>
        </div>
    </div>
</form:form>

<script>
    $(document).ready(function () {

        //register jquery validate plugin for user registration form
        $(".registrationForm").validate({
            rules: {
                username: {
                    required: true,
                    minlength: 3,
                    remote: {
                        url: "<spring:url value="/register/available.html" />",
                        type: "GET",
                        data: {
                            username: function () {
                                return $("#username").val();
                            }
                        }
                    }
                },
                email: {
                    required: true,
                    email: true
                },
                password: {
                    required: true,
                    minlength: 5
                },
                confirm_password: {
                    required: true,
                    minlength: 5,
                    equalTo: "#password"
                }
            },
            messages: {
                username: {
                    remote: "Username already exists!"
                }
            },
            errorClass: "is-invalid",
            validClass: "is-valid"
        });

    });
</script>