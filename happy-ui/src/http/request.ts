import axios, {
    AxiosInstance,
    AxiosRequestConfig
} from "axios";


const BASE_URL: string = process.env.REACT_APP_BASE_URL!;

const instance: AxiosInstance = axios.create({
    baseURL: `/${BASE_URL}`,
    timeout: 5000
});

axios.defaults.headers.common['Content-Type'] = 'application/json;charset=UTF-8';

//请求拦截器
instance.interceptors.request.use((config: AxiosRequestConfig) => {
    //获取到token,然后组装在请求控制里面
    if (!config.headers) {
        config.headers = {};
    }

    if (sessionStorage.getItem('token') !== null) {
        config.headers['Authorization'] = sessionStorage.getItem('token')!
    }

    return config;
}, err => { });

//响应拦截器
instance.interceptors.response.use((res: any) => {

}, err => {
    return Promise.reject(err);
})


export default instance;