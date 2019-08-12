<%--
  Created by IntelliJ IDEA.
  User: Danish
  Date: 6/29/2019
  Time: 1:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@include file="../layout/taglib.jsp" %>

<table class="table table-bordered table-hover table-striped">
    <thead>
        <tr>
            <th>Username</th>
            <th>Operations</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>
                    <a href='<spring:url value="/users/${user.id}.html" />'>
                        <c:out value="${user.username}"/> <!--c:out from JSTL is used to escape html entities-->
                    </a>
                </td>
                <td>
                    <a href='<spring:url value="/users/remove/${user.id}.html" />'
                       class="btn btn-danger triggerRemoveUser">Delete
                    </a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>


<!-- Modal -->
<div class="modal fade" id="removeUserModal" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Remove User</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                Are you sure you want to remove this user?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <a href="" class="btn btn-danger removeUserBtn">Remove</a>
            </div>
        </div>

    </div>
</div>