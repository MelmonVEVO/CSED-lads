import React from 'react';

export const FormSubmitBtn = (props) => {
    const { text, colour, disabled, block } = props;
    const className = "btn btn-" + colour + (block ? " btn-block" : "") + " btn-lg";
    if(disabled) {
        return (
            <button type="submit" className={className} disabled>{text}</button>
        );
    }
    return (
        <button type="submit" className={className}>{text}</button>
    );
}