import React from 'react';
import { useAppSelector } from '@/redux/hook'
import { MenuState } from "@/redux/types/Menu";
import { shallowEqual } from "react-redux";
import { AuthWrapperProps } from '@/types/IAuth';
import AuthUtil from '@/utils/AuthUtil';

export const HappyAuthWrapper: React.FC<AuthWrapperProps> = (props: AuthWrapperProps) => {

    const userStateMap: MenuState = useAppSelector((state: any) => ({ ...state.user }), shallowEqual);
    const authoritys: string[] = userStateMap.authoritys;

    return (
        <>
            {AuthUtil.checkAuth(props.hasPermiss, authoritys) ? props.children : null}
        </>
    );
}

HappyAuthWrapper.defaultProps = {
    children: <></>,
    hasPermiss: undefined
}

export default React.memo(HappyAuthWrapper);