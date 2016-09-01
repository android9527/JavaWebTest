/**
 * Created by chenfeiyue on 16/8/31.
 */
function login() {
    $('form#login_form').on('submit', function () {
        var username = $('input#username').val();
        var password = $('input#password').val();
        $(this).ajaxSubmit({
            type: 'post', // 提交方式 get/post
            url: login_url, // 需要提交的 url
            data: {
                'username': username,
                'password': password
            },
            success: function (data) { // data 保存提交后返回的数据，一般为 json 数据
                // 此处可对 data 作相关处理
                var result = JSON.parse(data);
                if (result.error != "") {
                    resetuser();
                    alert(result.error);
                } else { // 登录成功
                    fillUser(result);
                    window.location.href="html-master/pages/tables/data.html";
                }
                // $(this).resetForm(); // 提交后重置表单
            },
            error : function (data) {
                resetuser();
                alert("请求失败!");
            }
        });
        $('button#sign_in').unbind('click', login);
        return false; // 阻止表单自动提交事件
    });
}
$('button#sign_in').on('click', login);