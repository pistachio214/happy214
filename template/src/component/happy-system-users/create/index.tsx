import React, { useEffect } from "react";

import { Modal, Form, Input, Radio } from 'antd';

import { createUser } from "@/api/users";

interface IProps {
    isVisible: boolean
    closeModal: () => void
}

const CreateUserModal: React.FC<IProps> = (props: IProps) => {

    const [form] = Form.useForm();

    const handleOk = () => {
        createUser(form.getFieldsValue()).then(res => {
            handleCancel();
        })
    }

    const handleCancel = () => {
        props.closeModal();
    }

    useEffect(() => {
        const { isVisible } = props;
        if (isVisible) {
            //打开弹出层，重置界面
            form.resetFields();
        }
    }, [props.isVisible]);  // eslint-disable-line react-hooks/exhaustive-deps

    return (
        <>
            <Modal title={"新增管理员"}
                visible={props.isVisible}
                onCancel={() => handleCancel()}
                onOk={() => form.submit()}
                getContainer={false}
            >

                <Form
                    name="create-user"
                    form={form}
                    labelCol={{ span: 4 }}
                    wrapperCol={{ span: 20 }}
                    labelAlign="left"
                    onFinish={() => handleOk()}
                    autoComplete="off"
                >

                    <Form.Item
                        label="用户名"
                        name="username"
                        rules={[
                            { required: true, message: '请输入用户名!' }
                        ]}
                    >
                        <Input placeholder="用户名" />
                    </Form.Item>

                    <Form.Item
                        label="昵称"
                        name="nickname"
                        rules={[
                            { required: true, message: '请输入昵称!' }
                        ]}
                    >
                        <Input placeholder="昵称" />
                    </Form.Item>

                    <Form.Item
                        label="邮箱"
                        name="email"
                        rules={[
                            { required: true, message: '请输入邮箱!' }
                        ]}
                    >
                        <Input placeholder="邮箱" />
                    </Form.Item>

                    <Form.Item
                        label="状态"
                        name="status"
                        initialValue={1}
                        rules={[
                            { required: true, message: '请选择状态!' }
                        ]}
                    >
                        <Radio.Group options={[
                            { label: '启用', value: 1 },
                            { label: '禁用', value: 0 }
                        ]}>
                        </Radio.Group>
                    </Form.Item>

                </Form>
            </Modal>
        </>
    );
}

CreateUserModal.defaultProps = {
    isVisible: false,
}

export default CreateUserModal;
