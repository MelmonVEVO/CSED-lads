import 'isomorphic-fetch';

import React from 'react';

export default class PillAlert extends React.Component {
    state = {
        loading: true,
        taken: false
    }

    componentDidMount() {
        fetch('http://api.csed.test/pill-tracker/has-taken', { credentials: "include" })
            .then(res => res.json())
            .then(res => {
                if (res.success) {
                    this.setState({ loading: false, taken: res.taken });
                }
            });
    }

    btnClick = () => {
         fetch('http://api.csed.test/pill-tracker/track', {
            credentials: "include",
            method: "POST"
         }).then(res => res.json()).then(res => {
            if(res.success) {
                if(res.logged) {
                    this.setState({ taken: true });
                }
            }
         });
    }

    render() {
        const { loading, taken } = this.state;
        if (loading || taken) {
            return <div />;
        }
        return (
            <div className="alert alert-info alert-dismissible fade show" role="alert">
                <b>Have you taken your pill today?</b>
                <div className="d-block">
                    <button className="btn btn-primary btn-md" onClick={this.btnClick}>Yes</button>
                </div>
                <button type="button" className="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        );
    }
}