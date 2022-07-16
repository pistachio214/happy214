import { lazy } from "react";
import { IRouter } from "@/types/IRouter";

import Dashboard from "@/pages/dashboard";
import Dicts from "@/pages/utils/dicts";
import Role from "@/pages/system/role";
import Menu from "@/pages/system/menu";
import Users from "@/pages/system/users";

import OperLog from '@/pages/system/operLog';
import ExceptionLog from '@/pages/system/exceptionLog';

const Login = lazy(() => import('@/pages/login'));
const NotFound = lazy(() => import('@/pages/notfound'));

const router: IRouter[] = [
    {
        title: '登录',
        path: '/login',
        auth: false,
        component: <Login />
    },
    {
        title: '仪表盘',
        path: '/dashboard',
        auth: true,
        children: [
            {
                path: '/dashboard',
                title: '仪表盘',
                auth: true,
                component: <Dashboard />
            }
        ]
    },
    {
        title: '系统工具',
        path: '/utils',
        auth: true,
        children: [
            {
                path: '/utils/dicts',
                title: '数据字典',
                auth: true,
                component: <Dicts />
            }
        ]
    },
    {
        title: '系统设置',
        path: '/system',
        auth: true,
        children: [
            {
                path: '/system/roles',
                title: '角色管理',
                auth: true,
                component: <Role />
            },
            {
                path: '/system/menus',
                title: '菜单管理',
                auth: true,
                component: <Menu />
            },
            {
                path: '/system/users',
                title: '人员管理',
                auth: true,
                component: <Users />
            },
            {
                path: '/system/oper',
                title: '操作日志',
                auth: true,
                component: <OperLog />,
            },
            {
                path: '/system/exception',
                title: '异常日志',
                auth: true,
                component: <ExceptionLog />,
            }
        ]
    },
    {
        title: '404',
        path: '*',
        auth: false,
        component: <NotFound />
    },
    {
        title: '404',
        path: '/404',
        auth: false,
        component: <NotFound />
    },
];

export default router;