import styled from "styled-components";

export const MenuContainer = styled.div`

    .menu-type-button-success {
        background-color: #f0f9eb;
        border-color: #e1f3d8;
        color: #67c23a;
    }

    .menu-type-button-info {
        background-color: #f4f4f5;
        border-color: #e9e9eb;
        color: #909399;
    }

    .action-container {
        display: flex;
        flex-direction: row-reverse;
        margin-bottom: 10px;

        .common-btn-container {
            min-width: 170px;
            display: flex;
            justify-content: end;

            button {
                margin-right: 5px;
            }

            button:last-child {
                margin-right: 0;
            }
        }
    }

`