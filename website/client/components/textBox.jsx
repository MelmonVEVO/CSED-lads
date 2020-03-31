import React from 'react';

export const TextBox = (props) => {
    const { type, text, onChange } = props;
    return (
        <div className="form-group">
            <input type={type} className="form-control" placeholder={text} onChange={onChange} />
        </div>
    );
}