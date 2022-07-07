import React, { useState } from "react";

import { ColumnsType } from "antd/lib/table";
import { Form, Input, Button } from 'antd';

import HappyTable from "@/component/happy-table";
import { DictInfo } from "@/types/Dicts";
import { getDictList } from '@/api/dicts';
import HappyDictForm from "@/component/happy-dict/form";
import { QuestionType } from "@/types/Common";

const Dicts: React.FC = () => {

    const [form] = Form.useForm();
    const [params, setParams] = useState<QuestionType>();
    // const [isRefresh, setIsRefresh] = useState<boolean>(false);

    const columns: ColumnsType<DictInfo> = [
        {
            title: 'ID',
            dataIndex: 'id',
            align: 'center'
        },
        {
            title: '类型名称',
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
            dataIndex: 'description',
            align: 'center'
        },
        {
            title: '备注信息',
            dataIndex: 'description',
            align: 'center'
        },
        {
            title: '创建时间',
            dataIndex: 'createdAt',
            align: 'center'
        },
    ]

    const handleSearch = (values: {}) => { }

    const searchDom = (
        <Form form={form} layout="inline" name="search-dict" onFinish={(values) => handleSearch(values)}>
            <Form.Item name={'type'} label={'类型名称'}>
                <Input placeholder="类型名称" />
            </Form.Item>

            <HappyDictForm type={"dicts_type"} name={"system"} />

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
                rowKey={(record: DictInfo) => record.id}
                columns={columns}
                search={searchDom}
                url={getDictList()}
                // isVisible={isRefresh}
                params={params}
                // plusClick={() => showCreateUserModal()}
                quickJump={(page: number) => {
                    setParams({ ...params, ...{ current: page } })
                }}
            />
        </>
    );
}

export default Dicts;