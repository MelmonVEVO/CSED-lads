import 'isomorphic-fetch';

import React from 'react';

import { Form, Button } from 'react-bootstrap';

function validate(title, content) {
    return {
        title: title.length !== 0,
        content: content.length !== 0
    }
}

export default class NoteCreator extends React.Component {
    state = {
        title: "",
        content: "",
        loading: false,
        errors: {
            title: false,
            content: false,
            unknown: false
        }
    }

    create = e => {
        const { title, content } = this.state;
        const validateRes = validate(title, content);
        if(validateRes.title && validateRes.content) {
            this.setState({ loading: true, errors: { title: false, content: false, unknown: false } });
            fetch('http://api.csed.test/notes/create', {
                credentials: "include",
                method: "POST",
                headers: {
                    "content-type": "application/json"
                },
                body: JSON.stringify({
                    title: title,
                    content: content
                })
            }).then(res => res.json()).then(res => {
                if(res.success) {
                    window.location.href = '/notes';
                } else {
                    this.setState({ loading: false, errors: { unknown: true } });
                }
            }).catch(err => {
                console.error(err);
                this.setState({ loading: false, errors: { unknown: true } });
            });
        } else {
            this.setState({ errors: { title: !validateRes.title, content: !validateRes.content } });
        }
        e.preventDefault();
    }

    titleChange = e => {
        this.setState({ title: e.target.value });
    }

    contentChange = e => {
        this.setState({ content: e.target.value });
    }

    textBoxError(err) {
        return err ? "error" : "";
    }

    renderUnknownError() {
        const { unknown } = this.state.errors;
        if(unknown) {
            return <div className="alert alert-danger">An unknown error has happened</div>
        }
        return <div/>
    }

    render() {
        const { title, content } = this.state.errors;
        return (
            <div className="container" style={{ marginTop: 20 }}>
                <h1>Create note</h1>
                <hr></hr>
                {this.renderUnknownError()}
                <Form onSubmit={this.create}>
                    <Form.Group>
                        <Form.Label>Title</Form.Label>
                        <Form.Control type="text" onChange={this.titleChange} className={this.textBoxError(title)} />
                    </Form.Group>
                    <Form.Group>
                        <Form.Label>Content</Form.Label>
                        <Form.Control as="textarea" rows="4" onChange={this.contentChange} className={this.textBoxError(content)} />
                    </Form.Group>
                    <Button variant="success" type="submit">Add</Button>
                </Form>
            </div>
        );
    }
}