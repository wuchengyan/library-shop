<template>
  <div>
    <Aside></Aside>
    <div class="content-box">
        <router-view></router-view>
    </div>
  </div>
</template>

<script>
import Aside from './Aside'
export default {
    components:{
      Aside,
    }
}
</script>

<style scoped>
   .content-box{
   position: absolute;
   left: 150px;
   right: 0;
   overflow-y: scroll;
   -webkit-transition: left .3s ease-in-out;
   transition: left .3s ease-in-out;
   background: #E4E6E5;
 }
  .content-box::-webkit-scrollbar{
   width: 0;
 }
</style>