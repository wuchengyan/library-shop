<template>
    <div>
      <Header></Header>
      <div class="content-box">
        <router-view></router-view>
      </div>
    </div>
</template>

<script>
import Header from './Header'
export default {
    components:{
      Header,
    }
}
</script>

<style scoped>
  .content-box{
   position: absolute;
   left: 0;
   right: 0;
   top: 60px;
   bottom: 0;
   overflow-y: scroll;
   -webkit-transition: left .3s ease-in-out;
   transition: left .3s ease-in-out;
   background: #E4E6E5;
 }

 .content-box::-webkit-scrollbar{
   width: 0;
 }
</style>