import React from 'react';

import { Form } from 'react-bootstrap';

export default class Settings extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="container">
                <h1>Settings</h1>
                <hr></hr>
                <Form>
                </Form>
            </div>
        );
    }
}