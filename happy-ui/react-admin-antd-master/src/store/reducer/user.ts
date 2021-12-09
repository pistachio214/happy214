
interface userState {
    name: string
}

interface userAction {
    type: string
    data: string
}

const initUserState: userState = {
    name: 'roger'
}

const user = (state: userState = initUserState, action: userAction) => {
    switch (action.type) {
        case 'change':
            return { ...state, ...{ name: 'Jacksons' } }
        case 'rollback':
            return { ...state, ...{ name: 'roger' } }
        default:
            return state;
    }
}

export { user }