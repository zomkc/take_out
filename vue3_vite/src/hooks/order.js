import axios from "axios";
//提交订单
export function addOrderApi(data) {
    return axios.post( 'https://zomkc.cn:8080/order/submit',{...data})
}

//查询所有订单
export function orderListApi() {
    return axios.get( 'https://zomkc.cn:8080/order/list')
}

//分页查询订单
export function orderPagingApi(data) {
    return axios.get( 'https://zomkc.cn:8080/order/userPage',{params:{...data}})
}

//再来一单
export function orderAgainApi(data) {
    return axios.post( 'https://zomkc.cn:8080/order/again',{...data})
}