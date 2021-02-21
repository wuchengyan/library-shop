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
        <el-form-item label="姓名" prop="name">
          <el-input  v-model="ruleForm.name" placeholder="请输入姓名"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input  v-model="ruleForm.phone" placeholder="请输入电话"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="mail">
          <el-input  v-model="ruleForm.mail" placeholder="请输入邮箱"></el-input>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <v-distpicker  @province="onChangeProvince" @city="onChangeCity" @area="onChangeArea"></v-distpicker>
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
          <el-button type="info" @click="btn_login" round>返回登录</el-button>
        </div>
      </el-form>
    </div>
  </div>

</template>

<script>
import {registerStatus,getUserMsg} from "../api/index"
import {mixin} from "../mixins"
export default {
  name: 'Login',
  mixins:[mixin],
  data () {
    return {
      title: '欢迎注册',
      ruleForm:{
        account: '',
        password: '',
        name: '',
        phone: '',
        mail: '',
        address: '',
        pic: '先不做',
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
        name:[
           {required:true,message:"请输入姓名",trigger:"blur"}
        ],
        phone:[
           {required:true,message:"请输入手机号",trigger:"blur"},
           {validator: function(rule, value, callback) {
              if (/^1[34578]\d{9}$/.test(value) == false) {
                callback(new Error("手机号格式错误"));
              } else {
                callback();
              }
            },
            trigger: "blur"}
        ],
        mail:[
          {
            required: true,
            message: "请输入邮箱",
            trigger: "blur"
          },
          {
            validator: function(rule, value, callback) {
              if (
                /^\w{1,64}@[a-z0-9\-]{1,256}(\.[a-z]{2,6}){1,2}$/i.test(
                  value
                ) == false
              ) {
                callback(new Error("邮箱格式错误"));
              } else {
                callback();
              }
            },
            trigger: "blur"
          }
        ],
        address:[
           {required:true,message:"请输入地址",trigger:"blur"}
        ],
        type:[
          {required:true,message:"请选择身份",trigger:"blur"}
        ]
      },
      options:[
        {value: '0', label: '管理员'},
        {value: '1', label: '用户'}
      ],
      value: '1', //身份选择值
      province: '', //省
      city: '',   //市
      area: '', //区域
      detailaddress: '',//具体选择的地址
      hasAccount: true //账号是否已注册
    }
  },
  methods:{
    btn_submit(){
      getUserMsg(this.ruleForm.account)
      .then(res => {
        if(res.account === this.ruleForm.account){
          console.log('已经注册');
          this.hasAccount = false;
        }else{
          console.log('可以注册');
          this.hasAccount = true;
        }
      })

      if(this.value == 0){
        this.notify("不允许注册管理员","error");
      }else if(this.hasAccount){
        this.notify("该账号已注册，请换一个","error");
      }
      else{
        //获取具体地址
        this.detailaddress = this.province + this.city + this.area;
        this.ruleForm.address = this.detailaddress;
      
        if(this.ruleForm.account == '' || this.ruleForm.password == ''
        || this.ruleForm.name == '' || this.ruleForm.phone == ''||
        this.ruleForm.mail == '' || this.ruleForm.address == '' ||
        this.value == ''
        )
        {this.notify("请填写完整信息","error");}
        else{
          let params = new URLSearchParams();
          params.append("account",this.ruleForm.account);
          params.append("password",this.ruleForm.password);
          params.append("name",this.ruleForm.name);
          params.append("phone",this.ruleForm.phone);
          params.append("mail",this.ruleForm.mail);
          params.append("address",this.ruleForm.address);
          params.append("pic",'/img/userPic/user.jpg');
          params.append("type",this.value);

          registerStatus(params)
          .then((res) => {
            if(res.code == 1){
              //存入本地缓存，方便主页取
              localStorage.setItem('account',this.ruleForm.account);
              //提示信息
              this.notify("注册成功","success");
              //跳转到主页
              this.$router.push("/");
            }else{
              this.notify("注册失败","error");
            }
          })
          .catch(err => {
            console.log(err);
          });
        }
      }
    },
    btn_reset(){
      //重置输入信息
      this.ruleForm.account = '';
      this.ruleForm.password = '';
      this.value = '';
      this.ruleForm.name = '';
      this.ruleForm.phone = '';
      this.ruleForm.mail = '';
      this.notify("已重置","success");
      
    },
    btn_login(){
      //跳转登录页面
      this.$router.push("/");
    },
    onChangeProvince(a){
      this.province = a.value;
    },
    onChangeCity(a){
      this.city = a.value;
    },
    onChangeArea(a){
      this.area = a.value;
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
    top: 30%;
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
    top: 30%;
    width: 300px;height: 550px;
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