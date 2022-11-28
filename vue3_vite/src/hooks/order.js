import axios from "axios";
//提交订单
export function addOrderApi(data) {
    return axios.post( '/api/order/submit',{...data})
}

//查询所有订单
export function orderListApi() {
    return axios.get( '/api/order/list')
}

//分页查询订单
export function orderPagingApi(data) {
    return axios.get( '/api/order/userPage',{params:{...data}})
}

//再来一单
export function orderAgainApi(data) {
    return axios.post( '/api/order/again',{...data})
}