import Vue from 'vue'
import Router from 'vue-router'
//登录组件
import Login from '@/components/Login'
import Register from '@/components/Register'

//管理员组件
import Admin from '@/AdminPages/Admin'
import Home from '@/AdminPages/Home'
import Book from '@/AdminPages/Book'
import Order from '@/AdminPages/Order'
import UserMsg from '@/AdminPages/UserMsg'
//用户组件
import Index from '@/UserPages/Index'
import Main from '@/UserPages/Main'
import ShopCart from '@/UserPages/ShopCart'
import UserOrder from '@/UserPages/UserOrder'
import User from '@/UserPages/User'
import BasicMsg from '@/UserPages/BasicMsg'
import MyOrders from '@/UserPages/MyOrders'

//图书分类组件
import SortOfYouthLiterature from '@/BookPages/SortOfYouthLiterature'
import SortOfLiterature from '@/BookPages/SortOfLiterature'
import SortOfCartoon from '@/BookPages/SortOfCartoon'
import SortOfArt from '@/BookPages/SortOfArt'
import SortOfFashion from '@/BookPages/SortOfFashion'
import SortOfFiction from '@/BookPages/SortOfFiction'
import SortOfRelax from '@/BookPages/SortOfRelax'
import SortOfTravel from '@/BookPages/SortOfTravel'


Vue.use(Router)

const router = new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/Register',
      name: 'Register',
      component: Register,
    },
    {
      path: '/Index',
      name: 'Index',
      component: Index,
      children:[
        {
          path: '/Main',
          name: 'Main',
          component: Main,
          children:[
            {
              path: '/SortOfYouthLiterature',
              name: 'SortOfYouthLiterature',
              component: SortOfYouthLiterature,
            },
            {
              path: '/SortOfLiterature',
              name: 'SortOfLiterature',
              component: SortOfLiterature,
            },
            {
              path: '/SortOfFiction',
              name: 'SortOfFiction',
              component: SortOfFiction,
            }
            ,
            {
              path: '/SortOfRelax',
              name: 'SortOfRelax',
              component: SortOfRelax,
            }
            ,
            {
              path: '/SortOfArt',
              name: 'SortOfArt',
              component: SortOfArt,
            }
            ,
            {
              path: '/SortOfCartoon',
              name: 'SortOfCartoon',
              component: SortOfCartoon,
            }
            ,
            {
              path: '/SortOfFashion',
              name: 'SortOfFashion',
              component: SortOfFashion,
            }
            ,
            {
              path: '/SortOfTravel',
              name: 'SortOfTravel',
              component: SortOfTravel,
            }
          ]
        },
        {
          path: '/ShopCart',
          name: 'ShopCart',
          component: ShopCart
        },
        {
          path: '/User',
          name: 'User',
          component: User,
          children:[
            {
              path: '/BasicMsg',
              name: 'BasicMsg',
              component: BasicMsg,
            },
            {
              path: '/MyOrders',
              name: 'MyOrders',
              component: MyOrders,
            }
          ]
        }
      ]
    },
    {
      path: '/Home',
      name: 'Home',
      component:Home,
      meta:{
        requireAuth:true
      },
      children:[ 
        {
          path: '/Admin',
          name: 'Admin',
          component: Admin,
          meta:{
            requireAuth:true
          }
        },
        {
          path: '/Book',
          name: 'Book',
          component: Book,
          meta:{
            requireAuth:true
          }
        },
        {
          path: '/Order',
          name: 'Order',
          component: Order,
          meta:{
            requireAuth:true
          }
        },
        {
          path: '/UserMsg',
          name: 'UserMsg',
          component: UserMsg,
          meta:{
            requireAuth:true
          }
        },
      ]
    }
  ]
});



// //设置不允许通过url直接访问后台
router.beforeEach((to,from,next) => {
  console.log("从"+ from.name + "来");
  console.log("到" + to.name + "去");
  if(to.name == "Admin" || to.name == "Home" || to.name == "Book" || to.name == "Order" || to.name == "UserMsg"){
    if(localStorage.getItem("account")){
      next();
    }
    else{
      alert('未拥有管理员权限！！！');
    }
  }else{
    next();
  }
});

export default router;
