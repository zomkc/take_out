<template>
  <div id="order" class="app">
    <div class="divHead">
        <div class="divTitle">
            <!-- <i class="el-icon-arrow-left" @click="goBack"></i> -->
            <el-icon @click="goBack"><ArrowLeft /></el-icon>
            历史订单
        </div>
    </div>
    <div class="divBody" v-if="orderList.length > 0">
        <van-list
                v-model="loading"
                :finished="finished"
                finished-text="没有更多了"
                @load="getList"
        >
            <van-cell v-for="(order,index) in orderList" :key="index" class="item">
                <div class="timeStatus">
                    <span>{{order.orderTime}}</span>
                    <span>{{getStatus(order.status)}}</span>
                    <!-- <span>正在派送</span> -->
                </div>
                <div class="dishList">
                    <div v-for="(item,index) in order.orderDetails" :key="index" class="item">
                        <span>{{item.name}}×{{item.number}}份</span>
                        <span>{{item.amount}}</span>
                    </div>
                </div>
                <div class="result">
                    <span>共{{order.sumNum}} 件商品,实付</span><span class="price">￥{{order.amount}}</span>
                </div>
                <div class="btn" v-if="order.status === 4">
                    <div class="btnAgain" @click="addOrderAgain(order)">再来一单</div>
                </div>
            </van-cell>
        </van-list>
    </div>
    <div class="divNoData" v-else>
        <div class="divContainer">
            <img src="/images/no_order.png"/>
            <div>暂无订单</div>
        </div>
    </div>
</div>
</template>

<script>
import {orderPagingApi,orderAgainApi} from '../hooks/order'
export default {
    data() {
            return {
                paging: {
                    page: 1,
                    pageSize: 5,
                    userId:sessionStorage.getItem("userId")
                },
                orderList: [],
                loading: false,
                finished: false
            }
        },
        created() {
            this.initData()
        },
        methods: {
            goBack() {
                const url = document.referrer
                //表示是从订单页面跳转过来的
                if (url.includes('success')) {
                    window.requestAnimationFrame(() => {
                        this.$router.push('/home')
                    })
                } else {
                    history.go(-1)
                }
            },
            initData() {
                this.getList()
            },
            async getList() {
                if (this.finished) {
                    this.loading = false;
                    return
                }
                const res = await orderPagingApi(this.paging)
                if (res.data.code === 1) {
                    this.orderList.push(...res.data.data.records)
                    let records = res.data.data.records
                    if (records && Array.isArray(records)) {
                        records.forEach(item => {
                            let number = 0
                            item.orderDetails.forEach(ele => {
                                number += ele.number
                            })
                            item.sumNum = number
                        })
                    }
                    this.loading = false;
                    if (this.paging.page >= res.data.pages) {
                        this.finished = true;
                    }
                    this.paging.page = this.paging.page + 1
                } else {
                    console.log(res.data.console.error);
                }
            },
            async addOrderAgain(order) {
                const res = await orderAgainApi({id: order.id,userId:sessionStorage.getItem("userId")})
                if (res.data.code === 1) {
                    window.requestAnimationFrame(() => {
                       this.$router.push('/home')
                    })
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
            }
        }
}
</script>

<style scoped>
    #order {
    height: 100%;
}

#order .divHead {
    width: 100%;
    height: 88rem;
    opacity: 1;
    background: #333333;
    position: relative;
}

#order .divHead .divTitle {
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

#order .divHead .divTitle i {
    position: absolute;
    left: 16rem;
    top: 50%;
    transform: translate(0, -50%);
}

#order .divBody {
    margin: 10rem 12rem 10rem 12rem;
    background: #ffffff;
    border-radius: 6rem;
    padding-left: 10rem;
    padding-right: 10rem;
    height: calc(100% - 108rem);
    overflow-y: auto;
}

#order .divBody .van-list .van-cell::after {
    border: 0;
}

#order .divBody .item .timeStatus {
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
    border-top: 1px solid #efefef;
}

#order .divBody .item .timeStatus span:first-child {
    color: #333333;
}

#order .divBody .item .dishList {
    padding-top: 10rem;
    padding-bottom: 11rem;
}

#order .divBody .item .dishList .item {
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

#order .divBody .item .result {
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

#order .divBody .item .result .price {
    color: #343434;
}

#order .divBody .item .btn {
    display: flex;
    justify-content: flex-end;
    margin-bottom: 17rem;
    margin-top: 20rem;
}

#order .divBody .btn .btnAgain {
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

#order .divNoData {
    width: 100%;
    height: calc(100% - 88rem);
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
}

#order .divNoData .divContainer img {
    width: 240rem;
    height: 129rem;
}

#order .divNoData .divContainer div {
    font-size: 24rem;
    font-family: PingFangSC, PingFangSC-Medium;
    font-weight: 500;
    text-align: center;
    color: #333333;
    line-height: 33rem;
    height: 33rem;
    margin-top: 20rem;
}

</style>