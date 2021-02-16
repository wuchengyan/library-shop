<template>
  <div>
    <div v-if="showData">
      <el-row class="row_content">
        <el-col :span="5" v-for="(item, index) in tempData" :key="index" :offset="10">
          <el-card :body-style="{ padding: '20px',background: '#fff' }">
            <div>
              <span class="bookname_style">{{item.ordernumber}}</span><br>
              <span class="author_style">{{item.address}}</span>
              <span class="price_style">{{item.price}}</span>
              <div class="bottom clearfix">
              <span style="color:blue">{{changeStatus(item.status)}}</span>  
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
    <div style="display:flex;justify-content:center;line-height:820px" v-else-if="showNotLogin">
      <span style="color:#A6A6A6;font-size:30px">登录后才可查看订单</span>
    </div>
    <div style="display:flex;justify-content:center;line-height:820px" v-else-if="showNotData">
      <span style="color:#A6A6A6;font-size:30px">订单空荡荡，地狱在人间</span>
    </div>
  </div>
</template>

<script>
import {getOrderData} from "../api/index"
import {mixin} from "../mixins"
export default {
  mixins:[mixin],
  data(){
    return {
      tempData: [],
      // account: '',
      showData: false,
      showNotLogin: false,
      showNotData: false,
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
    //获得页面所需数据
    getData(){
      this.tempData = [];
      getOrderData()
      .then(res => {
        if(res){
          this.account = localStorage.getItem("account");
          let count = 0;
          for(let i = 0;i < res.length; i++){
            if(res[i].account == this.account){
              this.tempData[count] = res[i];
              count++;
            }
          }
          //this.tempData = res;
          
        }
        if(this.tempData.length != 0 && this.account){this.showData = true;}
        else if(this.tempData.length == 0 && this.account)  {this.showNotData = true;}
        else {this.showNotLogin = true;}
        //console.log(this.tempData);
      })  
      .catch(err => {
        console.log(err);
      });
    }
  },
}
</script>

<style scoped>
.time {
    font-size: 13px;
    color: #999;
  }
  
  .bottom {
    margin-top: 13px;
    line-height: 12px;
  }

  .button {
    padding: 0;
    float: right;
  }

  .image {
    width: 100%;
    display: block;
  }

  .clearfix:before,
  .clearfix:after {
      display: table;
      content: "";
  }
  
  .clearfix:after {
      clear: both
  }

  .row_content{
    margin-top: 1%;
  }

  .bookname_style{
    color:#3BB4FF;
    font-size: 30px;
    font-family: 'Courier New', Courier, monospace;
    text-align: center;
  }

  .author_style{
    color:#000;
    font-size: 20px;
    font-family:'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
    text-align: center;
  }

  .price_style{
    float:right;
    font-size: 25px;
    color: red;
  }

  .des_font{
    font-size: 18px;
    font-family:'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;
    color: gray;
    letter-spacing: 10px;
  }
</style>