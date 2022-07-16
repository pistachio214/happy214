import React from "react";
import { Modal,Descriptions } from "antd";

import { SysOperLogoDetailsProps } from "@/types/SysLog";

const HappyOperLogDetails: React.FC<SysOperLogoDetailsProps> = (props: SysOperLogoDetailsProps) => {

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
                    title="Custom Size"
                    size={size}
                    extra={<Button type="primary">Edit</Button>}
                >
                    <Descriptions.Item label="Product">Cloud Database</Descriptions.Item>
                    <Descriptions.Item label="Billing">Prepaid</Descriptions.Item>
                    <Descriptions.Item label="time">18:00:00</Descriptions.Item>
                    <Descriptions.Item label="Amount">$80.00</Descriptions.Item>
                    <Descriptions.Item label="Discount">$20.00</Descriptions.Item>
                    <Descriptions.Item label="Official">$60.00</Descriptions.Item>
                    <Descriptions.Item label="Config Info">
                        Data disk type: MongoDB
                        <br />
                        Database version: 3.4
                        <br />
                        Package: dds.mongo.mid
                        <br />
                        Storage space: 10 GB
                        <br />
                        Replication factor: 3
                        <br />
                        Region: East China 1<br />
                    </Descriptions.Item>
                </Descriptions>
            </Modal>
        </>
    )
}

export default HappyOperLogDetails;