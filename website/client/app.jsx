import React from 'react';
import { Switch, Route } from 'react-router';
import { ProtectedRoute } from './protectedRoute.jsx';

import NotFound from './pages/notFound.jsx';

import routes from './routes';

import auth from './auth.jsx';

function getRoutes(loggedIn) {
    let checkedRoutes = [];
    routes.map(route => {
        if(route.loggedIn == loggedIn) {
            checkedRoutes.push(route);
        }
    });
    return checkedRoutes;
}

const App = (isAuthenticated) => {
    auth.setAuth(isAuthenticated);
    return (
        <React.Fragment>
            <Switch>
                {getRoutes(false).map((route, i) => <Route key={i} {...route} />)}
                {getRoutes(true).map((route, i) => <ProtectedRoute key={i} {...route} />)}
                <Route path="*"><NotFound /></Route>
            </Switch>
        </React.Fragment>
    );
}

export default App;