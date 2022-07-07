import styled from "styled-components";

export const AppLayoutContainer = styled.div`
    height: 100%;

    .ant-layout {
        height: 100%;
    }

    .ant-layout-sider-children .logo {
        height: 32px;
        margin: 16px;
        text-align: center;
        
        line-height: 32px;

        .logo-container {
            display: flex;
            flex-direction: row;

            .system-title {
                padding-left: 3px;

                h1 {
                    color: #fff;
                }
            }

            .logo-img {
                height: 32px;
            }
        }
    }

    .site-layout-sub-header-background {
        padding-left: 20px;
        display: flex;
        align-items: center;
        background: #fff;
        justify-content: space-between;

        .user-container {
            padding-right: 20px;

            &:hover{
                background: #eee;
            }

            .username-container {
                display: flex;

                .username-nickname {
                    font-weight: bold;
                    font-size: 14px;
                    margin-left: 20px;
                }

                .username-avatar {
                    height: 64px;
                    line-height: 64px;
                    margin-left: 10px;

                    display: flex;
                    justify-content: center;
                    align-items: center;
                    img {
                        height: 30px;
                    }
                }
                
            }
        }

    }

    .site-layout-background {
        background: #fff;
    }
`