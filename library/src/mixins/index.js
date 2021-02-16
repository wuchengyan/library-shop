export const mixin = {
  methods:{
    //提示信息
    notify(title,type){
      this.$notify({
        title:title,
        type:type
      })
    },
    //获取绝对地址
    getUrl(url){
      return `${this.$store.state.HOST}/${url}`
    },
    //上传图片前校验
    beforeAvatorUpload(file){
      const isIMG = (file.type === 'image/jpeg')||(file.type === 'image/png');
      if(!isIMG){
        this.$message.error('上传图片格式要求为jpg/jpeg/png格式');
        return false;
      }
      const isLt2M = (file.size / 1024 / 1024) < 2;
      if(!isLt2M){
        this.$message.error('上传图片大小要求小于2M');
        return false;
      }
      return true;
    },
    //上传成功后
    handleAvatorSuccess(res){
      let _this = this;
      if(res.code == 1){
        _this.getData();
        _this.notify({
          title: '上传成功',
          type: 'success'
        });
      }else{
        _this.notify({
          title: '上传失败',
          type: 'error'
        });
      }
    },
    //转换用户类型
     changeType(value){
      if(value == 0){
        return "管理员"
      }else{
        return "用户"
      }
    },
    //提示库存不足
    changeStock(value){
      if(value == 0){
        return "库存不足，请及时补充"
      }else{
        return value
      }
    },
    //转换订单状态
    changeStatus(value){
      if(value == 0){
        return "待发货"
      }else if(value == 1){
        return "已发货"
      }else if(value == 2){
        return "未收货"
      }else{
        return "已收货"
      }
    },
    //弹出删除窗口
    handleDelete(id){
      this.idx = id;
      this.delDialogVisible = true;
    },
     //将选中的值赋给mul
     handleSelectionChange(v){
      this.multipleSelection = v;
    },
    delAll(){
      if(this.multipleSelection == ''){
        this.notify("请至少选中一项","error");
      }else{
        for(let i of this.multipleSelection){
        this.handleDelete(i.id);
        this.deleteRow();
        } 
        this.multipleSelection = [];
      }
    },
    //重置表单
    resetForm(formName){
      this.$refs[formName].resetFields();
    }
  }
}