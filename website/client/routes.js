import Index from './pages/index.jsx';
import Login from './pages/login.jsx';
import CreateAccount from './pages/createAccount.jsx';

import NotFound from './pages/notFound.jsx';

const routes = [
    {
        path: "/",
        exact: true,
        component: Index,
        loggedIn: true
    },
    {
        path: "/login",
        component: Login,
        loggedIn: false
    },
    {
        path: "/create-account",
        component: CreateAccount,
        loggedIn: false
    },
    {
        path: "*",
        component: NotFound
    }
]

export default routes;