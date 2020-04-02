import 'isomorphic-fetch';

import React from 'react';

import Cookies from 'universal-cookie';
const cookies = new Cookies();

import { Link } from 'react-router-dom';

import { TextBox } from '../components/textBox.jsx';
import { FormSubmitBtn } from '../components/formSubmitBtn.jsx';

import auth from '../auth.jsx';

import emailValidator from '../utils/emailValidator.jsx';

function validate(email, password) {
    return {
        email: emailValidator(email),
        password: password.length !== 0
    }
}

class Login extends React.Component {
    state = {
        email: "",
        password: "",
        errors: {
            email: false,
            password: false,
            login: false,
            unknown: false
        },
        loading: false
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
        if(validateResults.email && validateResults.password) {
            this.setState({ errors: { email: false, password: false }, loading: true });
            fetch('http://localhost:3000/accounts/login', {
                method: "POST",
                headers: {
                    "content-type": "application/json"
                },
                body: JSON.stringify({
                    email: email,
                    password: password
                })
            }).then(res => res.json()).then(res => {
                if(res.success) {
                    cookies.set('session', res.sessionID, { domain: ".csed.test" });
                    auth.setAuth(true);
                    this.props.history.push('/');
                } else {
                    this.setState({ errors: { login: true }, password: "", loading: false });
                }
            }).catch(() => {
                this.setState({ errors: { unknown: true }, password: "" });
            });
        } else {
            this.setState({ errors: { login: false, unknown: false, email: !validateResults.email, password: !validateResults.password } });
        }
        e.preventDefault();
    }

    renderLoginError() {
        const { login, unknown } = this.state.errors;
        if(login) {
            return <div className="alert alert-danger">Incorrect username or password</div>;
        }
        if(unknown) {
            return <div className="alert alert-danger">An unknown error has occured</div>;
        }
        return <div />
    }

    render() {
        const { password, loading } = this.state;
        return (
            <div className="container text-center margin-top">
                <h1>Login</h1>
                <div className="spacer"/>
                <div className="container-sm">
                    {this.renderLoginError()}
                    <form onSubmit={this.login}>
                        <TextBox type="text" text="Email" onChange={this.changeEmail} error={this.state.errors.email} />
                        <TextBox type="password" text="Password" onChange={this.changePassword} value={password} error={this.state.errors.password} />
                        <FormSubmitBtn colour="success" text="Login" disabled={loading} />
                    </form>
                    <Link to="/create-account"><b>Create an account</b></Link>
                </div>
            </div>
        );
    }
}

export default Login;