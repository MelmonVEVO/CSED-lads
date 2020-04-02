import React from 'react';

export const FormSubmitBtn = (props) => {
    const { text, colour, disabled } = props;
    if(disabled) {
        return (
            <button type="submit" className={"btn btn-" + colour + " btn-block"} disabled>{text}</button>
        );
    }
    return (
        <button type="submit" className={"btn btn-" + colour + " btn-block"}>{text}</button>
    );
}