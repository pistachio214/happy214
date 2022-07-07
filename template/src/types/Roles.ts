
export interface RolesListParams {
    current?: number
    username?: string
    size?: number
    status?: number
}

export interface RoleInfo {
    id: number
    name: string
    menuIds: any[]
    code: string
    createdAt: string
    remark: string
    status: number
    updatedAt: string
}