import request from '@/http/request';
import { MenuEdit } from '@/types/Menus';

const baseURL = '/sys-menu';

export const getNav = () => {
    return request({ url: `${baseURL}/nav`, method: "GET" });
}

export const getMenuList = () => {
    return request({ url: `${baseURL}/list`, method: "GET" });
}

export const editMenu = (data: MenuEdit) => {
    return request({ url: `${baseURL}/update`, method: "PUT", data: data });
}

export const saveMenu = (data: MenuEdit) => {
    return request({ url: `${baseURL}/save`, method: "POST", data: data });
}

export const delMenu = (id: number) => {
    return request({ url: `${baseURL}/delete/${id}`, method: "DELETE" });
}