import axios from "axios";

//获取所有的菜品分类
export function categoryListApi() {
    return axios.get( 'https://zomkc.cn:8080/category/list')
}
//获取菜品分类对应的菜品
export function dishListApi(data) {
    return axios.get( 'https://zomkc.cn:8080/dish/list',{params: {...data}})
}

//获取菜品分类对应的套餐
export function setmealListApi(data) {
    return axios.get( 'https://zomkc.cn:8080/setmeal/list',{params: {...data}})
}

//获取购物车内商品的集合
export function cartListApi(data) {
    return axios.get( 'https://zomkc.cn:8080/shoppingCart/list',{params: {...data}})
}

//购物车中添加商品
export function addCartApi(data) {
    return axios.post( 'https://zomkc.cn:8080/shoppingCart/add',{...data})
}

//购物车中修改商品
export function updateCartApi(data) {
    return axios.post( 'https://zomkc.cn:8080/shoppingCart/sub',{...data})
}

//删除购物车的商品
export function clearCartApi(data) {
    console.log(data)
    return axios.post('https://zomkc.cn:8080/shoppingCart/clean', {...data})
}

//获取套餐的全部菜品
export function setMealDishDetailsApi(id) {
    return axios.get(`https://zomkc.cn:8080/setmeal/dish/${id}`)
}


