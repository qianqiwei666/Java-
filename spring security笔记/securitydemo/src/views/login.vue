<template>
  <div id="login">
    <div id="login_data">
      <el-form label-position="right" :rules="rules" ref="login_form" :model="login_data" label-width="80px">
        <el-form-item label="用户名" prop="username">
          <el-input type="text" v-model="login_data.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="login_data.password"></el-input>
        </el-form-item>
        <el-form-item label="验证码" prop="code">
          <el-input type="text" v-model="login_data.code">
            <img slot="append" :src="image_code" class="code_img" @click="changCode"></img>
          </el-input>
        </el-form-item>
        <el-button type="primary" @click="submit_form('login_form')">提交</el-button>
        <span @click="register">还没有注册?</span>
      </el-form>
    </div>
  </div>


</template>

<script>
import router from "@/router";
import cookie from "js-cookie";
export default {
  name: "login",
  mounted() {
    cookie.remove('Authentication');
    this.$store.dispatch("verification_code").then(result=>{
        this.image_code="data:image/jpeg;base64,"+result.data;
    }).catch(error=>{
      this.$message.error("获取验证码失败");
    })
  },
  data() {
    let username = (rule, value, callback) => {
      if (value == '') {
        callback(new Error('请输入用户名'));
      } else if (value.length > 20) {
        callback(new Error('用户名长度必须小于20'))
      }else{
        callback();
      }
    };

    let password = (rule, value, callback) => {
      if (value == '') {
        callback(new Error('请输入密码'));
      }else{
        callback();
      }
    };

    let code = (rule, value, callback) => {
      if (value == '') {
        callback(new Error('请输入验证码'));
      }else{
        callback();
      }
    };
    return {
      login_data: {
        username: '',
        password: '',
        code: ''
      },
      rules: {
        username: [
          {validator: username, trigger: 'blur'}
        ],
        password: [
          {validator: password, trigger: 'blur'}
        ],
        code: [
          {validator: code, trigger: 'blur'}
        ]
      },
      image_code:''
    }
  },
  methods: {
    register() {
      router.push({path: '/register'})
    },
    submit_form(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$store.dispatch("login",this.login_data).then(result=>{
            cookie.set("Authentication",result.data);
            this.$message.success("登录成功");
            router.push({path:'/selectManager'})
          }).catch(error=>{
            if (error.response.status==404){
              this.$message.error("服务异常")
            }else if (error.response.status==402){
              this.$message.error("用户名与密码错误")
            }else if(error.response.status==406){
              this.$message.error("验证码错误")
            }else{
              this.$message.error("网络异常")
            }
          });
        } else {
          this.$message.error("请正确填写表单!")
        }
      });
    },
    changCode(){
      this.$store.dispatch("verification_code").then(result=>{
        this.image_code="data:image/jpeg;base64,"+result.data;
      }).catch(error=>{
        this.$message.success("获取验证码失败");
      })
    }
  }
}
</script>

<style scoped>


#login_data {
  background-size: 100%;
  width: 400px;
  height: 340px;
  border: 1px solid rgba(0,0,0,.0);
  margin: 90px auto;
  border-radius: 5px;
  background-color: rgba(255,255,255,.8);
}

.el-form {
  margin-top: 40px;
}

.el-input {
  width: 270px;
}

.el-button {
  width: 222px;
  margin-left: 81px;
  margin-top: 11px;

}

span {
  margin-top: 20px;
  display: block;
  font-size: 12px;
  margin-left: 320px;
  color: #409EFF;
  cursor: pointer;
}
.code_img{
  width: 69px;
  cursor: pointer;
}


</style>