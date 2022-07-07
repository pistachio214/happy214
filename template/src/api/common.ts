// import request from "@/http/request";

const BASE_URL: string = process.env.REACT_APP_BASE_URL!;

export const uploadUrl = () => {
    return `/${BASE_URL}/common/upload`;
}

