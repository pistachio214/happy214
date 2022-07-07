import React, { useState } from "react";

import { Badge, Button, Form, Input } from "antd";
import { ColumnsType } from "antd/lib/table";
import { DeleteOutlined, EditOutlined, PartitionOutlined } from "@ant-design/icons";

import HappyTable from "@/component/happy-table";
import HappyOperator from "@/component/happy-operator";
import { IOperator } from "@/types/IOperator";
import EditRole from "@/component/happy-system-role/edit";
import { RoleInfo } from "@/types/Roles";
import { getRoleList, deleteRole } from "@/api/roles";
import AssignPermissionsModal from "@/component/happy-system-role/assign-permissions";
import { QuestionType } from "@/types/Common";

interface RoleQuestionType extends QuestionType {
    name?: string
}

const Role: React.FC = () => {

    const [params, setParams] = useState<RoleQuestionType>();
    const [editVisible, setEditVisible] = useState<boolean>(false);
    const [assignPermissionsVisible, setAssignPermissionsVisible] = useState<boolean>(false);
    const [id, setId] = useState<number>();
    const [isEdit, setIsEdit] = useState<boolean>(false);
    const [isRefresh, setIsRefresh] = useState<boolean>(false);

    const columns: ColumnsType<RoleInfo> = [
        {
            title: 'ID',
            dataIndex: 'id',
            align: 'center'
        },
        {
            title: '角色名称',
            dataIndex: 'name',
            align: 'center',
        },
        {
            title: '唯一编码',
            dataIndex: 'code',
            align: 'center'
        },
        {
            title: '状态',
            dataIndex: 'status',
            align: 'center',
            render: (status: number, record: RoleInfo) => {
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
            render: (text, record: RoleInfo) => {
                const item: IOperator[] = [
                    {
                        title: '分配权限',
                        icon: <PartitionOutlined />,
                        permission: ['sys:role:perm'],
                        onClick: () => {
                            showAssignPermissionsModal(record.id);
                        }
                    },
                    {
                        title: '编辑',
                        icon: <EditOutlined />,
                        permission: ['sys:role:update'],
                        onClick: () => {
                            showEditModal(record.id);
                        }
                    },
                ];

                if (record.id !== 1) {
                    item.push({
                        title: '删除',
                        danger: true,
                        message: `是否删除角色 [ ${record.name} ] ?`,
                        icon: <DeleteOutlined />,
                        permission: ['sys:role:delete'],
                        onClick: () => {
                            handleDeleteRole(record.id);
                        }
                    })
                }

                return <HappyOperator items={item} />

            },
        },
    ];

    const handleSearch = (values: { name: string, code: string }) => {
        setParams(values);
    }

    const handleDeleteRole = (id: number) => {
        deleteRole(id).then(res => {
            setIsRefresh(!isRefresh);
        })
    }

    const showEditModal = (id: number) => {
        setId(id);
        setIsEdit(true);
        setEditVisible(true);
    }

    const closeEditModal = () => {
        setIsRefresh(!isRefresh);
        setEditVisible(false);
    }

    const showAssignPermissionsModal = (id: number) => {
        setId(id);
        setAssignPermissionsVisible(true);
    }

    const closeAssignPermissionsModal = () => {
        setIsRefresh(!isRefresh);
        setAssignPermissionsVisible(false);
    }

    const searchDom = (
        <Form layout="inline" name="search-users" onFinish={(values) => handleSearch(values)}>
            <Form.Item name={'name'}>
                <Input placeholder="角色名称" />
            </Form.Item>

            <Form.Item name={'code'}>
                <Input placeholder="唯一编码" />
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
                rowKey={(record: RoleInfo) => record.id}
                columns={columns}
                search={searchDom}
                url={getRoleList()}
                params={params}
                isVisible={isRefresh}
                quickJump={(page: number) => {
                    setParams({ ...params, ...{ current: page } })
                }}
                reload={{
                    hasPremiss: ['sys:role:list'],
                }}
                plus={{
                    hasPremiss: ['sys:role:save'],
                    click: () => {
                        setIsEdit(false);
                        setEditVisible(true);
                    }
                }}
            />

            <EditRole
                isVisible={editVisible}
                closeModal={() => closeEditModal()}
                id={id}
                edit={isEdit}
            />

            <AssignPermissionsModal
                id={id}
                isVisible={assignPermissionsVisible}
                closeModal={() => closeAssignPermissionsModal()}
            />

        </>
    )
}

export default Role;