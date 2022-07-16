interface SysLog {
    id: number

    operRequMethod: string

    operUserId: number
    operUserName: string
    operMethod: string
    operUri: string
    operIp: string
    operVer: string

    createdAt: string
    updatedAt: string
    isDelete: number
}

interface SysOperLog extends SysLog {
    operRequParam: string
    operModul: string
    operType: string
    operDesc: string
}

interface SysExceptionLog extends SysLog {
    excRequParam: string
    excName: string
    excMessage: string
}

interface SysOperLogoDetailsProps {
    isVisible: boolean
    closeModel: () => void
}

export type {
    SysOperLog, SysExceptionLog, SysOperLogoDetailsProps
}