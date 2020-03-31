import React from 'react';

import { TextBox } from '../components/textBox.jsx';
import { FormSubmitBtn } from '../components/formSubmitBtn.jsx';

class Login extends React.Component {
    render() {
        return (
            <div className="container text-center margin-top">
                <h1>Login</h1>
                <div className="spacer"></div>
                <div className="container-sm">
                    <form>
                        <TextBox type="text" text="Email" />
                        <TextBox type="password" text="Password" />
                        <FormSubmitBtn colour="success" text="Login" />
                    </form>
                </div>
            </div>
        );
    }
}

export default Login;