import React from 'react';

import { Link } from 'react-router-dom';

export const NavItem = (props) => {
    const { linkUrl, text } = props;
    return (
        <div className="nav-item">
            <Link to={linkUrl}>
                {text}
            </Link>
        </div>
    );
};