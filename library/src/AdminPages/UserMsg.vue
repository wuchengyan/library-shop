<template>
  <div>
    <div class="container">
      <div class="handle-box">
        <el-button type="primary" size="mini" @click="delAll">批量删除</el-button>
        <el-input v-model="select_word" placeholder="请输入用户账号" class="handle-input"></el-input>
        <el-button type="primary" size="mini" @click="centerDialogVisible = true">添加用户</el-button>
      </div>
    </div>
    <el-table size="mini" border style="width:100%" height="622px" :data="data" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40px" label=""></el-table-column>
      <el-table-column prop="account" label="账号" width="120px" align="center"></el-table-column>
      <el-table-column prop="name" label="姓名"  align="center"></el-table-column>
      <el-table-column prop="phone" label="电话"  align="center"></el-table-column>
      <el-table-column prop="mail" label="邮箱"  align="center"></el-table-column>
      <el-table-column prop="address" label="地址"  align="center"></el-table-column>
      <el-table-column prop="type" label="类型"  align="center">
        <template slot-scope="scope">
          {{changeType(scope.row.type)}}
        </template>
      </el-table-column>
      <el-table-column label="头像"  align="center">
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
     <!--添加用户-->
    <el-dialog title="添加用户" :visible.sync="centerDialogVisible" width="400px" center>
      <el-form :model="registerForm" status-icon :rules="rules" ref="registerForm" label-width="80px">
        <el-form-item prop="account" label="账号" size="mini">
          <el-input v-model="registerForm.account" placeholder="账号"></el-input>
        </el-form-item>
        <el-form-item prop="password" label="密码" size="mini">
          <el-input type="password" v-model="registerForm.password" placeholder="密码"></el-input>
        </el-form-item>
        <el-form-item prop="name" label="姓名" size="mini">
          <el-input  v-model="registerForm.name" placeholder="姓名"></el-input>
        </el-form-item>
        <el-form-item prop="phone" label="电话" size="mini">
          <el-input v-model="registerForm.phone" placeholder="电话"></el-input>
        </el-form-item>
        <el-form-item prop="mail" label="邮箱" size="mini">
          <el-input v-model="registerForm.mail" placeholder="邮箱"></el-input>
        </el-form-item>
        <el-form-item  label="地址" prop="address">
          <v-distpicker :province="province_select" :city="city_select" :area="area_select" @province="onChangeProvince" @city="onChangeCity" @area="onChangeArea"></v-distpicker>
        </el-form-item>
        <el-form-item label="类型" prop="type" size="mini">
          <el-radio-group v-model="registerForm.type">
            <el-radio :label="0">管理员</el-radio>
            <el-radio :label="1">用户</el-radio>
          </el-radio-group> 
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button size="mini" @click="resetOrderFormAndClose('registerForm')">取消</el-button>
        <el-button size="mini" @click="resetForm('registerForm')">重置</el-button>
        <el-button size="mini" @click="addUser">确定</el-button>
      </span>
    </el-dialog>
     <!--修改用户-->
    <el-dialog title="修改用户" :visible.sync="editDialogVisible" width="400px" center>
      <el-form :model="editForm" status-icon :rules="rules" ref="editForm" label-width="80px">
        <el-form-item prop="account" label="账号" size="mini">
          <el-input v-model="editForm.account" placeholder="账号"></el-input>
        </el-form-item>
        <el-form-item prop="password" label="密码" size="mini">
          <el-input type="password" v-model="editForm.password" placeholder="密码"></el-input>
        </el-form-item>
        <el-form-item prop="name" label="姓名" size="mini">
          <el-input  v-model="editForm.name" placeholder="姓名"></el-input>
        </el-form-item>
        <el-form-item prop="phone" label="电话" size="mini">
          <el-input v-model="editForm.phone" placeholder="电话"></el-input>
        </el-form-item>
        <el-form-item prop="mail" label="邮箱" size="mini">
          <el-input v-model="editForm.mail" placeholder="邮箱"></el-input>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <v-distpicker :province="province_select" :city="city_select" :area="area_select"  @province="onChangeProvince" @city="onChangeCity" @area="onChangeArea"></v-distpicker>
        </el-form-item>
        <el-form-item prop="type" label="类型" size="mini">
          <el-radio-group v-model="editForm.type">
            <el-radio :label="0">管理员</el-radio>
            <el-radio :label="1">用户</el-radio>
          </el-radio-group> 
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button size="mini" @click="editCancle">取消</el-button>
        <el-button size="mini" @click="editSave">确定</el-button>
      </span>
    </el-dialog>
    <!--删除用户-->
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
import {getUserData,registerStatus,updateUser,deleteUser} from "../api/index"
import {mixin} from '../mixins'
export default {
  name: 'UserMsg',
  mixins:[mixin],
  data(){
    return{
      //添加弹窗
      centerDialogVisible: false,
      //编辑弹窗
      editDialogVisible: false,
      //删除弹窗
      delDialogVisible: false,
      registerForm:{
        //添加框
        account: '',
        password: '',
        name: '',
        phone: '',
        mail: '',
        address: '',
        type: '',
      },
      editForm:{
        //编辑框
        id: '',
        account: '',
        password: '',
        name: '',
        phone: '',
        mail: '',
        address: '',
        type: '',
      },
      rules:{
        account:[
           {required:true,message:"请输入用户名",trigger:"blur"},
           { min: 1, max: 10, message: '长度在 1 到 10 个字符', trigger: 'blur' }
        ],
        password:[
           {required:true,message:"请输入密码",trigger:"blur"},
           { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
        ],
        name:[
           {required:true,message:"请输入姓名",trigger:"blur"}
        ],
        phone:[
           {required:true,message:"请输入手机号",trigger:"blur"},
           {validator: function(rule, value, callback) {
              if (/^1[34578]\d{9}$/.test(value) == false) {
                callback(new Error("手机号格式错误"));
              } else {
                callback();
              }
            },
            trigger: "blur"}
        ],
        mail:[
          {
            required: true,
            message: "请输入邮箱",
            trigger: "blur"
          },
          {
            validator: function(rule, value, callback) {
              if (
                /^\w{1,64}@[a-z0-9\-]{1,256}(\.[a-z]{2,6}){1,2}$/i.test(
                  value
                ) == false
              ) {
                callback(new Error("邮箱格式错误"));
              } else {
                callback();
              }
            },
            trigger: "blur"
          }
        ],
        address:[
           {required:true,message:"请输入地址",trigger:"blur"}
        ],
        type:[
          {required:true,message:"请选择身份",trigger:"blur"}
        ]
      },
      province: '', //省
      city: '',   //市
      area: '', //区域
      detailaddress: '',//具体选择的地址
      tableData:[],
      tempData: [],
      pageSize: 4,   //分页每页大小
      currentPage: 1,//当前页号
      idx: '',//当前选择项
      multipleSelection:[],  //选择的项
      select_word: '',
      province_select: '',//编辑的省份
      city_select: '',//编辑的城市
      area_select: '',//编辑的区域
    }
  },
  computed:{
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
          if(i.account.includes(this.select_word)){
            this.tableData.push(i);
          }
        }
      }
    }
  },
  created(){
    this.getData();
  },
  methods: {
    //重置并关闭添加框
    resetOrderFormAndClose(formName){
      this.$refs[formName].resetFields();
      this.province_select = '';
      this.city_select = '';
      this.area_select = '';
      this.centerDialogVisible = false;
    },
    //更新图片
    uploadUrl(id){
      return `${this.$store.state.HOST}/user/updateUserPic?id=${id}`;
    },
    //获取当前页
    handleCurrentChange(page){
      this.currentPage = page;
    },
    getData(){
      this.tableData = [];
      this.tempData = [];
      getUserData().then((res) => {
        this.tableData = res;
        this.tempData = res;
        //解决删除bug
        this.currentPage = 1;
      })
      .catch(err => {
        console.log(err);
      })
    },
    //获取选择的地址
     onChangeProvince(a){
      this.province = a.value;
      this.province_select = a.value;
    },
    onChangeCity(a){
      this.city = a.value;
      this.city_select = a.value;
    },
    onChangeArea(a){
      this.area = a.value;
      this.area_select = a.value;
    },
    //添加用户
    addUser(){
      //获取具体地址
      this.detailaddress = this.province + this.city + this.area;
      this.registerForm.address = this.detailaddress;

      let params = new URLSearchParams();
      params.append("account",this.registerForm.account);
      params.append("password",this.registerForm.password);
      params.append("name",this.registerForm.name);
      params.append("phone",this.registerForm.phone);
      params.append("mail",this.registerForm.mail);
      params.append("address",this.registerForm.address);
      params.append("pic",'/img/userPic/user.jpg');
      params.append("type",this.registerForm.type);

      registerStatus(params)
      .then((res) => {
         if(res.code == 1){
          //提示信息
          this.$notify({
            title:"添加成功",
            type:"success"
          });
          //刷新当前页面
          this.getData();
        }else{
          this.$notify({
            title:"添加失败",
            type:"fail"
          });
        }
      })
      .catch(err =>{
        console.log(err);
      });
      this.centerDialogVisible = false;
      //清空数据
      this.registerForm = [];
      this.province_select = '省';
      this.city_select = '市';
      this.area_select = '区';
      },
      //弹出编辑框
      handleEdit(row){
        this.editDialogVisible = true;
        this.editForm = {
          id: row.id,
          account:row.account,
          password:row.password,
          name:row.name,
          phone:row.phone, 
          mail:row.mail,
          type:row.type
        };
        //区分省市区
        var reg = /.+?(省|市|自治区|自治州|县|区)/g;
        var arr = row.address.match(reg);
        this.province_select = arr[0];
        this.city_select = arr[1];
        this.area_select = arr[2];
        
      },
      //取消修改
      editCancle(){
        this.editDialogVisible = !this.editDialogVisible;
        this.province_select = '省';
        this.city_select = '市';
        this.area_select = '区';
      },
      //修改用户
      editSave(){
        //获取具体地址
        this.detailaddress = this.province_select + this.city_select + this.area_select;
        this.editForm.address = this.detailaddress;
        
        let params = new URLSearchParams();
        params.append("id",this.editForm.id);
        params.append("account",this.editForm.account);
        params.append("password",this.editForm.password);
        params.append("name",this.editForm.name);
        params.append("phone",this.editForm.phone);
        params.append("mail",this.editForm.mail);
        params.append("address",this.editForm.address);
        params.append("type",this.editForm.type);

        updateUser(params)
        .then((res) => {
          if(res.code == 1){
             this.$notify({
              title:"修改成功",
              type:"success"
          });
          //关闭弹窗
          this.editDialogVisible = false;
          this.getData();
          }else{
          this.$notify({
            title:"修改失败",
            type:"fail"
          });
        }
        })
        .catch(err => {
          console.log(err);
        }); 
      },
      //删除用户
      deleteRow(){
        deleteUser(this.idx)
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
    height: 100px;
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