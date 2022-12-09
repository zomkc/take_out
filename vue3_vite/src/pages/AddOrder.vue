<template>
  <div id="add_order" class="app">
    <div class="divHead">
        <div class="divTitle">
            <el-icon @click="goBack"><ArrowLeft /></el-icon>
            鸡你太美
        </div>
    </div>
    <div class="divContent">
        <div class="divAddress">
            <div @click="toAddressPage">
                <div class="address">{{address.detail}}</div>
                <div class="name">
                    <span>{{address.consignee}}{{address.sex === '1' ? '先生':'女士'}}</span>
                    <span>{{address.phone}}</span>
                </div>
                <i class="el-icon-arrow-right"></i>
            </div>
            <div class="divSplit"></div>
            <div class="divFinishTime">预计{{finishTime}}送达</div>
        </div>
        <div class="order">
            <div class="title">订单明细</div>
            <div class="divSplit"></div>
            <div class="itemList">
                <div class="item" v-for="(item,index) in cartData" :key="index">
                    <el-image :src="imgPathConvert(item.image)">
                        <div slot="error" class="image-slot">
                            <img src="/images/noImg.png"/>
                        </div>
                    </el-image>
                    <div class="desc">
                        <div class="name">{{item.name}}</div>
                        <div class="numPrice">
                            <span class="num">x{{item.number}}</span>
                            <div class="price">
                                <span class="spanMoney">￥</span>{{item.amount}}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="note">
            <div class="title">备注</div>
            <van-field
                    v-model="note"
                    rows="2"
                    autosize
                    type="textarea"
                    maxlength="50"
                    placeholder="请输入您需要备注的信息"
                    show-word-limit
            />
        </div>
    </div>
    <div class="divCart">
        <div :class="{imgCartActive: cartData && cartData.length > 0, imgCart:!cartData || cartData.length<1}"></div>
        <div :class="{divGoodsNum:1===1, moreGoods:cartData && cartData.length > 99}"
             v-if="cartData && cartData.length > 0">{{ goodsNum }}
        </div>
        <div class="divNum">
            <span>￥</span>
            <span>{{goodsPrice}}</span>
        </div>
        <div class="divPrice"></div>
        <div :class="{btnSubmitActive: cartData && cartData.length > 0, btnSubmit:!cartData || cartData.length<1}"
             @click="goToPaySuccess">去支付
        </div>
    </div>
</div>
</template>

<script>
import { ElConfigProvider  } from 'element-plus'
import {getDefaultAddressApi} from '../hooks/address'
import {imgPath} from '../hooks/common'
import {addOrderApi} from '../hooks/order'
import {cartListApi} from '../hooks/home'
export default {
    components:{ElConfigProvider},
    data() {
            return {
                address: {
                    phone: '',//手机号
                    consignee: '',//姓名
                    detail: '',//详细地址
                    sex: '1',
                    userId:sessionStorage.getItem("userId")
                },
                finishTime: '',//送达时间
                cartData: [],
                note: ''//备注信息
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
        created() {
            this.initData()
        },
        methods: {
            goBack() {
                history.go(-1)
            },
            initData() {
                //获取默认的地址
                this.defaultAddress()
                //获取购物车的商品
                this.getCartData()
            },
            //获取默认地址
            async defaultAddress() {
                const res = await getDefaultAddressApi(this.address.userId)
                if (res.data.code === 1) {
                    this.address = res.data.data
                    this.getFinishTime()
                } else {
                    window.requestAnimationFrame(() => {
                        this.$router.push('/addressedit')
                        // window.location.href = '/front/page/address-edit.html'
                    })
                }
            },
            //获取送达时间
            getFinishTime() {
                let now = new Date()
                let hour = now.getHours() + 1
                let minute = now.getMinutes()
                if (hour.toString().length < 2) {
                    hour = '0' + hour
                }
                if (minute.toString().length < 2) {
                    minute = '0' + minute
                }
                this.finishTime = hour + ':' + minute
            },
            toAddressPage() {
                window.requestAnimationFrame(() => {
                    this.$router.push('/address')
                    // window.location.href = '/front/page/address.html'
                })
            },
            //获取购物车数据
            async getCartData() {
                const res = await cartListApi({userId: this.address.userId})
                if (res.data.code === 1) {
                    this.cartData = res.data.data
                } else {
                    console.log(res.data.console.error);
                }
            },
            async goToPaySuccess() {
                const params = {
                    remark: this.note,
                    payMethod: 1,
                    addressBookId: this.address.id,
                    userId:this.address.userId
                }
                const res = await addOrderApi(params)
                if (res.data.code === 1) {
                    window.requestAnimationFrame(() => {
                        this.$router.push('/order')
                        // window.location.replace('/front/page/pay-success.html')
                    })
                } else {
                    console.log(res.data.console.error);
                }
            },
            //网络图片路径转换
            imgPathConvert(path) {
                return imgPath(path)
            },
        }
}
</script>

<style scoped>
#add_order .divHead {
    width: 100%;
    height: 88rem;
    opacity: 1;
    background: #333333;
    position: relative;
}

#add_order .divHead .divTitle {
    font-size: 18rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 500;
    text-align: center;
    color: #ffffff;
    line-height: 25rem;
    letter-spacing: 0;
    position: absolute;
    bottom: 13rem;
    width: 100%;
}

#add_order .divHead .divTitle i {
    position: absolute;
    left: 16rem;
    top: 50%;
    transform: translate(0, -50%);
}

#add_order .divContent {
    margin: 10rem 10rem 0 10rem;
    height: calc(100vh - 56rem - 110rem);
    overflow-y: auto;
}

#add_order .divContent .divAddress {
    height: 120rem;
    opacity: 1;
    background: #ffffff;
    border-radius: 6rem;
    position: relative;
    padding: 11rem 10rem 0 16rem;
}

#add_order .divContent .divAddress .address {
    height: 25rem;
    opacity: 1;
    font-size: 18rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 500;
    text-align: left;
    color: #20232a;
    line-height: 25rem;
    margin-bottom: 4rem;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    width: 300rem;
}

#add_order .divContent .divAddress .name {
    height: 17rem;
    opacity: 1;
    font-size: 12rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 400;
    text-align: left;
    color: #333333;
    line-height: 17rem;
}

#add_order .divContent .divAddress .name span:first-child {
    margin-right: 2rem;
}

#add_order .divContent .divAddress i {
    position: absolute;
    right: 14rem;
    top: 32rem;
}

#add_order .divContent .divAddress .divSplit {
    width: 100%;
    height: 1px;
    opacity: 1;
    border: 0;
    background-color: #ebebeb;
    margin-top: 14rem;
}

#add_order .divContent .divAddress .divFinishTime {
    height: 47rem;
    opacity: 1;
    font-size: 12rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 400;
    text-align: left;
    color: #333333;
    line-height: 47rem;
    margin-left: 2rem;
}

#add_order .divContent .order {
    background: #ffffff;
    border-radius: 6rem;
    margin-top: 10rem;
    margin-bottom: 10rem;
    padding: 3rem 10rem 7rem 16rem;
}

#add_order .divContent .order .title {
    height: 56rem;
    line-height: 56rem;
    opacity: 1;
    font-size: 16rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 500;
    text-align: left;
    color: #333333;
    letter-spacing: 0;
}

#add_order .divContent .order .divSplit {
    height: 1px;
    opacity: 1;
    background-color: #efefef;
    border: 0;
}

#add_order .divContent .order .itemList .item {
    display: flex;
}

#add_order .divContent .order .itemList .item .el-image {
    padding-top: 20rem;
    padding-bottom: 20rem;
    width: 64rem;
    height: 64rem;
}

#add_order .divContent .order .itemList .item .el-image img {
    width: 64rem;
    height: 64rem;
}

#add_order .divContent .order .itemList .item:first-child .desc {
    border: 0;
}

#add_order .divContent .order .itemList .item .desc {
    padding-top: 20rem;
    padding-bottom: 20rem;
    border-top: 2px solid #ebeef5;
    width: calc(100% - 64rem);
}

#add_order .divContent .order .itemList .item .desc .name {
    height: 22rem;
    opacity: 1;
    font-size: 16rem;
    font-family: PingFangSC, PingFangSC-Semibold;
    font-weight: 600;
    text-align: left;
    color: #20232a;
    line-height: 22rem;
    letter-spacing: 0;
    margin-left: 10rem;
    margin-bottom: 16rem;
}

#add_order .divContent .order .itemList .item .desc .numPrice {
    height: 22rem;
    display: flex;
    justify-content: space-between;
}

#add_order .divContent .order .itemList .item .desc .numPrice span {
    margin-left: 12rem;
    height: 20rem;
    opacity: 1;
    font-size: 14rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 400;
    text-align: left;
    color: #818693;
    line-height: 20rem;
    letter-spacing: 0;
    display: inline-block;
}

#add_order .divContent .order .itemList .item .desc .numPrice .price {
    font-size: 20rem;
    font-family: DIN, DIN-Medium;
    font-weight: 500;
    text-align: left;
    color: #e94e3c;
}

#add_order
.divContent
.order
.itemList
.item
.desc
.numPrice
.price
.spanMoney {
    color: #e94e3c;
    font-size: 12rem;
}

#add_order .divContent .note {
    height: 164rem;
    opacity: 1;
    background: #ffffff;
    border-radius: 6px;
    margin-top: 11rem;
    padding: 3rem 10rem 10rem 11rem;
}

#add_order .divContent .note .title {
    height: 56rem;
    opacity: 1;
    font-size: 16rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 500;
    text-align: left;
    color: #333333;
    line-height: 56rem;
    letter-spacing: 0px;
    border-bottom: 2rem solid #efefef;
}

#add_order .divContent .note .van-cell {
    height: 103rem;
}

#add_order .divCart {
    width: 345rem;
    height: 44rem;
    opacity: 1;
    background: #000000;
    border-radius: 25rem;
    box-shadow: 0rem 3rem 5rem 0rem rgba(0, 0, 0, 0.25);
    margin: 0 auto;
    margin-top: 10rem;
    z-index: 3000;
    position: absolute;
    /* bottom: 35rem; */
    bottom: 12rem;
    left: 50%;
    transform: translate(-50%, 0);
}

#add_order .divCart .imgCartActive {
    background-image: url("./../images/cart_active.png");
}

#add_order .divCart .imgCart {
    background-image: url("./../images/cart.png");
}

#add_order .divCart > div:first-child {
    width: 60rem;
    height: 60rem;
    position: absolute;
    left: 11rem;
    bottom: 0;
    background-size: 60rem 60rem;
}

#add_order .divCart .divNum {
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

#add_order .divCart .divNum span:last-child {
    font-size: 20rem;
}

#add_order .divCart > div:last-child {
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

#add_order .divCart .btnSubmit {
    color: white;
    background: #d8d8d8;
}

#add_order .divCart .btnSubmitActive {
    color: #333333;
    background: #ffc200;
}

#add_order .divCart .divGoodsNum {
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
    left: 57rem;
    top: -5rem;
}

#add_order .divCart .moreGoods {
    width: 25rem;
    height: 25rem;
    line-height: 25rem;
}

</style>