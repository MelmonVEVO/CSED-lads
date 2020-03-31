import React from 'react';

export const TextBox = (props) => {
    const { type, text, onChange, value } = props;
    return (
        <div className="form-group">
            <input type={type} className="form-control" placeholder={text} onChange={onChange} value={value} />
        </div>
    );
}