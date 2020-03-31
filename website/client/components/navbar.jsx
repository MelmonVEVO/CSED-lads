import React from 'react';

import { Link } from 'react-router-dom';
import { NavItem } from './navItem.jsx';

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
                        <NavItem linkUrl="/login" text="Login" />
                    </div>
                </div>
            </nav>
        );
    }
}

export default Navbar;