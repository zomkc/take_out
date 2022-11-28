import axios from "axios"
//获取所有地址
export function addressListApi() {
    return axios.get( '/api/addressBook/list')
}

//获取最新地址
export function addressLastUpdateApi() {
    return axios.get( '/api/addressBook/lastUpdate')
}

//新增地址
export function addAddressApi(data) {
    return axios.post( '/api/addressBook',{...data})
}

//修改地址
export function updateAddressApi(data) {
    return axios.put( '/api/addressBook',{...data})
}

//删除地址
export function deleteAddressApi(params) {
    return axios.delete( '/api/addressBook',{params})
}

//查询单个地址
export function addressFindOneApi(id) {
    return axios.get( `/api/addressBook/${id}`)
}

//设置默认地址
export function setDefaultAddressApi(data) {
    return axios.put( `/api/addressBook/`,{...data})
}

//获取默认地址
export function getDefaultAddressApi() {
    return axios.get( `/api/addressBook/default`)
}