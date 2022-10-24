function loginApi(data) {
    return $axios({
        'url': '/user/login',
        'method': 'post',
        data
    })
}

function passwordloginApi(data) {
    return $axios({
        'url': '/user/password',
        'method': 'post',
        data
    })
}

function sendMsgApi(data) {
    return $axios({
        'url': '/user/sendMsg',
        'method': 'post',
        data
    })
}

function loginoutApi() {
    return $axios({
        'url': '/user/loginout',
        'method': 'post',
    })
}

//获取用户数据
function edituserApi(data) {
    return $axios({
        'url': '/user/edit',
        'method':'post',
        data
    })
}

// 修改用户信息
function editApi(data) {
    return $axios({
        'url': '/user/edituser',
        'method':'post',
        data
    })
}

  