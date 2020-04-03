import Index from './pages/index.jsx';
import Login from './pages/login.jsx';
import CreateAccount from './pages/createAccount.jsx';
import UpdateData from './pages/updateData.jsx';

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
        path: "/update-data",
        component: UpdateData,
        loggedIn: true
    }
]

export default routes;