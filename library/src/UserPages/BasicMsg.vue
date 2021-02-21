<template>
<div> 
  <div v-if="account">
      <div class="box"> 
        <div class="user-avator">
          <img class="person-img" alt="没有图片" :src="getUrl(ruleForm.pic)" />
        </div>
        <div class="btn_changepic">
          <el-upload :action="uploadUrl(ruleForm.id)" :before-upload="beforeAvatorUpload" :on-success="handleAvatorSuccess">
                <el-button type="primary">更新头像</el-button>
          </el-upload>
        </div>
        <div class="msgForm">
          <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <!-- <el-form-item label="账号" prop="account">
              <el-input  v-model="ruleForm.account" placeholder="请输入账号"></el-input>
            </el-form-item> -->
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
              <v-distpicker :province="province" :city="city" :area="area"  @province="onChangeProvince" @city="onChangeCity" @area="onChangeArea"></v-distpicker>
            </el-form-item>
            <el-form-item>
              <el-button style="float:left" type="primary" @click="btn_submit">提交</el-button>
              <el-button style="float:right" type="danger" @click="btn_reset">重置</el-button>
            </el-form-item>
          </el-form>
        </div>
        <div class="clock-style">
            <clock ></clock>
        </div>
      </div>
  </div>
   <div style="display:flex;justify-content:center;line-height:820px" v-else>
      <span style="color:#A6A6A6;font-size:30px">请先登录</span>
   </div>
</div>
</template>

<script>
import {getUserMsg,updateUser} from "../api/index"
import {mixin} from "../mixins"
import Clock from 'vue-clock2';
export default {
  mixins:[mixin],
  components:{Clock},
  data(){
    return {
      time: '',
      hasAccount: true, //账号是否已存在
      ruleForm:{
        id: '',
        account: localStorage.getItem("account"),
        password: '',
        name: '',
        phone: '',
        mail: '',
        address: ''
      },
      province: '',
      city: '',
      area: '',
      //修改规则
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
        },

      }
    },
    computed:{
      account(){
        return localStorage.getItem("account");
      }
    },
    created(){
      this.getData();
    },
    methods:{
      //更新头像
      uploadUrl(id){
        return `${this.$store.state.HOST}/user/updateUserPic?id=${id}`;
      },
      //重置表单数据
      btn_reset(){
        this.ruleForm = '';
        this.province = '';
        this.city = '';
        this.area = '';
      },
      //提交表单数据
      btn_submit(){

        // getUserMsg(this.ruleForm.account)
        // .then(res => {
        //   if(res.account === this.ruleForm.account){
        //     this.hasAccount = false;
        //   }else{
        //     this.hasAccount = true;
        //   }
        // })
        // .catch(err => {
        //   console.log(err);
        // })
        // if(this.hasAccount){
        //   this.notify("修改的账号已存在，请重新修改","error");
        // }else{
          //选择的地址
          let edit_address = this.province+this.city+this.area;
          
          let params = new URLSearchParams();
          params.append("id",this.ruleForm.id);
          params.append("account",this.ruleForm.account);
          params.append("password",this.ruleForm.password);
          params.append("name",this.ruleForm.name);
          params.append("phone",this.ruleForm.phone);
          params.append("mail",this.ruleForm.mail);
          params.append("address",edit_address);
          params.append("type",1);

          updateUser(params)
          .then(res => {
            if(res.code == 1){
              this.$notify({
                title:"修改成功",
                type:"success"
              });
              this.getData();
            }else{
              this.$notify({
                title:"修改失败",
                type:"fail"
              });
            }
          })
          .catch(err => {
            console.log(err);
          })
        
      },
      //获得数据
      getData(){
        //获得用户数据
        getUserMsg(this.account)
        .then(res => {
          // this.ruleForm.account = res.account;
          // this.ruleForm.password = res.password;
          // this.ruleForm.name = res.name;
         this.ruleForm = res;
          //区分省市区
         var reg = /.+?(省|市|自治区|自治州|县|区)/g;
         var arr =this.ruleForm.address.match(reg);
         this.province = arr[0];
         this.city = arr[1];
         this.area = arr[2];
        })
        .catch(err => {
          console.log(err);
        })
      
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
.box{
  padding-top: 20px;
  width: 100%;
  height: 700px;
  display: flex;
  justify-content: center;
}
.msgForm{
  flex: 2;
  padding-top: 20px;
}

.user-avator{
  flex: 1;
}

.person-img{
  border-radius: 50%;
  margin-bottom: 1rem;
  width: 500px;
  height: 500px;
  object-fit: cover;
  border: 4px solid var(--clr-grey-8);
  box-shadow: var(--dark-shadow);
}
.user-avator{
  margin-left: 60px;
}

.btn_changepic{
  position: relative;
  top:520px;
  left:-310px;
  
}

.clock-style{
  /* float: right; */
  flex: 0.5;
  padding-left: 50px;
  padding-top: 20px;
}

</style>