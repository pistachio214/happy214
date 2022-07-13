import React, {
    useEffect,
    useState
} from "react";

import {
    Button,
    Checkbox,
    Form,
    Image,
    Input,
    message
} from "antd";
import {
    UserOutlined,
    LockOutlined,
    SafetyCertificateOutlined
} from '@ant-design/icons';

import { useAppDispatch } from '@/redux/hook';
import { useNavigate } from 'react-router-dom';
import { MenuNavItem } from "@/redux/types/Menu";
import { setNavAndAuthoritys, setUserData } from '@/redux/slice/user';

import defaultSettings from "@/defaultSettings";

import { LoginContainerStyle } from "@/pages/login/style";
import logo from '@/assets/images/logo.svg';

import { login } from '@/api/auth';
import { getNav } from '@/api/menu';
import { getCaptcha } from '@/api/auth';

const Login: React.FC = () => {

    const dispath = useAppDispatch();
    const navigate = useNavigate();

    const [form] = Form.useForm();

    const [captchaToken, setCaptchaToken] = useState<string>('');
    const [imageUrl, setImageUrl] = useState<string>('');

    useEffect(() => {
        onClickImage();
    }, []);// eslint-disable-line react-hooks/exhaustive-deps

    const handleLogin = (values: { username: string, password: string, code: string, remember: boolean }) => {
        let params = {
            ...{ token: captchaToken },
            ...values
        };


        login(params).then((res: any) => {
            sessionStorage.setItem('tokenName', res.data.tokenName);
            sessionStorage.setItem('tokenValue', res.data.tokenValue);
            setNavAndAuthority();

            message.success('🎉🎉🎉 登录成功', 1, () => {
                navigate('/');
            });
        }).catch(() => {
            onClickImage();
        })
    }

    const onClickImage = async () => {
        await getCaptcha().then((data: any) => {
            setImageUrl(data.data.base64Img);
            setCaptchaToken(data.data.token);
        })
    }

    //获取到权限和侧边栏
    const setNavAndAuthority = async () => {
        await getNav().then((res) => {
            let newNav: MenuNavItem[] = generatorNav(res.data.nav);

            dispath(setNavAndAuthoritys({ nav: newNav, authoritys: res.data.authoritys }));
            dispath(setUserData(res.data.user))
        });
    }

    const generatorNav = (nav: MenuNavItem[]) => {
        let newNav: MenuNavItem[] = [];
        nav.forEach((item: MenuNavItem) => {
            if (item.children && item.children.length) {
                newNav.push({ id: item.id, title: item.title, path: item.path, icon: item.icon, children: generatorNav(item.children) })
            } else {
                newNav.push({ id: item.id, title: item.title, path: item.path, icon: item.icon, children: [] });
            }
        })
        return newNav;
    }

    const handForgetPassword = () => {
        message.info('请联系管理员处理');
    }

    return (
        <LoginContainerStyle>
            <div className={'logo'}>
                <Image className={'logo-img'} preview={false} src={logo} />
                <div>
                    <span className={"logo-title"}>{defaultSettings.title}</span>
                </div>
            </div>
            <div className="form-title">
                {defaultSettings.titleDescription}
            </div>
            <Form
                form={form}
                size="middle"
                className="login-form"
                initialValues={{ remember: true }}
                onFinish={({ username, password, remember, code }) => handleLogin({ username, password, remember, code })}
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
                        autoComplete="off"
                    />
                </Form.Item>

                <Form.Item
                    name={'code'}
                    rules={[
                        {
                            required: true,
                            message: '请输入验证码!'
                        }
                    ]}>
                    <div>
                        <Input
                            placeholder="请输入验证码"
                            prefix={<SafetyCertificateOutlined />}
                            style={{
                                width: '75%',
                                marginRight: 5,
                                padding: '6.5px 11px 6.5px 11px',
                                verticalAlign: 'middle'
                            }}
                            maxLength={5}
                        />
                        <img
                            style={{
                                width: '23%',
                                height: '35px',
                                verticalAlign: 'middle',
                                padding: '0'
                            }}
                            src={imageUrl}
                            onClick={onClickImage}
                            alt=""
                        />
                    </div>
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