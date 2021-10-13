<template>
    <div class="login-wrap">
        <div class="ms-title">留言板-欢迎登陆</div>
        <div class="ms-login">
            <el-form :model="ruleForm" :rules="rules" ref='ruleForm' label-width="0px" class="demo-ruleForm">
                <div v-if="errorInfo" style="margin-bottom: 5px">
                    <span>{{errInfo}}</span>
                </div>
                <el-form-item prop="username" >
                    <el-input v-model="ruleForm.username" placeholder="请输入用户名/邮箱" @keyup.enter.native="submitForm('ruleForm')" ></el-input>
                </el-form-item>
                <el-form-item prop="password" >
                    <el-input v-model="ruleForm.password" type="password" placeholder="请输入密码" @keyup.enter.native="submitForm('ruleForm')"></el-input>
                </el-form-item>
                <el-checkbox v-model="ruleForm.remember" true-label="true">remember me</el-checkbox>
                <div class="login-btn">
                    <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
                </div>
                <p class="register" @click="gotoRegister()">注册</p>
            </el-form>
        </div>
    </div>
</template>

<script>
    import api from "@/api/index"
    import {mapMutations} from "vuex";

    export default {
        data() {
            return {
                errorInfo:false,
                ruleForm:{
                    username:'',
                    password:'',
                    remember:'',
                },
                userToken:'',
                rules:{
                    username: [
                        {required:true,message:"请输入用户名",trigger:'blur'}
                    ],
                    password: [
                        {required:true,message:"请输入密码",trigger: 'blur'}
                    ],
                }
            }
        },
        methods: {
           ...mapMutations(['changeLogin']),
           submitForm(formName){
               const self=this;
               self.$refs[formName].validate(async (valid) => {
                   if(valid){
                      let user = {}
                      user["userName"] = self.ruleForm.username
                      user["userPassword"] = self.ruleForm.password
                       user["remember"] = self.ruleForm.remember
                       console.log(user)
                      let validInfo = await api.loginUser(user)
                      // console.log(validInfo)
                      if(validInfo.data.status==-1){
                          self.errorInfo=true,
                          self.errInfo=validInfo.data.content,
                          self.$message.error(validInfo.data.content)
                          this.refreshCode()
                      }
                      else if (validInfo.data.status==0){
                          console.log(validInfo.data.content.user)
                          self.userToken=validInfo.data.content.token,
                              // 将用户token保存到vuex中
                          this.$store.commit('Authorization',self.userToken)
                          self.$message.success("登录成功"),
                          await self.$router.push('/comment')
                      }
                   }else{
                       console.log("error submit!");
                       return false;
                   }
               })
           },
            gotoRegister(){
                this.$router.push('/register')
            },
        }
    }
</script>

<style scoped>
.login-wrap {
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

.login-btn {
    text-align: center;
}

.login-btn button {
    width: 100%;
    height: 36px;
}

.code {
    width: 112px;
    height: 35px;
    border: 1px solid #ccc;
    float: right;
    border-radius: 2px;
}

.validate-code {
    width: 136px;
    float: left;
}

.register {
    font-size: 14px;
    line-height: 30px;
    color: #999;
    cursor: pointer;
    float: right;
}
</style>