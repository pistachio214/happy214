import {
    createSlice,
    createAsyncThunk,
    PayloadAction
} from "@reduxjs/toolkit";
import { SettingState } from "@/redux/types/Setting";

const initialState: SettingState = {
    clientHeight: 0
};

interface PromiseNum {
    number: number;
}

const promise_one: Promise<PromiseNum> = new Promise((res, rej) => {
    setTimeout(() => {
        res({ number: 10 });
    }, 3000);
});

// 异步Action
export const getAsyncInfo = createAsyncThunk("getAsyncInfo", async () => {
    const data = await promise_one;
    return data;
});

export const settingSlice = createSlice({
    name: "setting",
    initialState,
    reducers: {
        setClientHeight: (state: SettingState, action: PayloadAction<number>) => {
            state.clientHeight = action.payload;
        },
    },
    extraReducers: (builder) => {
        // 进行请求阶段的一些操作
        builder.addCase(getAsyncInfo.pending, () => {
            console.log("进行中");
        });
        builder.addCase(getAsyncInfo.fulfilled, (state, action) => {
            console.log("action.payload: ", action.payload); //{number:"10"}
            console.log("成功");
        });
        builder.addCase(getAsyncInfo.rejected, () => {
            console.log("失败");
        });
    },
});

export const { setClientHeight } = settingSlice.actions;
export default settingSlice.reducer;




