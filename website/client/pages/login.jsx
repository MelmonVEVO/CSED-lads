import 'isomorphic-fetch';
import React from 'react';

import { Link } from 'react-router-dom';

import { TextBox } from '../components/textBox.jsx';
import { FormSubmitBtn } from '../components/formSubmitBtn.jsx';

const emailRegex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

function validate(email, password) {
    return {
        email: !emailRegex.test(email),
        password: password.length === 0
    }
}

class Login extends React.Component {
    state = {
        email: "",
        password: "",
        errors: {
            email: false,
            password: false
        }
    }

    changeEmail = e => {
        this.setState({ email: e.target.value });
    }

    changePassword = e => {
        this.setState({ password: e.target.value });
    }

    login = e => {
        const { email, password } = this.state;
        const validateResults = validate(email, password);
        if(!validateResults.email && !validateResults.password) {
            this.setState({ errors: { email: false, password: false } });
            // Send login request
        } else {
            this.setState({ errors: { email: validateResults.email, password: validateResults.password } });
        }
        e.preventDefault();
    }

    render() {
        const { password } = this.state;
        return (
            <div className="container text-center margin-top">
                <h1>Login</h1>
                <div className="spacer"/>
                <div className="container-sm">
                    <form onSubmit={this.login}>
                        <TextBox type="text" text="Email" onChange={this.changeEmail} error={this.state.errors.email} />
                        <TextBox type="password" text="Password" onChange={this.changePassword} value={password} error={this.state.errors.password} />
                        <FormSubmitBtn colour="success" text="Login" />
                    </form>
                    <Link to="/create-account"><b>Create an account</b></Link>
                </div>
            </div>
        );
    }
}

export default Login;