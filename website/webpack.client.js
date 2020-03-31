const path = require('path');

module.exports = {
    entry: "./client/client.jsx",
    output: {
        filename: "client.bundle.js",
        path: path.resolve(__dirname, 'public'),
        publicPath: "public"
    },
    module: {
        rules: [
            {
                test: /\.jsx|.js$/,
                loader: "babel-loader",
                exclude: "/node_modules/"
            }
        ]
    }
}