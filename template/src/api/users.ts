import request from '@/http/request';
import { UserCreateRequest } from '@/types/Users';

const BASE_URL = "/sys-user";

export const getUserList = (): string => {
    return `${BASE_URL}/list`;
}

export const permRole = (userId: number, roleIds: string[]) => {
    return request({ url: `${BASE_URL}/role/${userId}`, method: "POST", data: roleIds });
}

export const restPassword = (id: number) => {
    return request({ url: `${BASE_URL}/repass`, method: "POST", data: id });
}

export const createUser = (data: UserCreateRequest) => {
    return request({ url: `${BASE_URL}/save`, method: "POST", data: data });
}

export const delUser = (id: number) => {
    return request({ url: `${BASE_URL}/delete/${id}`, method: "DELETE" });
}

export const saveUserAvatar = (avatar: string) => {
    return request({ url: `${BASE_URL}/saveAvatar`, method: "POST", data: { avatar: avatar } });
}