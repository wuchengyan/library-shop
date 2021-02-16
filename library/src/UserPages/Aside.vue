<template>
  <el-menu
    class="categories"
    :default-active="onRoutes" 
    active-text-color="red"
    router>
    <el-menu-item index="SortOfYouthLiterature">
      <em class="el-icon-menu"></em>
      <span slot="title">青春文学</span>
    </el-menu-item>
    <el-menu-item index="SortOfLiterature">
      <em class="el-icon-menu"></em>
      <span slot="title">文学</span>
    </el-menu-item>
    <el-menu-item index="SortOfFiction">
      <em class="el-icon-menu"></em>
      <span slot="title">小说</span>
    </el-menu-item>
    <el-menu-item index="SortOfRelax">
      <em class="el-icon-menu"></em>
      <span slot="title">休闲爱好</span>
    </el-menu-item>
    <el-menu-item index="SortOfArt">
      <em class="el-icon-menu"></em>
      <span slot="title">艺术</span>
    </el-menu-item>
    <el-menu-item index="SortOfCartoon">
      <em class="el-icon-menu"></em>
      <span slot="title">动漫</span>
    </el-menu-item>
    <el-menu-item index="SortOfFashion">
      <em class="el-icon-menu"></em>
      <span slot="title">时尚</span>
    </el-menu-item>
    <el-menu-item index="SortOfTravel">
      <em class="el-icon-menu"></em>
      <span slot="title">旅游</span>
    </el-menu-item>
  </el-menu>
</template>

<script>
  export default {
    name: 'SideMenu',
    data(){
      return{
        
      }
    },
    computed:{
      onRoutes(){
        //登录后默认跳转到首页
        return this.$router.push('/SortOfYouthLiterature');
      },
    }
  }
</script>

<style scoped>
  .categories {
    position: fixed;
    width: 150px;
    height: 100%;
  }
</style>

