import {get,post} from "./http";


/**
 * 用户和管理员接口
 */
//判断是否登录成功
export const getLoginStatus = (params) => post(`/user/verify`,params);

//注册
export const registerStatus = (params) => post(`/user/add`,params);

//获取所有用户资料
export const getUserData = () => get(`/user/findall`);

//修改某一用户资料
export const updateUser = (params) => post(`/user/update`,params);

//删除某一用户资料
export const deleteUser = (id) => get(`user/delete?id=${id}`);

//根据账号获得用户头像相对地址
export const getUserPic = (account) => get(`user/findbyaccount?account=${account}`);

//根据账号获得用户所有信息
export const getUserMsg = (account) => get(`user/findbyaccount?account=${account}`);

//更新用户头像
export const updateUserPic = (id)=>get(`user/updateUserPic?id=${id}`);


/**
 * 图书接口
 */
//获取所有图书
export const getBookData = () => get(`/book/findall`);

//添加图书
export const addBook = (params) => post(`/book/add`,params);

//修改图书
export const updateBook = (params) => post(`/book/update`,params);

//根据id修改库存
export const updateBookStockById = (params) => post(`/book/updateStock`,params);

//删除图书
export const deleteBook = (id) => get(`book/delete?id=${id}`);

/**
 * 订单接口
 */
//获取所有订单
export const getOrderData = () => get(`/order/findall`);

//添加订单
export const addOrder = (params) => post(`/order/add`,params);

//修改订单
export const updateOrder = (params) => post(`/order/update`,params);

//删除订单
export const deleteOrder = (id) => get(`/order/delete?id=${id}`);

//发货
export const updateOrderStatus = (params) => post(`/order/updateStatus`,params);


/**
 * 购物车接口
 */
//添加到购物车
export const addShopCar = (params) => post(`/shopcar/add`,params);

//删除购物车
export const deleteShopCar = (id) => get(`/shopcar/delete?id=${id}`);

//根据账号查找购物车
export const getShopCarDataByAccount = (account) => get(`/shopcar/findbyaccount?account=${account}`);

//根据账号删除购物车
export const deleteShopByAccount = (account) => get(`/shopcar/deletebyaccount?account=${account}`);


