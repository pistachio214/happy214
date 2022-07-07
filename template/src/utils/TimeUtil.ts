
/**
 * 时间工具
 */
class TimeUtil {

    /**
     * 时间戳或者时间转特定格式
     * @param time 时间戳或者时间格式
     * @param cFormat 时间策略参数
     * @returns 时间格式
     */
    static parseTime = (
        time?: object | string | number | null, cFormat?: string
    ): string | null => {
        if (time === undefined || !time) {
            return null;
        }
        const format = cFormat || "{y}-{m}-{d} {h}:{i}:{s}";
        let date: Date;
        if (typeof time === "object") {
            date = time as Date;
        } else {
            if (typeof time === "string") {
                if (/^[0-9]+$/.test(time)) {
                    time = parseInt(time);
                } else {
                    time = time.replace(new RegExp(/-/gm), "/");
                }
            }

            if (typeof time === "number" && time.toString().length === 10) {
                time = time * 1000;
            }
            date = new Date(time);
        }

        const formatObj: { [key: string]: number } = {
            y: date.getFullYear(),
            m: date.getMonth() + 1,
            d: date.getDate(),
            h: date.getHours(),
            i: date.getMinutes(),
            s: date.getSeconds(),
            a: date.getDay()
        };

        const timeStr = format.replace(/{([ymdhisa])+}/g,
            (result: string, key: string) => {
                const value = formatObj[key];
                if (key === "a") {
                    return `星期${["日", "一", "二", "三", "四", "五", "六"][value]}`;
                }
                return value.toString().padStart(2, "0");
            });

        return timeStr;
    }
}

export default TimeUtil;


/**
 * 使用方式
    parseTime(new Date());
    // console :2021-11-18 10:33:24

    parseTime('2020-12-2');
    // console :2021-11-18 00:00:00

    parseTime(1604938866479);
    // console :2020-11-10 00:21:06

    parseTime('1604938866479');
    // console :2020-11-10 00:21:06

    parseTime('1604938866479';,'{y}-{m}-{d} {h}:{i}:{s}{a}');
    // console : 2020-11-10 00:21:06 星期二

    parseTime('1604938866479','{m}/{d}/{y}');
    // console : 12/02/2020

    parseTime(1);
    // console :1970-01-01 08:00:00

    parseTime(null);
    // console :null

    parseTime("");
    // console :null

    parseTime(undefined);
    // console :null
*/