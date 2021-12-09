export const changeName = () => {
    return (dispatch: any) => {
        dispatch({ type: 'change' });
    }
}

export const rollBackName = () => {
    return (dispatch: any) => {
        dispatch({ type: 'rollback' });
    }
}