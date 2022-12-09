<template>
  <div id="user" class="app">
    <div class="divHead">
        <div class="divTitle">
            <!-- <i class="el-icon-arrow-left" @click="goBack"></i> -->
            <el-icon @click="goBack"><ArrowLeft /></el-icon>
            个人中心
        </div>
        <div class="divUser" :model="form">
           <el-image class="image" :src="'https://zomkc.cn:8080/'+form.avatar">
                <div slot="error" class="image-slot">
                    <img class="image" src="/images/headPage.png">
                </div>
            </el-image>
            <div class="desc">
                <div class="divName">{{form.name}}
                <img v-if="form.sex==2" src="/images/women.png"/>
                <img v-if="form.sex==1" src="/images/1.png"/>
                </div>
                <div class="divPhone">{{form.phone}}</div>
            </div>
            <div class="divUpdate" @click="edit">
                编辑信息
            </div>
        </div>
    </div>
    <div class="divContent">
        <div class="divLinks">
            <div class="item" @click="toAddressPage">
                <img src="/images/locations.png"/>
                <span>地址管理</span>
                <i class="el-icon-arrow-right"></i>
            </div>
<!--            <div class="divSplit"></div>-->
            <div class="item" @click="toOrderPage">
                <img src="/images/orders.png"/>
                <span>历史订单</span>
                <i class="el-icon-arrow-right"></i>
            </div>
        </div>
        <div class="divOrders" v-if="order[0]">
            <div class="title">最新订单</div>
            <div class="timeStatus">
                <span>{{order[0].orderTime}}</span>
                <span>{{getStatus(order[0].status)}}</span>
                <!-- <span>正在派送</span> -->
            </div>
            <div class="dishList">
                <div v-for="(item,index) in order[0].orderDetails" :key="index" class="item">
                    <span>{{item.name}}×{{item.number}}份</span>
                    <span>{{item.amount}}</span>
                </div>
            </div>
            <div class="result">
                <span>共{{order[0].sumNum}} 件商品,实付</span>
                <span class="price">￥{{order[0].amount}}</span>
            </div>
            <div class="btn" v-if="order[0].status === 4">
                <div class="btnAgain" @click="addOrderAgain">再来一单</div>
            </div>
        </div>
        <div class="quitLogin" @click="toPageLogin">
            退出登录
        </div>
    </div>
</div>
</template>

<script>
 import { ElConfigProvider  } from 'element-plus'
 import {orderPagingApi,orderAgainApi} from '../hooks/order'
//  import {imgPath} from '../hooks/common'
 import {edituserApi,loginoutApi} from '../hooks/login'
export default {
    components:{ElConfigProvider},
    data() {
            return {
                form: {},
                msgFlag: false,
                order: [{
                    orderTime: '',//下单时间
                    status: undefined,//订单状态 1已结账，2未结账，3已退单，4已完成，5已取消
                    orderDetails: [{
                        name: '',//菜品名称
                        number: undefined,//数量
                        amount: '',
                    }],//明细
                    amount: undefined,//实收金额
                    sumNum: 0,//菜品总数
                }],
                userId:''
            }
        },
        created() {
            this.userPhone = sessionStorage.getItem("userPhone")
            this.userId = sessionStorage.getItem("userId")
            this.initData()
            this.initUserData()
        },
        methods: {
            //初始化用户数据
            async initUserData() {
                const res = await edituserApi({phone: this.userPhone})
                if (res.data.code === 1) {
                    this.form = res.data.data
                } else {
                    console.log(res.data.console.error);
                }
            },
            goBack() {
                window.requestAnimationFrame(() => {
                    this.$router.push('/home')
                })
            },
            toAddressPage() {
                window.requestAnimationFrame(() => {
                    this.$router.push('/address')
                })
            },
            toOrderPage() {
                window.requestAnimationFrame(() => {
                    this.$router.push('/order')
                })
            },
            initData() {
                this.getLatestOrder()
            },
            async getLatestOrder() {
                const params = {
                    page: 1,
                    pageSize: 1,
                    userId:this.userId
                }
                const res = await orderPagingApi(params)
                if (res.data.code === 1) {
                    this.order = res.data.data.records
                    if (this.order && this.order[0].orderDetails) {
                        let number = 0
                        this.order[0].orderDetails.forEach(item => {
                            number += item.number
                        })
                        this.order[0].sumNum = number
                    }
                } else {
                    console.log(res.data.console.error);
                }
            },
            getStatus(status) {
                let str = ''
                switch (status) {
                    case 1:
                        str = '待付款'
                        break;
                    case 2:
                        str = '正在派送'
                        break;
                    case 3:
                        str = '已派送'
                        break;
                    case 4:
                        str = '已完成'
                        break;
                    case 5:
                        str = '已取消'
                        break;

                }
                return str
            },
            async addOrderAgain() {
                const res = await orderAgainApi({id: this.order[0].id,userId:this.userId})
                if (res.data.code === 1) {
                    window.requestAnimationFrame(() => {
                        this.$router.push('/home')
                    })
                } else {
                    console.log(res.data.console.error);
                }
            },
            async toPageLogin() {
                const res = await loginoutApi()
                sessionStorage.removeItem("userPhone")
                if (res.data.code === 1) {
                    window.requestAnimationFrame(() => {
                        this.$router.push('/login')
                    })
                } else {
                    console.log(res.data.console.error);
                }
            },
            //跳转修改信息页面
            async edit() {
                    window.requestAnimationFrame(() => {
                        this.$router.push('/edituser')
                    })
            },
            imgPathConvert() {
            console.log(this.form,'==========');
            return '/api/'+this.form.avatar
            },
        }
}
</script>

<style scoped>
#user {
    height: 100%;
}

#user .divHead {
    width: 100%;
    height: 164rem;
    opacity: 1;
    background: #ffc200;
    box-sizing: border-box;
    padding-left: 12rem;
    padding-right: 12rem;
}

#user .divHead .divTitle {
    height: 25rem;
    opacity: 1;
    font-size: 18rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 500;
    text-align: center;
    color: #333333;
    line-height: 25rem;
    letter-spacing: 0;
    padding-top: 50rem;
    margin-bottom: 18rem;
    position: relative;
}

#user .divHead .divTitle i {
    position: absolute;
    left: 0;
    margin-top: 5rem;
}

#user .divHead .divUser {
    display: flex;
}

#user .divHead .divUser .image {
    width: 58rem;
    height: 58rem;
    border-radius: 50%;
    margin-right: 16rem;
}

#user .divHead .divUser .desc {
    display: flex;
    flex-direction: column;
    justify-content: center;
}

#user .divHead .divUser .divUpdate {
    margin-left: 110rem;
    margin-top: 15rem;
    color: rgba(128, 122, 126, 0.71);
    font-size: 12rem;
}

#user .divHead .divUser .desc .divName {
    opacity: 1;
    font-size: 16rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 500;
    text-align: left;
    color: #333333;
    margin-right: 6rem;
    margin-bottom: 5rem;
    display: flex;
    align-items: center;
}

#user .divHead .divUser .desc .divName img {
    width: 16rem;
    height: 16rem;
    opacity: 1;
    margin-left: 6rem;
}

#user .divHead .divUser .desc .divPhone {
    font-size: 14rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 400;
    text-align: left;
    color: #333333;
}

#user .divContent {
    height: calc(100% - 174rem);
    overflow-y: auto;
}

#user .divLinks {
    height: 104rem;
    opacity: 1;
    background: #ffffff;
    border-radius: 6rem;
    padding-left: 17rem;
    padding-right: 11rem;
    margin: 10rem;
}

#user .divLinks .item {
    height: 51rem;
    line-height: 51rem;
    position: relative;
    display: flex;
    align-items: center;
    font-size: 16rem;
}


#user .divLinks .divSplit {
    height: 1rem;
    opacity: 1;
    background-color: #ebebeb;
    border: 0;
}

#user .divLinks .item img {
    width: 18rem;
    height: 18rem;
    margin-right: 5rem;
}

#user .divLinks .item i {
    position: absolute;
    right: 0;
    top: 50%;
    transform: translate(0, -50%);
}

#user .divOrders {
    margin: 0 10rem 10rem 10rem;
    background: #ffffff;
    border-radius: 6rem;
    padding-left: 10rem;
    padding-right: 10rem;
    padding-bottom: 17rem;
}

#user .divOrders .title {
    height: 60rem;
    line-height: 60rem;
    opacity: 1;
    font-size: 16rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 500;
    text-align: left;
    color: #333333;
    letter-spacing: 0;
    border-bottom: 2px solid #efefef;
}

#user .divOrders .timeStatus {
    height: 46rem;
    line-height: 16rem;
    font-size: 14rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 400;
    text-align: left;
    color: #666666;
    line-height: 20rem;
    letter-spacing: 0;
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-bottom: 2rem dashed #efefef;
}

#user .divOrders .timeStatus span:first-child {
    color: #333333;
}

#user .divOrders .dishList {
    padding-top: 10rem;
    padding-bottom: 11rem;
}

#user .divOrders .dishList .item {
    padding-top: 5rem;
    padding-bottom: 5rem;
    display: flex;
    justify-content: space-between;
    height: 20rem;
    opacity: 1;
    font-size: 14rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 400;
    text-align: left;
    color: #666666;
    line-height: 20rem;
    letter-spacing: 0;
}

#user .divOrders .result {
    display: flex;
    justify-content: flex-end;
    height: 20rem;
    opacity: 1;
    font-size: 14rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 400;
    text-align: left;
    color: #666666;
    line-height: 20rem;
}

#user .divOrders .result .price {
    color: black;
}

#user .divOrders .btn {
    margin-top: 20rem;
    display: flex;
    justify-content: flex-end;
}

#user .divOrders .btn .btnAgain {
    width: 124rem;
    height: 36rem;
    opacity: 1;
    border: 1px solid #e5e4e4;
    border-radius: 19rem;
    opacity: 1;
    font-size: 14rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 500;
    text-align: center;
    color: #333333;
    line-height: 36rem;
    letter-spacing: 0;
    position: relative;
}

#user .quitLogin {
    margin: 0 10rem 10rem 10rem;
    height: 50rem;
    opacity: 1;
    background: #ffffff;
    border-radius: 6rem;
    opacity: 1;
    font-size: 15rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 500;
    text-align: center;
    color: #333333;
    line-height: 50rem;
}

</style>