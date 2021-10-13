<template>
    <div class="register-wrap">
        <div class="ms-title">留言板-欢迎注册</div>
        <div class="ms-login">
            <el-form :model="ruleForm" :rules="rules" ref='ruleForm' label-width="0px" class="demo-ruleForm">
                <div v-if="errorInfo" style="margin-bottom: 5px">
                    <span>{{errInfo}}</span>
                </div>
                <el-form-item prop="userName" >
                    <el-input v-model="ruleForm.userName" placeholder="用户名" ></el-input>
                </el-form-item>
                <el-form-item prop="userPassword" >
                    <el-input v-model="ruleForm.userPassword"  placeholder="密码" ></el-input>
                </el-form-item>

                <el-form-item prop="email" >
                    <el-input v-model="ruleForm.email"  placeholder="邮箱" @keyup.enter.native="submitForm('ruleForm')"></el-input>
                </el-form-item>

                <div class="register-btn">
                    <el-button type="primary" @click="submitForm('ruleForm')">注册</el-button>
                </div>
                <p class="register" @click="gotoLogin()">登录</p>
            </el-form>
        </div>
    </div>
</template>

<script>
    import api from "@/api/index"

    export default {
        name: "register",
        data() {
            return {
                errorInfo:false,
                ruleForm: {
                    userName:'',
                    userPassword:'',
                    email:''
                },
                rules:{
                    userName: [
                        {required:true,message:"请输入用户名",trigger:'blur'}
                    ],
                    userPassword: [
                        {required:true,message:"请输入密码",trigger: 'blur'}
                    ],
                    email: [
                        {required:true,message:"请输入邮箱",trigger:'blur'}
                    ]
                }
            }
        },
        mounted(){

        },
        methods: {
            async submitForm(formName){
            const self =this;
                //console.log(self.$refs[formName])
            self.$refs[formName].validate(async (validate) =>{
                // console.log(self.ruleForm)
                if(validate){
                    let res=await api.register(self.ruleForm)
                     console.log(res)
                    if (res.data.status==-1){
                        self.errorInfo=true,
                        self.errInfo=res.data.content
                        // self.$message.error(res.data.content)
                        self.ruleForm.userName
                    }else if (res.data.status==0){
                        self.$message.success(res.data.content)
                        await this.$router.push('/login')
                    }
                }else{
                    console.log("error register!");
                    return false;
                }
            })


            },
            gotoLogin() {
                this.$router.push('/login')
            }
        }
    }
</script>

<style scoped>
.register-wrap {
    position: fixed;
    width: 100%;
    height: 100%;
    background: #999999;
}

.ms-title {
    position: absolute;
    top: 50%;
    width: 100%;
    margin-top: -230px;
    text-align: center;
    font-size: 30px;
    color: #fff;

}

.ms-login {
    position: absolute;
    left: 50%;
    top: 50%;
    width: 300px;
    height: 260px;
    margin: -150px 0 0 -190px;
    padding: 40px;
    border-radius: 5px;
    background: #fff;
}

.ms-login span {
    color: red;
}

.register-btn {
    text-align: center;
}

.register-btn button {
    width: 100%;
    height: 36px;
}


.register {
    font-size: 14px;
    line-height: 30px;
    color: #999;
    cursor: pointer;
    float: right;
}
</style>