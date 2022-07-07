import { message } from "antd";
import axios, {
    AxiosInstance,
    AxiosRequestConfig
} from "axios";

const BASE_URL: string = process.env.REACT_APP_BASE_URL!;

const instance: AxiosInstance = axios.create({
    baseURL: `/${BASE_URL}`,
    timeout: 5000
});

//请求拦截器
instance.interceptors.request.use((config: AxiosRequestConfig) => {
    config.headers!['Content-Type'] = 'application/json;charset=UTF-8';

    //获取到token,然后组装在请求控制里面
    if (sessionStorage.getItem('tokenValue') !== null) {
        const tokenName = sessionStorage.getItem('tokenName')!
        const tokenValue = sessionStorage.getItem('tokenValue')!
        
        config.headers![tokenName] = tokenValue
    }
    return config;
}, err => {
    return Promise.reject(err);
});

//响应拦截器
instance.interceptors.response.use((res: any) => {
    if (res.data.code !== 200) {
        switch (res.data.code) {
            case 1001:
                message.error(res.data.message, 2, () => {
                    window.location.href = "/login";
                });
                break;
            default:
                message.error(res.data.message);
        }

        return Promise.reject(res.data);
    }
    return res.data;
}, err => {
    return Promise.reject(err);
})


export default instance;