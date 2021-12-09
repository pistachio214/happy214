import React from "react";
import { Button, Checkbox, Form, Image, Input, message } from "antd";
import { UserOutlined, LockOutlined } from '@ant-design/icons'

import { LoginContainerStyle } from "@/pages/login/style";
import logo from '@/assets/images/logo.svg';

const Login: React.FC = () => {

    const [form] = Form.useForm();

    const handleLogin = () => {

    }

    const handForgetPassword = () => {
        message.info('请联系管理员处理');
    }

    return (
        <LoginContainerStyle>
            <div className={'logo'}>
                <Image className={'logo-img'} preview={false} src={logo} />
                <div>
                    <span className={"logo-title"}>PSY Admin</span>
                </div>
            </div>
            <div className="form-title">
                山有木兮 木有枝 心悦君兮 君不知
            </div>
            <Form
                form={form}
                size="middle"
                className="login-form"
                initialValues={{ remember: true }}
                onFinish={handleLogin}
            >
                <Form.Item
                    name={'username'}
                    rules={[
                        {
                            required: true,
                            message: '请输入您的账号!'
                        }
                    ]}>
                    <Input
                        prefix={<UserOutlined className="site-form-item-icon" />}
                        placeholder="账号"
                        autoComplete="off"
                    />
                </Form.Item>

                <Form.Item
                    name={'password'}
                    rules={[
                        {
                            required: true,
                            message: '请输入您的密码!'
                        }
                    ]}>
                    <Input
                        prefix={<LockOutlined className="site-form-item-icon" />}
                        placeholder="密码"
                        type={'password'}
                    />
                </Form.Item>

                <Form.Item>
                    <Form.Item name={'remember'} valuePropName="checked" noStyle>
                        <Checkbox>自动登录</Checkbox>
                    </Form.Item>

                    <Button type="link" className="login-form-forget" onClick={handForgetPassword}>
                        忘记密码
                    </Button>
                </Form.Item>

                <Form.Item>
                    <Button type="primary" htmlType="submit" className="login-form-button">
                        Log in
                    </Button>
                </Form.Item>


            </Form>

        </LoginContainerStyle>
    );
}

export default Login;