import React, { useEffect, useState } from "react";

import { Modal, Form, Select } from 'antd';
import { AssignRolesDto } from "@/types/Users";
import { getRoleAll } from "@/api/roles";
import { permRole } from "@/api/users";
import { RoleInfo } from "@/types/Roles";
import { OptionsInterface } from "@/types/Common";

interface IProps {
    isVisible: boolean
    closeModal: () => void
    assignRoles?: AssignRolesDto
}

const AssignRolesModal: React.FC<IProps> = (props: IProps) => {

    const [form] = Form.useForm();
    const [keys, setKeys] = useState<string[]>([]);
    const [selectOption, setSelectOption] = useState<OptionsInterface[]>([]);

    useEffect(() => {
        initChildren();
    }, [])// eslint-disable-line react-hooks/exhaustive-deps

    useEffect(() => {
        initAssignRoles()
    }, [props.isVisible]);  // eslint-disable-line react-hooks/exhaustive-deps

    const initAssignRoles = () => {
        const { assignRoles, isVisible } = props;
        let key: string[] = [];
        if (isVisible && assignRoles?.id !== 0) {
            assignRoles?.sysRoles.forEach((item: RoleInfo) => {
                key.push(item.id.toString());
            })

            setKeys(key)
        }
    }

    const initChildren = async () => {
        await getRoleAll({ size: 9999, status: 1 }).then(res => {
            let option: OptionsInterface[] = [];
            res.data.records.forEach((item: RoleInfo, index: number) => {
                option.push({ label: item.name, value: item.id.toString() })
            })

            setSelectOption(option);
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
        console.log(value)
        value.forEach((item: string) => {
            console.log(`selected ${item}`);
        })

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

                    <Form.Item label={'用户角色'}>
                        <Select
                            mode="multiple"
                            allowClear
                            placeholder="Please select"
                            value={keys}
                            onChange={handleChange}
                            options={selectOption}
                        />
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
