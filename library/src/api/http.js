import axios from 'axios';
axios.defaults.timeout = 5000; //超时时间
axios.defaults.withCredentials = true; //允许跨域
//Content-type 响应头
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';
//基础url
 axios.defaults.baseURL = "http://localhost:8082";
 //axios.defaults.baseURL = "http://localhost:8080";

//响应拦截器
axios.interceptors.response.use(
  response => {
    //如果status为200就成功访问
    if(response.status == 200){
      return Promise.resolve(response);
    }else{
      return Promise.reject(response);
    }
  },
  error => {
    if(error.response.status){
      switch(error.response.status){
        case 401://未登录
          router.replace({
            path:'/',
            query:{
              redirect:router.currentRoue.fullPath
            }
          });
          break;
        case 404://没找到
          break;
      }
      return Promise.reject(error.response);
    }
  }
);

/**
 * get方法
 */
export function get(url,params={}){
  return new Promise((resolve,reject) => {
    axios.get(url,{params:params})
    .then(response => {
      resolve(response.data);
    })
    .catch(err => {
      reject(err);
    })
  }

  );
}

/**
 * post方法
 */
export function post(url,data={}){
  return new Promise((resolve,reject) => {
    axios.post(url,data)
    .then(response => {
      resolve(response.data);
    })
    .catch(err => {
      reject(err);
    })
  }

  );
}