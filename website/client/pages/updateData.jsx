import 'isomorphic-fetch';

import React from 'react';
import { Form } from 'react-bootstrap';

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
        } else {
            currentData = window.initialData.data;
        }
        this.state = {
            loading: false,
            currentData: currentData
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
                    this.setState({ loading: false, currentData: res.data });
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

    handleDateChange = e => {
        console.log(e.target.value);
    }

    renderForm() {
        const { currentData } = this.state;
        return (
            <Form>
                <Form.Label>Start date</Form.Label>
                <Form.Control type="date" defaultValue={defaultDateValue(currentData)} />
                <Form.Label style={{ marginTop: 10 }}>Cycle length</Form.Label>
                <Form.Control type="number" min={1} max={31} defaultValue={defaultCycleLenValue(currentData)} />
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
                <Link className="btn btn-dark btn-lg" to="/" style={{ marginTop: 10 }}>Cancel</Link>
            </div>
        );
    }
}

export default UpdateData;