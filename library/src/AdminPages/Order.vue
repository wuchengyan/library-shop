<template>
  <div>
    <div class="container"> 
      <div class="handle-box">
        <el-button type="primary" size="mini" @click="delAll">批量删除</el-button>
        <el-input v-model="select_word" placeholder="请输入订单号" class="handle-input"></el-input>
        <el-button type="primary" size="mini" @click="centerDialogVisible = true">添加订单</el-button>
      </div>
    </div>
    <el-table :data="data" border style="width: 100%" size="big" height="333px" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40px" ></el-table-column>
      <el-table-column prop="ordernumber" label="订单号" width="120px" align="center"></el-table-column>
      <el-table-column prop="account" label="账号"  align="center"></el-table-column>
      <el-table-column prop="price" label="总价" align="center"></el-table-column>
      <el-table-column prop="address" label="发货地址" align="center"></el-table-column>
      <el-table-column prop="status" label="发货状态" align="center">
        <template slot-scope="scope">
          {{changeStatus(scope.row.status)}}
        </template>
      </el-table-column>
      <el-table-column prop="createtime" label="下单时间" align="center"></el-table-column>
      <el-table-column label="操作" width="250px" align="center">
        <template slot-scope="scope"> 
          <el-button type="info" size="mini" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="danger" size="mini" @click="handleDelete(scope.row.id)">删除</el-button>
          <el-button :disabled="handleStatus(scope.row.status)" type="primary" size="mini" @click="SendOrder(scope.row)">发货</el-button>
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

    <!--添加订单-->
    <el-dialog title="添加订单" :visible.sync="centerDialogVisible" width="400px" center>
      <el-form ref="registerForm" :model="registerForm" status-icon :rules="rules" label-width="80px">
        <el-form-item prop="ordernumber" label="订单号" size="mini">
          <el-input v-model="registerForm.ordernumber" placeholder="订单号"></el-input>
        </el-form-item>
        <el-form-item prop="account" label="账号" size="mini">
          <el-select v-model="value1" filterable placeholder="请选择或搜索">
            <el-option
              v-for="item in accountOptions"
              :key="item.value"
              :label="item"
              :value="item">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="price" label="总价" size="mini">
          <el-input v-model="registerForm.price" placeholder="总价"></el-input>
        </el-form-item>
        <el-form-item  label="发货地址" prop="address">
          <v-distpicker :province="province_select" :city="city_select" :area="area_select" @province="onChangeProvince" @city="onChangeCity" @area="onChangeArea"></v-distpicker>
        </el-form-item>
        <el-form-item prop="status" label="发货状态" size="mini">
          <el-select v-model="value" filterable placeholder="请选择">
            <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item  label="下单时间" prop="createtime">
          <el-date-picker
            v-model="registerForm.createtime"
            type="date"
            placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <span slot="footer" class="addbtn">
        <el-button size="mini" @click="resetOrderFormAndClose('registerForm')">取消</el-button>
        <el-button size="mini"  @click="resetOrderForm('registerForm')">重置</el-button>
        <el-button size="mini" @click="addOrder">确定</el-button>
      </span>
    </el-dialog>

    <!--修改订单-->
    <el-dialog title="修改订单" :visible.sync="editDialogVisible" width="400px" center>
      <el-form :model="editForm" status-icon :rules="rules" ref="editForm" label-width="80px">
       <el-form-item prop="ordernumber" label="订单号" size="mini">
          <el-input v-model="editForm.ordernumber" placeholder="订单号"></el-input>
        </el-form-item>
        <el-form-item prop="account" label="账号" size="mini">
          <el-select v-model="editForm.account" filterable placeholder="请选择或搜索">
            <el-option
              v-for="item in accountOptions"
              :key="item.value"
              :label="item"
              :value="item">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="price" label="总价" size="mini">
          <el-input v-model="editForm.price" placeholder="总价"></el-input>
        </el-form-item>
        <el-form-item  label="发货地址" prop="address">
          <v-distpicker :province="province_select" :city="city_select" :area="area_select" @province="onChangeProvince" @city="onChangeCity" @area="onChangeArea"></v-distpicker>
        </el-form-item>
        <el-form-item prop="status" label="发货状态" size="mini">
          <el-select  v-model="value" filterable placeholder="请选择">
            <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item  label="下单时间" prop="createtime">
          <el-date-picker
            v-model="editForm.createtime"
            type="date"
            placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
      </el-form>
       <span slot="footer">
        <el-button size="mini" @click="editCancle">取消</el-button>
        <el-button size="mini" @click="editSave">确定</el-button>
      </span>
    </el-dialog>

    <!--删除订单-->
    <el-dialog title="删除订单" :visible.sync="delDialogVisible" width="300px" center>
      <div> 是否删除？</div>
      <span slot="footer">
        <el-button size="mini" @click="delDialogVisible = false">取消</el-button>
        <el-button size="mini" @click="deleteRow">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {mixin} from "../mixins"
import {addOrder, getOrderData,getUserData,updateOrder,deleteOrder,updateOrderStatus} from "../api/index"
export default {
  name: 'Order',
  mixins:[mixin],
  data(){
    //定义订单号规则
    var checkOrderNumber = (rule,value,callback) => {
      if(!value){return callback(new Error('订单号不能为空'));}
      setTimeout(()=>{
        if(!Number(value)){callback(new Error('必须为数值'));}
        else if(value <= 0){callback(new Error('订单号不能为负数'));}
        else{callback();}
      },1000);
    };
    //定义总价规则
    var checkPrice = (rule,value,callback) => {
      if(!value){return callback(new Error('总价不能为空'));}
      setTimeout(()=>{
        if(!Number(value)){callback(new Error('必须为数值'));}
        else if(value <= 0) {callback(new Error('总价不能为负数'));}
        else{callback();}
      },1000);
    };
    return{
      select_word:'',//选择的订单号
      tableData:[],
      tempData: [],
      currentPage: 1,
      pageSize: 4,
      province_select: '',//动态绑定省
      city_select: '',//动态绑定市
      area_select: '',//动态绑定区域
      options:[
        {value: '0', label: '待发货'},
        {value: '1', label: '已发货'},
        {value: '2', label: '未收货'},
        {value: '3', label: '已收货'}
      ],
      accountOptions:[],//获取用户账号集合
      value:'',//发货状态值
      isSend: '',//是否发货,
      value1: '',//账号选择值
      idx: '',//当前选择行
      multipleSelection: [],//多选删除项
      centerDialogVisible:false, //添加弹窗
      editDialogVisible: false,//修改弹窗
      delDialogVisible: false,//删除弹窗
      //添加框
      registerForm:{
        ordernumber: '',
        account: '',
        price: '',
        address: '',
        status: '',
        createtime: ''
      },
      editForm:{
        id: '',
        ordernumber: '',
        account: '',
        price: '',
        address: '',
        status: '',
        createtime: ''
      },
      rules:{
        ordernumber:[
          {required: true,message:"请输入订单号",trigger:"blur"},
          {validator:checkOrderNumber,trigger:"blur"}
        ],
        account:[
          {required: true,message:"请选择或搜索账号",trigger:"blur"},
        ],
        price:[
          {required: true,message:"请输入总价",trigger:"blur"},
          {validator:checkPrice,trigger:"blur"}
        ],
        address:[
          {required: true,message:"请选择发货地址",trigger:"blur"},
        ],
        status:[
          {required: true,message:"请选择发货状态",trigger:"blur"},
        ],
        createtime:[
          {required: true,message:"请选择下单时间",trigger:"blur"},
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
  methods:{
    //控制发货按钮disabled
    handleStatus(status){
      if(status == 0) {
        //去掉disabled
        return false;
        }
      else{ 
        return true;
        }
    },
    //发货按钮
    SendOrder(row){
      // console.log(row.status);
      // console.log(row.id);
      let params = new URLSearchParams();
      params.append("id",row.id);
      params.append("status",2);

      updateOrderStatus(params)
      .then(res => {
        if(res.code == 1){
          this.getData();
          this.notify("发货成功","success");
        }else{
          this.notify("发货失败","fail");
        }
      })
      .catch(err => {
        console.log(err);
      });
    },
    //删除订单
    deleteRow(){
      deleteOrder(this.idx)
      .then(res => {
        if(res){
          this.getData();
          this.notify("删除成功","success");
        }else{
          this.notify("删除失败","fail");
        }
      })
      .catch(err => {
        console.log(err);
      });
      this.delDialogVisible = false;
    },
    //弹出编辑框
    handleEdit(row){
      this.editDialogVisible = true;
      this.editForm ={
        id: row.id,
        ordernumber: row.ordernumber,
        account: row.account,
        price: row.price,
        createtime: row.createtime
      };
       //区分省市区
        var reg = /.+?(省|市|自治区|自治州|县|区)/g;
        var arr = row.address.match(reg);
        this.province_select = arr[0];
        this.city_select = arr[1];
        this.area_select = arr[2];

        //转换订单状态
        if(row.status == 0){
          this.editForm.status = "待发货"
        }else if(row.status == 1){
          this.editForm.status = "已发货"
        }else if(row.status == 2){
          this.editForm.status = "未收货"
        }else{
          this.editForm.status = "已收货"
        }
        this.value = this.editForm.status;
    },
    //取消修改
    editCancle(){
      this.editDialogVisible = false;
      this.province_select = '省';
      this.city_select = '市';
      this.area_select = '区';
      this.value = '';
    },
    //修改订单
    editSave(){
      //转换时间
      let d = new Date(this.editForm.createtime);
      let datetime = d.getFullYear()+'-'+(d.getMonth()+1)+'-'+d.getDate();
      //获得地址
      let area = this.province_select+this.city_select+this.area_select;

      let realvalue = '';
      if(this.value == "待发货" || this.value == 0){realvalue = 0;}
      else if(this.value == "已发货" || this.value == 1){realvalue = 1;}
      else if(this.value == "未收货" || this.value == 2){realvalue = 2;}
      else if(this.value == "已收货" || this.value == 3){realvalue = 3;}

      let params = new URLSearchParams();
      params.append("id",this.editForm.id);
      params.append("ordernumber",this.editForm.ordernumber);
      params.append("account",this.editForm.account);
      params.append("price",this.editForm.price);
      params.append("address",area);
      params.append("status",realvalue);
      params.append("createtime",datetime);

      updateOrder(params)
      .then(res => {
        if(res.code == 1){
          this.getData();
          this.notify("修改成功","success");
        }else{
          this.notify("修改失败","fail");
        }
      })
      .catch(err => {
        console.log(err);
      })

      this.editDialogVisible = false;

    },
    //获取当前页
    handleCurrentChange(page){
      this.currentPage = page;
    },
    //获取选择的发货地址
    onChangeProvince(v){this.province_select = v.value;},
    onChangeCity(v){this.city_select = v.value},
    onChangeArea(v){this.area_select = v.value},
    //重置添加框
    resetOrderForm(formName){
      this.$refs[formName].resetFields();
      this.province_select = '';
      this.city_select = '';
      this.area_select = '';
      this.value = '';
    },
    //关闭并重置添加框
    resetOrderFormAndClose(formName){
      this.$refs[formName].resetFields();
      this.province_select = '';
      this.city_select = '';
      this.area_select = '';
      this.value = '';
      this.centerDialogVisible = false;
    },
    //添加订单
    addOrder(){
      //转换时间
      let d = new Date(this.registerForm.createtime);
      let datetime = d.getFullYear()+'-'+(d.getMonth()+1)+'-'+d.getDate();
      //获得地址
      let area = this.province_select+this.city_select+this.area_select;

      let params = new URLSearchParams();
      params.append("ordernumber",this.registerForm.ordernumber);
      params.append("account",this.value1);
      params.append("price",this.registerForm.price);
      params.append("address",area);
      params.append("status",this.value);
      params.append("createtime",datetime);

      addOrder(params)
      .then(res => {
        if(res.code == 1){
          //刷新下页面
          this.getData();
          this.notify("添加成功","success");
        }else{
          this.notify("添加失败","fail");
        }
      })
      .catch(err => {
        console.log(err);
      });

      //关闭添加框
      this.resetOrderFormAndClose('registerForm');
      this.value1 = '';
    },
    //获取所有数据
    getData(){
      this.tableData = [];
      this.tempData = [];
      getOrderData()
      .then(res => {
        this.tableData = res;
        this.tempData = res;
        this.currentPage = 1;
      })
      .catch(err => {
        console.log(err);
      })
    //获取用户账号
      getUserData()
      .then(res => {
        let length = res.length;
        for(let i = 0;i < length; i++){
          this.accountOptions[i] = res[i].account;
        }
      })
      .then(err => {
        console.log(err);
      });

    },
    

  },
  watch:{
   //搜索框里的内容变化，搜索table里面的内容动态变化
    select_word:function(){
      if(this.select_word == ''){
        this.tableData = this.tempData;
      }else{
        this.tableData = [];
        for(let i of this.tempData){
          if(i.ordernumber.includes(this.select_word)){
            this.tableData.push(i);
          }
        }
      }
    },
  },
  created(){
    this.getData();
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

  .handle-input{
    width: 300px;
    display: inline-block;

  }

  .pagination{
    display: flex;
    justify-content: center;
  }

  .addbtn{
    margin-right: 25%;
  }


</style>