import React, {
    Suspense,
} from "react";
import {
    BrowserRouter,
    Route,
    Routes,
    Navigate,
} from "react-router-dom";
import { PageLoading } from "@ant-design/pro-layout";

import HappyLayout from "@/component/happy-layout";
import router from "@/router";
import { IRouter } from "@/types/IRouter";
import RouterGuards from "@/component/happy-router/router-guards";

const Index: React.FC = () => {

    const generateRouter = (routerList?: IRouter[]) => {
        return (
            <>
                {
                    routerList?.map((item: IRouter, index: number) => {
                        if (item.children !== undefined) {
                            return (
                                <Route key={index} path={item.path} element={<HappyLayout />}>
                                    {generateRouter(item.children)}
                                </Route>
                            )
                        }
                        return (
                            <Route
                                key={index}
                                // auth={item.auth ?? false}
                                path={item.path}
                                element={item.component}
                            />
                        )
                    })
                }
            </>
        );
    }

    return (
        <>
            <Suspense fallback={<PageLoading />}>
                <BrowserRouter>
                    <Routes>
                        <Route path='/' element={<Navigate to={'/dashboard'} />} />
                        <Route path='/' element={<RouterGuards />}>
                            {
                                generateRouter(router)
                            }
                        </Route>
                    </Routes>
                </BrowserRouter>
            </Suspense>
        </>
    );
}

export default Index;