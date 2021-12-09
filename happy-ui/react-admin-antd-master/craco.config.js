const CracoAntDesignPlugin = require('craco-antd');
const CracoAlias = require('craco-alias');

module.exports = {
    devServer: {
        proxy: {
            '/dev': {
                target: 'http://127.0.0.1:13001/admin',
                changeOrign: true,
                pathRewrite: {
                    "^/dev": ''
                }
            }
        }
    },
    plugins: [
        {
            plugin: CracoAlias,
            options: {
                source: 'tsconfig',
                baseUrl: './src',
                tsConfigPath: './tsconfig.extend.json'
            }
        },
        {
            plugin: CracoAntDesignPlugin,
            options: {
                customizeTheme: {
                    '@primary-color': '#1DA57A',
                }
            }
        }
    ]
};