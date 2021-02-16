<template>
  <div>
    <div v-show="showData">
      <el-table border size="mini" style="width:100%" height="622px" :data="tableData">
        <el-table-column width="150px" label="封面"  align="center">
          <template slot-scope="scope">
              <div class="client-img">
                <img alt="没有图片" :src="getUrl(scope.row.bookpic)" style="width:100%" />
              </div>
            </template>
        </el-table-column>
        <el-table-column prop="bookname" width="300px" label="书名"  align="center"></el-table-column>
        <el-table-column prop="bookprice" width="1000px"  label="单价"  align="center"></el-table-column>
        <el-table-column prop="bookcount"  label="件数" width="150px"  align="center"></el-table-column>
        <el-table-column label="操作"  align="center">
          <template slot-scope="scope">
            <el-button :disabled="isMax(scope.row)"  size="mini" type="info" @click="handleInc(scope.row)">增加</el-button>
            <el-button :disabled="scope.row.bookcount == 1" size="mini" type="primary" @click="handleDes(scope.row)">减少</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div>
        <div class="content_price">
          <h2>商品总额</h2><span style="margin-left:20px;color:red;font-size:30px">{{totalPrice}}</span>
        </div>
        <div style="float:right">
          <el-button type="primary" @click="handlePay">支付</el-button>
        </div>
      </div>

      <!--删除购物车-->
      <el-dialog title="删除购物车" :visible.sync="delDialogVisible" width="300px" center>
        <div style="display:flex;justify-content:center"> 是否删除？</div>
        <span slot="footer">
          <el-button size="mini" @click="delDialogVisible = false">取消</el-button>
          <el-button size="mini" @click="deleteRow">确定</el-button>
        </span>
      </el-dialog>

      <!--支付弹窗-->
      <el-dialog title="支付购物车" :visible.sync="payDialogVisible" width="300px" center>
        <div style="display:flex;justify-content:center"> 确定支付？</div>
        <span slot="footer">
          <el-button size="mini" @click="payDialogVisible = false">取消</el-button>
          <el-button size="mini" @click="payRow">确定</el-button>
        </span>
      </el-dialog>
    </div>
    <div style="display:flex;justify-content:center;line-height:820px" v-show="showNotData">
      <span style="color:#A6A6A6;font-size:30px">多大个人了，还不买书</span>
    </div>
    <div style="display:flex;justify-content:center;line-height:820px" v-show="showNotLogin">
      <span style="color:#A6A6A6;font-size:30px">请先登录</span>
    </div>
  </div>
</template>

<script>
import {getShopCarDataByAccount,deleteShopCar,getBookData,updateBookStockById,addOrder,getUserData,deleteShopByAccount} from "../api/index"
import {mixin} from '../mixins'
export default {
    mixins:[mixin],
    data(){
      return {
        tableData:[],
        bookData:[],
        userData: [],
        showData: false,
        showNotLogin: false,
        showNotData: false,
        //删除弹窗
        delDialogVisible: false,
        //支付弹窗
        payDialogVisible: false,
        idx: '',
      }
    },
    computed:{
      totalPrice(){
        let Price = 0;
        for(let i = 0; i < this.tableData.length;i++){
          Price += this.tableData[i].bookcount*this.tableData[i].bookprice;
        }
        return Price.toFixed(2);
      },
      account(){
        return localStorage.getItem("account");
      },
    },
    created(){
      this.getData();
    },
    methods:{
      //控制增加按钮不可用
      isMax(row){
        let stock = 0;
        for(let i=0;i<this.bookData.length;i++){
          if(this.bookData[i].bookname == row.bookname){
            stock = this.bookData[i].stock;
            break;
          }
        }
        if(row.bookcount >= stock){
          return true;
        }else{
          return false;
        }
      },
      //获取所需数据
      getData(){
        this.bookData = [];
        this.tableData = [];
        let account = localStorage.getItem("account");
        getShopCarDataByAccount(account)
        .then(res => {
          if(res){
            this.tableData = res;
            if(this.tableData.length != 0 && this.account){this.showData = true;}
            else if(this.tableData.length == 0 && this.account)  {this.showNotData = true;}
            else {this.showNotLogin = true;}
            // console.log(this.tableData);
          }
        });
        getBookData()
        .then(res => {
          if(res){
            this.bookData = res;
          }
        })
        getUserData()
        .then(res => {
          if(res){
            this.userData = res;
          }
        })
      },
      handleInc(row){
        //如果库存小于此行书的件数,则提示库存不足
        let stock = 0;
        for(let i=0;i<this.bookData.length;i++){
          if(this.bookData[i].bookname == row.bookname){
            stock = this.bookData[i].stock;
            break;
          }
        }
        if(row.bookcount >= stock){
          this.$notify({
              title:"库存不足",
              type:"fail"
            });
        }else{
          row.bookcount++;
        }
      },
      handleDes(row){
        row.bookcount--;
      },
      //删除购物车
      deleteRow(){
        deleteShopCar(this.idx)
        .then(res => {
          if(res){
            this.getData();
            this.$notify({
              title:"删除成功",
              type:"success"
            });
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
        this.delDialogVisible = false;
      },
      //支付按钮
      handlePay(){
        this.payDialogVisible = true;
      },
      //根据当前时间生成随机订单号
      Random(){
        var orderCode='';
        for (var i = 0; i < 6; i++) //6位随机数，用以加在时间戳后面。
        {
          orderCode += Math.floor(Math.random() * 10);
        }
        orderCode = new Date().getTime() + orderCode;  //时间戳，用来生成订单号。
        return orderCode;
      },
      //支付
      payRow(){
        //获得表格里的书名和数量
        let buybook = [];
        let buycount = [];
        let bookDatas = [];
        // let params = new URLSearchParams();
        for(let i = 0; i < this.tableData.length; i++){
          buybook[i] = this.tableData[i].bookname;
          buycount[i] = this.tableData[i].bookcount;
        }
        //对比图书表
        for(let i = 0; i < buybook.length; i++){
          for(let j = 0; j < this.bookData.length; j++){
            if(this.bookData[j].bookname == buybook[i]){
              //减少对应库存
              this.bookData[j].stock -= buycount[i];
              //赋值给临时新数组
              bookDatas[i] = this.bookData[j];
              // console.log(bookDatas.length);
            }
          }
        }
        //更新图书表
        for(let k = 0; k < bookDatas.length; k++){
          //这个定义必须为块级变量
          let params1 = new URLSearchParams();
          params1.append("id",bookDatas[k].id);
          params1.append("stock",bookDatas[k].stock);
          updateBookStockById(params1)
          .then(res => {
            if(res.code == 1){
              this.getData();
              this.$notify({
                title:"付款成功",
                type:"success"
              });
            }else{
              this.$notify({
                title:"付款失败",
                type:"fail"
              });
            }
          })
          .catch(err => {
            console.log(err);
          });
        }
        //存入订单
        let d = new Date();
        let datetime = d.getFullYear()+'-'+(d.getMonth()+1)+'-'+d.getDate();
        let address = '';
        for(let i of this.userData){
          if(i.account == this.account){
            address = i.address;
          }
        }
        let params2 = new URLSearchParams();
          params2.append("ordernumber",this.Random());
          params2.append("account",this.account);
          params2.append("price",this.totalPrice);
          params2.append("address",address);
          params2.append("status",0);
          params2.append("createtime",datetime);
        addOrder(params2)
        .then(res => {
          if(res.code == 1){
            console.log("添加订单成功");
          }
        })
        .catch(err => {
          console.log(err);
        })
        //删除此账号的购物车
        deleteShopByAccount(this.account)
        .then(res => {
          if(res){
            this.getData();
          }
        })
        .catch(err => {
          console.log(err);
        })
        
        //关闭弹窗
        this.payDialogVisible = false;
        this.showData = false;
        this.showNotData = true;
      }
    },
}
</script>

<style scoped>
.table{
    min-width:800px;
  }

  .client-img{
    display: flex;
    justify-content: center;
    width:150px;
    height: 100px;
    border-radius: 5px;
    margin-bottom: 5px;
    overflow: hidden;
  }

  .handle-input{
    width: 300px;
    display: inline-block;

  }

  .content_price{
    display: flex;
    justify-content: center;
  }
</style>