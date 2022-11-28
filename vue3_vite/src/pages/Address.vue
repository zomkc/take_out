<template>
  <div id="address" class="app">
    <div class="divHead">
        <div class="divTitle">
            <el-icon @click="goBack"><ArrowLeft /></el-icon>
            地址管理
        </div>
    </div>
    <div class="divContent">
        <div class="divItem" v-for="(item,index) in addressList" :key="index" @click.capture="itemClick(item)">
            <div class="divAddress">
                <span :class="{spanCompany:item.label === '公司',spanHome:item.label === '家',spanSchool:item.label === '学校'}">{{item.label}}</span>
                {{item.detail}}
            </div>
            <div class="divUserPhone">
                <span>{{item.consignee}}</span>
                <span>{{item.sex === '0' ? '女士' : '先生'}}</span>
                <span>{{item.phone}}</span>
            </div>
            <img src="/images/edit.png" @click.stop.prevent="toAddressEditPage(item)"/>
            <div class="divSplit"></div>
            <div class="divDefault">
                <img src="/images/checked_true.png" v-if="item.isDefault === 1">
                <img src="/images/checked_false.png" @click.stop.prevent="setDefaultAddress(item)" v-else>设为默认地址
            </div>
        </div>
    </div>
    <div class="divBottom" @click="toAddressCreatePage">+ 添加收货地址</div>
</div>
</template>

<script>
import {addressListApi,setDefaultAddressApi} from '../hooks/address'
export default {
    data() {
            return {
                addressList: [
                    //     {
                    //     label:'公司',
                    //     detail:'金燕龙写字楼 4层电梯口fsdfdsfsdfsdfsssssssssssss（电梯可…）',
                    //     consignee:'周先生',//姓名
                    //     phone:18544445566,//手机号
                    //     isDefault:0,//是否是默认
                    // }
                ],
            }
        },
        created() {
            this.initData()
        },
        methods: {
            goBack() {
               this.$router.push('/user')
            },
            toAddressEditPage(item) {
                window.requestAnimationFrame(() => {
                    // window.location.href = `/#addressedit?id=${item.id}`
                    sessionStorage.setItem('addressId',item.id)
                    this.$router.push('/addressedit')
                })
            },
            toAddressCreatePage() {
                window.requestAnimationFrame(() => {
                    this.$router.push('/addressedit')
                })
            },
            async initData() {
                const res = await addressListApi()
                if (res.data.code === 1) {
                    this.addressList = res.data.data
                } else {
                    console.log(res.data.console.error);
                }
            },
            async setDefaultAddress(item) {
                if (item.id) {
                    const res = await setDefaultAddressApi({id: item.id})
                    if (res.data.code === 1) {
                        this.initData()
                    } else {
                        console.log(res.data.console.error);
                    }
                }
            },
            itemClick(item) {
                const url = document.referrer
                //表示是从订单页面跳转过来的
                if (url.includes('order')) {
                    this.setDefaultAddress(item)
                    history.go(-1)
                }
            }

        }
}
</script>

<style scoped>
#address .divHead {
    width: 100%;
    height: 88rem;
    opacity: 1;
    background: #333333;
    position: relative;
}

#address .divHead .divTitle {
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

#address .divHead .divTitle i {
    position: absolute;
    left: 16rem;
    top: 50%;
    transform: translate(0, -50%);
}

#address .divContent {
    height: calc(100vh - 157rem);
    overflow: auto;
}

#address .divContent .divItem {
    height: 128rem;
    opacity: 1;
    background: #ffffff;
    border-radius: 6rem;
    margin-top: 10rem;
    margin-left: 10rem;
    margin-right: 9rem;
    padding-left: 12rem;
    position: relative;
}

#address .divContent .divItem > img {
    width: 16rem;
    height: 16rem;
    position: absolute;
    top: 40rem;
    right: 24rem;
}

#address .divContent .divItem .divDefault img {
    width: 16rem;
    height: 16rem;
    opacity: 1;
}

#address .divContent .divItem .divAddress {
    font-size: 14rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 400;
    text-align: left;
    color: #333333;
    line-height: 20rem;
    letter-spacing: 0;
    padding-top: 21rem;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    width: 280rem;
}

#address .divContent .divItem .divAddress span {
    width: 34rem;
    height: 20rem;
    opacity: 1;
    font-size: 12rem;
    display: inline-block;
    text-align: center;
    margin-right: 4rem;
    margin-bottom: 10rem;
}

#address .divContent .divItem .divUserPhone span {
    height: 20rem;
    opacity: 1;
    font-size: 14rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 400;
    text-align: left;
    color: #999999;
    line-height: 20rem;
    letter-spacing: 0;
    margin-right: 10rem;
}

#address .divContent .divItem .divUserPhone span:first-child {
    margin-right: 2rem;
}

#address .divContent .divItem .divAddress .spanCompany {
    background-color: #e1f1fe;
}

#address .divContent .divItem .divAddress .spanHome {
    background: #fef8e7;
}

#address .divContent .divItem .divAddress .spanSchool {
    background: #e7fef8;
}

#address .divContent .divItem .divSplit {
    height: 1px;
    opacity: 1;
    background: #efefef;
    border: 0;
    margin-top: 16rem;
    margin-bottom: 10rem;
    margin-right: 10rem;
}

#address .divContent .divItem .divDefault {
    height: 18rem;
    opacity: 1;
    font-size: 13rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 400;
    text-align: left;
    color: #333333;
    line-height: 18rem;
    letter-spacing: 0;
}

#address .divContent .divItem .divDefault img {
    height: 18rem;
    width: 18rem;
    margin-right: 5rem;
    vertical-align: bottom;
}

#address .divBottom {
    height: 36rem;
    opacity: 1;
    background: #ffc200;
    border-radius: 18rem;
    opacity: 1;
    font-size: 15rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 500;
    text-align: center;
    color: #333333;
    line-height: 36rem;
    letter-spacing: 0;
    position: absolute;
    bottom: 23rem;
    left: 50%;
    transform: translate(-50%, 0);
    width: 334rem;
}

</style>