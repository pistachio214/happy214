import React, { useEffect } from "react";

import { Modal, Form, Input, Radio } from 'antd';

import { findRoleById, editRole, createRole } from "@/api/roles";

interface IProps {
    isVisible: boolean
    closeModal: () => void
    id?: number
    edit?: boolean
}

const EditRole: React.FC<IProps> = (props: IProps) => {

    const [form] = Form.useForm();

    const handleOk = () => {
        const { edit } = props;
        let responseRes;
        if (edit) {
            responseRes = editRole(form.getFieldsValue())
        } else {
            responseRes = createRole(form.getFieldsValue())
        }

        responseRes.then((res) => {
            handleCancel()
        })
    }

    const handleCancel = () => {
        props.closeModal();
    }

    useEffect(() => {
        const { id, edit, isVisible } = props;
        if (edit && isVisible && id !== 0) {
            //编辑
            findRoleById(id!).then((res) => {
                form.setFieldsValue(res.data);
            })
        } else {
            if (isVisible) {
                form.resetFields();
            }
        }
    }, [props.id, props.isVisible]);  // eslint-disable-line react-hooks/exhaustive-deps

    return (
        <>
            <Modal title={props.edit ? "编辑角色" : "新增角色"}
                visible={props.isVisible}
                onCancel={() => handleCancel()}
                onOk={() => form.submit()}
                getContainer={false}
            >

                <Form
                    name="edit-role"
                    form={form}
                    labelCol={{ span: 4 }}
                    wrapperCol={{ span: 20 }}
                    onFinish={() => handleOk()}
                    labelAlign="left"
                    autoComplete="off"
                >

                    <Form.Item
                        label="ID"
                        name="id"
                        hidden={true}
                    >
                        <Input />
                    </Form.Item>

                    <Form.Item
                        label="角色名称"
                        name="name"
                        rules={[
                            { required: true, message: '请输入角色名称!' }
                        ]}
                    >
                        <Input placeholder="角色名称" />
                    </Form.Item>

                    <Form.Item
                        label="唯一编码"
                        name="code"
                        rules={[
                            { required: true, message: '请输入唯一编码!' }
                        ]}
                    >
                        <Input placeholder="唯一编码" />
                    </Form.Item>

                    <Form.Item
                        label="角色状态"
                        name="status"
                        rules={[
                            { required: true, message: '请选择角色状态!' }
                        ]}
                    >
                        <Radio.Group value={1} options={[
                            { label: '启用', value: 1 },
                            { label: '禁用', value: 0 }
                        ]}>
                        </Radio.Group>
                    </Form.Item>

                    <Form.Item
                        label="描述"
                        name="remark"
                        rules={[
                            { required: true, message: '请输入角色描述!' }
                        ]}
                    >
                        <Input.TextArea rows={4} placeholder="角色描述" />
                    </Form.Item>

                </Form>
            </Modal>
        </>
    );
}

EditRole.defaultProps = {
    isVisible: false,
    id: 0,
    edit: true
}

export default EditRole;
