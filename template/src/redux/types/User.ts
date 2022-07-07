import { MenuState } from "@/redux/types/Menu";

interface UserBaseInfo {
    nickname: string
    avatar: string
}

interface UserState extends MenuState {
    data: UserBaseInfo
}

export type {
    UserBaseInfo,
    UserState
}