<%--
  Created by IntelliJ IDEA.
  User: Danish
  Date: 6/29/2019
  Time: 1:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@include file="../layout/taglib.jsp" %>

<h1>${user.username}</h1>

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
                        <th>Title</th>
                        <th>Link</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${blog.items}" var="item">
                        <tr>
                            <td>${item.title}</td>
                            <td>${item.link}</td>
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