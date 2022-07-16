
import ReactDOM from 'react-dom/client';

import { ConfigProvider } from 'antd';
import zhCN from 'antd/es/locale/zh_CN';
import 'moment/locale/zh-cn';

import { Provider } from 'react-redux';
import { PersistGate } from 'redux-persist/integration/react';

import { store, persistor } from '@/redux/store';
import HappyRouter from '@/component/happy-router';

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);

root.render(
  <ConfigProvider locale={zhCN}>
    <Provider store={store}>
      <PersistGate loading={null} persistor={persistor}>
        <HappyRouter />
      </PersistGate>
    </Provider>
  </ConfigProvider>
);