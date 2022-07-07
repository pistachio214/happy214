import styled from "styled-components";

export const HappyTableContainer = styled.div`

    .action-container{
        padding-bottom: 5px;
        display: flex;
        justify-content: space-between;

        .search-container {
            .ant-form-item {
                margin-bottom: 10px;
            }
        }

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