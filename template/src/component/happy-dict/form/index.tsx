
import React, { useEffect, useState } from "react";

import { Form, Select } from 'antd';

import { getDictByKey } from '@/api/dicts';
import { OptionsInterface } from "@/types/Common";

interface IProps {
    type: string
    name?: string
    defaultValue?: string | number
    style?: React.CSSProperties
    disabled?: boolean
}

const HappyDictForm: React.FC<IProps> = (props: IProps) => {

    const [options, setOptions] = useState<OptionsInterface[]>([]);
    const [label, setLabel] = useState<string>('');

    useEffect(() => {
        getDictByKey(props.type).then((res) => {
            setLabel(res.data.name)
            let option: OptionsInterface[] = [];
            option.push.apply(option, res.data.items)

            setOptions(option)
        })
    }, [props.type]) // eslint-disable-line react-hooks/exhaustive-deps

    return (
        <>
            <Form.Item name={props.name} label={label} initialValue={props.defaultValue}>
                <Select
                    placeholder={`请选择`}
                    style={props.style}
                    disabled={props.disabled}
                    options={options}
                />
            </Form.Item>
        </>
    )
}

HappyDictForm.defaultProps = {
    type: '',
    name: 'name',
    defaultValue: undefined,
    style: { width: 120 }
}

export default React.memo(HappyDictForm);