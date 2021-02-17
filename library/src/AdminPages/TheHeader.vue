<template>
  <div class="header">
    <div class="logo">图书后台管理</div> 
    <div class="header-right">
      <div class="btn-fullscreen" @click="handleFullScreen">
        <el-tooltip :content="fullscreen?'取消全屏':'全屏'" placement="bottom">
          <em class="el-icon-rank"></em>
        </el-tooltip>
      </div>
      <div class="user-avator">
            <img alt="没有图片" :src="getUrl(pic)" /> 
      </div>
      <el-dropdown class="user-name" trigger="click" @command="handleCommand">
        <span class="el-dropdown-link">
          {{account}} 
          <em class="el-icon-caret-bottom"></em>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item  command="logout">退出登录</el-dropdown-item>
          <el-dropdown-item  command="gotoIndex">去首页</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import {mixin} from '../mixins'
import {getUserPic} from '../api/index'
export default {
  mixins:[mixin],
  data(){
    return{
      fullscreen:false,
      pic: ''
    }
  },
  computed:{
    account(){
      return localStorage.getItem('account');
    }
  },
  created(){
    this.getPic();
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
    handleFullScreen(){
      //全屏控制(多版本浏览器控制)
      if(this.fullscreen){
        if(document.exitFullscreen){
          document.exitFullscreen();
        }else if(document.webkitCancelFullScreen){
          document.webkitCancelFullscreen()          //谷歌等     
        }else if(document.mozCancelFullScreen){
          document.mozCancelFullScreen()             //火狐等
        }else if(document.msExitFullScreen){
          document.msExitFullScreen()             //IE等
        }
      }else{
        let e = document.documentElement;
        if(e.requestFullscreen){
          e.requestFullscreen()
        }else if(e.webkitRequestFullScreen){
          e.webkitRequestFullScreen()
        }else if(e.mozRequestlFullScreen){
          e.mozRequestlFullScreen()
        }else if(e.msRequestFullScreen){
          e.msRequestFullScreen()
        }
      }
      this.fullscreen = !this.fullscreen
    },
    handleCommand(command){
      if(command == "logout"){
        localStorage.removeItem('account')
        localStorage.removeItem('type')
        this.$router.push("/")
      }
      if(command == "gotoIndex"){
        this.$router.push("/index");
      }
    }
  }
}
</script>


<style scoped>
  .header{
    position: relative;
    /* background-color: #253041; */
    background-color: #5A5DE0;
    box-sizing: border-box;
    width: 100%;
    height: 70px;
    font-size: 22px;
    color: #fff;
  }

  .header .logo{
    float: left;
    line-height: 70px;
    color: #409EFF;
    font-size: 130%;
  }

  .header-right{
    float: right;
    padding-right: 50px;
    display: flex;
    height: 70px;
    align-items: center;
  }

  .btn-fullscreen{
    transform: rotate(45deg);
    margin-right: 5px;
    font-size: 24px;
    color: #409EFF;
    cursor: pointer;
  }

  .user-avator{
    margin-left: 20px;
  }

  .user-avator img{
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