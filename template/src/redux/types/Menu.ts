interface MenuNavItem {
    id: number
    title: string
    path: string
    icon?: string
    children?: MenuNavItem[]
}

interface MenuState {
    nav: MenuNavItem[],
    authoritys: string[]
}

export type {
    MenuNavItem,
    MenuState
}