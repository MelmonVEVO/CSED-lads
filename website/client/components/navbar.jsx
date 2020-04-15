import React from 'react';

import { Link } from 'react-router-dom';

class Navbar extends React.Component {    
    render() {
        return (
            <nav className="navbar navbar-expand-md navbar-dark">
                <div className="container">
                    <Link className="navbar-brand" to="/">
                        Tsunami
                    </Link>
                    <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    <div className="collapse navbar-collapse justify-content-end" id="navbarCollapse">
                        <div className="nav-item">
                            <Link to="/mood-history">Mood History</Link>
                            <Link to="/settings">Settings</Link>
                            <a href="/logout">Logout</a>
                        </div>
                    </div>
                </div>
            </nav>
        );
    }
}

export default Navbar;