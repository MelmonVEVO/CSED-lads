import React from 'react';

import { Form } from 'react-bootstrap';
import { FormSubmitBtn } from '../components/formSubmitBtn.jsx';

import Cookies from 'universal-cookie';
const cookies = new Cookies();

function convert(human) {
    return human === 'Yes' ? true : false;
}

export default class Settings extends React.Component {
    constructor(props) {
        super(props);
        let settings;
        if(props.staticContext) {
            settings = props.staticContext.settings;
        } else if(window.initialData) {
            settings = window.initialData.settings;
        }
        this.state = {
            settings: settings
        }
    }

    static getInitialData(req) {
        const session = req.cookies.session;
        return fetch('http://api.csed.test/accounts/settings/' + session)
            .then(res => res.json());
    }

    componentDidMount() {
        if(this.state.settings === undefined) {
            const session = cookies.get('session');
            fetch('http://api.csed.test/accounts/settings/' + session)
                .then(res => res.json())
                .then(res => {
                    if(res.success) {
                        this.setState({ settings: res.settings });
                    }
                });    
        }
    }

    update = e => {
        const { pillTracking, periodTracking } = this.state.settings;
        console.log(pillTracking);
        console.log(periodTracking);
        fetch('http://api.csed.test/accounts/settings/update', {
            credentials: "include",
            method: "POST",
            headers: {
                "content-type": "application/json"
            },
            body: JSON.stringify({
                "pillTracking": pillTracking,
                "periodTracking": periodTracking
            })
        }).then(res => res.json()).then(res => {
            if(res.success) {
                window.location.href = '/';
            }
        });
        e.preventDefault();
    }

    pillTrackingChange = e => {
        let settings = this.state.settings;
        settings.pillTracking = convert(e.target.value);
        this.setState({ settings: settings });
    }

    periodTrackingChange = e => {
        let settings = this.state.settings;
        settings.periodTracking = convert(e.target.value);
        this.setState({ settings: settings });
    }
    
    render() {
        if(this.state.settings !== undefined) {
            const { periodTracking, pillTracking } = this.state.settings;
            return (
                <div className="container" style={{ marginTop: 20 }}>
                    <h1>Settings</h1>
                    <hr></hr>
                    <Form onSubmit={this.update}>
                        <Form.Group>
                            <Form.Label>Period tracking</Form.Label>
                            <Form.Control as="select" defaultValue={periodTracking ? "Yes" : "No"} onChange={this.periodTrackingChange}>
                                <option>Yes</option>
                                <option>No</option>
                            </Form.Control>
                        </Form.Group>
                        <Form.Group>
                            <Form.Label>Pill tracking</Form.Label>
                            <Form.Control as="select" defaultValue={pillTracking ? "Yes" : "No"} onChange={this.pillTrackingChange}>
                                <option>Yes</option>
                                <option>No</option>
                            </Form.Control>
                        </Form.Group>
                        <FormSubmitBtn text="Update" colour="warning" block={false} />
                    </Form>
                </div>
            );
        }
        return <div/>
    }
}