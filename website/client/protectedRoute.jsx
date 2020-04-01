import React from 'react';
import { Route, Redirect } from 'react-router-dom';
import auth from './auth.jsx';

import Navbar from './components/navbar.jsx';

export const ProtectedRoute = ({ component: Component, ...rest }) => {
    return (
        <Route {...rest} render={
            (props) => {
                if(auth.isAuthenticated()) {
                    return (
                        <React.Fragment>
                            <Navbar {...props} />
                            <Component {...props} />
                        </React.Fragment>
                    );
                } else {
                    return <Redirect to="/login" />
                }
            }
        } />
    );
}