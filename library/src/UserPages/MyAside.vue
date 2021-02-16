<template>
  <div class="sidebar">
    <el-menu class="sidebar-el-menu" 
      :default-active="onRoutes" 
      background-color="#fff"
      text-color="#000"
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
          icon:'el-icon-user',
          index:'BasicMsg',
          title: '基本信息'
        },
        {
          icon:'el-icon-document',
          index:'MyOrders',
          title: '我的订单'
        },
      ]
    }
  },
  computed:{
    onRoutes(){
      //点击个人中心后默认跳转到基本信息
      return this.$router.push('/BasicMsg');
    },
  }
}
</script>

<style scoped>
   .sidebar{
    display: block;
    position:absolute;
    left:0;
    top:0;
    bottom:0;
    
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