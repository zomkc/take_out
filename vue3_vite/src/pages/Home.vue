<template>
    <div id="main" class="app">
    <div class="divHead">
        <img src="/images/user.png" @click="toUserPage"/>
    </div>
    <div class="divTitle">
        <div class="divStatic">
            <img src="/images/favico.jpg" class="logo"/>
            <div class="divDesc">
                <div class="divName">只因</div>
                <div class="divSend">
                    <span><img src="/images/time.png"/> 距离1.5km</span>
                    <span><img src="/images/money.png"/> 配送费6元</span>
                    <span><img src="/images/location.png"/> 预计时长12min</span>
                </div>
            </div>
        </div>
        <div class="divDesc">
            简介: 只因炸鸡店是只因点餐的独立的品牌，定位“大众 化的美食外送餐饮”，旨为顾客打造专业美食。
        </div>
    </div>
    <div class="divBody">
        <div class="divType">
            <ul>
                <li v-for="(item,index) in categoryList" :key="index" @click="categoryClick(index,item.id,item.type)"
                    :class="{active:activeType === index}">{{item.name}}
                </li>
            </ul>
        </div>
        <div class="divMenu">
            <div>
                <div class="divItem" v-for="(item,index) in dishList" :key="index" @click="dishDetails(item)">
                    <el-image :src="imgPathConvert(item.image)">
                        <div slot="error" class="image-slot">
                            <img src="/images/noImg.png"/>
                        </div>
                    </el-image>
                    <div>
                        <div class="divName">{{item.name}}</div>
                        <div class="divDesc">{{item.description}}</div>
                        <div class="divDesc">{{'月销' + (item.saleNum ? item.saleNum : 0) }}</div>
                        <div class="divBottom"><span>￥</span><span>{{item.price/100}}</span></div>
                        <div class="divNum">
                            <div class="divSubtract" v-if="item.number > 0">
                                <img src="/images/subtract.png" @click.prevent.stop="subtractCart(item)"/>
                            </div>
                            <div class="divDishNum">{{item.number}}</div>
                            <div class="divTypes" v-if="item.flavors && item.flavors.length > 0 && !item.number "
                                 @click.prevent.stop="chooseFlavorClick(item)">选择规格
                            </div>
                            <div class="divAdd" v-else>
                                <img src="/images/add.png" @click.prevent.stop="addCart(item)"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
    <div class="divLayer">
        <div class="divLayerLeft"></div>
        <div class="divLayerRight"></div>
    </div>
    <div class="divCart" v-if="categoryList.length > 0">
        <div :class="{imgCartActive: cartData && cartData.length > 0, imgCart:!cartData || cartData.length<1}"
             @click="openCart"></div>
        <div :class="{divGoodsNum:1===1, moreGoods:cartData && cartData.length > 99}"
             v-if="cartData && cartData.length > 0">{{ goodsNum }}
        </div>
        <div class="divNum">
            <span>￥</span>
            <span>{{goodsPrice}}</span>
        </div>
        <div class="divPrice"></div>
        <div :class="{btnSubmitActive: cartData && cartData.length > 0, btnSubmit:!cartData || cartData.length<1}"
             @click="toAddOrderPage">去结算
        </div>
    </div>
    <van-dialog v-model:show="dialogFlavor.show" :title="dialogFlavor.name" :show-confirm-button="false" class="dialogFlavor">
        <div class="divContent">
            <div v-for="flavor in dialogFlavor.flavors" :key="flavor.id">
                <div class="divFlavorTitle">{{flavor.name}}</div>
                <span v-for="item in JSON.parse(flavor.value)"
                      :key="item"
                      @click="flavorClick(flavor,item)"
                      :class="{spanActive:flavor.dishFlavor === item}"
                >{{item}}</span>
            </div>
        </div>
        <div class="divBottom">
            <div><span class="spanMoney">￥</span>{{dialogFlavor.price/100}}</div>
            <div @click="dialogFlavorAddCart">加入购物车22</div>
        </div>
        <div @click="dialogFlavor.show = false" class="divFlavorClose">
            <img src="/images/close.png"/>
        </div>
    </van-dialog>

    <!-- <van-dialog v-model:show="dialogFlavor.show" :show-confirm-button="false" class="dialogFlavor" ref="flavorDialog">
        <div class="dialogTitle">{{dialogFlavor.name}}</div>
        <div class="divContent">
            <div v-for="flavor in dialogFlavor.flavors" :key="flavor.id">
                <div class="divFlavorTitle">{{flavor.name}}</div>
                <span v-for="item in JSON.parse(flavor.value)"
                      :key="item"
                      @click="flavorClick(flavor,item)"
                      :class="{spanActive:flavor.dishFlavor === item}"
                >{{item}}</span>
            </div>
        </div>
        <div class="divBottom">
            <div><span class="spanMoney">￥</span>{{dialogFlavor.price/100}}</div>
            <div @click="dialogFlavorAddCart">加入购物车22</div>
        </div>
        <div class="divFlavorClose" @click="dialogFlavor.show = false">
            <img src="/images/close.png"/>
        </div>
    </van-dialog> -->

    <van-popup v-model:show="cartDialogShow" position="bottom" :style="{ height: '50%' }" class="dialogCart">
        <div class="divCartTitle">
            <div class="title">购物车</div>
            <div class="clear" @click="clearCart">
                <i class="el-icon-delete"></i> 清空
            </div>
        </div>
        <div class="divCartContent">
            <div v-for="item in cartData" :key="item.id" class="divCartItem">
                <el-image :src="imgPathConvert(item.image)">
                    <div slot="error" class="image-slot">
                        <img src="/images/noImg.png"/>
                    </div>
                </el-image>
                <div class="divDesc">
                    <div class="name">{{item.name}}</div>
                    <div class="price">
                        <span class="spanMoney">￥</span>{{item.amount}}
                    </div>
                    <div class="flavor" >
                        {{item.dishFlavor}}
                    </div>
                </div>
<!--                <div class="divFlavor">-->
<!--                    <div class="flavor" >-->
<!--                        {{item.dishFlavor}}-->
<!--                    </div>-->
<!--                </div>-->
                <div class="divNum">
                    <div class="divSubtract">
                        <img src="/images/subtract.png" @click="cartNumberSubtract(item)"/>
                    </div>
                    <div class="divDishNum">{{item.number}}</div>
                    <div class="divAdd">
                        <img src="/images/add.png" @click="cartNumAdd(item)"/>
                    </div>
                </div>
                <div class="divSplit"></div>
            </div>
        </div>
    </van-popup>

    <van-dialog v-model="detailsDialog.show"
                :show-confirm-button="false"
                class="detailsDialog"
                ref="detailsDialog"
                v-if="detailsDialog.show"
    >
        <div class="divContainer">
            <el-image :src="imgPathConvert(detailsDialog.item.image)">
                <div slot="error" class="image-slot">
                    <img src="/images/noImg.png"/>
                </div>
            </el-image>
            <div class="title">{{detailsDialog.item.name}}</div>
            <div class="content">{{detailsDialog.item.description}}</div>
        </div>
        <div class="divNum">
            <div class="left">
                <span>￥</span><span>{{detailsDialog.item.price/100}}</span>
            </div>
            <div class="right">
                <div class="divSubtract" v-if="detailsDialog.item.number > 0">
                    <img src="/images/subtract.png" @click="subtractCart(detailsDialog.item)"/>
                </div>
                <div class="divDishNum">{{detailsDialog.item.number}}</div>
                <div class="divTypes" v-if="detailsDialog.item.flavors && detailsDialog.item.flavors.length > 0 && !detailsDialog.item.number "
                     @click="chooseFlavorClick(detailsDialog.item)">选择规格
                </div>
                <div class="divAdd" v-else>
                    <img src="/images/add.png" @click="addCart(detailsDialog.item)"/>
                </div>
            </div>
        </div>
        <div class="detailsDialogClose" @click="detailsDialog.show = false">
            <img src="/images/close.png"/>
        </div>
    </van-dialog>

    <van-dialog v-model="setMealDialog.show"
                :show-confirm-button="false"
                class="setMealDetailsDialog"
                ref="setMealDetailsDialogd"
                v-if="setMealDialog.show"
    >
        <div class="divContainer">
            <div class="title">{{setMealDialog.item.name}}</div>
            <div class="item" v-for="(item,index) in setMealDialog.item.list" :key="index">
                <el-image :src="imgPathConvert(item.image)">
                    <div slot="error" class="image-slot">
                        <img src="/images/noImg.png"/>
                    </div>
                </el-image>
                <div class="divSubTitle">{{item.name + '(' + item.copies + '份)' }}
                    <div class="divPrice">
                        <span>￥</span><span>{{item.price/100}}</span>
                    </div>
                </div>
                <div class="content">{{item.description}}</div>
            </div>
        </div>
        <div class="divNum">
            <div class="left">
                <span>￥</span><span>{{setMealDialog.item.price/100}}</span>
            </div>
            <div class="right">
                <div class="divSubtract" v-if="setMealDialog.item.number > 0">
                    <img src="/images/subtract.png" @click="subtractCart(setMealDialog.item)"/>
                </div>
                <div class="divDishNum">{{setMealDialog.item.number}}</div>
                <div class="divAdd" v-if="setMealDialog.item.number">
                    <img src="/images/add.png" @click="addCart(setMealDialog.item)"/>
                </div>
                <div class="addCart" @click="addCart(setMealDialog.item)" v-if="!setMealDialog.item.number">加入购物车</div>
            </div>
        </div>
        <div class="detailsDialogClose" @click="setMealDialog.show = false">
            <img src="/images/close.png"/>
        </div>
    </van-dialog>
</div>
</template>

<script >
 import { ElConfigProvider  } from 'element-plus'
 import {imgPath} from '../hooks/common'
 import {categoryListApi,dishListApi,setmealListApi,cartListApi,addCartApi,updateCartApi,clearCartApi,setMealDishDetailsApi} from '../hooks/home'

export default {
    components:{ElConfigProvider},
    data() {
            return {
                //左边菜品类别index
                activeType: 0,
                categoryList: [],
                categoryId: undefined,
                dishList: [],
                cartData: [],
                dialogFlavor: {
                    name: '',
                    flavors: [],
                    dishId: undefined,
                    price: undefined,
                    show: false,
                    image: ''
                },
                cartDialogShow: false,
                detailsDialog: {
                    show: false,
                    item: {image: ''}
                },
                setMealDialog: {
                    show: false,
                    item: {}
                },
            }
        },
        computed: {
            goodsNum() {
                let num = 0
                this.cartData.forEach(item => {
                    num += item.number
                })
                if (num < 99) {
                    return num
                } else {
                    return '99+'
                }
            },
            goodsPrice() {
                let price = 0
                this.cartData.forEach(item => {
                    price += (item.number * item.amount)
                })
                return price
            }
        },
        watch: {
            'dialogFlavor.show'(flag) {
                if (flag) {
                    document.querySelector('.divCart').style.zIndex = 1
                } else {
                    document.querySelector('.divCart').style.zIndex = 3000
                }
            },
        },
        mounted() {
            this.initData()
        },
        methods: {
            //初始化数据
            initData() {
                Promise.all([categoryListApi(), cartListApi({userId:sessionStorage.getItem("userId")})]).then(res => {
                    //获取分类数据
                    if (res[0].data.code === 1) {
                        this.categoryList = res[0].data.data
                        if (Array.isArray(res[0].data.data) && res[0].data.data.length > 0) {
                            this.categoryId = res[0].data.data[0].id
                            if (res[0].data.data[0].type === 1) {
                                this.getDishList()
                            } else {
                                this.getSetmealData()
                            }
                        }
                    } else {
                        console.log(res.data.console.error);
                    }
                    //获取菜品数据
                    if (res[1].data.code === 1) {
                        this.cartData = res[1].data.data
                    } else {
                        console.log(res.data.console.error);
                    }
                })
            },
            //分类点击
            categoryClick(index, id, type) {
                this.activeType = index
                this.categoryId = id
                if (type === 1) {//菜品
                    this.getDishList()
                } else {
                    this.getSetmealData()
                }
            },
            //获取菜品数据
            async getDishList() {
                if (!this.categoryId) {
                    return
                }
                const res = await dishListApi({categoryId: this.categoryId, status: 1})
                if (res.data.code === 1) {
                    let dishList = res.data.data
                    const cartData = this.cartData
                    if (dishList.length > 0 && cartData.length > 0) {
                        dishList.forEach(dish => {
                            cartData.forEach(cart => {
                                if (dish.id === cart.dishId) {
                                    dish.number = cart.number
                                }
                            })
                        })
                    }
                    this.dishList = dishList
                } else {
                   console.log(res.data.console.error);
                }
            },
            //获取套餐数据setmealId
            async getSetmealData() {
                if (!this.categoryId) {
                    return
                }
                const res = await setmealListApi({categoryId: this.categoryId, status: 1})
                if (res.data.code === 1) {
                    let dishList = res.data.data
                    const cartData = this.cartData
                    if (dishList.length > 0 && cartData.length > 0) {
                        dishList.forEach(dish => {
                            cartData.forEach(cart => {
                                if (dish.id === cart.setmealId) {
                                    dish.number = cart.number
                                }
                            })
                        })
                    }
                    this.dishList = dishList
                } else {
                   console.log(res.data.console.error);
                }
            },
            //获取购物车数据
            async getCartData() {
                const res = await cartListApi({userId:sessionStorage.getItem("userId")})
                if (res.data.code === 1) {
                    this.cartData = res.data.data
                } else {
                    console.log(res.data.console.error);
                }
            },
            //菜单中往购物车中添加商品
            async addCart(item) {
                let params = {
                    amount: item.price / 100,//金额
                    dishFlavor: item.dishFlavor,//口味  如果没有传undefined
                    dishId: undefined,//菜品id
                    setmealId: undefined,//套餐id
                    name: item.name,
                    image: item.image,
                    userId:sessionStorage.getItem("userId")
                }
                if (Array.isArray(item.flavors)) {//表示是菜品
                    params.dishId = item.id
                } else {//表示套餐 套餐没有口味
                    params.setmealId = item.id
                }
                const res = await addCartApi(params)
                if (res.data.code === 1) {
                    this.dishList.forEach(dish => {
                        if (dish.id === item.id) {
                            dish.number = res.data.data.number
                        }
                    })
                    if (this.setMealDialog.show) {
                        item.number = res.data.data.number
                    }
                    this.getCartData()
                } else {
                    console.log(res.data.console.error);
                }
            },

            //菜单中减少选中的商品
            async subtractCart(item) {
                let params = {
                    dishId: item.id,
                }
                if (!Array.isArray(item.flavors)) {
                    params = {
                        setmealId: item.id,
                    }
                }

                const res = await updateCartApi(params)
                if (res.data.code === 1) {
                    this.dishList.forEach(dish => {
                        if (dish.id === item.id) {
                            dish.number = (res.data.number === 0 ? undefined : res.data.data.number)
                        }
                    })
                    if (this.setMealDialog.show) {
                        item.number = (res.data.data.number === 0 ? undefined : res.data.data.number)
                    }
                    this.getCartData()
                } else {
                    console.log(res.data.console.error);
                }
            },

            //展开购物车
            openCart() {
                if (this.cartData.length > 0) {
                    this.cartDialogShow = true
                }
            },
            //购物车中增加商品数量
            async cartNumAdd(item) {
                let params = {
                    amount: item.amount,//金额
                    dishFlavor: item.dishFlavor,//口味  如果没有传undefined
                    dishId: item.dishId,//菜品id
                    setmealId: item.setmealId,//套餐id
                    name: item.name,
                    image: item.image,
                    userId:sessionStorage.getItem("userId")
                }
                const res = await addCartApi(params)
                if (res.data.code === 1) {
                    this.dishList.forEach(dish => {
                        if (dish.id === (item.dishId || item.setmealId)) {
                            dish.number = res.data.data.number
                        }
                    })
                    console.log(this.dishList)
                    this.getCartData()
                } else {
                    console.log(res.data.console.error);
                }
            },
            //购物车中减少商品数量
            async cartNumberSubtract(item) {
                let params = {
                    dishId: item.dishId,
                    setmealId: item.setmealId,
                }
                const res = await updateCartApi(params)
                if (res.data.code === 1) {
                    this.dishList.forEach(dish => {
                        if (dish.id === (item.dishId || item.setmealId)) {
                            dish.number = (res.data.data.number === 0 ? undefined : res.data.data.number)
                        }
                    })
                    this.getCartData()
                } else {
                   console.log(res.data.console.error);
                }
            },

            //修改商品列表中的数据number
            changeDishList(item) {
                for (let ele of this.dishList) {
                    if (ele.id === (item.setmealId || item.dishId)) {
                        ele.number = item.number
                    }
                }
            },

            //清空购物车
            async clearCart() {
                const res = await clearCartApi({userId:sessionStorage.getItem("userId")})
                if (res.data.code === 1) {
                    for (let ele of this.dishList) {
                        ele.number = undefined
                    }
                    this.cartData = []
                    this.cartDialogShow = false
                } else {
                    console.log(res.data.console.error);
                }
            },
            //点击选择规格
            chooseFlavorClick(item) {
                this.dialogFlavor = {
                    name: '',
                    flavors: [],
                    dishId: undefined,
                    price: undefined,
                    show: false
                }
                this.dialogFlavor = {
                    name: item.name,
                    flavors: item.flavors,
                    dishId: item.id,
                    price: item.price,
                    show: true,
                    image: item.image
                }
            },
            flavorClick(flavor, item) {
                flavor.dishFlavor = item
                //强制刷新dialog的dom
                this.dialogFlavor.show = false
                this.dialogFlavor.show = true
            },
            //选择规格加入购物车
            dialogFlavorAddCart() {
                const dialogFlavor = this.dialogFlavor
                let flag = true
                let dishFlavor = []
                if (dialogFlavor.flavors!=null){
                    dialogFlavor.flavors.forEach(item => {
                        if (item.dishFlavor) {
                            dishFlavor.push(item.dishFlavor)
                        } else {
                            flag = false
                            // Notify({type: 'warning', message: '请选择' + item.name});
                            // this.$message({type: 'warning', message: '请选择口味',center: true})
                            console.log(res.data.console.error);
                        }
                    })
                }

                if (flag) {
                    this.addCart({
                        price: dialogFlavor.price,
                        dishFlavor: dishFlavor.join(","),
                        id: dialogFlavor.dishId,
                        flavors: [],
                        image: dialogFlavor.image,
                        name: dialogFlavor.name
                    })
                    this.dialogFlavor.show = false
                }

            },
            //网络图片路径转换
            imgPathConvert(path) {
                return imgPath(path)
            },
            //跳转到去结算界面
            toAddOrderPage() {
                if (this.cartData.length > 0) {
                    window.requestAnimationFrame(() => {
                        // window.location.href = '/#addorder'
                        this.$router.push('/addorder')
                    })
                }
            },
            toUserPage() {
                window.requestAnimationFrame(() => {
                    // window.location.href = '/#user'
                    this.$router.push('/user')
                })
            },
            async dishDetails(item) {
                //先清除对象数据，如果不行的话dialog使用v-if
                this.detailsDialog.item = {}
                this.setMealDialog.item = {}
                if (Array.isArray(item.flavors)) {
                    this.detailsDialog.item = item
                    this.detailsDialog.show = true
                } else {
                    //显示套餐的数据
                    const res = await setMealDishDetailsApi(item.id)
                    if (res.data.code === 1) {
                        this.setMealDialog.item = {...item, list: res.data.data}
                        this.setMealDialog.show = true
                    } else {
                       console.log(res.data.console.error);
                    }
                }

            }

        }
}
</script>

<style scoped>
/**
首屏样式
*/
#main {
    height: 100%;
}

#main .divHead {
    background: url(/images/mainBg.png);
    background-repeat: no-repeat;
    height: 152rem;
    background-size: contain;
}

#main .divHead img {
    position: absolute;
    left: 19rem;
    top: 41rem;
    width: 28rem;
    height: 28rem;
}

#main .divTitle {
    width: 345rem;
    height: 118rem;
    opacity: 1;
    background: #ffffff;
    border-radius: 6rem;
    box-shadow: 0rem 2rem 5rem 0rem rgba(69, 69, 69, 0.1);
    position: absolute;
    left: 50%;
    top: 77rem;
    transform: translate(-50%, 0);
    box-sizing: border-box;
    padding: 14rem 0 0 8rem;
}

#main .divTitle .divStatic {
    display: flex;
}

#main .divTitle .divStatic .logo {
    width: 39rem;
    height: 39rem;
    opacity: 1;
    background: #333333;
    border-radius: 6rem;
    margin-right: 10rem;
}

#main .divTitle .divStatic .divDesc .divName {
    width: 90rem;
    height: 25rem;
    opacity: 1;
    font-size: 18rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 500;
    text-align: left;
    color: #20232a;
    line-height: 25rem;
}

#main .divTitle .divStatic .divDesc .divSend {
    opacity: 1;
    font-size: 11rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 400;
    text-align: left;
    color: #333333;
    margin-bottom: 10rem;
}

#main .divTitle .divStatic .divDesc .divSend img {
    width: 14rem;
    height: 14rem;
    opacity: 1;
    vertical-align: sub;
}

#main .divTitle .divStatic .divDesc .divSend span {
    margin-right: 12rem;
}

#main .divTitle .divStatic .divDesc .divSend span:last-child {
    margin-right: 0;
}

#main .divTitle > .divDesc {
    opacity: 1;
    font-size: 12rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 400;
    text-align: left;
    color: #9b9b9b;
    line-height: 17rem;
    margin-right: 18rem;
    padding-top: 9rem;
    border-top: 1rem dashed #ebebeb;
}

#main .divBody {
    display: flex;
    height: 100%;
}

#main .divBody .divType {
    background: #f6f6f6;
}

#main .divBody .divType ul {
    margin-top: 61rem;
    overflow-y: auto;
    height: calc(100% - 61rem);
    padding-bottom: 200rem;
    box-sizing: border-box;
    width: 84rem;
    opacity: 1;
}

#main .divBody .divType ul li {
    padding: 16rem;
    opacity: 1;
    font-size: 13rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 400;
    text-align: left;
    color: #666666;
    line-height: 18rem;
    letter-spacing: 0rem;
    word-wrap: break-word;
    word-break: normal;
}

#main .divBody .divType ul li.active {
    color: #333333;
    font-weight: 500;
    background-color: #ffffff;
    font-family: PingFangSC, PingFangSC-Regular;
}

#main .divBody .divMenu {
    background-color: #ffffff;
    box-sizing: border-box;
    width: 100%;
    height: 100%;
}

#main .divBody .divMenu > div {
    margin-top: 61rem;
    overflow-y: auto;
    height: calc(100% - 61rem);
    padding-bottom: 200rem;
    box-sizing: border-box;
}

#main .divBody .divMenu .divItem {
    margin: 10rem 15rem 20rem 14rem;
    display: flex;
}

#main .divBody .divMenu .divItem .el-image {
    width: 86rem;
    height: 86rem;
    margin-right: 14rem;
}

#main .divBody .divMenu .divItem .el-image img {
    width: 86rem;
    height: 86rem;
    border-radius: 5rem;
}

#main .divBody .divMenu .divItem > div {
    position: relative;
}

#main .divBody .divMenu .divItem .divName {
    height: 22rem;
    opacity: 1;
    font-size: 16rem;
    font-family: PingFangSC, PingFangSC-Semibold;
    font-weight: 600;
    text-align: left;
    color: #333333;
    line-height: 22rem;
    letter-spacing: 0;
    margin-bottom: 5rem;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    width: 175rem;
}

#main .divBody .divMenu .divItem .divDesc {
    height: 16rem;
    opacity: 1;
    font-size: 12rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 400;
    text-align: left;
    color: #666666;
    line-height: 16rem;
    letter-spacing: 0rem;
    margin-bottom: 4rem;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    width: 144rem;
}

#main .divBody .divMenu .divItem .divBottom {
    font-size: 15rem;
    font-family: DIN, DIN-Medium;
    font-weight: 500;
    text-align: left;
    color: #e94e3c;
    line-height: 20rem;
    letter-spacing: 0rem;
}

#main .divBody .divMenu .divItem .divBottom span:first-child {
    font-size: 12rem;
}

#main .divBody .divMenu .divItem .divBottom span:last-child {
    font-size: 15rem;
}

#main .divBody .divMenu .divItem .divNum {
    display: flex;
    position: absolute;
    right: 12rem;
    bottom: 0;
}

#main .divBody .divMenu .divItem .divNum .divDishNum {
    font-size: 15rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 500;
    text-align: center;
    color: #333333;
    line-height: 36rem;
    letter-spacing: 0;
    width: auto;
}

#main .divBody .divMenu .divItem .divNum .divTypes {
    width: 64rem;
    height: 24rem;
    opacity: 1;
    background: #ffc200;
    border-radius: 12rem;
    font-size: 12rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 500;
    text-align: center;
    color: #333333;
    line-height: 24rem;
    letter-spacing: 0;
}

#main .divBody .divMenu .divItem .divNum img {
    width: 36rem;
    height: 36rem;
}

#main .divCart {
    width: 345rem;
    height: 44rem;
    opacity: 1;
    background: #000000;
    border-radius: 25rem;
    box-shadow: 0rem 3rem 5rem 0rem rgba(0, 0, 0, 0.25);
    margin: 0 auto;
    bottom: 24rem;
    position: fixed;
    left: 50%;
    transform: translate(-50%, 0);
    z-index: 3000;
}

#main .divCart .imgCartActive {
    background-image: url("/images/cart_active.png");
}

#main .divCart .imgCart {
    background-image: url("/images/cart.png");
}

#main .divCart > div:first-child {
    width: 60rem;
    height: 60rem;
    position: absolute;
    left: 11rem;
    bottom: 0;
    background-size: 60rem 60rem;
}

#main .divCart .divNum {
    font-size: 12rem;
    font-family: DIN, DIN-Medium;
    font-weight: 500;
    text-align: left;
    color: #ffffff;
    letter-spacing: 0rem;
    position: absolute;
    left: 92rem;
    top: 10rem;
}

#main .divCart .divNum span:last-child {
    font-size: 20rem;
}

#main .divCart > div:last-child {
    width: 102rem;
    height: 36rem;
    opacity: 1;
    border-radius: 18rem;
    font-size: 15rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 500;
    text-align: center;
    line-height: 36rem;
    position: absolute;
    right: 5rem;
    top: 4rem;
}

#main .divCart .btnSubmit {
    color: white;
    background: #d8d8d8;
}

#main .divCart .btnSubmitActive {
    color: #333333;
    background: #ffc200;
}

#main .divCart .divGoodsNum {
    width: 18rem;
    height: 18rem;
    opacity: 1;
    background: #e94e3c;
    border-radius: 50%;
    text-align: center;
    font-size: 12rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 500;
    color: #ffffff;
    line-height: 18rem;
    position: absolute;
    left: 50rem;
    top: -5rem;
}

#main .divCart .moreGoods {
    width: 25rem;
    height: 25rem;
    line-height: 25rem;
}

#main .divLayer {
    position: absolute;
    height: 68rem;
    width: 100%;
    bottom: 0;
    display: flex;
}

/* #main .divLayer .divLayerLeft {
    background-color: #f6f6f6;
    opacity: 0.5;
    width: 84rem;
    height: 100%;
}

#main .divLayer .divLayerRight {
    background-color: white;
    opacity: 0.5;
    width: calc(100% - 84rem);
    height: 100%;
} */

#main .dialogFlavor {
    opacity: 1;
    background: #ffffff;
    border-radius: 10rem;
}

#main .dialogFlavor .dialogTitle {
    margin-top: 26rem;
    margin-bottom: 14rem;
    font-size: 18rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 500;
    color: #333333;
    letter-spacing: 0;
    text-align: center;
}

#main .dialogFlavor .divContent {
    margin-left: 15rem;
    margin-right: 15rem;
}

#main .dialogFlavor .divContent .divFlavorTitle {
    height: 20rem;
    opacity: 1;
    font-size: 14rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 400;
    text-align: left;
    color: #666666;
    line-height: 20rem;
    letter-spacing: 0;
    margin-left: 5rem;
    margin-bottom: 10rem;
    margin-top: 10rem;
}

#main .dialogFlavor .divContent span {
    display: inline-block;
    height: 30rem;
    opacity: 1;
    background: #ffffff;
    border: 1rem solid #ffc200;
    border-radius: 7rem;
    line-height: 30rem;
    padding-left: 13rem;
    padding-right: 13rem;
    margin: 0 5rem 10rem 5rem;
    font-size: 14rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 400;
    text-align: center;
    color: #333333;
}

#main .dialogFlavor .divContent .spanActive {
    background: #ffc200;
    font-weight: 500;
}

#main .dialogFlavor .divBottom {
    margin-top: 20rem;
    margin-bottom: 19rem;
    margin-left: 20rem;
    display: flex;
    position: relative;
}

#main .dialogFlavor .divBottom div:first-child {
    height: 30rem;
    opacity: 1;
    font-size: 20rem;
    font-family: DIN, DIN-Medium;
    font-weight: 500;
    text-align: left;
    color: #e94e3c;
    line-height: 30rem;
    letter-spacing: 0;
}

#main .dialogFlavor .divBottom div span {
    font-size: 14rem;
}

#main .dialogFlavor .divBottom div:last-child {
    width: 100rem;
    height: 30rem;
    opacity: 1;
    background: #ffc200;
    border-radius: 15rem;
    text-align: center;
    line-height: 30rem;
    position: absolute;
    right: 20rem;
    font-size: 14rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 500;
    text-align: center;
    color: #333333;
}

#main .dialogFlavor .divFlavorClose {
    /* position: absolute; */
    /* bottom: -70rem;
    left: 50%; */
    /* transform: translate(-50%, 0); */
    width: 100%;
    height: 50rem;
    position: relative;
}

#main .dialogFlavor .divFlavorClose img {  
    margin: 0 auto; 
    position: relative;
    top: 5px;
    left: 40%;
    width: 44rem;
    height: 44rem;
}

#main .dialogCart {
    background: linear-gradient(180deg, #ffffff 0%, #ffffff 81%);
    border-radius: 12px 12px 0px 0px;
}

#main .dialogCart .divCartTitle {
    height: 59rem;
    display: flex;
    line-height: 60rem;
    position: relative;
    margin-left: 15rem;
    margin-right: 10rem;
    border-bottom: 1px solid #efefef;
}

#main .dialogCart .divCartTitle .title {
    font-size: 20rem;
    font-family: PingFangSC, PingFangSC-Semibold;
    font-weight: 600;
    text-align: left;
    color: #333333;
}

#main .dialogCart .divCartTitle i {
    margin-right: 3rem;
    font-size: 15rem;
    vertical-align: middle;
}

#main .dialogCart .divCartTitle .clear {
    position: absolute;
    right: 5px;
    top: 50%;
    transform: translate(0, -50%);
    color: #999999;
    font-size: 14px;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 400;
    text-align: left;
}

#main .dialogCart .divCartItem {
    height: 108rem;
    margin-left: 15rem;
    margin-right: 10rem;
    display: flex;
    align-items: center;
    position: relative;
}

#main .dialogCart .divCartContent {
    height: calc(100% - 130rem);
    overflow-y: auto;
}

#main .dialogCart .divCartContent .el-image {
    width: 64rem;
    height: 64rem;
    opacity: 1;
    margin-right: 10rem;
}

#main .dialogCart .divCartContent .el-image img {
    width: 64rem;
    height: 64rem;
}

#main .dialogCart .divCartContent .divDesc .name {
    height: 22rem;
    opacity: 1;
    font-size: 16rem;
    font-family: PingFangSC, PingFangSC-Semibold;
    font-weight: 600;
    text-align: left;
    color: #333333;
    line-height: 22rem;
    letter-spacing: 0;
    margin-bottom: 17rem;
}

#main .dialogCart .divCartContent .divDesc .price {
    font-size: 18rem;
    font-family: DIN, DIN-Medium;
    font-weight: 500;
    text-align: left;
    color: #e94e3c;
}

/*#main .dialogCart .divCartContent .divFlavor{*/
/*    width: 110px;*/
/*    !*position: revert;*!*/
/*    height: 50%;*/
/*    float: right;*/
/*    position: relative;*/
/*    left: 5px;*/
/*    top: 0px;*/
/*}*/

#main .dialogCart .divCartContent .divDesc .flavor{
    font-size: 15rem;
    color: #bbb4b4;
}

#main .dialogCart .divCartContent .divDesc .price .spanMoney {
    font-size: 12rem;
}

#main .dialogCart .divCartContent .divCartItem .divNum {
    position: absolute;
    right: 0;
    bottom: 10rem;
    display: flex;
    line-height: 36rem;
    height: 36rem;
}

#main .dialogCart .divCartContent .divCartItem .divNum img {
    width: 36rem;
    height: 36rem;
}

#main .dialogCart .divCartContent .divCartItem .divSplit {
    width: calc(100% - 64rem);
    position: absolute;
    bottom: 0;
    right: 0;
    height: 1px;
    opacity: 1;
    background-color: #efefef;
}

#main .dialogCart .divCartContent .divCartItem:last-child .divSplit {
    height: 0;
}

#main .detailsDialog {
    display: flex;
    flex-direction: column;
    text-align: center;
}

#main .detailsDialog .divContainer {
    padding: 20rem 20rem 0 20rem;
    overflow: auto;
    max-height: 50vh;
    overflow-y: auto;
}

#main .detailsDialog .el-image {
    width: 100%;
    height: 100%;
}

#main .detailsDialog .el-image img {
    width: 100%;
    height: 100%;
}

#main .detailsDialog .title {
    height: 28rem;
    opacity: 1;
    font-size: 20rem;
    font-family: PingFangSC, PingFangSC-Semibold;
    font-weight: 600;
    color: #333333;
    line-height: 28rem;
    letter-spacing: 0;
    margin-top: 18rem;
    margin-bottom: 11rem;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    width: 100%;
}

#main .detailsDialog .content {
    opacity: 1;
    font-size: 14rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 400;
    text-align: justify;
    color: #333333;
    line-height: 24rem;
}

#main .detailsDialog .divNum {
    display: flex;
    justify-content: space-between;
    margin-top: 23rem;
    margin-bottom: 20rem;
    padding-left: 20rem;
    padding-right: 20rem;
}

#main .detailsDialog .divNum .left {
    font-size: 20rem;
    font-family: DIN, DIN-Medium;
    font-weight: 500;
    text-align: left;
    color: #e94e3c;
    line-height: 36rem;
    letter-spacing: 0rem;
}

#main .detailsDialog .divNum .left span:first-child {
    font-size: 12rem;
}

#main .detailsDialog .divNum .right {
    display: flex;
}

#main .detailsDialog .divNum .divDishNum {
    font-size: 15rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 500;
    text-align: center;
    color: #333333;
    line-height: 36rem;
    letter-spacing: 0;
    width: auto;
}

#main .detailsDialog .divNum .divTypes {
    width: 64rem;
    height: 24rem;
    opacity: 1;
    background: #ffc200;
    border-radius: 12rem;
    font-size: 12rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 500;
    text-align: center;
    color: #333333;
    line-height: 24rem;
    letter-spacing: 0;
}

#main .detailsDialog .divNum .divSubtract,
.divAdd {
    height: 36rem;
}

#main .detailsDialog .divNum img {
    width: 36rem;
    height: 36rem;
}

#main .detailsDialog .detailsDialogClose {
    position: absolute;
    bottom: -70rem;
    left: 50%;
    transform: translate(-50%, 0);
}

#main .detailsDialog .detailsDialogClose img {
    width: 44rem;
    height: 44rem;
}

#main .setMealDetailsDialog {
    display: flex;
    flex-direction: column;
    text-align: center;
}

#main .setMealDetailsDialog .divContainer {
    padding: 20rem 20rem 0 20rem;
    overflow: auto;
    max-height: 50vh;
    overflow-y: auto;
}

#main .setMealDetailsDialog .el-image {
    width: 100%;
    height: 100%;
}

#main .setMealDetailsDialog .el-image img {
    width: 100%;
    height: 100%;
}

#main .setMealDetailsDialog .divSubTitle {
    text-align: left;
    margin-top: 16rem;
    margin-bottom: 6rem;
    height: 25rem;
    opacity: 1;
    font-size: 18rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 500;
    text-align: left;
    color: #333333;
    line-height: 25rem;
    letter-spacing: 0px;
    position: relative;
}

#main .setMealDetailsDialog .divContainer .item .divSubTitle .divPrice {
    position: absolute;
    right: 0;
    top: 0;
    font-size: 18rem;
    font-family: DIN, DIN-Medium;
    font-weight: 500;
    text-align: left;
    color: #e94e3c;
    line-height: 25rem;
    letter-spacing: 0rem;
}

#main
.setMealDetailsDialog
.divContainer
.item
.divSubTitle
.divPrice
span:first-child {
    font-size: 12rem;
}

#main .setMealDetailsDialog .title {
    height: 28rem;
    opacity: 1;
    font-size: 20rem;
    font-family: PingFangSC, PingFangSC-Semibold;
    font-weight: 600;
    color: #333333;
    line-height: 28rem;
    letter-spacing: 0;
    margin-top: 18rem;
    margin-bottom: 11rem;
}

#main .setMealDetailsDialog .content {
    opacity: 1;
    font-size: 14rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 400;
    text-align: justify;
    color: #333333;
    line-height: 24rem;
}

#main .setMealDetailsDialog .divNum {
    display: flex;
    justify-content: space-between;
    margin-top: 23rem;
    padding-bottom: 15rem;
    padding-left: 20rem;
    padding-right: 20rem;
}

#main .setMealDetailsDialog .divNum .left {
    font-size: 20rem;
    font-family: DIN, DIN-Medium;
    font-weight: 500;
    text-align: left;
    color: #e94e3c;
    line-height: 36rem;
    letter-spacing: 0rem;
}

#main .setMealDetailsDialog .divNum .left span:first-child {
    font-size: 12rem;
}

#main .setMealDetailsDialog .divNum .right {
    display: flex;
}

#main .setMealDetailsDialog .divNum .divDishNum {
    font-size: 15rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 500;
    text-align: center;
    color: #333333;
    line-height: 36rem;
    letter-spacing: 0;
    width: auto;
}

#main .setMealDetailsDialog .divNum .divTypes {
    width: 64rem;
    height: 24rem;
    opacity: 1;
    background: #ffc200;
    border-radius: 12rem;
    font-size: 12rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 500;
    text-align: center;
    color: #333333;
    line-height: 24rem;
    letter-spacing: 0;
}

#main .setMealDetailsDialog .divNum .divSubtract,
.divAdd {
    height: 36rem;
}

#main .setMealDetailsDialog .divNum img {
    width: 36rem;
    height: 36rem;
}

#main .setMealDetailsDialog .divNum .right .addCart {
    width: 100rem;
    height: 30rem;
    opacity: 1;
    background: #ffc200;
    border-radius: 15rem;
    font-size: 14rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 500;
    text-align: center;
    color: #333333;
    line-height: 30rem;
}

#main .setMealDetailsDialog .detailsDialogClose {
    position: absolute;
    bottom: -70rem;
    left: 50%;
    transform: translate(-50%, 0);
}

#main .setMealDetailsDialog .detailsDialogClose img {
    width: 44rem;
    height: 44rem;
}

</style>