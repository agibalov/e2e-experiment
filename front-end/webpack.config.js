const path = require('path');

module.exports = {
    entry: './src/main.ts',
    output: {
        path: path.resolve(__dirname, 'dist'),
        filename: 'bundle.js'
    },
    module: {
        rules: [{
            test: /\.ts$/,
            use: 'awesome-typescript-loader'
        }]
    },
    devServer: {
        proxy: {
            '/api': {
                target: 'http://localhost:8080/',
                secure: false
            }
        }
    }
};
