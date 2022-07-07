import React, { useState } from "react";

import { Badge, Button, message, Table } from 'antd';
import { useEffect } from "react";


import { ColumnsType } from "antd/lib/table";
import { DeleteOutlined, EditOutlined, PlusOutlined, ReloadOutlined } from "@ant-design/icons";

import { MenuInfo } from "@/types/Menus";
import { MenuContainer } from "@/pages/system/menu/style";
import { IOperator } from "@/types/IOperator";
import HappyOperator from "@/component/happy-operator";
import EditMenuModal from "@/component/happy-system-menu/edit";
import { getMenuList, delMenu } from "@/api/menu";
import HappyAuthWrapper from "@/component/happy-auth-hoc/HappyAuthWrapper";

const Menu: React.FC = () => {

    const [data, setData] = useState<MenuInfo[]>();
    const [isRef, setIsRef] = useState<boolean>(false);
    const [isLoading, setIsLoading] = useState<boolean>(false);
    const [isEditModalVisible, setIsEditModalVisible] = useState<boolean>(false);
    const [editMenu, setEditMenu] = useState<MenuInfo>();
    const [isEdit, setIsEdit] = useState<boolean>(false);

    const columns: ColumnsType<MenuInfo> = [
        {
            title: '名称',
            dataIndex: 'name',
            align: 'left'
        },
        {
            title: '权限编码',
            dataIndex: 'perms',
            align: 'center',
        },
        {
            title: '图标',
            dataIndex: 'icon',
            align: 'center',
        },
        {
            title: '类型',
            dataIndex: 'type',
            width: '80px',
            align: 'center',
            render: (type: number, record: MenuInfo) => {
                switch (type) {
                    case 0:
                        return <Button type="primary" ghost={true} size="small">目录</Button>
                    case 1:
                        return <Button type="default" ghost={true} className="menu-type-button-success" size="small">菜单</Button>
                    case 2:
                        return <Button type="default" ghost={true} className="menu-type-button-info" size="small">按钮</Button>
                    default:
                        return <Button type="default" ghost={true} danger={true} size="small">未知</Button>
                }
            }
        },
        {
            title: '菜单URL',
            dataIndex: 'path',
            align: 'center',
        },
        // {
        //     title: '菜单组件',
        //     dataIndex: 'component',
        //     align: 'center',
        // },
        {
            title: '序号',
            dataIndex: 'orderNum',
            width: '80px',
            align: 'center',
        },
        {
            title: '状态',
            dataIndex: 'status',
            width: '80px',
            align: 'center',
            render: (status: number, record: MenuInfo) => {
                let dom: JSX.Element = <></>;

                switch (status) {
                    case 1:
                        dom = <Badge status="success" text='启用' />
                        break;
                    case 0:
                        dom = <Badge status='default' text='禁用' />
                        break;
                    default:
                        dom = <Badge status='error' text='未知' />
                }
                return dom;
            }
        },
        {
            title: '操作',
            key: 'action',
            render: (text, record: MenuInfo) => {
                const item: IOperator[] = [
                    {
                        title: '编辑',
                        icon: <EditOutlined />,
                        permission: ['sys:menu:update'],
                        onClick: () => {
                            showEditModal(record);
                        }
                    },
                    {
                        title: '删除',
                        danger: true,
                        message: `是否删除 [ ${record.name} ] ?`,
                        icon: <DeleteOutlined />,
                        permission: ['sys:menu:delete'],
                        onClick: () => {
                            handleDeleteMenu(record.id);
                        }
                    }
                ];
                return <HappyOperator items={item} />
            },
        },
    ];

    useEffect(() => {
        initUseEffect();
    }, [isRef]);  // eslint-disable-line react-hooks/exhaustive-deps

    const initUseEffect = () => {
        setIsLoading(true);
        getMenuList().then(res => {
            setData(generatorMenuTree(res.data));
            setIsLoading(false);
        })
    }

    const generatorMenuTree = (data: MenuInfo[]) => {
        data.forEach((item: MenuInfo, index: number) => {
            if (item.children && item.children.length > 0) {
                generatorMenuTree(item.children)
            } else {
                item.children = undefined;
            }
        })

        return data;
    }

    const handleDeleteMenu = (id: number) => {
        delMenu(id).then(res => {
            message.success(`删除成功!`, 1, () => {
                setIsRef(!isRef);
            })
        })
    }

    const showEditModalAdd = () => {
        setIsEdit(false);
        setIsEditModalVisible(true);
    }

    const showEditModal = (menuInfo: MenuInfo) => {
        setIsEdit(true);
        setEditMenu(menuInfo);
        setIsEditModalVisible(true);
    }

    const closeEditModal = () => {
        setIsRef(!isRef);
        setIsEditModalVisible(false);
    }

    const refRelease = () => {
        setIsRef(!isRef);
    }

    return (
        <MenuContainer>
            <div className="action-container">
                <div className="common-btn-container">
                    <HappyAuthWrapper hasPermiss={['sys:menu:list']}>
                        <Button
                            type="primary"
                            icon={<ReloadOutlined />}
                            onClick={() => refRelease()}
                        >
                            刷新
                        </Button>
                    </HappyAuthWrapper>

                    <HappyAuthWrapper hasPermiss={['sys:menu:save']}>
                        <Button
                            icon={<PlusOutlined />}
                            onClick={() => showEditModalAdd()}
                        >
                            新增
                        </Button>
                    </HappyAuthWrapper>

                </div>
            </div>

            <Table
                rowKey={(record: MenuInfo) => record.id}
                columns={columns}
                dataSource={data}
                scroll={{ y: 600 }}
                loading={isLoading}
            />              

            <EditMenuModal
                isVisible={isEditModalVisible}
                closeModal={() => closeEditModal()}
                menuInfo={editMenu}
                menuList={data}
                edit={isEdit}
            />
        </MenuContainer>
    )
}

export default Menu;