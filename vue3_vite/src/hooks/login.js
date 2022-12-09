import axios from "axios";

export function loginApi(data) {
    return axios.post( 'https://zomkc.cn:8080/user/login',{...data})
}

export function passwordloginApi(data) {
    return axios.post( 'https://zomkc.cn:8080/user/password',{...data})
}

export function sendMsgApi(data) {
    // return axios.post( 'https://zomkc.cn:8080/user/sendMsg',{...data})
    return axios({
        headers: {
            'Content-Type': 'application/json'
        },
        method: 'post',
        url: 'https://zomkc.cn:8080/user/sendMsg',
        data: {...data}
    })
}
export function loginoutApi() {
    return axios.post( 'https://zomkc.cn:8080/user/loginout')
}

//获取用户数据
export function edituserApi(data) {
    return axios.post( 'https://zomkc.cn:8080/user/edit',{...data})
}

// 修改用户信息
export function editApi(data) {
    return axios.post( 'https://zomkc.cn:8080/user/edituser',{...data})
}