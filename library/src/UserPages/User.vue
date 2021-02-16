<template>
  <div>
     <MyAside></MyAside>
     <div class="content-box">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import MyAside from '@/UserPages/MyAside'
export default {
  components:{
    MyAside
  
  },
  data(){
    return {  
     
    }
  },
  created(){
    this.getData()
  },
  computed:{
    account(){
      return localStorage.getItem("account");
    }
  },
  methods:{
    getData(){
      
    }
  }

}
</script>

<style scoped>
 .content-box{
   position: absolute;
   left: 130px;
   right: 0;
   bottom: 0;
   top:0;
   overflow-y: scroll;
   -webkit-transition: left .3s ease-in-out;
   transition: left .3s ease-in-out;
   /* background-image: url('../assets/img/user.jpg'); */
 }

 .content-box::-webkit-scrollbar{
   width: 0;
 }
</style>