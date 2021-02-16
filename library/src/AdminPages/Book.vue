<template>
  <div>
    <div class="container">
      <div class="handle-box">
        <el-button type="primary" size="mini" @click="delAll">批量删除</el-button>
        <el-input v-model="select_word" placeholder="请输入图书名" class="handle-input"></el-input>
        <el-button type="primary" size="mini" @click="centerDialogVisible = true">添加图书</el-button>
      </div>
    </div>
    <el-table size="mini" border style="width:100%" height="622px" :data="data" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40px" label=""></el-table-column>
      <el-table-column prop="bookname" label="书名" width="120px" align="center"></el-table-column>
      <el-table-column prop="author" label="作者"  align="center"></el-table-column>
      <el-table-column prop="des" label="描述"  align="center"></el-table-column>
      <el-table-column prop="price" label="价格"  align="center"></el-table-column>
      <el-table-column prop="sort" label="分类"  align="center"></el-table-column>
      <el-table-column prop="createtime" label="出版时间"  align="center"></el-table-column>
      <el-table-column prop="stock" label="库存"  align="center">
        <template slot-scope="scope">
          {{changeStock(scope.row.stock)}}
        </template>
      </el-table-column>
      <el-table-column label="封面"  align="center">
         <template slot-scope="scope">
            <div class="client-img">
              <img class="person-img" alt="没有图片" :src="getUrl(scope.row.pic)" style="width:100%" />
            </div>
            <el-upload :action="uploadUrl(scope.row.id)" :before-upload="beforeAvatorUpload" :on-success="handleAvatorSuccess">
              <el-button size="mini" type="primary">更新图片</el-button>
            </el-upload>
          </template>
      </el-table-column>
      <el-table-column label="操作" width="150px" align="center">
        <template slot-scope="scope">
          <el-button size="mini" type="info" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <div class="pagination">
      <el-pagination 
        background 
        layout="total,prev,pager,next"
        :current-page="currentPage"
        :page-size="pageSize"
        :total="tableData.length"
        @current-change="handleCurrentChange"></el-pagination>
    </div>

    <!--添加图书-->
    <el-dialog title="添加图书" :visible.sync="centerDialogVisible" width="400px" center>
      <el-form :model="registerForm" status-icon :rules="rules" ref="registerForm" label-width="80px">
        <el-form-item prop="bookname" label="书名" size="mini">
          <el-input v-model="registerForm.bookname" placeholder="书名"></el-input>
        </el-form-item>
        <el-form-item prop="author" label="作者" size="mini">
          <el-input v-model="registerForm.author" placeholder="作者"></el-input>
        </el-form-item>
        <el-form-item prop="des" label="描述" size="mini">
          <el-input  v-model="registerForm.des" type="textarea" autosize placeholder="描述"></el-input>
        </el-form-item>
        <el-form-item prop="price" label="价格" size="mini">
          <el-input v-model="registerForm.price" placeholder="价格"></el-input>
        </el-form-item>
        <el-form-item prop="sort" label="分类" size="mini">
          <el-select v-model="value" filterable placeholder="请选择">
            <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item  label="出版时间" prop="createtime">
          <el-date-picker
            v-model="selected_time"
            type="date"
            placeholder="选择日期">
        </el-date-picker>
        </el-form-item>
        <el-form-item label="库存" prop="stock" size="mini">
          <el-input v-model.number="registerForm.stock" placeholder="库存"></el-input> 
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button size="mini" @click="resetOrderFormAndClose('registerForm')">取消</el-button>
        <el-button size="mini" @click="resetForm('registerForm')">重置</el-button>
        <el-button size="mini" @click="addBook">确定</el-button>
      </span>
    </el-dialog>

    <!--修改图书-->
     <el-dialog title="修改图书" :visible.sync="editDialogVisible" width="400px" center>
      <el-form :model="editForm" status-icon :rules="rules" ref="editForm" label-width="80px">
        <el-form-item prop="bookname" label="书名" size="mini">
          <el-input v-model="editForm.bookname" placeholder="书名"></el-input>
        </el-form-item>
        <el-form-item prop="author" label="作者" size="mini">
          <el-input v-model="editForm.author" placeholder="作者"></el-input>
        </el-form-item>
        <el-form-item prop="des" label="描述" size="mini">
          <el-input  v-model="editForm.des" type="textarea" autosize placeholder="描述"></el-input>
        </el-form-item>
        <el-form-item prop="price" label="价格" size="mini">
          <el-input v-model="editForm.price" placeholder="价格"></el-input>
        </el-form-item>
        <el-form-item prop="sort" label="分类" size="mini">
          <el-select v-model="editForm.sort" filterable placeholder="请选择">
            <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item  label="出版时间" prop="createtime">
          <el-date-picker
            v-model="editForm.createtime"
            type="date"
            placeholder="选择日期">
        </el-date-picker>
        </el-form-item>
        <el-form-item label="库存" prop="stock" size="mini">
          <el-input v-model.number="editForm.stock" placeholder="库存"></el-input> 
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button size="mini" @click="editDialogVisible = false">取消</el-button>
        <el-button size="mini" @click="editSave">确定</el-button>
      </span>
    </el-dialog>

     <!--删除图书-->
    <el-dialog title="删除用户" :visible.sync="delDialogVisible" width="300px" center>
      <div> 是否删除？</div>
      <span slot="footer">
        <el-button size="mini" @click="delDialogVisible = false">取消</el-button>
        <el-button size="mini" @click="deleteRow">确定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
import {getBookData,addBook,updateBook,deleteBook} from "../api/index"
import {mixin} from "../mixins"
export default {
  name: 'Book',
  mixins:[mixin],
  data(){
    //定义价格和库存规则
     var checkPrice = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('价格不能为空'));
        }
        setTimeout(() => {
          if (!Number(value)) {
            callback(new Error('请输入数值'));
          } else {
            callback();
          }
        }, 1000);
      };
     var checkStock = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('库存不能为空'));
        }
        setTimeout(() => {
          if (!Number.isInteger(value)) {
            callback(new Error('请输入数值'));
          } else {
            if (value <= 0) {
              callback(new Error('必须大于一本'));
            } else {
              callback();
            }
          }
        }, 1000);
      };
    return{
      //添加弹窗
      centerDialogVisible: false,
      //修改弹窗
      editDialogVisible: false,
      //删除弹窗
      delDialogVisible: false,
      //添加框
      registerForm:{
        bookname: '',
        author: '',
        des: '',
        price: '',
        sort: '',
        createtime: '',
        stock: ''
      },
      //修改框
      editForm:{
        id: '',
        bookname: '',
        author: '',
        des: '',
        price: '',
        sort: '',
        createtime: '',
        stock: ''
      },
      tableData:[],
      tempData: [],
      select_word: '',//选择的项
      pageSize: 10,   //分页每页大小
      currentPage: 1,//当前页号
      idx: '',//当前选择项
      multipleSelection:[],  //选择的项
      //分类选项
      options:[
        {value: '青春文学', label: '青春文学'},
        {value: '小说', label: '小说'},
        {value: '休闲爱好', label: '休闲爱好'},
        {value: '文学', label: '文学'},
        {value: '艺术', label: '艺术'},
        {value: '动漫', label: '动漫'},
        {value: '时尚', label: '时尚'},
        {value: '旅游', label: '旅游'}
      ],
      value: '', //分类选择值
      selected_time: '',//时间选择值
      rules:{
        bookname:[
           {required:true,message:"请输入书名",trigger:"blur"},
        ],
        author:[
           {required:true,message:"请输入作者",trigger:"blur"},
        ],
        des:[
           {required:true,message:"请输入描述",trigger:"blur"},
           { min: 1, max: 150, message: '长度在 1 到 50 个字符', trigger: 'blur' }
        ],
        price:[
           {validator: checkPrice,trigger:"blur"},
           {required:true,message:"请输入价格",trigger:"blur"},
        ],
        sort:[
          {required:true,message:"请选择或搜索分类",trigger:"blur"},
        ],
        stock:[
          {validator: checkStock,trigger:"blur"},
          {required:true,message:"请输入库存",trigger:"blur"},
        ]
      }
    }
  },
  computed:{
    //计算当前搜索结果的数据
    data(){
      return this.tableData.slice((this.currentPage-1)*this.pageSize,this.currentPage*this.pageSize);
    }
  },
  watch:{
    //搜索框里的内容变化，搜索table里面的内容动态变化
    select_word:function(){
      if(this.select_word == ''){
        this.tableData = this.tempData;
      }else{
        this.tableData = [];
        for(let i of this.tempData){
          if(i.bookname.includes(this.select_word)){
            this.tableData.push(i);
          }
        }
      }
    }
  },
  created(){
    this.getData();
  },
  methods:{
    //重置并关闭添加框
    resetOrderFormAndClose(formName){
      this.$refs[formName].resetFields();
      this.value = '';
      this.selected_time = '';
      this.centerDialogVisible = false;
    },
    //更新图片
    uploadUrl(id){
      return `${this.$store.state.HOST}/book/updateBookPic?id=${id}`;
    },
    //获取当前页
    handleCurrentChange(page){
      this.currentPage = page;
    },
    getData(){
      this.tableData = [];
      this.tempData = [];
      getBookData()
      .then(res => {
        this.tableData = res;
        this.tempData = res;
        this.currentPage = 1;
      })
      .catch(err => {
        console.log(err);
      })
    },
    //添加图书
    addBook(){
      //转换时间
      let d = this.selected_time;
      let datetime = d.getFullYear()+'-'+(d.getMonth()+1)+'-'+d.getDate();

      let params = new URLSearchParams();
      params.append("bookname",this.registerForm.bookname);
      params.append("author",this.registerForm.author);
      params.append("des",this.registerForm.des);
      params.append("price",this.registerForm.price);
      params.append("sort",this.value);
      params.append("createtime",datetime);
      params.append("stock",this.registerForm.stock);
      params.append("pic",'/img/bookPic/demo.png');

      addBook(params)
      .then(res => {
        if(res.code == 1){
           //提示信息
          this.$notify({
            title:"添加成功",
            type:"success"
          });
          //刷新当前页面
          this.getData();
          
        }else{
           //提示信息
          this.$notify({
            title:"添加失败",
            type:"fail"
          });
        }
      })
      .catch(err => {
        console.log(err);
      });

      //清空数据
      this.registerForm = [];
      this.value = '';
      this.selected_time = '';
      this.centerDialogVisible = false;
    },
    handleEdit(row){
       this.editDialogVisible = true;
        this.editForm = {
          id: row.id,
          bookname:row.bookname,
          author:row.author,
          des:row.des,
          price:row.price, 
          sort:row.sort,
          createtime:row.createtime,
          stock: row.stock
        };
    },
    //修改图书
    editSave(){
      //转换时间
      let d = new Date(this.editForm.createtime);
      let datetime = d.getFullYear()+'-'+(d.getMonth()+1)+'-'+d.getDate();
      console.log(this.editForm.createtime);
      let params = new URLSearchParams();
      params.append("id",this.editForm.id);
      params.append("bookname",this.editForm.bookname);
      params.append("author",this.editForm.author);
      params.append("des",this.editForm.des);
      params.append("price",this.editForm.price);
      params.append("sort",this.editForm.sort);
      params.append("createtime",datetime);
      params.append("stock",this.editForm.stock);

      updateBook(params)
      .then(res => {
        if(res.code == 1){
          //刷新下页面
          this.getData();
          this.notify("修改成功","success");
        }else{
          this.notify("修改失败","error");
        }
      })
      .catch(err => {
        console.log(err);
      });

      this.editDialogVisible = false;
    },
    //删除图书
    deleteRow(){
      deleteBook(this.idx)
      .then(res => {
        if(res){
           //刷新下页面
          this.getData();
          this.notify("删除成功","success");
        }else{
          this.notify("删除失败","fail");
        }
      })
      .catch(err => {
        console.log(err);
      })
      this.delDialogVisible = false;
    }
  }
}
</script>

<style scoped>
.table{
    min-width:800px;
  }

  .container{
    padding: 20px;
    background: #fff;
    border:1px solid #dddddd;
    border-radius:5px;
  }

  .handle-box{
    margin-bottom: 20px;
  }

  .client-img{
    margin: 5px auto;
  }

  .person-img{ 
    border-radius: 10px;
    margin-bottom: 1rem;
    height: 200px;
    object-fit: cover;
    border: 4px solid var(--clr-grey-8);
    box-shadow: var(--dark-shadow);
  }
  .handle-input{
    width: 300px;
    display: inline-block;

  }

  .pagination{
    display: flex;
    justify-content: center;
  }
</style>