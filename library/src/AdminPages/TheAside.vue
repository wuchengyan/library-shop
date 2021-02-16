<template>
  <div class="sidebar">
    <el-menu class="sidebar-el-menu" 
      :default-active="onRoutes" 
      background-color="#5A5DE0"
      text-color="#fff"
      active-text-color="#20a0ff"
      router>
     <template v-for="item in items">
       <template>
          <el-menu-item :index="item.index" :key="item.index">
              <em :class="item.icon"></em>
              {{item.title}}
          </el-menu-item>
       </template>
     </template>
    </el-menu>
  </div>
</template>

<script>
export default {
  data(){
    return{
       items:[
        {
          icon:'el-icon-star-on',
          index:'Admin',
          title: '首页'
        },
        {
          icon:'el-icon-reading',
          index:'Book',
          title: '图书管理'
        },
        {
          icon:'el-icon-s-goods',
          index:'Order',
          title: '订单管理'
        },
        {
          icon:'el-icon-user-solid',
          index:'UserMsg',
          title: '用户信息'
        },
      ]
    }
  },
  computed:{
    onRoutes(){
      //登录后默认跳转到首页
      return this.$route.path.replace('/','');
    },
  }
}
</script>

<style scoped>
   .sidebar{
    display: block;
    position:absolute;
    left:0;
    top:70px;
    bottom:0;
    /* 334256 */
    background-color: #E4E6E5;
    overflow-y: scroll;
  }
  /*删除菜单栏箭头*/
  .sidebar::-webkit-scrollbar{
    width: 0;
  }
  /*右侧突出消除*/
  .sidebar >ul {
    height: 100%;
  }

</style>