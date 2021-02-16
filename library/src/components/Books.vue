<template>

  <div>
      <div class="pagination">
          <el-pagination 
            background 
            layout="total,prev,pager,next"
            :current-page="currentPage"
            :page-size="pageSize" 
            :total="SortData.length"
            @current-change="handleCurrentChange"></el-pagination>
        </div>
    <el-row style="height: 840px;">
      <el-tooltip effect="dark" placement="right"
                  v-for="item in data"
                  :key="item.id">
        <p slot="content" style="font-size: 14px;margin-bottom: 6px;">{{item.bookname}}</p>
        <p slot="content" style="font-size: 13px;margin-bottom: 6px">
          <span>{{item.author}}</span> <br><br>
          <span style="width:200px;overflow:hidden;display:block">{{item.des}}</span> <br>
          <span><el-button  @click="addShopCart(item.pic,item.bookname,item.price,item.id)" type="text" class="button">加入购物车</el-button></span>
        </p>
        <el-card style="width: 300px;margin-left: 140px;margin-bottom: 20px;height: 380px;float: left;margin-right: 15px" class="book"
                 bodyStyle="padding:10px" shadow="hover">
          <div class="cover">
            <img :src="getUrl(item.pic)" alt="封面">
          </div>
          <div class="info">
            <div class="title">
              <a href="">{{item.bookname}}</a>
            </div>
          </div>
          <div class="author">{{item.author}}</div>
          <span class="price_style">{{item.price}}</span>
          <div class="bottom ">
            <el-button  @click="addShopCart(item.pic,item.bookname,item.price,item.id)" type="text" class="button">加入购物车</el-button>
          </div>
        </el-card>
      </el-tooltip>    
      
    </el-row>

  </div>
</template>

<script>
import {getBookData,addShopCar} from "../api/index.js"
import {mixin} from "../mixins"
export default {
  mixins:[mixin],
  props:{
    'sort':[String]
  },
  data() {
    return {
      BookData:[],
      SortData:[],
      //传入购物车的数据
      bookName: '',
      bookPic: '',
      bookPrice: '',
      pageSize: 9,   //分页每页大小
      currentPage: 1,//当前页号
      tempData: [],
      select_word: '',//选择的项
    }
  },
  computed:{
      //计算当前搜索结果的数据
    data(){
      return this.SortData.slice((this.currentPage-1)*this.pageSize,this.currentPage*this.pageSize);
    }
  },
  created(){
    this.getBooks();
  },
  methods:{
     //获取当前页
    handleCurrentChange(page){
      this.currentPage = page;
    },
    //获得图书信息
    getBooks(){
      getBookData()
      .then(res => {
        let count = 0;
        for(let i = 0; i< res.length; i++){
          //如果图书库存为0则不显示
          if(res[i].sort == this.sort&&res[i].stock!=0){
            this.BookData[count] = res[i];
            count++;
          }
        }
        //魔术代码，(＾－＾)V，删了没数据哦！
        this.SortData = this.BookData;
        // console.log(this.SortData);
      })
    },
    //加入购物车
    addShopCart(a,b,c,d){
      this.bookPic = a;
      this.bookName = b;
      this.bookPrice = c;
      this.bookId = d;
      //加入该账户的购物车
      let account = localStorage.getItem("account");
      if(account){
        let params = new URLSearchParams();
        params.append("id",this.bookId);
        params.append("account",account);
        params.append("bookname",this.bookName);
        params.append("bookpic",this.bookPic);
        params.append("bookcount",1);
        params.append("bookprice",this.bookPrice);

        addShopCar(params)
        .then(res => {
          if(res.code == 1){
            this.$notify({
            title:"加入购物车成功",
            type:"success"
          });
          }else{
            this.$notify({
            title:"加入购物车失败",
            type:"fail"
          });
          }
        })
        .catch(err => {
          console.log(err);
        })
      }else{
         this.$notify({
            title:"请先登录",
            type:"fail"
          });
      }

    }
  }
}
</script>

<style scoped>
 .cover {
    width: 100%;
    margin-bottom: 7px;
    overflow: hidden;
    cursor: pointer;
  }

  img {
    width: 200px;
    height: 180px;
    /*margin: 0 auto;*/
  }

  .title {
    font-size: 24px;
    text-align: left;
  }

  .author {
    color: #333;
    width: 102px;
    font-size: 13px;
    margin-bottom: 6px;
    text-align: left;
  }

  .abstract {
    display: block;
    line-height: 17px;
  }

  a {
    text-decoration: none;
  }

  a:link, a:visited, a:focus {
    color: #3377aa;
  }

   .price_style{
    float:right;
    font-size: 15px;
    color: red;
  }

   .pagination{
   display: flex;
   justify-content: center;
  }

  .handle-input{
    width: 300px;
    display: inline-block;
    margin-left: 35%;
    margin-bottom: 20px;
  }

  

</style>