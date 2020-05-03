import 'isomorphic-fetch';

import React from 'react';
import { Form } from 'react-bootstrap';
import { FormSubmitBtn } from '../components/formSubmitBtn.jsx';

import { Link } from 'react-router-dom';

import moment from 'moment';

import Cookies from 'universal-cookie';
const cookies = new Cookies();

function defaultDateValue(currentData) {
    if (currentData === undefined) {
        return moment(new Date()).format('YYYY-MM-DD');
    }
    return currentData.started;
}

function defaultCycleLenValue(currentData) {
    if(currentData === undefined) {
        return 1;
    }
    return currentData.cycleLength;
}

class UpdateData extends React.Component {
    constructor(props) {
        super(props);
        let currentData;
        if (props.staticContext) {
            currentData = props.staticContext.data;
        } else if(window.initialData !== undefined) {
            currentData = window.initialData.data;
            delete window.initialData;
        }
        this.state = {
            loading: false,
            currentData: currentData,
            newStartDate: defaultDateValue(currentData),
            newCycleLen: defaultCycleLenValue(currentData),
            errors: {
                startDate: false,
                cycleLen: false
            }
        }
    }

    static getInitialData(req) {
        return fetch('http://api.csed.test/period-tracker/' + req.cookies.session).then(res => res.json());
    }

    componentDidMount() {
        if (this.state.currentData === undefined) {
            this.setState({ loading: true });
            fetch('http://api.csed.test/period-tracker/' + cookies.get('session'))
                .then(res => res.json()).then(res => {
                    const { data } = res;
                    this.setState({ loading: false, currentData: res.data, newStartDate: defaultDateValue(data), newCycleLen: defaultCycleLenValue(data) });
                });
        }
    }

    renderHeader() {
        const { currentData } = this.state;
        if (currentData === undefined) {
            return (
                <React.Fragment>
                    <div className="alert alert-success" role="alert" style={{ marginTop: 20 }}>
                        <strong>Welcome!</strong><br></br>
                        Fill out the fields below, then hit the <strong>update</strong> button - it's that simple.
                    </div>
                    <h1>Insert data</h1>
                </React.Fragment>
            );
        }
        return <h1>Update data</h1>
    }

    formSubmitBtn() {
        const { currentData } = this.state;
        let text = "Update";
        if(currentData === undefined) {
            text = "Insert";
        }
        return <FormSubmitBtn colour="success" text={text} block={false} />
    }

    getRequestUrl() {
        const { currentData } = this.state;
        let url = "http://api.csed.test/period-tracker/";
        if(currentData === undefined) {
            url += "insert";
        } else {
            url += "update";
        }
        return url;
    }

    formSubmit = e => {
        const { newStartDate, newCycleLen } = this.state;
        const validStartDate = newStartDate !== "";
        const validCycleLen = newCycleLen > 0;
        if(validStartDate && validCycleLen) {
            this.setState({ errors: { startDate: false, cycleLen: false } });
            fetch(this.getRequestUrl(), {
                method: "POST",
                credentials: "include",
                headers: {
                    "content-type": "application/json"
                },
                body: JSON.stringify({
                    "started": newStartDate,
                    "cycleLength": newCycleLen
                })
            }).then(res => res.json()).then(res => {
                if(res.success) {
                    window.location.href = '/';
                }
            });
        } else {
            this.setState({ errors: { startDate: !validStartDate, cycleLen: !validCycleLen } });
        }
        e.preventDefault();
    }

    startDateChange = e => {
        this.setState({ newStartDate: e.target.value });
    }

    cycleLenChange = e => {
        this.setState({ newCycleLen: e.target.value });
    }

    formClassName(error) {
        if(error) {
            return "error";
        }
        return "";
    }

    renderForm() {
        const { newStartDate, newCycleLen } = this.state;
        return (
            <Form onSubmit={this.formSubmit}>
                <Form.Label>Start date</Form.Label>
                <Form.Control type="date" defaultValue={newStartDate}
                    onChange={this.startDateChange} className={this.formClassName(this.state.errors.startDate)} />
                <Form.Label style={{ marginTop: 10 }}>Cycle length</Form.Label>
                <Form.Control type="number" min={1} max={31} 
                    defaultValue={newCycleLen} style={{marginBottom: 15}}
                    onChange={this.cycleLenChange} className={this.formClassName(this.state.errors.cycleLen)} />
                <div className="d-inline">
                    {this.formSubmitBtn()}
                    <Link className="btn btn-dark" to="/" style={{ marginLeft: 5 }}>Cancel</Link>
                </div>
            </Form>
        );
    }

    render() {
        const { loading } = this.state;
        if (loading) {
            return <div />;
        }
        return (
            <div className="container" style={{ marginTop: 25 }}>
                {this.renderHeader()}
                <hr></hr>
                {this.renderForm()}                
            </div>
        );
    }
}

export default UpdateData;