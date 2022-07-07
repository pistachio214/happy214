import request from '@/http/request';

import { RoleInfo, RolesListParams } from '@/types/Roles';

const BASE_URL = "/sys-role";

export const getRoleList = (): string => {
    return `${BASE_URL}/list`;
}

export const getRoleAll = (params?: RolesListParams) => {
    return request({ url: getRoleList(), method: "GET", params: params });
}

export const findRoleById = (id: number) => {
    return request({ url: `${BASE_URL}/info/${id}`, method: "GET" });
}

export const editRole = (body: RoleInfo) => {
    return request({ url: `${BASE_URL}/update`, method: "PUT", data: body });
}

export const createRole = (body: RoleInfo) => {
    return request({ url: `${BASE_URL}/save`, method: "POST", data: body });
}

export const deleteRole = (id: number) => {
    return request({ url: `${BASE_URL}/delete/${id}`, method: "DELETE" });
}

export const permRole = (roleId: number, menuIds: number[]) => {
    return request({ url: `${BASE_URL}/perm/${roleId}`, method: "POST", data: menuIds });
}