import { Button } from "antd";
import React from "react";
import { useDispatch, useSelector } from "react-redux";

import { changeName, rollBackName } from "@/store/action/user";

const Index: React.FC = () => {

    const name = useSelector((state: any) => state.user.name)

    const distpath = useDispatch();

    return (
        <>
            hello, {name}
            <Button type={'primary'} onClick={() => {
                distpath(changeName());
            }}>
                click
            </Button>

            <Button type={'primary'} danger onClick={() => {
                distpath(rollBackName());
            }}>
                rollback click
            </Button>
        </>
    );

}

export default Index;