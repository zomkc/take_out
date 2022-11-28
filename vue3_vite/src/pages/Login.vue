<template>
    <div id="login" v-loading="loading">
    <div class="divHead">登录</div>
    <div class="divContainer" v-if="loginWay">
        <el-input placeholder="  请输入手机号码"  v-model="form.phone" maxlength='20'/>
<!--        <div class="divSplit"></div>-->
        <el-input placeholder=" 请输入验证码"  v-model="form.code" maxlength='20'/>
        <span v-if="codeShow" style="color:#ff0000;"  @click='getCode'>获取验证码</span>
        <!--倒计时-->
        <span v-if="!codeShow" style="color:#bbbbbb;" class="count">{{count}}秒后重试</span>
    </div>
    <div class="divContainer" v-if="!loginWay">
        <el-input placeholder="  请输入手机号码"  v-model="form.phone" maxlength='20'/>
        <!--        <div class="divSplit"></div>-->
        <el-input placeholder=" 请输入密码" type="password" v-if="!loginWay" v-model="form.password" maxlength='20'/>
    </div>

    <div class="divMsg" v-if="msgFlag">手机号输入不正确，请重新输入</div>

    <el-button type="primary" v-if="loginWay" :class="{btnSubmit:1===1,btnNoPhone:!form.phone,btnPhone:form.phone}" @click="btnLogin">
        登录
    </el-button>

    <el-button type="primary" v-if="!loginWay" :class="{btnSubmit:1===1,btnNoPhone:!form.phone,btnPhone:form.phone}" @click="passwordbtnLogin">
        密码登录
    </el-button>
        <div class="login_hint">
            <span>
               温馨提示：未注册帐号的手机号，登录时将自动注册，且代表已同意
            </span>
            <a href="javascript:;">《用户服务协议》</a>
        </div>

    <div class="password">
            <span  :class="{on: !loginWay}" v-if="loginWay">
              <a href="javascript:;" @click="loginWay = false">密码登录</a>
            </span>
        <span :class="{on: loginWay}" v-if="!loginWay">
                <a href="javascript:;"  @click="loginWay = true">短信登录</a>
            </span>
    </div>


</div>
</template>
<script>
//  import { ref} from 'vue';
 import { ElConfigProvider  } from 'element-plus'
 import {loginApi,passwordloginApi,sendMsgApi} from '../hooks/login'

export default {
// setup() {
// const a = ref('login')

// return{a}
// },
// methods: {
//   login() {
//     sessionStorage.setItem('zomkc','zomkc')
//     this.$router.push('/home')
//    }
// },
components:{ElConfigProvider},
        data() {
            return {
                loginWay: true,
                form: {
                    phone: '19980841590',
                    code: '',
                    password: '123',
                },
                msgFlag: false,
                loading: false,
                codeShow:true,
                count: '',
                timer: null,
            }
        },
        methods: {
            getCode() {
                this.form.code = ''
                //const regex = /^(13[0-9]{9})|(15[0-9]{9})|(17[0-9]{9})|(18[0-9]{9})|(19[0-9]{9})$/;
                const regex = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
                const TIME_COUNT = 60;
                if (regex.test(this.form.phone)) {
                    this.count = TIME_COUNT;
                    this.codeShow = false;
                    this.timer = setInterval(() => {
                        //========60秒倒计时
                        if (this.count > 0 && this.count <= TIME_COUNT) {
                            this.count--;
                        } else {
                            this.codeShow = true;
                            clearInterval(this.timer);
                            this.timer = null;
                        }
                        }, 1000);
                        //=======================

                    this.msgFlag = false
                    //this.form.code = (Math.random() * 1000000).toFixed(0)
                    sendMsgApi({phone:this.form.phone})
                } else {
                    this.msgFlag = true
                }
            },
            async btnLogin() {
                if (this.form.phone && this.form.code) {
                    this.loading = true
                    const res = await loginApi({phone: this.form.phone,code: this.form.code})
                    this.loading = false
                    if (res.data.code === 1) {
                        sessionStorage.setItem("userPhone", this.form.phone)
                        window.requestAnimationFrame(() => {
                            this.$router.push('/home')
                        })
                    } else {
                        ElMessage.error('登录失败')
                    }
                } else {
                    ElMessage.error('请输入手机号或验证码')
                }
            },
                //密码登录
                async passwordbtnLogin() {
                const regex = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
                    if (regex.test(this.form.phone)) {
                        if (this.form.phone && this.form.password) {
                            this.loading = true
                            const res = await passwordloginApi({phone: this.form.phone,password: this.form.password})
                            this.loading = false
                            if (res.data.code === 1) {
                                sessionStorage.setItem("userPhone", this.form.phone)
                                window.requestAnimationFrame(() => {
                                    this.$router.push('/home')
                                })
                            } else {
                                ElMessage.error('登录失败')
                            }
                        }else {
                            ElMessage.error({
                                message:'请输入手机号或密码'
                            })
                        }
                        this.msgFlag = false
                    }else {
                        this.msgFlag = true
                    }
             }
        }


}
</script>

<style>
a {
    text-decoration: none;  /* 取消下划线 */
}
#login .divHead {
    opacity: 1;
    background: #333333;
    height: 88rem;
    width: 100%;
    font-size: 18rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 500;
    text-align: center;
    color: #ffffff;
    line-height: 88rem;
}

#login .divContainer {
    width: 356rem;
    height: 128rem;
    opacity: 1;
    background: #ffffff;
    border-radius: 6rem;
    margin: 0 auto;
    margin-top: 10rem;
    position: relative;
}

#login .divContainer input {
    border: 0;
    height: 63rem;
}

#login .divContainer .divSplit {
    height: 1px;
    background-color: #efefef;
    border: 0;
    margin-left: 10rem;
    margin-right: 10rem;
}

#login .divContainer span {
    position: absolute;
    right: 20rem;
    top: 20rem;
    cursor: pointer;
    opacity: 1;
    font-size: 12rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 400;
    text-align: left;
    color: #ffc200;
    letter-spacing: 0px;
}

#login .divMsg {
    width: 168px;
    height: 17px;
    opacity: 1;
    font-size: 12px;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 400;
    text-align: center;
    color: #e94e3c;
    line-height: 17px;
    margin-left: 26rem;
    margin-top: 10rem;
}


#login .btnSubmit {
    width: 356rem;
    height: 40rem;
    margin: 20rem 10rem 0 10rem;
    border-radius: 20rem;
    border: 0;

    font-size: 15rem;
    font-family: PingFangSC, PingFangSC-Regular;
    font-weight: 500;
    text-align: center;
}

#login .btnNoPhone {
    color: #666666;
    background: #d8d8d8;
}

#login .btnNoPhone:active {
    background: #afafaf;
}

#login .btnPhone {
    background: #ffc200;
    color: #333333;
}

#login .btnPhone:active {
    background: rgba(255, 142, 0, 1);
    color: #333333;
}

#login  .login_hint{
    width: 80%;
    height: 50rem;
    text-align: center;
    margin: 20rem 10rem 0rem 35rem;
    color: #b8b8b8;
    font-size: 12rem;
}

#login .password {
    width: 150rem;
    height: 17rem;
    float: right;
}
#login .password span{
    float: right;
    margin-right: 30rem;
    font-size: 12rem;
}

#login .password span a{
    color: #666666;
}

.didi{
    width: 100%;
    margin-top: 150rem;
    text-align: center;
    background-color: rgba(168, 167, 155, 0.51);
}
.didi span{
    color: #ffffff;
    font-size: 15rem;
}


</style>