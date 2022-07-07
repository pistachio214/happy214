import React, { useEffect, useState } from "react";

import { Modal, Form, Input, Radio, Select, InputNumber } from 'antd';

import { MenuInfo } from "@/types/Menus";
import { saveMenu, editMenu } from "@/api/menu";

interface IProps {
    isVisible: boolean
    closeModal: () => void
    menuInfo?: MenuInfo
    menuList: MenuInfo[] | undefined
    edit?: boolean
}

interface optionSelect {
    label: string
    value: number
}

const EditMenuModal: React.FC<IProps> = (props: IProps) => {

    const [form] = Form.useForm();

    const [menuOptions, setMenuOptions] = useState<optionSelect[]>();

    useEffect(() => {
        const { menuInfo, edit, isVisible, menuList } = props;
        if (edit && isVisible && menuInfo !== undefined) {
            //编辑
            form.setFieldsValue(menuInfo);
        } else {
            if (isVisible) {
                form.resetFields();
            }
        }

        if (menuList !== undefined && menuList.length) {
            let optionsSelect: optionSelect[] = generatorMenuOption(menuList);
            optionsSelect.unshift({ label: '一级目录', value: 0 });
            setMenuOptions(optionsSelect);
        }


    }, [props.menuInfo, props.isVisible]);  // eslint-disable-line react-hooks/exhaustive-deps

    const handleOk = () => {
        const { edit } = props;
        let responseRes;
        if (edit) {
            responseRes = editMenu(form.getFieldsValue())
        } else {
            responseRes = saveMenu(form.getFieldsValue())
        }

        responseRes.then(res => {
            handleCancel()
        })
    }

    const generatorMenuOption = (menuList: MenuInfo[]) => {
        let options: optionSelect[] = [];

        if (menuList.length && menuList !== undefined) {
            menuList.forEach((item: MenuInfo) => {
                if ([0, 1].includes(item.type)) {
                    let name = item.type === 0 ? item.name : ` ---- ${item.name}`;
                    options.push({ label: name, value: item.id });
                    if (item.children && item.children.length) {
                        let resOptions: optionSelect[] = generatorMenuOption(item.children);
                        options.push.apply(options, resOptions);
                    }
                }
            })
        }

        return options;
    }

    const handleCancel = () => {
        props.closeModal();
    }

    return (
        <>
            <Modal title={props.edit ? "编辑权限" : "新增权限"}
                visible={props.isVisible}
                onCancel={() => handleCancel()}
                onOk={() => form.submit()}
                getContainer={false}
            >

                <Form
                    name="edit-menu"
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
                        label="上级菜单"
                        name="parentId"
                        rules={[
                            { required: true, message: '请选择上级菜单!' }
                        ]}
                    >
                        <Select
                            placeholder="上级菜单"
                            options={menuOptions}
                        >
                        </Select>
                    </Form.Item>

                    <Form.Item
                        label="菜单名称"
                        name="name"
                        rules={[
                            { required: true, message: '请输入菜单名称!' }
                        ]}
                    >
                        <Input placeholder="菜单名称" />
                    </Form.Item>

                    <Form.Item
                        label="权限编码"
                        name="perms"
                        rules={[
                            { required: true, message: '请输入权限编码!' }
                        ]}
                    >
                        <Input placeholder="权限编码" />
                    </Form.Item>

                    {/* <Form.Item
                        label="菜单组件"
                        name="component"
                    >
                        <Input placeholder="菜单组件" />
                    </Form.Item> */}

                    <Form.Item
                        label="类型"
                        name="type"
                        rules={[
                            { required: true, message: '请选择类型!' }
                        ]}
                    >
                        <Radio.Group value={1} options={[
                            { label: '目录', value: 0 },
                            { label: '菜单', value: 1 },
                            { label: '按钮', value: 2 }
                        ]}>
                        </Radio.Group>
                    </Form.Item>

                    <Form.Item
                        label="状态"
                        name="status"
                        rules={[
                            { required: true, message: '请选择状态!' }
                        ]}
                    >
                        <Radio.Group value={1} options={[
                            { label: '启用', value: 1 },
                            { label: '禁用', value: 0 }
                        ]}>
                        </Radio.Group>
                    </Form.Item>

                    <Form.Item
                        label="排序号"
                        name="orderNum"
                        initialValue={1}
                        rules={[
                            { required: true, message: '请输入排序号!' }
                        ]}
                    >
                        <InputNumber style={{ width: '100%' }} min={1} max={100} keyboard={true} placeholder="排序号" />
                    </Form.Item>

                    <Form.Item
                        label="菜单URL"
                        name="path"
                    >
                        <Input placeholder="菜单URL" />
                    </Form.Item>

                    <Form.Item
                        label="图标"
                        name="icon"
                    >
                        <Input placeholder="图标" />
                    </Form.Item>

                </Form>
            </Modal>
        </>
    );
}

EditMenuModal.defaultProps = {
    isVisible: false,
    menuInfo: undefined,
    edit: true
}

export default EditMenuModal;
