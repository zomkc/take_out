import axios from "axios"
//获取所有地址
export function addressListApi(data) {
    return axios.post( 'https://zomkc.cn:8080/addressBook/list',{...data})
}

//获取最新地址
export function addressLastUpdateApi() {
    return axios.get( 'https://zomkc.cn:8080/addressBook/lastUpdate')
}

//新增地址
export function addAddressApi(data) {
    return axios.post( 'https://zomkc.cn:8080/addressBook',{...data})
}

//修改地址
export function updateAddressApi(data) {
    return axios.put( 'https://zomkc.cn:8080/addressBook',{...data})
}

//删除地址
export function deleteAddressApi(params) {
    return axios.delete( 'https://zomkc.cn:8080/addressBook',{params})
}

//查询单个地址
export function addressFindOneApi(id) {
    return axios.get( `https://zomkc.cn:8080/addressBook/${id}`)
}

//设置默认地址
export function setDefaultAddressApi(data) {
    return axios.put( `https://zomkc.cn:8080/addressBook/`,{...data})
}

//获取默认地址
export function getDefaultAddressApi(id) {
    return axios.get( `https://zomkc.cn:8080/addressBook/default/${id}`)
}