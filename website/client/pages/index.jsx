import 'isomorphic-fetch';

import { Link } from 'react-router-dom';

import Moment from 'react-moment';
import Countdown from 'react-countdown-now';

import MoodTracker from '../components/moodTracker.jsx';
import PillAlert from '../components/pillAlert.jsx';

import Cookies from 'universal-cookie';
const cookies = new Cookies();

import React from 'react';

function renderDays(days) {
    if (days > 1) {
        return <span>{days} days away</span>
    }
    return <span>Tommorow</span>
}

const countdownRenderer = ({ days, completed }) => {
    if (completed) {
        return <span>Today</span>
    }
    return renderDays(days);
}

class Index extends React.Component {
    constructor(props) {
        super(props);
        let settings, nextPeriodDate;
        if (props.staticContext) {
            settings = props.staticContext.settings;
            if (settings.periodTracking) {
                nextPeriodDate = props.staticContext.next;
            }
        } else {
            settings = window.initialData.settings;
            if (settings !== undefined) {
                if (settings.periodTracking) {
                    nextPeriodDate = window.initialData.next;
                }
            }
        }
        this.state = {
            settings: settings,
            nextPeriodDate: nextPeriodDate
        }
    }

    static getInitialData(req) {
        return new Promise((resolve, reject) => {
            const session = req.cookies.session;
            fetch('http://api.csed.test/accounts/settings/' + session)
                .then(settingsRes => settingsRes.json())
                .then(settingsRes => {
                    if (settingsRes.success) {
                        const { settings } = settingsRes;
                        if (settings.periodTracking) {
                            fetch('http://api.csed.test/period-tracker/prediction/' + session)
                                .then(predictionRes => predictionRes.json())
                                .then(predictionRes => {
                                    const { next } = predictionRes;
                                    if (predictionRes.success) {
                                        resolve({ settings, next });
                                    } else {
                                        reject();
                                    }
                                });
                        } else {
                            resolve({ settings });
                        }
                    } else {
                        reject();
                    }
                });
        });
    }

    componentDidMount() {
        if (this.state.nextPeriodDate === undefined) {
            const session = cookies.get('session');
            fetch('http://api.csed.test/accounts/settings/' + session)
                .then(settingsRes => settingsRes.json())
                .then(settingsRes => {
                    if (settingsRes.success) {
                        const { settings } = settingsRes;
                        if (settings.periodTracking) {
                            fetch('http://api.csed.test/period-tracker/prediction/' + session)
                                .then(predictionRes => predictionRes.json())
                                .then(predictionRes => {
                                    if (predictionRes.success) {
                                        this.setState({ settings: settings, nextPeriodDate: predictionRes.next });
                                    }
                                });
                        } else {
                            this.setState({ settings: settings });
                        }
                    }
                });
        }
    }

    renderNextPeriodDate() {
        const { nextPeriodDate } = this.state;
        if(this.state.settings !== undefined) {
            const { periodTracking } = this.state.settings;
            if (periodTracking === false) {
                return (
                    <div className="jumbotron">
                        <h1 className="display-4">Welcome!</h1>
                        <hr></hr>
                        <p>You currently don't have period tracking enabled. Enable it on the settings page.</p>
                    </div>
                )
            }    
        }
        if (nextPeriodDate !== undefined) {
            return (
                <div className="jumbotron">
                    <p className="lead">Your next period is on:</p>
                    <h1 className="display-4">
                        <Moment format="ddd D MMM">{nextPeriodDate}</Moment>
                    </h1>
                    <p><Countdown date={nextPeriodDate} renderer={countdownRenderer}></Countdown></p>
                    <hr className="my-4" />
                    <Link className="btn btn-primary btn-lg" to="/update-data" style={{ marginBottom: 0 }}>Change data</Link>
                </div>
            );
        }
        return (
            <div className="jumbotron">
                <h1 className="display-4">You haven't uploaded any data...</h1>
                <hr className="my-4" />
                <p className="lead">
                    <Link className="btn btn-primary btn-lg" to="/update-data">Enter data</Link>
                </p>
            </div>
        );
    }

    renderPillAlert() {
        if(this.state.settings !== undefined) {
            const { pillTracking } = this.state.settings;
            if (pillTracking) {
                return <PillAlert />
            }    
        }
        return <div />
    }

    onMoodSet = () => {
        this.props.history.push('/mood-history?newEntry=true');
    }

    render() {
        return (
            <div className="container" style={{ marginTop: 25 }}>
                {this.renderPillAlert()}
                {this.renderNextPeriodDate()}
                <MoodTracker {...this.props} onSet={this.onMoodSet} />
            </div>
        )
    }
}

export default Index;