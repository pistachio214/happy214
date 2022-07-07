import React, {
    useEffect,
    useState
} from "react";
import {
    Link,
    useLocation
} from 'react-router-dom';

import { Menu } from 'antd';
import * as Icon from '@ant-design/icons';
import { ItemType } from "antd/lib/menu/hooks/useItems";

import { shallowEqual } from 'react-redux';
import { MenuNavItem } from "@/redux/types/Menu";
import { useAppSelector } from '@/redux/hook';

import matchPath from "@/utils/Match";
import { UserState } from "@/redux/types/User";

const LeftBar: React.FC = () => {

    const user: UserState = useAppSelector((state: any) => ({ ...state.user }), shallowEqual);
    const navs: MenuNavItem[] = user.nav;

    const [defaultOpenKeys, setDefaultOpenKeys] = useState<string[] | undefined>(undefined);
    const [defaultSelectedKeys, setDefaultSelectedKeys] = useState<string[]>(['']);

    let location = useLocation();

    useEffect(() => {
        heightMenu(navs)
        // eslint-disable-next-line
    }, [navs, location])

    const heightMenu = (leftRoutes: MenuNavItem[]) => {
        if (leftRoutes.length < 1) {
            return;
        }

        let path = location.pathname;
        leftRoutes.forEach((item: MenuNavItem, index: number) => {
            let match = matchPath(item.path, path);
            if (match !== null) {
                if (match.isExact) {
                    setDefaultSelectedKeys([`${item.id}`]);
                } else {
                    setDefaultOpenKeys([`${item.id}`]);
                }
            }

            if (item.children !== undefined && item.children.length > 0) {
                heightMenu(item.children);
            }
        })
    }

    const iconBC = (name?: string) => {
        if (name !== null && name !== undefined && name.length) {
            return React.createElement((Icon && Icon as any)[name]);
        }

        return <></>;
    }

    // 动态化处理菜单栏
    const generateMenu = (routerList: MenuNavItem[]) => {

        let routerArray: ItemType[] = []

        routerList.forEach((item: MenuNavItem) => {
            const { id, icon, title, children, path } = item
            if (children && children.length > 0) {

                routerArray.push({
                    key: id,
                    icon: iconBC(icon),
                    label: title,
                    children: generateMenu(children)
                })

            } else {
                routerArray.push({
                    key: id,
                    icon: iconBC(icon),
                    label: <Link to={path}>{title}</Link>,
                })
            }
        })

        return routerArray;
    }


    return (
        <>
            <Menu
                multiple={true}
                theme="dark"
                mode="inline"
                selectedKeys={defaultSelectedKeys}
                openKeys={defaultOpenKeys}
                onOpenChange={(openKeys: string[]) => {
                    setDefaultOpenKeys(openKeys)
                }}
                items={generateMenu(navs)}
            />
        </>
    );
}

export default React.memo(LeftBar);