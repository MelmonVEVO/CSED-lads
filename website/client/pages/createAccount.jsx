import React from 'react';

import { Link } from 'react-router-dom';

import { TextBox } from '../components/textBox.jsx';
import { FormSubmitBtn } from '../components/formSubmitBtn.jsx';

class CreateAccount extends React.Component {
    render() {
        return (
            <div className="container text-center margin-top">
                <h1>Create Account</h1>
                <div className="spacer"/>
                <div className="container-sm">
                    <form>
                        <TextBox type="text" text="Email" />
                        <TextBox type="text" text="First name" />
                        <TextBox type="text" text="Last name" />
                        <TextBox type="password" text="password" />
                        <FormSubmitBtn colour="success" text="Create account" />
                    </form>
                    <Link to="/login"><b>Already have an account?</b></Link>
                </div>
            </div>  
        );     
    }
}

export default CreateAccount;