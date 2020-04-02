
import 'isomorphic-fetch';

import { Link } from 'react-router-dom';

import Moment from 'react-moment';
import Countdown from 'react-countdown-now';

import Cookies from 'universal-cookie';
const cookies = new Cookies();

import React from 'react';

const countdownRenderer = ({ days, completed }) => {
    if(completed) {
        return <p>Change the date</p>
    }
    return (
        <React.Fragment>
            <span>{days} days away</span>
            <hr className="my-4"/>
            <Link className="btn btn-primary btn-lg" to="/update-data" style={{ marginBottom: 0 }}>Change data</Link>
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
                        <p className="lead">Your next period is due on:</p>
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