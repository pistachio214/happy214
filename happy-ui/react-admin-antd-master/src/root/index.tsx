import React, { lazy, Suspense } from "react";
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import { PageLoading } from '@ant-design/pro-layout';

const Index = lazy(() => import('@/pages/index'));
const Login = lazy(() => import('@/pages/login'));

const Root: React.FC = () => {
    return (
        <>
            <Suspense fallback={<PageLoading />}>
                <BrowserRouter>
                    <Switch>
                        <Route path={'/'} exact component={Index} />
                        <Route path={'/login'} exact component={Login} />
                    </Switch>
                </BrowserRouter>
            </Suspense>
        </>
    );
}

export default Root;