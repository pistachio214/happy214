// fix bug craco-less在react-scripts v5中数组的push问题
const CracoAntDesignPlugin = require('./cracoAntdFix');
const CracoAlias = require('craco-alias');

module.exports = {
    devServer: {
        proxy: {
            '/dev': {
                target: 'http://192.168.0.7:14001/',
                changeOrign: true,
                pathRewrite: {
                    "^/dev": ''
                }
            },
            '/mac': {
                target: 'http://127.0.0.1:14001/',
                changeOrign: true,
                pathRewrite: {
                    "^/mac": ''
                }
            },
            '/house': {
                target: 'http://192.168.0.103:14001/',
                changeOrign: true,
                pathRewrite: {
                    "^/house": ''
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
                },
            }
        }
    ]
};