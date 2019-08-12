$(document).ready(function () {
    $('.nav-tabs a:first').addClass('active'); // Make first tab ACTIVE
    $('.tab-content .tab-pane:first').addClass('show active'); // SHOW first pane

    //remove blog
    $('.triggerRemoveBlog').click(function (e) {
        e.preventDefault();
        $('#removeBlogModal .removeBlogBtn').attr('href', $(this).attr('href'));
        $('#removeBlogModal').modal();
    });

    //remove user
    $('.triggerRemoveUser').click(function (e) {
        e.preventDefault();
        $('#removeUserModal .removeUserBtn').attr('href', $(this).attr('href'));
        $('#removeUserModal').modal();
    });

    //register jquery validate plugin for user registration form
    // $(".registrationForm").validate({
    //     rules: {
    //         name: {
    //             required: true,
    //             minlength: 3,
    //             remote: {
    //                 url: "/java_blog_aggregator/register/available.html",
    //                 type: "GET",
    //                 data: {
    //                     username: function() {
    //                         return $("#name").val();
    //                     }
    //                 }
    //             }
    //         },
    //         email: {
    //             required: true,
    //             email: true
    //         },
    //         password: {
    //             required: true,
    //             minlength: 5
    //         },
    //         confirm_password: {
    //             required: true,
    //             minlength: 5,
    //             equalTo: "#password"
    //         }
    //     },
    //     messages: {
    //         name: {
    //             remote: "Username already exists!"
    //         }
    //     },
    //     errorClass: "is-invalid",
    //     validClass: "is-valid"
    // });

    $(".addBlogForm").validate(
        {
            rules: {
                name: {
                    required: true,
                    minlength: 1
                },
                url: {
                    required: true,
                    url: true,
                    minlength: 1
                }
            },
            errorClass: "is-invalid",
            validClass: "is-valid"
        }
    );
});