import React, { useEffect, useState } from "react";
import { Modal, Descriptions } from "antd";

import { SysExceptionLog, SysExceptionLogoDetailsProps } from "@/types/SysLog";

import { findExceptionLog } from '@/api/logs'
import ReactJson from "react-json-view";

const HappyExceptionLogDetails: React.FC<SysExceptionLogoDetailsProps> = (props: SysExceptionLogoDetailsProps) => {


    const [sysOperLog, setSysOperLog] = useState<SysExceptionLog>();

    useEffect(() => {
        const { id, isVisible } = props;

        if (isVisible) {
            findExceptionLog(id).then(res => {
                setSysOperLog(res.data);
            })
        }
    }, [props.isVisible])// eslint-disable-line react-hooks/exhaustive-deps

    const handleCancel = () => {
        props.closeModel()
    }

    return (
        <>
            <Modal title={`日志详情`}
                visible={props.isVisible}
                onCancel={() => handleCancel()}
                getContainer={false}
                width={'80%'}
                footer={false}
            >
                <Descriptions
                    bordered
                    size='small'
                    column={3}
                    labelStyle={{
                        width: '150px'
                    }}
                >
                    <Descriptions.Item label="操作者">{sysOperLog?.operUserName}</Descriptions.Item>
                    <Descriptions.Item label="IP">{sysOperLog?.operIp}</Descriptions.Item>
                    <Descriptions.Item label="版本号">{sysOperLog?.operVer}</Descriptions.Item>

                    <Descriptions.Item label="异常名称">{sysOperLog?.excName}</Descriptions.Item>
                    <Descriptions.Item label="操作时间" span={2}>
                        {sysOperLog?.createdAt}
                    </Descriptions.Item>

                    <Descriptions.Item label="操作方法" span={3}>
                        {sysOperLog?.operMethod}
                    </Descriptions.Item>
                    <Descriptions.Item label="请求地址" span={3}>
                        {sysOperLog?.operUri}
                    </Descriptions.Item>
                    <Descriptions.Item label="请求方式" span={3}>
                        {sysOperLog?.operRequMethod}
                    </Descriptions.Item>
                    <Descriptions.Item label="请求参数" span={3}>
                        {
                            sysOperLog?.excRequParam !== undefined ? (
                                <ReactJson
                                    src={JSON.parse(sysOperLog?.excRequParam)}
                                    name={false}
                                    displayDataTypes={false}
                                    enableClipboard={false}
                                />
                            ) : null
                        }

                    </Descriptions.Item>

                    <Descriptions.Item label="错误信息" span={3}>
                        {sysOperLog?.excMessage}
                    </Descriptions.Item>

                    

                </Descriptions>
            </Modal>
        </>
    )
}

export default HappyExceptionLogDetails;