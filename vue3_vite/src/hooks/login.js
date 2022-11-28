import axios from "axios";

export function loginApi(data) {
    return axios.post( '/api/user/login',{...data})
}

export function passwordloginApi(data) {
    return axios.post( '/api/user/password',{...data})
}

export function sendMsgApi(data) {
    return axios.post( '/api/user/sendMsg',{...data})
}
export function loginoutApi() {
    return axios.post( '/api/user/loginout')
}

//获取用户数据
export function edituserApi(data) {
    return axios.post( '/api/user/edit',{...data})
}

// 修改用户信息
export function editApi(data) {
    return axios.post( '/api/user/edituser',{...data})
}