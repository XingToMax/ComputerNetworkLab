$('#signupB').click(function () {
    var username = $('#username').val()
    var password = $('#password').val()
    var passwordCon = $('#conPass').val()
    if (password !== passwordCon) {
        layer.alert('两次输入密码不一致')
        return
    }

    var param = {
        username : username,
        password : password
    }
    layer.load(2)
    $.post('/user/signup', param, function (result) {
        layer.closeAll('loading')
        if (result.code === 0) {
            layer.msg('注册成功')
            window.location.href = '/'
        } else {
            layer.alert(result.msg)
        }
    })
});

function go_to_signin() {
    window.location.href = "/"
}