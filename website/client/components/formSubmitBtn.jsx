import React from 'react';

export const FormSubmitBtn = (props) => {
    const { text, colour } = props;
    return (
        <button type="submit" className={"btn btn-" + colour + " btn-block"}>{text}</button>
    );
}