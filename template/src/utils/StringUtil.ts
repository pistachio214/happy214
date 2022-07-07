class StringUtil {

    static isNotEmpty(str: string): boolean {
        if (str.length > 0 && str !== undefined) {
            return true;
        }
        return false;
    }
}

export default StringUtil;