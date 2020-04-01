class Auth {
    constructor() {
        this.authenticated = false;
    }

    setAuth(authenticated) {
        this.authenticated = authenticated;
    }

    isAuthenticated() {
        return this.authenticated;
    }
}

export default new Auth();