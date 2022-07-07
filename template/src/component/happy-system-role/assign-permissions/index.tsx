import React, { useEffect, useState } from "react";

import { Modal, Form, Tree, message } from 'antd';
import { Key } from "rc-table/lib/interface";
import { getMenuList } from "@/api/menu";
import { findRoleById, permRole } from "@/api/roles";
import { MenuInfo, MenuTreeNodesType } from "@/types/Menus";

interface IProps {
    id: number | undefined
    isVisible: boolean
    closeModal: () => void
}

const AssignPermissionsModal: React.FC<IProps> = (props: IProps) => {

    const [form] = Form.useForm();
    const [nodes, setNodes] = useState<MenuTreeNodesType[] | undefined>();
    const [checkedKeys, setCheckedKeys] = useState<number[]>([]);

    useEffect(() => {
        const initSelected = () => {
            const { id, isVisible } = props;
            if (isVisible && id !== undefined && id) {
                findRoleById(id).then(res => {
                    let checkedKey: number[] = [];
                    res.data.menuIds.forEach((item: number) => {
                        checkedKey.push(item);
                    })
                    setCheckedKeys(checkedKey);
                })
            } else {
                setCheckedKeys([]);
            }
        }
        initSelected();
    }, [props.isVisible]);  // eslint-disable-line react-hooks/exhaustive-deps

    useEffect(() => {
        initMenuList();
    }, []);  // eslint-disable-line react-hooks/exhaustive-deps

    const initMenuList = async () => {
        await getMenuList().then(res => {
            let menuList: MenuInfo[] = res.data;
            let treeData: MenuTreeNodesType[] = generatorMenuTreeData(menuList);
            setNodes(treeData);
        })
    }

    const generatorMenuTreeData = (data: MenuInfo[]) => {
        let node: MenuTreeNodesType[] = [];
        data.forEach((item: MenuInfo) => {
            if (item.children && item.children.length) {
                node.push({
                    title: item.name,
                    key: item.id,
                    children: generatorMenuTreeData(item.children)
                })
            } else {
                node.push({
                    title: item.name,
                    key: item.id,
                })
            }
        })
        return node;
    }

    const handleOk = () => {
        permRole(props.id!, checkedKeys).then((res: any) => {
            message.success(res.message)
            handleCancel()
        });
    }

    const handleCancel = () => {
        props.closeModal();
    }

    const onCheck = (checkedKeys: Key[] | { checked: Key[]; halfChecked: Key[]; }, info: any) => {
        let keys: number[] = [];
        info.checkedNodes.forEach((item: { key: string, title: string }) => {
            keys.push(Number(item.key));
        })
        setCheckedKeys(keys);
    };

    return (
        <>
            <Modal title={"分配权限"}
                visible={props.isVisible}
                onCancel={() => handleCancel()}
                onOk={() => handleOk()}
                getContainer={false}
                width={800}
            >
                <Form
                    name="edit-role"
                    form={form}
                    labelCol={{ span: 8 }}
                    wrapperCol={{ span: 16 }}
                    labelAlign="left"
                    autoComplete="off"
                >
                    <Form.Item
                        label='权限选择'
                        name="menuIds"
                        rules={[
                            { required: true, message: '权限不能为空!' }
                        ]}
                    >
                        <Tree
                            checkable
                            showLine={true}
                            selectable={false}
                            checkedKeys={checkedKeys}
                            onCheck={onCheck}
                            treeData={nodes}
                            checkStrictly={false}
                            defaultExpandAll={true}
                        />
                    </Form.Item>
                </Form>
            </Modal>
        </>
    );
}

AssignPermissionsModal.defaultProps = {
    id: 0,
    isVisible: false,
}

export default AssignPermissionsModal;
