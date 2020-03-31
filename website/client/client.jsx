import React from 'react';
import ReactDOM from 'react-dom';

import { BrowserRouter } from 'react-router-dom';

import App from './app.jsx';

ReactDOM.hydrate(<BrowserRouter><App /></BrowserRouter>, document.querySelector('#root'));