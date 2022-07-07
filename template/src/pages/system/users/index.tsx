import React, { useState } from "react";

import {
    Avatar,
    Image,
    Badge,
    Tag,
    message,
    Button,
    Form,
    Input
} from 'antd';
import { ColumnsType } from "antd/lib/table";
import {
    PartitionOutlined,
    RedoOutlined,
    DeleteOutlined
} from '@ant-design/icons';

import HappyTable from "@/component/happy-table";
import HappyOperator from "@/component/happy-operator";
import { IOperator } from "@/types/IOperator";
import { AssignRolesDto, UsersInfo } from "@/types/Users";
import { RoleInfo } from "@/types/Roles";
import { UserRoleContainer } from '@/pages/system/users/style';
import { getUserList, restPassword, delUser } from "@/api/users";
import AssignRolesModal from "@/component/happy-system-users/assign-roles";
import CreateUserModal from "@/component/happy-system-users/create";

import defaultSettings from "@/defaultSettings";
import { QuestionType } from "@/types/Common";

interface UsersQuestionType extends QuestionType {
    username?: string
}

const Users: React.FC = () => {

    const [form] = Form.useForm();

    const [params, setParams] = useState<UsersQuestionType>();
    const [isAssignRolesVisible, setIsAssignRolesVisible] = useState<boolean>(false);
    const [isCreateUserVisible, setIsCreateUserVisible] = useState<boolean>(false);
    const [assignRoles, setAssignRoles] = useState<AssignRolesDto>();

    const [isRefresh, setIsRefresh] = useState<boolean>(false);

    const columns: ColumnsType<UsersInfo> = [
        {
            title: 'ID',
            dataIndex: 'id',
            align: 'center'
        },
        {
            title: '头像',
            dataIndex: 'avatar',
            align: 'center',
            render: (avatar: string, record: UsersInfo) => {
                return <Avatar src={
                    <Image
                        src={avatar}
                        style={{ width: 32 }}
                        onError={
                            (event: React.SyntheticEvent<HTMLImageElement, Event>) => {
                                event.currentTarget.src = defaultSettings.userAvatar;
                                console.log(event)
                            }}
                    />}
                />
            }
        },
        {
            title: '昵称',
            dataIndex: 'nickname',
            align: 'center'
        },
        {
            title: '用户名',
            dataIndex: 'username',
            align: 'center'
        },
        {
            title: '角色名称',
            dataIndex: 'sysRoles',
            align: 'center',
            render: (sysRoles: RoleInfo[], record: UsersInfo) => {
                return (
                    <UserRoleContainer>
                        {
                            sysRoles.map((item: RoleInfo, index: number) => {
                                return <Tag key={`role-info-key-${index}`} color="#2db7f5">{item.name}</Tag>
                            })
                        }
                    </UserRoleContainer>
                )
            }
        },
        {
            title: '邮箱',
            dataIndex: 'email',
            align: 'center'
        },
        {
            title: '状态',
            dataIndex: 'status',
            align: 'center',
            render: (status: number, record: UsersInfo) => {
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
            title: '创建时间',
            dataIndex: 'createdAt',
            align: 'center'
        },
        {
            title: '操作',
            key: 'action',
            render: (text, record: UsersInfo) => {
                const item: IOperator[] = [
                    {
                        title: '分配角色',
                        icon: <PartitionOutlined />,
                        permission: ['sys:user:role'],
                        onClick: () => {
                            showAssignRolesModal(record.id, record.sysRoles);
                        }
                    },
                    {
                        title: '重置密码',
                        icon: <RedoOutlined />,
                        permission: ['sys:user:repass'],
                        onClick: () => {
                            handleResetPassword(record.id);
                        }
                    }
                ];

                if (record.id !== 1) {
                    item.push({
                        title: '删除',
                        danger: true,
                        icon: <DeleteOutlined />,
                        permission: ['sys:user:delete'],
                        message: `是否删除该用户 [ ${record.username} ] ?`,
                        onClick: () => {
                            handleDeleteUser(record.id);
                        }
                    })
                }

                return (
                    <HappyOperator items={item} />
                )
            },
        },
    ];

    const showAssignRolesModal = (id: number, sysRoles: RoleInfo[]) => {
        setAssignRoles({ id: id, sysRoles: sysRoles })
        setIsAssignRolesVisible(true);
    }

    const closeAssignRoleModal = () => {
        setIsRefresh(!isRefresh);
        setIsAssignRolesVisible(false);
    }

    const showCreateUserModal = () => {
        setIsCreateUserVisible(true);
    }

    const closeCreateUserModal = () => {
        setIsRefresh(!isRefresh);
        setIsCreateUserVisible(false);
    }

    const handleResetPassword = (id: number) => {
        restPassword(id).then(res => {
            message.success(`密码初始化成功! 初始化密码为: ${res.data}`)
        })
    }

    const handleDeleteUser = (id: number) => {
        delUser(id).then(res => {
            message.success(`删除管理员成功!`)
            setIsRefresh(!isRefresh);
        })
    }

    const handleSearch = (values: { username: string }) => {
        setParams(values);
    }

    const searchDom = (
        <Form form={form} layout="inline" name="search-users" onFinish={(values) => handleSearch(values)}>
            <Form.Item name={'username'}>
                <Input placeholder="用户名" />
            </Form.Item>

            <Form.Item>
                <Button type="primary" htmlType="submit">
                    查询
                </Button>
            </Form.Item>
        </Form>
    );

    return (
        <>
            <HappyTable
                rowKey={(record: UsersInfo) => record.id}
                columns={columns}
                search={searchDom}
                url={getUserList()}
                isVisible={isRefresh}
                params={params}
                plus={{
                    hasPremiss: ['sys:user:save'],
                    click: () => showCreateUserModal()
                }}
                reload={{
                    hasPremiss: ['sys:user:list']
                }}
                quickJump={(page: number) => {
                    setParams({ ...params, ...{ current: page } })
                }}
            />

            <AssignRolesModal
                isVisible={isAssignRolesVisible}
                assignRoles={assignRoles}
                closeModal={() => closeAssignRoleModal()}
            />

            <CreateUserModal
                isVisible={isCreateUserVisible}
                closeModal={() => closeCreateUserModal()}
            />
        </>
    )
}

export default Users;