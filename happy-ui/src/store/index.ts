import { Reducer } from 'react';
import thunk from 'redux-thunk';
import reducer from '@/store/reducer';
import { createStore, applyMiddleware, ReducersMapObject } from 'redux';

//引入redux-persist，全局数据持久化
import { persistStore, persistReducer } from 'redux-persist';
import storage from 'redux-persist/lib/storage';
import autoMergeLevel2 from 'redux-persist/lib/stateReconciler/autoMergeLevel2';

//引入redux工具 以便在浏览器查看
import logger from 'redux-logger';
import { composeWithDevTools } from 'redux-devtools-extension';
import { PersistConfig, Persistor } from 'redux-persist/es/types';
import { PersistPartial } from 'redux-persist/es/persistReducer';

const persistConfig: PersistConfig<ReducersMapObject> = {
    key: 'root',
    storage: storage,
    stateReconciler: autoMergeLevel2
}

const myPersisReducer: Reducer<any & PersistPartial, any> = persistReducer(persistConfig, reducer);

//引入createStore后，store需要调用创建
const store = createStore(
    myPersisReducer,
    composeWithDevTools(
        applyMiddleware(thunk, logger)
    )
);

export const persistor: Persistor = persistStore(store);

export default store;