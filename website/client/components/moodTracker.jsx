import React from 'react';

import { Form } from 'react-bootstrap';

export default class MoodTracker extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            formVis: false
        }
    }

    moodOptionClick = e => {
        console.log(e.target.id);
    }

    renderForm() {
        const { formVis } = this.state;
        if(formVis) {
            return (
                <div className="d-inline">
                    <button className="btn white-text btn-lg" id="1" style={{ backgroundColor: '#BF360C'}} onClick={this.moodOptionClick}>1</button>
                    <button className="btn white-text btn-lg" id="2" style={{ backgroundColor: '#E64A19' }} onClick={this.moodOptionClick}>2</button>
                    <button className="btn white-text btn-lg" id="3" style={{ backgroundColor: '#FF5722' }} onClick={this.moodOptionClick}>3</button>
                    <button className="btn white-text btn-lg" id="4" style={{ backgroundColor: '#388E3C' }} onClick={this.moodOptionClick}>4</button>
                    <button className="btn white-text btn-lg" id="5" style={{ backgroundColor: '#1B5E20' }} onClick={this.moodOptionClick}>5</button>
                </div>
            );
        }
        return <div/>;
    }

    changeFormVis = () => {
        const { formVis } = this.state;
        this.setState({ formVis: !formVis })
    }

    render() {
        return (
            <div className="jumbotron green">
                <h1 className="display-5">Set your mood for today</h1>
                {this.renderForm()}
                <div className="d-block">
                    <button className="btn btn-light btn-lg" onClick={this.changeFormVis}>Set mood</button>
                </div>
            </div>
        )
    }
}