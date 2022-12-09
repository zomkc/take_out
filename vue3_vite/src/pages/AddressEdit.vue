<template>
  <div id="address_edit" class="app">
    <div class="divHead">
        <div class="divTitle">
            <el-icon @click="goBack"><ArrowLeft /></el-icon>
            {{title}}
        </div>
    </div>
    <div class="divContent">
        <div class="divItem">
            <span>联系人：</span>
            <el-input placeholder=" 请填写收货人的姓名" v-model="form.consignee" maxlength='10' class="inputUser"/>
            <span class="spanChecked" @click="form.sex = '1'">
                    <i :class="{iActive:form.sex === '1'}"></i>
                    先生
                   </span>
            <span class="spanChecked" @click="form.sex = '0'">
                    <i :class="{iActive:form.sex === '0'}"></i>
                    女士
                </span>
        </div>
        <div class="divItem">
            <span>手机号：</span>
            <el-input placeholder=" 请填写收货人手机号码" v-model="form.phone" maxlength='20' style="width: calc(100% - 80rem);"/>
        </div>
        <div class="divItem">
            <span>收货地址：</span>
            <el-input placeholder=" 请输入收货地址" v-model="form.detail" maxlength='140'/>
        </div>
        <div class="divItem ">
            <span>标签：</span>
            <span v-for="(item,index) in labelList" :key="index" @click="form.label = item;activeIndex = index"
                  :class="{spanItem:true,spanActiveSchool:activeIndex === index}">{{item}}</span>
        </div>
        <div class="divSave" @click="saveAddress">保存地址</div>
        <div class="divDelete" @click="deleteAddress" v-if="id">删除地址</div>
    </div>
</div>
</template>

<script>
 import { ElConfigProvider  } from 'element-plus'
 import {addressFindOneApi,updateAddressApi,addAddressApi,deleteAddressApi} from '../hooks/address'
export default {
    components:{ElConfigProvider},
    data() {
            return {
                title: '新增收货地址',
                form: {
                    consignee: '',//联系人
                    phone: undefined,//手机号
                    sex: '1',//0表示女 1 表示男
                    detail: '',//收货地址
                    label: '公司',//标签
                    userId:sessionStorage.getItem("userId")
                },
                labelList: [
                    '无', '公司', '家', '学校'
                ],
                id: undefined,
                activeIndex: 0,
                userid:''
            }
        },
        created() {
            this.initData()
        },
        methods: {
            goBack() {
                this.$router.push('/address')
            },
            async initData() {
                // const params = parseUrl(window.location.search)
                // this.id = params.id
                const params  =  sessionStorage.getItem('addressId')
                this.id = params
                sessionStorage.removeItem('addressId')
                if (params) {
                    this.title = '编辑收货地址'
                    const res = await addressFindOneApi(params)
                    if (res.data.code === 1) {
                        this.form = res.data.data
                    } else {
                        console.log(res.data.error);
                    }
                }
            },
            async saveAddress() {
                const form = this.form
                if (!form.consignee) {
                    // this.$notify({type: 'warning', message: '请输入联系人'});
                    ElMessage.error('请输入联系人')
                    return
                }
                if (!form.phone) {
                    // this.$notify({type: 'warning', message: '请输入手机号'});
                    ElMessage.error('请输入手机号')
                    return
                }
                if (!form.detail) {
                    // this.$notify({type: 'warning', message: '请输入收货地址'});
                    ElMessage.error('请输入收货地址')
                    return
                }
                const reg = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
                if (!reg.test(form.phone)) {
                    // this.$notify({type: 'warning', message: '手机号码不合法'});
                    ElMessage.error('请输入正确的手机号码')
                    return
                }
                let res = {}
                if (this.id) {
                    res = await updateAddressApi(this.form)
                } else {
                    res = await addAddressApi(this.form)
                }
                console.log(res.data)
                if (res.data.code === 1) {
                    window.requestAnimationFrame(() => {
                        this.$router.push('/address')
                    })
                } else {
                    console.log(res.data.console.error);
                }
            },
            deleteAddress() {
                this.$dialog.confirm({
                    title: '确认删除',
                    message: '确认要删除当前地址吗？',
                }).then(async () => {
                        const res = await deleteAddressApi({ids: this.id})
                        if (res.data.code === 1) {
                            window.requestAnimationFrame(() => {
                                this.$router.push('/address')
                            })
                        } else {
                            ElMessage.error(res.data.error)
                        }
                    })
                    .catch(() => {
                    });
            },
        }
}
</script>

<style scoped>
#address_edit {
    height: 100%;
}

#address_edit .divHead {
    width: 100%;
    height: 88rem;
    opacity: 1;
    background: #333333;
    position: relative;
}

#address_edit .divHead .divTitle {
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

#address_edit .divHead .divTitle i {
    position: absolute;
    left: 16rem;
    top: 50%;
    transform: translate(0, -50%);
}

#address_edit .divContent {
    height: 100%;
    opacity: 1;
    background: #ffffff;
    padding-left: 9rem;
    padding-right: 9rem;
}

#address_edit .divContent .divItem {
    height: 55rem;
    line-height: 55rem;
    font-size: 14rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 500;
    text-align: left;
    color: #333333;
    line-height: 20rem;
    letter-spacing: 0rem;
    border-bottom: 1px solid #efefef;
    display: flex;
    align-items: center;
}

#address_edit .divContent .divItem .el-input {
    width: auto;
}

#address_edit .divContent .divItem input {
    border: 0;
    padding: 0;
}

#address_edit .divContent .divItem .inputUser {
    width: 150rem;
}

#address_edit .divContent .divItem span {
    display: block;
}

#address_edit .divContent .divItem span:first-child {
    margin-right: 12rem;
    white-space: nowrap;
    width: 69rem;
}

#address_edit .divContent .divItem .spanChecked {
    width: 50rem;
}

#address_edit .divContent .divItem span i {
    width: 16rem;
    height: 16rem;
    background: url(./../images/checked_false.png);
    display: inline-block;
    background-size: cover;
    vertical-align: sub;
}

#address_edit .divContent .divItem span .iActive {
    background: url(./../images/checked_true.png);
    background-size: cover;
}

#address_edit .divContent .divItem .spanItem {
    width: 34rem;
    height: 20rem;
    opacity: 1;
    border: 1px solid #e5e4e4;
    border-radius: 3rem;
    text-align: center;
    margin-right: 10rem;
    border-radius: 2px;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 400;
    color: #333333;
}

#address_edit .divContent .divItem .spanActiveCompany {
    background: #e1f1fe;
}

#address_edit .divContent .divItem .spanActiveHome {
    background: #fef8e7;
}

#address_edit .divContent .divItem .spanActiveSchool {
    background: #e7fef8;
}

#address_edit .divContent .divItem .el-input__inner {
    font-size: 13px;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 400;
    text-align: left;
    color: #333333;
}

#address_edit .divContent .divSave {
    height: 36rem;
    opacity: 1;
    background: #ffc200;
    border-radius: 18rem;
    font-size: 15rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 500;
    text-align: center;
    color: #333333;
    line-height: 36rem;
    margin-top: 20rem;
}

#address_edit .divContent .divDelete {
    height: 36rem;
    opacity: 1;
    background: #f6f6f6;
    border-radius: 18rem;
    font-size: 15rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 500;
    text-align: center;
    color: #333333;
    line-height: 36rem;
    margin-top: 20rem;
}

</style>