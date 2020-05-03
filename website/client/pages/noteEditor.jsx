import 'isomorphic-fetch';

import React from 'react';
import { Link } from 'react-router-dom';

import { Button, Form } from 'react-bootstrap';

import Cookies from 'universal-cookie';
const cookies = new Cookies();

export default class NoteEditor extends React.Component {
    constructor(props) {
        super(props);
        let note;
        if (props.staticContext) {
            note = props.staticContext.note;
        } else if(window.initialData !== undefined) {
            note = window.initialData.note;
            delete window.initialData;
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

    updateClick = e => {
        const { id } = this.state.note;
        const { newNote } = this.state;
        if(newNote !== undefined) {
            fetch('http://api.csed.test/notes/update', {
                credentials: "include",
                method: "POST",
                headers: {
                    "content-type": "application/json"
                },
                body: JSON.stringify({
                    "id": id,
                    "title": newNote.title,
                    "content": newNote.content
                })
            }).then(res => res.json()).then(res => {
                if(res.success) {
                    newNote.id = id;
                    this.setState({ editing: false, note: newNote, newNote: undefined });
                }
            });
        }
        e.preventDefault();
    }

    delete = () => {
        const { id } = this.state.note;
        fetch('http://api.csed.test/notes/delete', {
            credentials: "include",
            method: "POST",
            headers: {
                "content-type": "application/json"
            },
            body: JSON.stringify({
                id: id
            })
        }).then(res => res.json()).then(res => {
            if(res.success) {
                this.props.history.push('/notes');
            }
        });
    }

    editBtnClick = () => {
        const note = this.state.note;
        this.setState({ editing: true, newNote: { title: note.title, content: note.content } });   
    }

    cancelBtnClick = () => {
        this.setState({ editing: false, newNote: undefined });
    }

    contentChange = e => {
        let note = this.state.newNote;
        note.content = e.target.value;
        this.setState({ newNote: note });
    }

    titleChange = e => {
        let note = this.state.newNote;
        note.title = e.target.value;
        this.setState({ newNote: note });
    }

    renderForm(note, editing) {
        if (editing) {
            return (
                <Form>
                    <Form.Group>
                        <Form.Label>Title</Form.Label>
                        <Form.Control type="text" defaultValue={note.title} onChange={this.titleChange} />
                    </Form.Group>
                    <Form.Group>
                        <Form.Label>Content</Form.Label>
                        <Form.Control as="textarea" rows="4" defaultValue={note.content} onChange={this.contentChange} />
                    </Form.Group>
                    <div className="d-inline">
                        <Button variant="success" type="submit" style={{ marginRight: 5 }} onClick={this.updateClick}>Update</Button>
                        <Button variant="dark" onClick={this.cancelBtnClick}>Cancel</Button>
                    </div>
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
                <Button variant="warning" onClick={this.editBtnClick} style={{ marginRight: 5 }}>Edit</Button>
                <Button variant="danger" onClick={this.delete} style={{ marginRight: 5 }}>Delete</Button>
                <Link className="btn btn-dark" to="/notes">Back</Link>
            </React.Fragment>
        )
    }

    render() {
        const { note, editing } = this.state;
        if (note === undefined) {
            return <div />;
        }
        return (
            <div className="container" style={{ marginTop: 20 }}>
                <h1>{editing ? "Editing" : "Viewing"}</h1>
                <hr></hr>
                {this.renderForm(note, editing)}
            </div>
        );
    }
}