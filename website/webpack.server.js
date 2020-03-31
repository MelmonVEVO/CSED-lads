const path = require('path');
const webpackNodeExternals = require('webpack-node-externals');

module.exports = {
    target: "node",
    entry: "./server.js",
    output: {
        filename: "bundle.js",
        path: path.resolve(__dirname, 'private'),
        publicPath: "/private"
    },
    module: {
        rules: [
            {
                test: /\.jsx|.js$/,
                loader: "babel-loader",
                exclude: "/node_modules/"
            }
        ]
    },
    externals: [webpackNodeExternals()]
}