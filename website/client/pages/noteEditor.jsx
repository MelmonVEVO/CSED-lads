import 'isomorphic-fetch';

import React from 'react';

import { Button, Form } from 'react-bootstrap';

import Cookies from 'universal-cookie';
const cookies = new Cookies();

export default class NoteEditor extends React.Component {
    constructor(props) {
        super(props);
        let note;
        if (props.staticContext) {
            note = props.staticContext.note;
        } else {
            note = window.initialData.note;
        }
        this.state = {
            editing: false,
            note: note,
            newNote: undefined
        }
    }

    static getInitialData(req) {
        const session = req.cookies.session;
        const noteID = req.params[0].split('/')[2];
        return fetch('http://api.csed.test/notes/' + session + '/' + noteID).then(res => res.json());
    }

    componentDidMount() {
        if (this.state.note === undefined) {
            const { id } = this.props.match.params;
            const session = cookies.get('session');
            fetch('http://api.csed.test/notes/' + session + '/' + id).then(res => res.json()).then(res => {
                if (res.success) {
                    this.setState({ note: res.note });
                }
            });
        }
    }

    editBtnClick = () => {
        const note = this.state.note;
        this.setState({ editing: true, newNote: { title: note.title, content: note.content } });   
    }

    cancelBtnClick = () => {
        this.setState({ editing: false, newNote: undefined });
    }

    renderForm(note) {
        const { editing } = this.state;
        if (editing) {
            return (
                <Form>
                    <Form.Group>
                        <Form.Label>Title</Form.Label>
                        <Form.Control type="text" defaultValue={note.title} />
                    </Form.Group>
                    <Form.Group>
                        <Form.Label>Content</Form.Label>
                        <Form.Control as="textarea" rows="4" defaultValue={note.content} />
                    </Form.Group>
                    <Button variant="dark" onClick={this.cancelBtnClick}>Cancel</Button>
                </Form>
            );
        }
        return (
            <React.Fragment>
                <Form.Group>
                    <Form.Label>Title</Form.Label>
                    <Form.Control readOnly type="text" defaultValue={note.title} />
                </Form.Group>
                <Form.Group>
                    <Form.Label>Content</Form.Label>
                    <Form.Control readOnly as="textarea" rows="4" defaultValue={note.content} />
                </Form.Group>
                <Button variant="warning" onClick={this.editBtnClick}>Edit</Button>
            </React.Fragment>
        )
    }

    render() {
        const { note } = this.state;
        if (note === undefined) {
            return <div />;
        }
        return (
            <div className="container" style={{ marginTop: 20 }}>
                <h1>Note editor</h1>
                <hr></hr>
                {this.renderForm(note)}
            </div>
        );
    }
}