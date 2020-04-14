import 'isomorphic-fetch';

import React from 'react';

import Cookies from 'universal-cookie';
const cookies = new Cookies();

export default class MoodTracker extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: true,
            exists: false,
            selected: 0,
            formVis: false,
            error: false
        }
    }

    componentDidMount() {
        const sessionID = cookies.get('session');
        if(sessionID !== undefined) {
            fetch('http://api.csed.test/mood/exists/' + sessionID).then(res => res.json()).then(res => {
                if(res.success) {
                    this.setState({ loading: false, exists: res.exists });
                } else {
                    this.setState({ loading: false });
                }
            });
        }
    }

    setMoodClick = () => {
        const { selected } = this.state;
        if(selected >= 1 && selected <= 5) {
            this.setState({ error: false });
            fetch('http://api.csed.test/mood/insert', {
                credentials: "include",
                method: "POST",
                headers: {
                    "content-type": "application/json"
                },
                body: JSON.stringify({
                    "score": selected
                })
            }).then(res => res.json()).then(res => {
                if(res.success && this.props.redirect) {
                    this.props.history.push('/mood-history?newEntry=true');
                }
            });
        } else {
            this.setState({ error: true });
        }
    }

    moodOptionClick = e => {
        this.setState({ selected: e.target.id, error: false });
    }

    renderSelected() {
        const { selected, error } = this.state;
        if(error) {
            return <p><b>You must select an option!</b></p>;
        }
        if (selected == 0) {
            return <div />;
        }
        return <p><b>Currently selected: {selected}</b></p>;
    }
        
    renderForm() {
        const { formVis } = this.state;
        if (formVis) {
            return (
                <React.Fragment>
                    {this.renderSelected()}
                    <div className="d-inline">
                        <button className="btn white-text btn-lg" id="1" style={{ backgroundColor: '#BF360C' }} onClick={this.moodOptionClick}>1</button>
                        <button className="btn white-text btn-lg" id="2" style={{ backgroundColor: '#E64A19' }} onClick={this.moodOptionClick}>2</button>
                        <button className="btn white-text btn-lg" id="3" style={{ backgroundColor: '#FF5722' }} onClick={this.moodOptionClick}>3</button>
                        <button className="btn white-text btn-lg" id="4" style={{ backgroundColor: '#388E3C' }} onClick={this.moodOptionClick}>4</button>
                        <button className="btn white-text btn-lg" id="5" style={{ backgroundColor: '#1B5E20' }} onClick={this.moodOptionClick}>5</button>
                    </div>
                    <div className="d-block">
                        <button className="btn btn-info btn-lg" style={{ marginRight: 5 }} onClick={this.setMoodClick}>Set mood</button>
                        <button className="btn btn-dark btn-lg" onClick={this.changeFormVis}>Cancel</button>
                    </div>
                </React.Fragment>
            );
        }
        return (
            <div className="d-block">
                <button className="btn btn-info btn-lg" onClick={this.changeFormVis}>Set mood</button>
            </div>
        );
    }

    changeFormVis = () => {
        const newVis = !this.state.formVis;
        if(!newVis) {
            this.setState({ formVis: newVis, selected: 0, error: false });
        } else {
            this.setState({ formVis: newVis })
        }
    }

    render() {
        const { loading, exists } = this.state;
        if(loading || exists) {
            return <div/>;
        }
        return (
            <div className="jumbotron" style={this.props.style}>
                <h1 className="display-5">Set your mood for today</h1>
                <hr></hr>
                {this.renderForm()}
            </div>
        )
    }
}