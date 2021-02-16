<template>
  <div>
    <TheHeader></TheHeader>
    <TheAside></TheAside>
    <div class="content-box">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import TheHeader from './TheHeader'
import TheAside from './TheAside'
export default {
    components:{
      TheHeader,
      TheAside
    }
}
</script>

<style scoped>
  .content-box{
   position: absolute;
   left: 130px;
   right: 0;
   bottom: 0;
   top:70px;
   overflow-y: scroll;
   -webkit-transition: left .3s ease-in-out;
   transition: left .3s ease-in-out;
   background: #fff;
   /* background-image: url('../assets/img/user.jpg'); */
 }

 .content-box::-webkit-scrollbar{
   width: 0;
 }
</style>