class UuidUtil {

    //生成永不重复的随机数
    static getUuiD = (randomLength: number) => {
        return Number(Math.random().toString().substring(2, randomLength) + Date.now()).toString(36)
    }

}

export default UuidUtil;
