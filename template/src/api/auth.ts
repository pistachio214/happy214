import request from '@/http/request';
import { LoginParams } from '@/types/IAuth';
// import { stringify } from 'qs';

const BASE_URL = "/sys-auth";

export const getCaptcha = () => {
    return request({ url: `${BASE_URL}/captcha`, method: "GET" });
}

export const login = (data: LoginParams) => {
    return request({ url: `${BASE_URL}/admin/doLogin`, method: "POST", data: data });
}

export const logout = () => {
    return request({ url: `${BASE_URL}/admin/logout`, method: "GET" });
}