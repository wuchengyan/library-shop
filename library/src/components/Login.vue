<template>
  <div class="login-wrap">
    <div class="ms-title">
      <em>{{title}}</em>
    </div>
    <div class="ms-login">
      <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="50px">
        <el-form-item label="账号" prop="account">
          <el-input  v-model="ruleForm.account" placeholder="请输入账号"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password"  v-model="ruleForm.password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item label="身份" prop="type">
          <el-select v-model="value" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <div class="btn">
          <el-button type="primary" @click="btn_submit" round>提交</el-button>
          <el-button type="danger" @click="btn_reset" round>重置</el-button>
          <el-button type="info" @click="btn_register" round>注册</el-button>
          <el-button @click="btn_travel" round>游客</el-button>
        </div>
      </el-form>
    </div>
  </div>

</template>


<script>
import {getLoginStatus,getUserMsg} from "../api/index"
import {mixin} from "../mixins"
export default {
  name: 'Login',
  mixins:[mixin],
  data () {
    return {
      title: '欢迎登录',
      ruleForm:{
        account: '',
        password: '',
        type: ''
      },
      rules:{
        account:[
           {required:true,message:"请输入用户名",trigger:"blur"},
           { min: 1, max: 10, message: '长度在 1 到 10 个字符', trigger: 'blur' }
        ],
        password:[
           {required:true,message:"请输入密码",trigger:"blur"},
           { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
        ],
        type:[
          {required:true,message:"请选择身份",trigger:"blur"}
        ]
      },
      options:[
        {value: '0', label: '管理员'},
        {value: '1', label: '用户'}
      ],
      value: '' //身份选择值
    }
  },
  methods:{
    btn_submit(){
      //判断输入是否完整
      if(this.ruleForm.account == '' || this.ruleForm.password == '' || this.value == ''){
        this.notify("请填写完整信息","error");
      }
      else{
        let params = new URLSearchParams();
        params.append("account",this.ruleForm.account);
        params.append("password",this.ruleForm.password);
        params.append("type",this.value);
        
        getLoginStatus(params)
        .then((res) => {
          if(res.code == 1){
            //存入本地缓存，方便主页取
            localStorage.setItem('account',this.ruleForm.account);
            localStorage.setItem('type',this.ruleForm.type);
            //提示登录成功
            this.notify("登录成功","success");
            //跳转
            if(this.value == 0){
              this.$router.push("/Admin");
            }else{
              this.$router.push("/Index");
            }
          }else{
            this.notify("登录失败","error");
          }
        })
        .catch(err => {
          console.log(err);
        });
      }
    },
    
    btn_reset(){
      //重置输入信息
        this.ruleForm.account = '';
        this.ruleForm.password = '';
        this.value = '';
        this.notify("已重置","success");
      
    },
    btn_register(){
      //跳转注册页面
      this.$router.push("/Register");
    },
    btn_travel(){
      //跳转首页
      this.$router.push("/Index");
    }
  }
}
</script>


<style scoped>
 .login-wrap{
    position: relative;
    background: url('../assets/img/login.jpeg');
    background-attachment: fixed;
    background-position: center;
    background-size: cover;
    width: 100%;
    height: 100%;
  }
 .ms-title{
    position: absolute;
    top: 50%;
    width: 100%;
    margin-top: -230px;
    text-align: center;
    font-size: 30px;
    font-weight: 600;
    color:#fff;
    
 }
 .ms-login{
    position: absolute;
    left: 50%;
    top: 50%;
    width: 348px;
    height: 200px;
    margin-left: -190px;
    margin-top: -150px;
    padding: 40px;
    border-radius: 5px;
    background: #fff;
    box-shadow: 2px 2px 10px #000;
 }
 .btn{
   text-align: center;
 }


</style>