class AuthUtil {

    /**
     * 校验当前用户是否有功能编码对应的权限
     * @param {string[]} permission 
     * @param {string[]} authoritys
     */
    static checkAuth = (permission: string[], authoritys: string[]) => {
        // 没有设定权限
        if (permission === undefined) {
            return true;
        } else if (permission !== undefined && permission !== null && permission.length > 0) {
            for (const permiss of permission) {
                if (authoritys.indexOf(permiss) > -1) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }
}

export default AuthUtil;