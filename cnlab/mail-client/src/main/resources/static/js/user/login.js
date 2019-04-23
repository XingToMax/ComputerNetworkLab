//点击登录按钮效果(登录按钮的id为`loginBtn`)
$("#loginBtn").on('click',function () {
    //按照通过input的id获取填写的值
    var username = $('#username').val()
    var password = $('#password').val()
    var param = {'username':username, 'password':password}
    layer.load(2)
    $.post('/user/login', param, function (result) {
        layer.closeAll('loading')
        if (result.code === 0) {
            window.location.href = '/page/index'
        } else {
            layer.alert(result.msg, {icon: 2});
        }
    })
})

function go_to_signup() {
    window.location.href = "/signup"
}



