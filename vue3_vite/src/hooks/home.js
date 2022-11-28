import axios from "axios";

//获取所有的菜品分类
export function categoryListApi() {
    return axios.get( '/api/category/list')
}
//获取菜品分类对应的菜品
export function dishListApi(data) {
    return axios.get( '/api/dish/list',{params: {...data}})
}

//获取菜品分类对应的套餐
export function setmealListApi(data) {
    return axios.get( '/api/setmeal/list',{params: {...data}})
}

//获取购物车内商品的集合
export function cartListApi(data) {
    return axios.get( '/api/shoppingCart/list',{params: {...data}})
}

//购物车中添加商品
export function addCartApi(data) {
    return axios.post( '/api/shoppingCart/add',{...data})
}

//购物车中修改商品
export function updateCartApi(data) {
    return axios.post( '/api/shoppingCart/sub',{...data})
}

//删除购物车的商品
export function clearCartApi() {
    return axios.delete('/api/shoppingCart/clean')
}

//获取套餐的全部菜品
export function setMealDishDetailsApi(id) {
    return axios.get(`/api/setmeal/dish/${id}`)
}


