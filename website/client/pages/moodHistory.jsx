import 'isomorphic-fetch';

import React from 'react';

import MoodTracker from '../components/moodTracker.jsx';
import MoodGraph from '../components/moodGraph.jsx';

import Cookies from 'universal-cookie';
const cookies = new Cookies();

import moment from 'moment';

import { convertFromNum } from '../utils/dateUtils.jsx';

export default class MoodHistory extends React.Component {
    constructor(props) {
        super(props);
        let current, weekAverages, monthAverages;
        if(props.staticContext) {
            current = props.staticContext.current;
            weekAverages = props.staticContext.weekAverages;
            monthAverages = props.staticContext.monthAverages;
        } else if(window.initialData !== undefined) {
            current = window.initialData.current;
            weekAverages = window.initialData.weekAverages;
            monthAverages = window.initialData.monthAverages;
        }
        this.state = {
            current: current,
            weekAverages: weekAverages,
            monthAverages: monthAverages
        }
    }

    static getInitialData(req) {
        const { session } = req.cookies;
        if(session != undefined) {
            return fetch('http://api.csed.test/mood/' + session).then(res => res.json());
        }
    }

    componentDidMount() {
        if(this.state.entries === undefined) {
            fetch('http://api.csed.test/mood/' + cookies.get('session')).then(res => res.json()).then(res => {
                this.setState({ current: res.current, weekAverages: res.weekAverages, monthAverages: res.monthAverages });
            });
        }  
    }

    renderGraphs() {
        let formattedCurrent = []
        let formattedmonthAverages = []
        let formattedWeekAverages = []
        const { current, monthAverages, weekAverages } = this.state;
        if(current !== undefined) {
            current.map(entry => {
                formattedCurrent.push({ x: moment(entry.recordedAt).format('ddd D'), y: entry.score });
            });
            monthAverages.map(entry => {
                formattedmonthAverages.push({ x: convertFromNum(entry.month) + ' ' + entry.year, y: entry.average });
            });
            weekAverages.map(entry => {
                formattedWeekAverages.push({ x: entry.week + ' ' + convertFromNum(entry.month), y: entry.average });
            });
            formattedCurrent.reverse();
            return (
                <React.Fragment>
                    <h3 className="text-center">Last 14 days</h3>
                    <MoodGraph data={formattedCurrent} lineColour={"#c43a31"} />
                    <hr></hr>
                    <h3 className="text-center">Weekly averages (last 14 weeks)</h3>
                    <MoodGraph data={formattedWeekAverages} lineColour={"#00E676"} />
                    <hr></hr>
                    <h3 className="text-center">Monthly averages (last 12 months)</h3>
                    <MoodGraph data={formattedmonthAverages} lineColour={"#18FFFF"} />
                </React.Fragment>
            );
        }
        return <h3>No history to display</h3>
    }

    onMoodSet() {
        window.location.href = '/mood-history';
    }

    render() {
        return (
            <div className="container" style={{ marginTop: 20 }}>
                <MoodTracker {...this.props} onSet={this.onMoodSet} />
                <h1 style={{ marginTop: 5 }}>Mood History</h1>
                <hr></hr>
                {this.renderGraphs()}
            </div>
        )
    }
}