import React from 'react';

export const TextBox = (props) => {
    const { type, text, onChange, value, error } = props;
    const className = "form-control" + (error ? " error" : "");
    return (
        <div className="form-group">
            <input type={type} className={className} placeholder={text} onChange={onChange} value={value} />
        </div>
    );
}