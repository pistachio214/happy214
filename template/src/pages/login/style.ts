import styled from "styled-components";

import backgroundImage from '@/assets/images/background.svg';

export const LoginContainerStyle = styled.div`
    width: 100%;
    height: 100%;

    position: fixed;

    display: flex;
    display: -webkit-flex; /* Safari */
    flex-direction: column;
    justify-content: center;
    align-items: center;

    background-image: url(${backgroundImage});
    background-repeat: no-repeat;
    background-size: 100%;

    .logo {
        height: 50px;
        line-height: 50px;
        display: flex;
        display: -webkit-flex;
        flex-direction: row;

        .logo-img {
            height: 44px;
        }

        .logo-title {
            padding: 0 0 0 10px;
            color: rgba(0,0,0,0.85);
            font-weight: 600;
            font-size: 33px;
            font-family: Avenir, 'Helvetica Neue', Arial, Helvetica, sans-serif;
        }
    }

    .form-title {
        margin-top: 12px;
        margin-bottom: 40px;
        color: rgba(0,0,0,0.45);
        font-size: 14px;
    }

    .login-form {
        min-width: 400px;
    }

    .login-form-forget {
        float: right;
    }

    .ant-col-rtl .login-form-forget {
        float:  left;
    }

    .login-form-button {
        width: 100%;
    }

`