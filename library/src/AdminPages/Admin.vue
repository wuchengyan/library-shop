<template>
  <div>
    <el-row :gutter="10">
      <el-col :span="8">
        <div class="content">
          <span style="color:#409EFF;font-size:30px;">
            用户总数
          </span>
          <div class="content_top">
            <span style="color:#409EFF;font-size:25px">
              {{userNumber}}
            </span>
          </div>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="content">
          <span style="color:#409EFF;font-size:30px;">
            图书总数
          </span>
          <div class="content_top">
            <span style="color:#409EFF;font-size:25px">
              {{bookNumber}}
            </span>
          </div>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="content">
          <span style="color:#409EFF;font-size:30px;">
            订单总数
          </span>
          <div class="content_top">
            <span style="color:#409EFF;font-size:25px">
              {{orderNumber}}
            </span>
          </div>
        </div>
      </el-col>
    </el-row>
    <el-row :gutter="10">
      <el-col :span="10">
        <div class="content_graph">
          <h2 class="content_h2">用户类型比例</h2>
          <div class="background-color:white">
            <ve-pie :data="chartData"></ve-pie>
          </div>
        </div>
      </el-col>
      <el-col :span="10">
        <div class="content_graph">
          <h2 class="content_h2">图书类型分布</h2>
          <div>
            <ve-histogram :data="styleData"></ve-histogram>
          </div>
        </div>
      </el-col>
    </el-row>
    <el-row :gutter="10">
      <el-col :span="10">
        <div class="content_graph">
          <h2 class="content_h2">订单类型比例</h2>
          <div class="background-color:white">
            <ve-pie :data="OrderData"></ve-pie>
          </div>
        </div>
      </el-col>
      <el-col :span="10">
        <div class="content_graph">
          <h2 class="content_h2">月度订单数</h2>
          <div>
            <ve-histogram :data="monthPriceData"></ve-histogram>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>


<script>
import {getUserData,getBookData,getOrderData} from "../api/index"
export default {
  name: 'Admin',
  data(){
    return {
      userNumber: 0,
      bookNumber: 0,
      orderNumber: 0,
      //按类型分使用者数
      chartData:{
        columns: ['类型','总数'],
        rows:[
          {'类型': '管理员','总数': 0},
          {'类型': '用户','总数': 0},
        ]
      }, 
      user: [],//所有用户
      //按图书类型
      styleData:{
        columns: ['类型','总数'],
        rows:[
          {'类型': '小说','总数': 0},
          {'类型': '文学','总数': 0},
          {'类型': '青春文学','总数': 0},
          {'类型': '艺术','总数': 0},
          {'类型': '动漫','总数': 0},
          {'类型': '时尚','总数': 0},
          {'类型': '休闲爱好','总数': 0},
          {'类型': '旅游','总数': 0},
        ]
      },
      //按订单类型
      OrderData:{
        columns: ['类型','总数'],
        rows:[
          {'类型': '未发货','总数': 0},
          {'类型': '已发货','总数': 0},
          {'类型': '待收货','总数': 0},
          {'类型': '已收货','总数': 0},
        ]
      },
      order:[],//所有订单
      //月度订单数
      monthPriceData:{
        columns:['月份','总数'],
        rows:[
          {'月份': '1','总数': 0},
          {'月份': '2','总数': 0},
          {'月份': '3','总数': 0},
          {'月份': '4','总数': 0},
          {'月份': '5','总数': 0},
          {'月份': '6','总数': 0},
          {'月份': '7','总数': 0},
          {'月份': '8','总数': 0},
          {'月份': '9','总数': 0},
          {'月份': '10','总数': 0},
          {'月份': '11','总数': 0},
          {'月份': '12','总数': 0},
        ]
      }
    }
  },
  mounted(){
    this.getUserNumber();
    this.getBookNumber();
    this.getOrderNumber();
  },
  methods:{
    //获得用户数量
    getUserNumber(){
      getUserData()
      .then(res => {
        if(res){
          this.userNumber = res.length;
          this.user = res;
          this.chartData.rows[0]['总数'] = this.setType(0,this.user);
          this.chartData.rows[1]['总数'] = this.setType(1,this.user);
        }else{
          this.userNumber = 0;
        }
      })
      .catch(err => {
        console.log(err);
      })
    },
    //根据类型获取用户数
    setType(type,user){
      let count = 0;
      for(let i of user){
        if(type == i.type){
          count++;
        }
      }
      return count;
    },
    //获得图书数量
    getBookNumber(){
      getBookData()
      .then(res => {
        if(res){
          this.bookNumber = res.length;
          for(let i of res){
            this.setStyle(i.sort);
          }
        }else{
          this.bookNumber = 0;
        }
      })
      .catch(err => {
        console.log(err);
      })
    }, 
    //根据图书类型获取数量
    setStyle(type){
      for(let i of this.styleData.rows){
        if(type == (i['类型'])){
          i['总数']++;
        }
      }
    },
    //获得订单数量
    getOrderNumber(){
      getOrderData()
      .then(res => {
        if(res){
          this.orderNumber = res.length;
          //订单状态赋值
          this.order = res;
          this.OrderData.rows[0]['总数'] = this.setStatus(0,this.order);
          this.OrderData.rows[1]['总数'] = this.setStatus(1,this.order);
          this.OrderData.rows[2]['总数'] = this.setStatus(2,this.order);
          this.OrderData.rows[3]['总数'] = this.setStatus(3,this.order);
          //月营业额赋值
          for(let i = 0;i < 12; i++){
            this.monthPriceData.rows[i]['总数'] = this.setMonth(i,this.order);
          }
        }else{
          this.orderNumber = 0;
        }
      })
      .catch(err => {
        console.log(err);
      })
    },
    //根据订单状态获得数量
    setStatus(status,order){
      let count = 0;
      for(let i of order){
        if(status == i.status){
          count++;
        }
      }
      return count;
    },
    //根据订单月份获取数量
    setMonth(m,order){
      let count = 0;
      for(let i of order){
        let d = new Date(i.createtime);
        let dd = d.getMonth();
        if(m == dd){
          count++;
        }
      }
      return count;
    },
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

</style>