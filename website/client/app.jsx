import React from 'react';
import { Switch, Route } from 'react-router';

import routes from './routes';

import Navbar from './components/navbar.jsx';

const App = () => {
    return (
        <React.Fragment>
            <Navbar />
            <Switch>
                {routes.map((route, i) => <Route key={i} {...route} />)}
            </Switch>
        </React.Fragment>
    );
}

export default App;