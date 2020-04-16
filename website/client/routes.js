import Index from './pages/index.jsx';
import Login from './pages/login.jsx';
import CreateAccount from './pages/createAccount.jsx';
import UpdateData from './pages/updateData.jsx';
import MoodHistory from './pages/moodHistory.jsx';
import Settings from './pages/settings.jsx';
import Notes from './pages/notes.jsx';

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
    },
    {
        path: "/mood-history",
        component: MoodHistory,
        loggedIn: true
    },
    {
        path: "/settings",
        component: Settings,
        loggedIn: true
    },
    {
        path: "/notes",
        component: Notes,
        loggedIn: true
    }
]

export default routes;