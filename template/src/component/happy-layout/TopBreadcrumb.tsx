import React, { Fragment } from "react";

import { Breadcrumb } from 'antd';
import { IRouter } from "@/types/IRouter";
import { useLocation } from "react-router-dom";

import { useAppSelector } from '@/redux/hook'

import matchPath from "@/utils/Match";
import { shallowEqual } from "react-redux";
import { MenuState } from "@/redux/types/Menu";

const TopBreadcrumb: React.FC = () => {
    let location = useLocation();
    const menuStateMap: MenuState = useAppSelector((state: any) => ({...state.user}),shallowEqual);

    const menuNavs = menuStateMap.nav;

    const generate = (routerList: IRouter[]) => {
        let path = location.pathname;
        return (
            <>
                {
                    routerList.map((r: IRouter, index: number) => {
                        let match = matchPath(r.path, path);
                        if (match !== null) {
                            return (
                                <Fragment key={r.path}>
                                    <Breadcrumb.Item key={`bread-crumb-item-${index}`}>{r.title}</Breadcrumb.Item>
                                    {
                                        r.children ?
                                            generate(r.children)
                                            :
                                            null
                                    }
                                </Fragment>
                            )
                        }
                        return null
                    })
                }
            </>
        )
    }

    return (
        <>
            <Breadcrumb>
                {generate(menuNavs)}
            </Breadcrumb>
        </>
    );
}

export default TopBreadcrumb;