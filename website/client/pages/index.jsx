
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

const countdownRenderer = ({ days, completed }) => {
    if(completed) {
        return <span>Today</span>
    }
    return renderDays(days);
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
            loading: false,
            nextPerioDate: nextPerioDate
        }
    }

    static getInitialData(req) {
        return fetch('http://api.csed.test/period-tracker/prediction/' + req.cookies.session).then(res => res.json())
    }

    componentDidMount() {
        if (this.state.nextPerioDate === undefined) {
            this.setState({ loading: true });
            fetch('http://api.csed.test/period-tracker/prediction/' + cookies.get('session')).then(res => res.json()).then(res => {
                this.setState({ loading: false, nextPerioDate: res.next });
            });
        }
    }

    render() {
        const { loading, nextPerioDate } = this.state;
        if(loading) {
            return <div/>
        }
        if(nextPerioDate !== undefined) {
            return (
                <div className="container" style={{ marginTop: 25 }}>
                    <div className="jumbotron">
                        <p className="lead">Your next period is on:</p>
                        <h1 className="display-4">
                            <Moment format="ddd D MMM">{nextPerioDate}</Moment>
                        </h1>
                        <p><Countdown date={nextPerioDate} renderer={countdownRenderer}></Countdown></p>
                        <hr className="my-4"/>
                        <Link className="btn btn-primary btn-lg" to="/update-data" style={{ marginBottom: 0}}>Change data</Link>
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