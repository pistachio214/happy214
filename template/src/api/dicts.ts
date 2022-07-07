import request from '@/http/request';
import { SysDict } from '@/types/SysDicts';

const BASE_URL = "/sys-dict";

export const getDictList = (): string => {
    return `${BASE_URL}/list`;
}

export const SaveDict = (data: SysDict) => {
    return request({ url: `${BASE_URL}/save`, method: "POST", data })
}

export const findDict = (id: number) => {
    return request({ url: `${BASE_URL}/${id}`, method: "GET" })
}

export const editDict = (data: SysDict) => {
    return request({ url: `${BASE_URL}/edit`, method: "PUT", data })
}

export const delDict = (id: number) => {
    return request({ url: `${BASE_URL}/${id}`, method: "DELETE" })
}

export const getDictByKey = (key: string) => {
    return request({ url: `${BASE_URL}/findByKey/${key}`, method: "GET" })
}