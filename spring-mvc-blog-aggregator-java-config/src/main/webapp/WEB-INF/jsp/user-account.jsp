<%--
  Created by IntelliJ IDEA.
  User: Danish
  Date: 6/29/2019
  Time: 1:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@include file="../layout/taglib.jsp" %>

<!-- Trigger the modal with a button -->
<button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#addBlogModal">New Blog</button>

<form:form modelAttribute="blog" cssClass="form-horizontal addBlogForm">
    <!-- Modal -->
    <div class="modal fade" id="addBlogModal" role="dialog">
        <div class="modal-dialog">

            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">New Blog</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <div class="modal-body">

                    <div class="form-group">
                        <label for="name" class="col-sm-2 control-label">Name:</label>
                        <div class="col-sm-10">
                            <form:input path="name" cssClass="form-control"/>
                            <form:errors path="name" cssClass="error"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="url" class="col-sm-2 control-label">URL:</label>
                        <div class="col-sm-10">
                            <form:input path="url" cssClass="form-control"/>
                            <form:errors path="url" cssClass="error"/>
                        </div>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <input type="submit" class="btn btn-primary" value="Save"/>
                </div>
            </div>

        </div>
    </div>
</form:form>

<br/><br/>


<!--Nav tabs-->
<ul class="nav nav-tabs" id="myTab" role="tablist">
    <c:forEach items="${user.blogs}" var="blog">
        <li class="nav-item">
            <a class="nav-link" id="#blog_${blog.id}-tab" data-toggle="tab" href="#blog_${blog.id}" role="tab"
               aria-controls="blog_${blog.id}" aria-selected="true">${blog.name}</a>
        </li>
    </c:forEach>
</ul>

<!--Tab panes-->
<div class="tab-content">
    <c:forEach items="${user.blogs}" var="blog">
        <div class="tab-pane fade" id="blog_${blog.id}" role="tabpanel" aria-labelledby="blog_${blog.id}-tab">
            <h1>
                    ${blog.name}
                <a href='<spring:url value="/blog/remove/${blog.id}.html" />'
                   class="btn btn-danger triggerRemoveBlog">Remove Blog</a>
            </h1>
            <p>${blog.url}</p>

            <table class="table table-bordered table-hover table-striped">
                <thead>
                    <tr>
                        <th>Date</th>
                        <th>Item</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${blog.items}" var="item">
                        <tr>
                            <td>${item.publishedDate}</td>
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
        </div>
    </c:forEach>
</div>


<!-- Modal -->
<div class="modal fade" id="removeBlogModal" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Remove Blog</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                Are you sure you want to remove this blog?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <a href="" class="btn btn-danger removeBlogBtn">Remove</a>
            </div>
        </div>

    </div>
</div>