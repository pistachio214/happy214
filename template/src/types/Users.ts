import { RoleInfo } from "@/types/Roles";

export interface UsersListParams {
    current?: number
    username?: string
}

export interface UsersInfo {
    id: number
    avatar: string
    nickname: string
    username: string
    sysRoles: RoleInfo[]
    email: string
    status: number
    createdAt: string
}

export interface AssignRolesDto {
    id: number
    sysRoles: RoleInfo[]
}

export interface UserCreateRequest {
    username: string
    email: string
    status: number
} 