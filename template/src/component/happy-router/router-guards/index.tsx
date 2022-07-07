import React from "react";

import { Navigate, Outlet, useLocation } from 'react-router-dom';
import router from "@/router";
import { IRouter } from '@/types/IRouter'

const RouterGuards: React.FC = () => {

    let { pathname } = useLocation();
    const token = sessionStorage.getItem('tokenValue');

    let authRouter: IRouter = { title: '', path: '' };

    router.forEach((item: IRouter, index: number) => {
        if (item.children === undefined) {
            if (pathname === item.path) {
                authRouter = { ...item };
            }
        }
        else {
            item.children.forEach((childrenItem: IRouter, childrenIndex: number) => {
                if (pathname === childrenItem.path) {
                    authRouter = { ...childrenItem };
                }
            })
        }
    })

    if (authRouter.path === '') {
        return <Navigate to={'/404'} />
    } else {
        if (authRouter.auth && !token) {
            return <Navigate to={'/login'} />
        }

        return <Outlet />;
    }

}

export default React.memo(RouterGuards);