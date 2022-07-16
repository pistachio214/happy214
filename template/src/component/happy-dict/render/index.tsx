
import React, { useEffect, useState } from "react";
import { getDictByKey } from '@/api/dicts';

interface IProps {
    type: string
    defaultValue?: string | number
    style?: React.CSSProperties
}

const HappyDictRender: React.FC<IProps> = (props: IProps) => {

    const [title, setTitle] = useState<string>('');

    useEffect(() => {
        getDictByKey(props.type).then((res) => {
            res.data.items.forEach((item: { label: string, value: string | number }, index: number) => {
                if (Number(props.defaultValue) === Number(item.value)) {
                    setTitle(item.label);
                }
            })
        });
    }, [props.type]); // eslint-disable-line react-hooks/exhaustive-deps

    return (
        <>
            {title}
        </>
    );
}

export default React.memo(HappyDictRender);