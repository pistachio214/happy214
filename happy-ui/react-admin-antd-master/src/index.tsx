
import ReactDOM from 'react-dom';

import { ConfigProvider } from 'antd';
import { Provider } from 'react-redux'

import zhCn from 'antd/es/locale/zh_CN';
import store from '@/store';

import Root from '@/root'

ReactDOM.render(
  <ConfigProvider locale={zhCn}>
    <Provider store={store}>
      <Root />
    </Provider>
  </ConfigProvider>,
  document.getElementById('root')
);