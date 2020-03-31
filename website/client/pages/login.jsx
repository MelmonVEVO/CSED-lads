import 'isomorphic-fetch';
import React from 'react';

import { TextBox } from '../components/textBox.jsx';
import { FormSubmitBtn } from '../components/formSubmitBtn.jsx';

const emailRegex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

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
        e.preventDefault();
        this.setState({ password: "" });
    }

    render() {
        const { password } = this.state;
        return (
            <div className="container text-center margin-top">
                <h1>Login</h1>
                <div className="spacer"/>
                <div className="container-sm">
                    <form onSubmit={this.login}>
                        <TextBox type="text" text="Email" onChange={this.changeEmail} />
                        <TextBox type="password" text="Password" onChange={this.changePassword} value={password} />
                        <FormSubmitBtn colour="success" text="Login" />
                    </form>
                </div>
            </div>
        );
    }
}

export default Login;