import React, { useState } from 'react';

import { useNavigate } from 'react-router-dom';
import { Outlet } from 'react-router-dom';

import {
    Layout,
    Menu,
    Image,
    Avatar,
    Dropdown,
    Button
} from 'antd';
import {
    LogoutOutlined,
    SettingFilled,
    UserOutlined,
} from '@ant-design/icons';
import { CollapseType } from 'antd/lib/layout/Sider';

import { shallowEqual } from 'react-redux';
import { useAppDispatch, useAppSelector } from '@/redux/hook';
import { UserState } from '@/redux/types/User';
import { clearUserState } from '@/redux/slice/user';

import { AppLayoutContainer } from '@/component/happy-layout/style';
import LeftBar from '@/component/happy-layout/LeftBar';
import TopBreadcrumb from '@/component/happy-layout/TopBreadcrumb';
import HappyLayoutProfileModal from '@/component/happy-layout/profile';

import { logout } from '@/api/auth';

import logo from '@/assets/images/logo.svg';
import defaultSettings from '@/defaultSettings';

const { Header, Content, Footer, Sider } = Layout;

const HappyLayout: React.FC = () => {

    const navigate = useNavigate();
    const dispath = useAppDispatch();

    const user: UserState = useAppSelector((state: any) => ({ ...state.user }), shallowEqual);

    const [collapsedStyle, setCollapsedStyle] = useState<string>();
    const [isProfileVisible, setIsProfileVisible] = useState<boolean>(false);

    const menu: JSX.Element = (
        <Menu items={[
            {
                key: 'profile',
                label: (
                    <Button
                        type='link'
                        icon={<UserOutlined />}
                        onClick={() => showProfileModal()}
                    >
                        个人中心
                    </Button>
                )
            },
            {
                key: 'setting',
                label: (
                    <Button
                        type='link'
                        icon={<SettingFilled />}
                        onClick={() => {
                            console.log('setting')
                        }}
                    >
                        系统设置
                    </Button>
                )
            },
            {
                type: 'divider',
              },
              {
                key: 'logout',
                disabled: true,
                label: (
                    <Button
                        type='link'
                        icon={<LogoutOutlined />}
                        onClick={() => handleLogout()}
                    >
                        退出系统
                    </Button>
                )
              }
        ]}
        />
    );

    const showProfileModal = () => {
        setIsProfileVisible(true);
    }

    const closeProfileModal = () => {
        setIsProfileVisible(false);
    }

    const handleLogout = () => {
        logout().then(res => {
            navigate('/login');
            sessionStorage.removeItem('token');
            dispath(clearUserState())
        })

    }

    const changeCollapse = (collapsed: boolean) => {
        if (collapsed) {
            setCollapsedStyle('none');
        } else {
            setCollapsedStyle('block')
        }
    }

    return (
        <AppLayoutContainer>
            <Layout className='layout-parent-container'>
                <Sider
                    collapsible={true}
                    breakpoint="lg"
                    collapsedWidth="70"
                    onBreakpoint={broken => {
                        // console.log(broken);
                    }}
                    onCollapse={(collapsed: boolean, type: CollapseType) => {
                        changeCollapse(collapsed);
                        // console.log(collapsed, type);
                    }}
                    className='layout-menu-container'
                >
                    <div className="logo">
                        <a href='1' className='logo-container'>
                            <Image src={logo} alt={'logo'} className='logo-img' preview={false} />

                            <div className='system-title' style={{ display: collapsedStyle }}>
                                <h1>{defaultSettings.logoSystemTitle}</h1>
                            </div>
                        </a>
                    </div>

                    {/* 左侧菜单栏 */}
                    <LeftBar />
                </Sider>
                <Layout>
                    <Header className="site-layout-sub-header-background" >
                        {/* 面包屑 */}
                        <TopBreadcrumb />

                        <div className='user-container'>
                            <Dropdown overlay={menu} placement='bottom'>
                                <div className='username-container'>
                                    <div className='username-nickname'>{user.data.nickname}</div>
                                    <Avatar className='username-avatar' src={user.data.avatar ?? logo} draggable={false} />
                                </div>
                            </Dropdown>
                        </div>

                    </Header>
                    <Content style={{ margin: '24px 16px 0' }}>
                        <div className="site-layout-background" style={{ padding: 24, minHeight: '100%' }}>
                            <Outlet />
                        </div>
                    </Content>

                    <Footer style={{ textAlign: 'center' }}>{defaultSettings.footerDescription}</Footer>
                </Layout>
            </Layout>

            <HappyLayoutProfileModal
                isVisible={isProfileVisible}
                closeModal={() => closeProfileModal()}
            />
        </AppLayoutContainer>
    );
}

export default React.memo(HappyLayout);