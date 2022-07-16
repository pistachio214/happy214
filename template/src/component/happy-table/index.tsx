import React, { useEffect, useState } from "react";

import { Button, Table } from "antd";

import request from '@/http/request';
import { HappyTableContainer } from '@/component/happy-table/style';
import { PlusOutlined, ReloadOutlined } from "@ant-design/icons";
import IPagination from "@/types/IPagination";
import { HappyTableProps } from "@/types/Table";
import HappyAuthWrapper from "@/component/happy-auth-hoc/HappyAuthWrapper";

const HappyTable: React.FC<HappyTableProps> = (props: HappyTableProps) => {

    const [loadingStatus, setLoadingStatus] = useState<boolean>(false);
    const [data, setData] = useState<object[]>([]);
    const [pagination, setPagination] = useState<IPagination>();

    useEffect(() => {
        initList();
    }, [props.url, props.params, props.isVisible]); // eslint-disable-line react-hooks/exhaustive-deps

    const initList = async () => {
        setLoadingStatus(true);
        const { url, method, params } = props;
        await request({
            url: url,
            method: method,
            params: params
        }).then((res) => {
            setData(res.data.records);
            let pagination: IPagination = {
                pageSize: res.data.size,
                defaultCurrent: 1,
                current: res.data.current,
                total: res.data.total,
                onChange: (page: number) => {
                    props.quickJump(page);
                }
            }
            setPagination(pagination);
            setLoadingStatus(false);
        });
    }

    return (
        <HappyTableContainer>
            <div className="action-container">
                <div className="search-container">
                    {props.search}
                </div>
                {
                    props.operator ?
                        <div className="common-btn-container">
                            {
                                props.reload?.hide ? null : (
                                    <HappyAuthWrapper hasPermiss={props.reload?.hasPremiss!}>
                                        <Button
                                            type="primary"
                                            icon={<ReloadOutlined />}
                                            onClick={
                                                () => {
                                                    props.reload?.click !== undefined ? props.reload?.click() : initList()
                                                }}
                                            disabled={props.reload?.hide}
                                        >
                                            刷新
                                        </Button>
                                    </HappyAuthWrapper>
                                )
                            }

                            {
                                props.plus?.hide ? null : (
                                    <HappyAuthWrapper hasPermiss={props.plus?.hasPremiss!}>
                                        <Button
                                            icon={<PlusOutlined />}
                                            onClick={() => props.plus?.click!()}
                                            disabled={props.plus?.hide}
                                        >
                                            新增
                                        </Button>
                                    </HappyAuthWrapper>
                                )
                            }

                        </div>
                        :
                        null
                }
            </div>

            <Table
                rowKey={props.rowKey}
                columns={props.columns}
                dataSource={data}
                loading={loadingStatus}
                bordered={true}
                size={'small'}
                pagination={{
                    ...pagination
                }}
                scroll={{
                    scrollToFirstRowOnChange: true
                }}
                style={{
                    paddingRight: '5px'
                }}
            />
        </HappyTableContainer>
    );
}

HappyTable.defaultProps = {
    columns: [],
    operator: true,
    search: <></>,
    reload: {
        hide: false,
        click: undefined,
        hasPremiss: []
    },
    method: 'GET',
    params: {},
    plus: {
        hide: false,
        click: () => { console.log('点击新增按钮') },
        hasPremiss: []
    },
    isVisible: false
}

export default HappyTable;