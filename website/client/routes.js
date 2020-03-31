import Index from './pages/index.jsx';
import Login from './pages/login.jsx';

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
    }
]

export default routes;