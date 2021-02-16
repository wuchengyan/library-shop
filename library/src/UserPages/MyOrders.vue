<template>
<div >
  <div v-show="showData">
    <el-row :gutter="10">
      <el-col :span="6">
        <div class="content">
          <span style="color:#409EFF;font-size:30px;">
            未发货订单
          </span>
          <div class="content_top">
            <span style="color:#409EFF;font-size:25px">
              {{status_one}}
            </span>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="content">
          <span style="color:#409EFF;font-size:30px;">
            已发货订单
          </span>
          <div class="content_top">
            <span style="color:#409EFF;font-size:25px">
              {{status_two}}
            </span>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="content">
          <span style="color:#409EFF;font-size:30px;">
            未收货订单
          </span>
          <div class="content_top">
            <span style="color:#409EFF;font-size:25px">
              {{status_three}}
            </span>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="content">
          <span style="color:#409EFF;font-size:30px;">
            已收货订单
          </span>
          <div class="content_top">
            <span style="color:#409EFF;font-size:25px">
              {{status_four}}
            </span>
          </div>
        </div>
      </el-col>
    </el-row>

    <div>
      <el-row class="row_content">
        <el-col :span="5" v-for="(item, index) in OrderData" :key="index" :offset="2">
          <el-card :body-style="{ padding: '20px',background: '#fff' }">
            <div>
              <span class="bookname_style">{{item.ordernumber}}</span><br>
              <span class="author_style">{{item.address}}</span>
              <span class="price_style">{{item.price}}</span>
              <div class="bottom clearfix">
                <el-button :disabled="buttonStatus(item.status)"  @click="btn_collect(item.id)" type="text" class="button">{{changeStatus(item.status)}}</el-button>               
                <el-button  @click="btn_delete(item.id)" type="danger" class="button">删除</el-button>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
  <div style="display:flex;justify-content:center;line-height:820px" v-show="showNotLogin">
      <span style="color:#A6A6A6;font-size:30px">请先登录</span>
  </div>
   <div style="display:flex;justify-content:center;line-height:820px" v-show="showNotData">
        <span style="color:#A6A6A6;font-size:30px">暂无订单</span>
    </div>
</div>
</template>

<script>
import {getOrderData, updateOrderStatus,deleteOrder} from "../api/index"
import {mixin} from "../mixins"
export default {
  mixins:[mixin],
  data(){
    return {
      OrderData:[],
      status_one: '0',
      status_two: '0',
      status_three: '0',
      status_four: '0',
      isActiveOne: true,
      showData: false,
      showNotData: false,
      showNotLogin: false,
    }
  },
  computed:{
    account(){
      return localStorage.getItem("account");
    },
    showNotData(){
      return this.OrderData.length == 0 ? true : false;
    }
    // status_one(){
    //   return 
    // }
  },
  created(){
    this.getData();
  },
  methods:{
    buttonStatus(status){
      if(status == 1||status == 2){
        return false;
      }
      else{
        return true;
      }
    },
    getData(){
      //获得该账号的订单数据
      this.status_one = 0;
      this.status_two = 0;
      this.status_three = 0;
      this.status_four = 0;
      this.OrderData = [];
      getOrderData()
      .then(res => {
        if(res){
          for(let i = 0; i < res.length; i++){
            if(res[i].account == this.account){
              this.OrderData[i] = res[i];
              if(this.OrderData[i].status == 0){this.status_one++;}
              else if(this.OrderData[i].status == 1){this.status_two++;}
              else if(this.OrderData[i].status == 2){this.status_three++;}
              else if(this.OrderData[i].status == 3){this.status_four++;}
            }
          }
          
          if(this.OrderData.length != 0 && this.account){this.showData = true;}
          else if(this.OrderData.length == 0 && this.account)  {this.showNotData = true;this.showData = false;}
          else {this.showNotLogin = true;}
        }
      })
      .catch(err => {
        console.log(err);
      });

      
    },
    //收货按钮
    btn_collect(id){
      let params = new URLSearchParams();
      params.append("id",id);
      params.append("status",3);
      updateOrderStatus(params)
      .then(res => {
        if(res.code == 1){
            this.$notify({
              title:"收货成功",
              type:"success"
            });
            this.getData();
        }else{
            this.$notify({
              title:"收货失败",
              type:"fail"
            });
        }
      })
      .catch(err => {
        console.log(err);
      })
      },
      //删除按钮
      btn_delete(id){
        deleteOrder(id)
        .then(res => {
          if(res){
            this.$notify({
            title:"删除成功",
            type:"success"
          });
          this.getData();
          }else{
            this.$notify({
            title:"删除失败",
            type:"fail"
          });
          }
        })
        .catch(err => {
          console.log(err);
        });
        
        
      }
  }
}
</script>

<style scoped>
.content{
  height: 100px; 
  text-align: center;
  /* border: 1px solid; */
  background-color: #F8F8F8;
}
.content_top{
  margin-top: 20px;
}
.content_h2{
  margin-left: 30%;
  margin-top: 20%;
  font-size: 25px;
  color: #0EBEFF;
}
.content_graph{
  /* border: 1px solid; */
  margin-top: 10%;
  margin-left: 20%;
  width: 100%;
  background-color: #7EE1FF;
}
  
  .bottom {
    margin-top: 13px;
    line-height: 12px;
  }

  .button {
    padding: 10px;
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
    font-size: 15px;
    color: red;
  }

  .des_font{
    font-size: 18px;
    font-family:'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;
    color: gray;
    letter-spacing: 10px;
  }
  
  /*
    是否显示对应按钮
  */
  .active_one{
    display: none;
  }
</style>