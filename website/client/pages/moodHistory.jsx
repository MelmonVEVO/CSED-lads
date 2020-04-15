import 'isomorphic-fetch';

import React from 'react';

import MoodTracker from '../components/moodTracker.jsx';
import MoodGraph from '../components/moodGraph.jsx';

import Cookies from 'universal-cookie';
const cookies = new Cookies();

import moment from 'moment';

export default class MoodHistory extends React.Component {
    constructor(props) {
        super(props);
        let entries;
        if(props.staticContext) {
            entries = props.staticContext.entries;
        } else {
            entries = window.initialData.entries;
        }
        this.state = {
            entries: entries
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
                this.setState({ entries: res.entries });
            });
        }  
    }

    renderGraph() {
        let data = []
        const { entries } = this.state;
        if(entries !== undefined) {
            entries.map((entry) => {
                data.push({ x: moment(entry.recordedAt).format('ddd D'), y: entry.score });
            });
            return <MoodGraph data={data} />
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
                {this.renderGraph()}
            </div>
        )
    }
}