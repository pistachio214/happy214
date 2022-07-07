import request from '@/http/request';
import { SysDictsItem } from '@/types/SysDictsItem';

const BASE_URL = "/sys-dict-item";

export const DictItemList = (id: number) => {
    return `${BASE_URL}/list/${id}`;
}

export const DictItemInfo = (id: number) => {
    return request({ url: `${BASE_URL}/${id}`, method: "GET" });
}

export const DictItemSave = (data: SysDictsItem) => {
    return request({ url: `${BASE_URL}/save`, method: "POST", data });
}

export const DictItemDelete = (id: number) => {
    return request({ url: `${BASE_URL}/${id}`, method: "DELETE" });
}

export const DictItemEdit = (data: SysDictsItem) => {
    return request({ url: `${BASE_URL}/edit`, method: "PUT", data });
}