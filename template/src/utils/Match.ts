import { Key, pathToRegexp } from 'path-to-regexp';

const matchPath = (path: string, pathname: string, options?: any) => {

    const keys: Key[] = [];

    const regExp = pathToRegexp(path, keys, getOptions(options));
    const result = regExp.exec(pathname);
    if (!result) {
        return null;
    } else {
        let execArr: string[] = Array.from(result);
        execArr = execArr.slice(1);
        const params = getParams(execArr, keys);
        if (!params) {
            return null;
        }
        return {
            isExact: pathname === result[0],
            params,
            path,
            url: result[0]
        }

    }
}

const getOptions = (options = {}) => {
    const defaultOptions = {
        exact: false,
        sensitive: false,
        strict: false
    }

    const opts = {
        ...defaultOptions, ...options
    }

    return {
        sensitive: opts.sensitive,
        strict: opts.sensitive,
        end: opts.exact
    }
}


const getParams = (groups: string[], keys: Key[]) => {
    const obj: any = {};

    for (let i = 0; i < groups.length; i++) {
        const value = groups[i];
        const name = keys[i].name;
        obj[name] = value;
    }

    return obj;
}

export default matchPath;