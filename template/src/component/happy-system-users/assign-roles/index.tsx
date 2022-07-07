import React, { useEffect, useState } from "react";

import { Modal, Form, Select } from 'antd';
import { AssignRolesDto } from "@/types/Users";
import { getRoleAll } from "@/api/roles";
import { permRole } from "@/api/users";
import { RoleInfo } from "@/types/Roles";

const { Option } = Select

interface IProps {
    isVisible: boolean
    closeModal: () => void
    assignRoles?: AssignRolesDto
}

const AssignRolesModal: React.FC<IProps> = (props: IProps) => {

    const [form] = Form.useForm();
    const [keys, setKeys] = useState<string[]>([]);
    const [updateState, setUpdateState] = useState<number>(0);
    const [children, setChildren] = useState<React.ReactNode[]>([]);

    useEffect(() => {
        initChildren();
    }, [])// eslint-disable-line react-hooks/exhaustive-deps

    useEffect(() => {
        initAssignRoles()
    }, [props.isVisible]);  // eslint-disable-line react-hooks/exhaustive-deps

    useEffect(() => {
        console.log(keys)
    }, [updateState]); // eslint-disable-line react-hooks/exhaustive-deps

    const initAssignRoles = () => {
        const { assignRoles, isVisible } = props;
        let key: string[] = [];
        if (isVisible && assignRoles?.id !== 0) {
            assignRoles?.sysRoles.forEach((item: RoleInfo) => {
                key.push(item.id.toString());
            })
        }

        console.log('key=', key)
        setKeys(key)
        setUpdateState(1)
        console.log('keys=', keys)
        console.log('assignRoles=', assignRoles)
    }

    const initChildren = async () => {
        await getRoleAll({ size: 9999, status: 1 }).then(res => {
            let childrenOption: React.ReactNode[] = [];
            res.data.records.forEach((item: RoleInfo, index: number) => {
                childrenOption.push(<Option key={item.id.toString()}>{item.name}</Option>)
            })

            setChildren(childrenOption);
        })
    }

    const handleOk = () => {
        permRole(props.assignRoles?.id!, keys).then(res => {
            props.closeModal();
        });
    }

    const handleCancel = () => {
        props.closeModal();
    }

    const handleChange = (value: string[]) => {
        console.log(`selected ${value}`);
    }

    return (
        <>
            <Modal title={"分配角色"}
                visible={props.isVisible}
                onCancel={() => handleCancel()}
                onOk={() => handleOk()}
                getContainer={false}
            >
                <Form
                    name="edit-role"
                    form={form}
                    labelCol={{ span: 4 }}
                    wrapperCol={{ span: 20 }}
                    labelAlign="left"
                    autoComplete="off"
                >

                    <Form.Item>
                        <Select
                            mode="multiple"
                            allowClear
                            style={{ width: '100%' }}
                            placeholder="Please select"
                            defaultValue={keys}
                            onChange={handleChange}
                        >
                            {children}
                        </Select>
                    </Form.Item>
                </Form>
            </Modal>
        </>
    );
}

AssignRolesModal.defaultProps = {
    isVisible: false,
    assignRoles: { id: 0, sysRoles: [] }
}

export default AssignRolesModal;
