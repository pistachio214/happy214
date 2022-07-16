import request from '@/http/request';

export const getOperLogList = () => {
    return '/sys-oper-log/list';
}

export const findOperLog = (id: number) => {
    return request({ url: `/sys-oper-log/${id}`, method: 'GET' })
}