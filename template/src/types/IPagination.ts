interface IPagination {
    pageSize: number
    defaultCurrent: number
    current: number
    total: number
    onChange?: (page: number) => void
}

export default IPagination;