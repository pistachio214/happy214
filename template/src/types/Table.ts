import { ColumnsType } from "antd/lib/table";
import { Method } from "axios";


interface TablePlusType {
    hide?: boolean
    click?: () => void
    hasPremiss?: string[]
}

interface TableReloadType {
    hide?: boolean
    click?: () => void
    hasPremiss?: string[]
}

interface HappyTableProps {
    columns: ColumnsType<any>
    rowKey?: string | ((record: any) => number)
    operator?: boolean
    search?: React.ReactElement
    reload?: TableReloadType
    plus?: TablePlusType
    url: string
    method?: Method
    params?: object
    quickJump: (page: number) => void
    isVisible?: boolean
}


export type {
    TablePlusType,
    TableReloadType,
    HappyTableProps
}

