import 'isomorphic-fetch';

import React from 'react';
import { Link } from 'react-router-dom';

import { Table } from 'react-bootstrap';

import Moment from 'react-moment';

import Cookies from 'universal-cookie';
const cookies = new Cookies();

export default class Notes extends React.Component {
    constructor(props) {
        super(props);
        let notes;
        if (props.staticContext) {
            notes = props.staticContext.notes;
        } else {
            notes = window.initialData.notes;
        }
        this.state = {
            notes: notes
        }
    }

    static getInitialData(req) {
        const session = req.cookies.session;
        return fetch('http://api.csed.test/notes/' + session).then(res => res.json());
    }

    componentDidMount() {
        if (this.state.notes === undefined) {
            const session = cookies.get('session');
            if (session !== undefined) {
                fetch('http://api.csed.test/notes/' + session).then(res => res.json()).then(res => {
                    if (res.success) {
                        this.setState({ notes: res.notes });
                    }
                });
            }
        }
    }

    render() {
        const { notes } = this.state;
        if(notes === undefined) {
            return <div/>;
        }
        const dateFormat = "ddd D MMM YYYY";
        return (
            <div className="container" style={{ marginTop: 20 }}>
                <h1>Notes</h1>
                <hr></hr>
                <Link className="btn btn-primary" to="/notes/create">Create</Link>
                <Table striped bordered size="sm">
                    <thead>
                        <tr>
                            <th>Title</th>
                            <th>Created</th>
                            <th>Last edited</th>
                        </tr>
                    </thead>
                    <tbody>
                        {notes.map((note, k) => (
                            <tr key={k}>
                                <td><Link to={"/notes/" + note.id}>{note.title}</Link></td>
                                <td><Moment format={dateFormat}>{note.created}</Moment></td>
                                <td>{note.edited !== null ? <Moment format={dateFormat}>{note.edited}</Moment> : ""}</td>
                            </tr>
                        ))}
                    </tbody>
                </Table>
            </div>
        );
    }
}