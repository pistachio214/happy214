import { JSXElementConstructor, ReactElement } from "react";

export interface IRouter {
    title: string
    path: string
    auth?: boolean
    icon?: string
    component?: ReactElement<any, string | JSXElementConstructor<any>> | null | undefined
    children?: IRouter[]
}