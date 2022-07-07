import React from 'react';

import { useNavigate } from 'react-router-dom';

import { Result, Button } from 'antd';

import defaultSettings from '@/defaultSettings';

const Empty: React.FC = () => {

    const navigate = useNavigate();

    const backHistory = () => {
        navigate(-1)
    }

    return (
        <Result
            status="404"
            title="404"
            subTitle={defaultSettings.notfoundTitle}
            extra={
                <Button
                    type="primary"
                    onClick={() => backHistory()}
                >
                    Go Back
                </Button>
            }
        />
    )
}

export default Empty;