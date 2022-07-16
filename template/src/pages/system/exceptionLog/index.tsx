import React, { useState } from "react";

import { Form, Input, Button, Tooltip, DatePicker } from 'antd';
import { ColumnsType } from "antd/lib/table";

import { IOperator } from "@/types/IOperator";
import HappyOperator from "@/component/happy-operator";
import HappyTable from "@/component/happy-table";
import { QuestionType } from "@/types/Common";
import { getExceptionLoginList } from '@/api/logs';
import { SysExceptionLog } from "@/types/SysLog";

import * as moment from "moment";
import HappyExceptionLogDetails from "@/component/happy-log/exception";

interface OperLogQuestionType extends QuestionType {
    operUserName?: string
    startAt?: string
    endAt?: string
}

const { RangePicker } = DatePicker;

const OperLog: React.FC = () => {

    const [form] = Form.useForm();

    const [params, setParams] = useState<OperLogQuestionType>();

    const [id, setId] = useState<number>(0);
    const [showVisible, setShowVisible] = useState<boolean>(false);

    const tableWidthHiddenStyle: React.HTMLAttributes<SysExceptionLog> | React.TdHTMLAttributes<SysExceptionLog> = {
        style: {
            maxWidth: 200,
            overflow: 'hidden',
            whiteSpace: 'nowrap',
            textOverflow: 'ellipsis',
            cursor: 'pointer'
        }
    }

    const columns: ColumnsType<SysExceptionLog> = [
        {
            title: 'ID',
            dataIndex: 'id',
            align: 'center'
        },
        {
            title: '操作员名称',
            dataIndex: 'operUserName',
            align: 'center'
        },
        {
            title: '异常名称',
            dataIndex: 'excName',
            align: 'center'
        },
        {
            title: '操作方法',
            dataIndex: 'operMethod',
            align: 'center',
            width: 200,
            onCell: () => tableWidthHiddenStyle,
            render: (text: string) => {
                return <Tooltip placement="topLeft" title={text}>{text}</Tooltip>
            }
        },
        {
            title: '请求URL',
            dataIndex: 'operUri',
            align: 'center',
            width: 150,
            onCell: () => tableWidthHiddenStyle,
            render: (text: string) => {
                return <Tooltip placement="topLeft" title={text}>{text}</Tooltip>
            }
        },
        {
            title: '请求IP',
            dataIndex: 'operIp',
            align: 'center'
        },
        {
            title: '版本号',
            dataIndex: 'operVer',
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
            render: (text, record: SysExceptionLog) => {
                const item: IOperator[] = [
                    {
                        title: '详情',
                        permission: ['sys:exception:log:info'],
                        onClick: () => {
                            showEditDictModal(record.id!);
                        }
                    }
                ];

                return (
                    <HappyOperator items={item} />
                )
            },
        },
    ];

    const showEditDictModal = (id: number) => {
        setId(id);
        setShowVisible(true);
    }

    const handleSearch = (values: { operUserName: string, time: moment.Moment[] }) => {
        const { operUserName, time } = values

        let par = {
            ...params,
            ...{
                operUserName
            }
        }

        if (time !== undefined) {
            par = {
                ...par,
                ...{
                    startAt: time[0].format('YYYY-MM-DD HH:mm:ss'),
                    endAt: time[1].format('YYYY-MM-DD HH:mm:ss')
                }
            }
        }
        setParams(par)
    }

    const searchDom = (
        <Form form={form} layout="inline" name="search-dict" onFinish={(values) => handleSearch(values)}>
            <Form.Item name={'operUserName'} label={'操作者'}>
                <Input placeholder="操作者" />
            </Form.Item>

            <Form.Item name={'time'} label={'操作时间'}>
                <RangePicker showTime />
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
                rowKey={(record: SysExceptionLog) => record.id!}
                columns={columns}
                search={searchDom}
                url={getExceptionLoginList()}
                params={params}
                reload={{
                    hasPremiss: ['sys:exception:log:list']
                }}
                plus={{
                    hide: true
                }}
                quickJump={(page: number) => {
                    setParams({ ...params, ...{ current: page } })
                }}
            />

            <HappyExceptionLogDetails id={id} isVisible={showVisible} closeModel={() => setShowVisible(false)} />
        </>
    );
}

export default OperLog;