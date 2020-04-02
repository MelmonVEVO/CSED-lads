
import 'isomorphic-fetch';

import { Link } from 'react-router-dom';

import Moment from 'react-moment';
import Countdown from 'react-countdown-now';

import Cookies from 'universal-cookie';
const cookies = new Cookies();

import React from 'react';

function renderDays(days) {
    if(days > 1) {
        return <span>{days} days away</span>
    }
    return <span>Tommorow</span>
}

function renderButton(text, type) {
    return <Link className={"btn btn-" + type + " btn-lg"} to="/update-data" style={{ marginBottom: 0 }}>{text}</Link>
}

const countdownRenderer = ({ days, completed }) => {
    if(completed) {
        return (
            <React.Fragment>
                <p className="my-4">Data is out of sync</p>
                <hr className="my-4" />
                {renderButton('Update data', 'danger')}
            </React.Fragment>
        );
    }
    return (
        <React.Fragment>
            {renderDays(days)}
            <hr className="my-4"/>
            {renderButton('Change data', 'primary')}
        </React.Fragment>
    );
}

class Index extends React.Component {
    constructor(props) {
        super(props);
        let nextPerioDate;
        if (props.staticContext) {
            nextPerioDate = props.staticContext.next;
        } else {
            nextPerioDate = window.initialData.next;
        }
        this.state = {
            nextPerioDate: nextPerioDate
        }
    }

    static getInitialData(req) {
        return fetch('http://api.csed.test/period-tracker/prediction/' + req.cookies.session).then(res => res.json())
    }

    componentDidMount() {
        if (this.state.nextPerioDate === undefined) {
            fetch('http://api.csed.test/period-tracker/prediction/' + cookies.get('session')).then(res => res.json()).then(res => {
                this.setState({ nextPerioDate: res.next });
            });
        }
    }

    render() {
        const { nextPerioDate } = this.state;
        if(nextPerioDate !== undefined) {
            return (
                <div className="container" style={{ marginTop: 25 }}>
                    <div className="jumbotron">
                        <p className="lead">Your next period is on:</p>
                        <h1 className="display-4">
                            <Moment format="D MMM">{nextPerioDate}</Moment>
                        </h1>
                        <p><Countdown date={nextPerioDate} renderer={countdownRenderer}></Countdown></p>
                    </div>
                </div>
            );
        }
        return (
            <div className="container" style={{ marginTop: 25 }}>
                <div className="jumbotron">
                    <h1 className="display-4">You haven't uploaded any data...</h1>
                    <hr className="my-4"/>
                    <p className="lead">
                        <Link className="btn btn-primary btn-lg" to="/update-data">Enter data</Link>
                    </p>
                </div>
            </div>
        );
    }
}

export default Index;