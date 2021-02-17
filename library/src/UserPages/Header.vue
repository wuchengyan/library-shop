<template>
  <div>
    <div>
      <el-menu router :default-active="onRoutes"  class="el-menu-demo" mode="horizontal">
        <el-menu-item index="Main">首页</el-menu-item>
        <el-menu-item index="ShopCart">购物车</el-menu-item>
        <el-menu-item index="User">个人中心</el-menu-item>
        <el-menu-item index="Title" style="margin-left:35%;color:red" disabled>
          <h2>{{title}}</h2>
        </el-menu-item>
        <el-menu-item  style="float:right">
          <div v-if="pic" >
            <img class="userImg" :src="getUrl(pic)" alt="没有图片">
           <el-dropdown class="user-name" trigger="click" @command="handleCommand">
              <span class="el-dropdown-link">
                {{account}} 
                <em class="el-icon-caret-bottom"></em>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item  command="logout">退出登录</el-dropdown-item>
                <el-dropdown-item v-if="isAdmin === 0"  command="gotoAdmin">去管理员页</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div> 
          <div v-else>
            <span style="color:red"><a @click="toLogin">点我去登录</a></span>
          </div>
        </el-menu-item>
      </el-menu>
    </div>
  </div>
</template>

<script>
import {mixin} from '../mixins'
import {getUserPic,getUserMsg} from '../api/index'
export default {
  name: 'Header',
  mixins:[mixin],
  data(){
    return {
      title: 'BookShop!',
      pic: '',
      isAdmin: ''
    }
  },
  computed:{
    account(){
      return localStorage.getItem('account');
    },
    onRoutes(){
      return this.$router.push('/Main')
    }
  },
  created(){
    this.getPic();
    this.getAdmin();
  },
  methods:{
    getPic(){
       getUserPic(this.account)
      .then(res => {
          this.pic = res.pic;
      })
      .catch(err => {
        console.log(err);
      })
    },
    getAdmin(){
      getUserMsg(this.account)
      .then(res => {
        this.isAdmin = res.type;
      })
      .catch(err => {
        console.log(err);
      })
    },
    handleCommand(command){
      if(command == "logout"){
        localStorage.removeItem('account');
        this.$router.push("/");
      }
      if(command == "gotoAdmin"){
        this.$router.push("/Admin");
      }
    },
    toLogin(){
        this.$router.push("/");
    }
  }
}
</script>

<style scoped>
.userImg{
   width: 40px;
   height: 40px;
   border-radius: 50%;
   cursor: pointer;
}
.user-name{
    margin-left: 10px;
}
.el-dropdown-link{
    color: #409EFF;
    cursor: pointer;
}
</style>