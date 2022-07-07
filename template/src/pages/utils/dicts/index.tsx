import React, { useEffect, useState } from "react";

import { Form, Input, Button } from 'antd';
import { ColumnsType } from "antd/lib/table";
import { DeleteOutlined } from "@ant-design/icons";

import { SysDict } from "@/types/SysDicts";
import { IOperator } from "@/types/IOperator";
import HappyOperator from "@/component/happy-operator";
import HappyTable from "@/component/happy-table";
import { getDictList, delDict, getDictByKey } from '@/api/dicts';
import SaveDictsModal from "@/component/happy-utils/dicts/save";
import DictItem from "@/component/happy-utils/dicts/item";
import HappyDictForm from "@/component/happy-dict/form";
import { OptionsInterface, QuestionType } from "@/types/Common";
import DictUtil from "@/utils/DictUtil";

interface DIctsQuestionType extends QuestionType {
    type?: string
    system?: number
}

const Dicts: React.FC = () => {

    const [form] = Form.useForm();

    const [params, setParams] = useState<DIctsQuestionType>();

    const [isSaveVisible, setIsSaveVisible] = useState<boolean>(false);
    const [id, setId] = useState<number>(0);
    const [edit, setEdit] = useState<boolean>(false);

    const [isDictItemVisible, setIsDictItemVisible] = useState<boolean>(false);
    const [dictName, setDictName] = useState<string>();
    const [type, setType] = useState<string>('');

    const [isRefresh, setIsRefresh] = useState<boolean>(false);
    const [dictsType, setDictsType] = useState<OptionsInterface[]>([]);

    useEffect(() => {
        getDictByKey('dicts_type').then(res => {
            setDictsType(res.data.items)
        });
    }, [])

    const columns: ColumnsType<SysDict> = [
        {
            title: 'ID',
            dataIndex: 'id',
            align: 'center'
        },
        {
            title: '类型标识',
            dataIndex: 'type',
            align: 'center'
        },
        {
            title: '描述',
            dataIndex: 'description',
            align: 'center'
        },
        {
            title: '字典类型',
            dataIndex: 'system',
            align: 'center',
            render: (value: string, record: SysDict) => {
                return DictUtil.generateDictItem(dictsType, value);
            }
        },
        {
            title: '备注信息',
            dataIndex: 'remarks',
            align: 'center'
        },
        {
            title: '创建时间',
            dataIndex: 'createdAt',
            align: 'center'
        },
        {
            title: '操作',
            key: 'action',
            render: (text, record: SysDict) => {
                const item: IOperator[] = [
                    {
                        title: '字典项',
                        permission: ['sys:dict:item:list'],
                        onClick: () => {
                            showDictItemModal(record.id!, `${record.description} ( ${record.type} ) `, record.type)
                        }
                    },
                    {
                        title: '编辑',
                        permission: ['sys:dict:edit'],
                        onClick: () => {
                            showEditDictModal(record.id!);
                        }
                    }
                ];

                if (record.id !== 1) {
                    item.push({
                        title: '删除',
                        danger: true,
                        icon: <DeleteOutlined />,
                        permission: ['sys:dict:delete'],
                        message: `是否删除该字典 [ ${record.description} ] ?`,
                        onClick: () => {
                            handleDelete(record.id!);
                        }
                    })
                }

                return (
                    <HappyOperator items={item} />
                )
            },
        },
    ];

    const handleDelete = (id: number) => {
        delDict(id).then(res => {
            setIsRefresh(!isRefresh);
        })
    }

    const showDictItemModal = (id: number, name: string, type: string) => {
        setId(id);
        setType(type);
        setDictName(name);
        setIsDictItemVisible(true);
    }

    const closeDictItemModal = () => {
        setIsDictItemVisible(false);
    }

    const showEditDictModal = (id: number) => {
        setId(id);
        setEdit(true);
        setIsSaveVisible(true);
    }

    const showSaveDictModal = () => {
        setIsSaveVisible(true);
        setEdit(false);
    }

    const closeSaveDictModal = () => {
        setIsSaveVisible(false);
        setIsRefresh(!isRefresh);
    }

    const handleSearch = (values: { type: string, system: number }) => {
        setParams({ ...params, ...values })
    }

    const searchDom = (
        <Form form={form} layout="inline" name="search-dict" onFinish={(values) => handleSearch(values)}>
            <Form.Item name={'type'} label={'类型名称'}>
                <Input placeholder="类型名称" />
            </Form.Item>

            <HappyDictForm type={"dicts_type"} name={"system"} style={{ width: 180 }} />

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
                rowKey={(record: SysDict) => record.id!}
                columns={columns}
                search={searchDom}
                url={getDictList()}
                isVisible={isRefresh}
                params={params}
                reload={{
                    hasPremiss: ['sys:dict:list']
                }}
                plus={{
                    click: () => showSaveDictModal(),
                    hasPremiss: ['sys:dict:save']
                }}
                quickJump={(page: number) => {
                    setParams({ ...params, ...{ current: page } })
                }}
            />

            <SaveDictsModal
                isVisible={isSaveVisible}
                isEdit={edit}
                id={id}
                closeModal={() => closeSaveDictModal()}
            />

            <DictItem
                id={id}
                type={type}
                name={dictName}
                isVisible={isDictItemVisible}
                closeModal={() => closeDictItemModal()}
            />
        </>
    );
}

export default Dicts;