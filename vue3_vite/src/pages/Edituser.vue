<template>
   <div id="app" class="app">
    <div class="divHead">
        <div class="divTitle">
            <el-icon @click="goBack"><ArrowLeft /></el-icon>
            修改信息
        </div>
    </div>
        <el-form label-width="80px" class="form" :model="form">
            <el-form-item label="头像" >
                <div class="uploader">
                    <el-upload
                            action="/api/common/upload"
                            :show-file-list="false"
                            :on-success="handleAvatarSuccess"
                            :before-upload="beforeAvatarUpload">
                        <img v-if="form.avatar" :src="'/api'+form.avatar" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </div>
            </el-form-item>
            <el-form-item label="手机号">
                <el-input  type="phone" v-model="form.phone"></el-input>
            </el-form-item>
            <el-form-item label="昵称">
                <el-input type="name" v-model="form.name"></el-input>
            </el-form-item>
            <el-form-item label="密码" >
                <el-input type="password" v-model="form.password"></el-input>
            </el-form-item>
            <el-form-item label="性别" >
                <el-radio v-model="form.sex" label="1">男</el-radio>
                <el-radio v-model="form.sex" label="2">女</el-radio>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitForm">确认修改</el-button>
                <el-button type="primary" @click="cancelForm">取消</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
 import { ElConfigProvider  } from 'element-plus'
import {edituserApi,editApi} from '../hooks/login'
export default {
    components:{ElConfigProvider},
    data(){
                return {
                    form: {
                        // phone: '123',//手机号
                        // name: '张三',//姓名
                        // sex: '1',//性别,1男,2女
                        // avatar: '', //头像
                        // password: '123'//密码
                    },
                }
            },
            created() {
                this.userPhone = sessionStorage.getItem("userPhone")
                this.initData()
            },
            methods: {
                goBack(){
                    history.go(-1)
                },
                //初始化用户数据
                async initData() {
                    const res = await edituserApi({phone: this.userPhone})
                    if (res.data.code === 1) {
                        this.form = res.data.data
                    } else {
                        console.log(res.data.console.error);
                    }
                },
                beforeAvatarUpload(file) {
                    if (file) {
                        const suffix = file.name.split('.')[1]
                        const size = file.size / 1024 / 1024 < 2
                        if (['png', 'jpeg', 'jpg'].indexOf(suffix) < 0) {
                            this.$message.error('上传图片只支持 png、jpeg、jpg 格式！')
                            this.$refs.upload.clearFiles()
                            return false
                        }
                        if (!size) {
                            this.$message.error('上传文件大小不能超过 2MB!')
                            return false
                        }
                        return file
                    }
                },
                //上传成功后返回头像地址
                handleAvatarSuccess(response, file, fileList) {
                    this.form.avatar = `/common/download?name=${response.data}`
                    // alert(this.form.avatar)
                },
                //确认修改
                async submitForm(){
                    const res = await editApi(this.form)
                    if (res.data.code === 1) {
                        window.requestAnimationFrame(() => {
                            this.$router.push('/user')
                        })
                    }else {
                        console.log(res.data.console.error);
                    }
                },
                //取消修改
                cancelForm(){
                    window.requestAnimationFrame(() => {
                        this.$router.push('/user')
                    })
                },
            }
}
</script>

<style scoped>
#app {
    width: 100%;
    height: 100%;
}

#app .divHead {
    width: 100%;
    height: 88rem;
    opacity: 1;
    background: #333333;
    position: relative;
}

#app .divHead .divTitle {
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

#app .divHead .divTitle i {
    position: absolute;
    left: 16rem;
    top: 50%;
    transform: translate(0, -50%);
}

#app .form{
    width: 80%;
}

#app .form .uploader{
    width: 50rem;
    height: 50rem;
}

#app .form .uploader .avatar{
    width: 50rem;
    height: 50rem;
    display: block;
}
</style>