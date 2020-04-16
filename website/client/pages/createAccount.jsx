import 'isomorphic-fetch';

import React from 'react';

import { Link } from 'react-router-dom';

import { Form } from 'react-bootstrap';

import { TextBox } from '../components/textBox.jsx';
import { FormSubmitBtn } from '../components/formSubmitBtn.jsx';

import emailValidator from '../utils/emailValidator.jsx';

function validate(email, firstName, lastName, password) {
    return {
        email: emailValidator(email),
        firstName: firstName.length !== 0,
        lastName: lastName.length !== 0,
        password: password.length !== 0
    }
}

class CreateAccount extends React.Component {
    state = {
        email: "",
        firstName: "",
        lastName: "",
        password: "",
        pillTracking: false,
        errors: {
            email: false,
            firstName: false,
            lastName: false,
            password: false,
            exists: false,
            unknown: false
        },
        loading: false
    }

    createAccount = e => {
        const { email, firstName, lastName, password, pillTracking } = this.state;
        const validateResults = validate(email, firstName, lastName, password);
        if(validateResults.email && validateResults.firstName && validateResults.lastName && validateResults.password) {
            this.setState({ loading: true });
            fetch('http://localhost:3000/accounts/create', {
                method: "POST",
                headers: {
                    "content-type": "application/json"
                },
                body: JSON.stringify({
                    email: email,
                    firstName: firstName,
                    lastName: lastName,
                    password: password,
                    pillTracking: pillTracking,
                    periodTracking: !pillTracking
                })
            }).then(res => res.json()).then(res => {
                if(res.success) {
                    this.props.history.push('/login?newAccount=true');
                } else {
                    this.setState({ password: "", email: "", firstName: "", lastName: "", errors: { exists: true } });
                }
                this.setState({ loading: false });
            }).catch(() => {
                this.setState({ password: "", errors: { unknown: true } });
            });
        } else {
            this.setState({
                errors: {
                    exists: false,
                    unknown: false,
                    email: !validateResults.email,
                    firstName: !validateResults.firstName,
                    lastName: !validateResults.lastName,
                    password: !validateResults.password
                }
            })
        }
        e.preventDefault();
    }
    
    changeEmail = e => {
        this.setState({ email: e.target.value });
    }

    changeFirstName = e => {
        this.setState({ firstName: e.target.value });
    }

    changeLastName = e => {
        this.setState({ lastName: e.target.value });
    }

    changePassword = e => {
        this.setState({ password: e.target.value });
    }

    pillTrackingEnabled = e => {
        const value = e.target.value;
        if(value === 'Yes') {
            this.setState({ pillTracking: true });
        } else if(value === 'No') {
            this.setState({ pillTracking: false });
        }
    }

    renderError() {
        const { exists, unknown } = this.state.errors;
        if(exists) {
            return <div className="alert alert-danger">An account with that email already exists</div>
        }
        if(unknown) {
            return <div className="alert alert-danger">An unknown error occured</div>
        }
        return <div/>
    }

    render() {
        const { loading } = this.state;
        return (
            <div className="container text-center margin-top">
                <h1>Create Account</h1>
                <div className="spacer"/>
                <div className="container-sm">
                    {this.renderError()}
                    <form onSubmit={this.createAccount}>
                        <TextBox type="text" text="Email" onChange={this.changeEmail} 
                            error={this.state.errors.email} value={this.state.email} />
                        <TextBox type="text" text="First name" onChange={this.changeFirstName}
                            error={this.state.errors.firstName} value={this.state.firstName} />
                        <TextBox type="text" text="Last name" onChange={this.changeLastName}
                            error={this.state.errors.lastName} value={this.state.lastName} />
                        <TextBox type="password" text="password" onChange={this.changePassword} 
                            error={this.state.errors.password} value={this.state.password} />
                        <Form.Group>
                            <Form.Label>Do you want pill tracking?</Form.Label>
                            <Form.Control as="select" defaultValue="No" onChange={this.pillTrackingEnabled}>
                                <option>Yes</option>
                                <option>No</option>
                            </Form.Control>
                        </Form.Group>
                        <FormSubmitBtn colour="success" text="Create account" disabled={loading} block={true} />
                    </form>
                    <Link to="/login"><b>Already have an account?</b></Link>
                </div>
            </div>  
        );     
    }
}

export default CreateAccount;