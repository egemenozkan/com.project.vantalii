const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const WebpackMd5Hash = require('webpack-md5-hash');
const MiniCssExtractPlugin = require("mini-css-extract-plugin");
const CleanWebpackPlugin = require('clean-webpack-plugin');
const UglifyJsPlugin = require('uglifyjs-webpack-plugin');

module.exports = {
        mode: 'production',
        entry: {
            index: './src/main/resources/static/js/index.js',
            events_index: './src/main/resources/static/js/events_index.js',
            events: './src/main/resources/static/js/events.js',
            places: './src/main/resources/static/js/places.js',
            styles: ['./src/main/resources/static/styles/index.scss', 
                './src/main/resources/static/styles/common.scss', './src/main/resources/static/styles/events.scss',
                './src/main/resources/static/styles/places.scss']
        },
  output: {
      path: path.resolve(__dirname, './src/main/resources/static/bundle'),
//    filename: '[name].[chunkhash].js'
      filename: 'js/[name].js',
//      publicPath: './src/main/resources/static/bundle/js'
  },
  resolve: {
      alias: {
        'vue$': 'vue/dist/vue.esm.js' // 'vue/dist/vue.common.js' for webpack 1
      }
    },
  module: {
    rules: [
      {
        test: /\.js$/,
        exclude: /node_modules/,
        use: {
          loader: "babel-loader"
        }
      },
      {
          test: /\.scss$/,
          use: [
              {
                  loader: 'file-loader',
                  options: {
                      name: '/css/[name].css',
                  }
              },
              {
                  loader: 'extract-loader'
              },
              {
                  loader: 'css-loader?-url'
              },
              {
                  loader: 'postcss-loader'
              },
              {
                  loader: 'sass-loader'
              }
          ]
      }
    ]
  },
  plugins: [
    new CleanWebpackPlugin('./src/main/resources/static/bundle', {}),
    new MiniCssExtractPlugin({
      filename: '[name].css',
    }),
    new WebpackMd5Hash()
  ],
  optimization: {
      minimizer: [new UglifyJsPlugin({
        test: /\.js(\?.*)?$/i,
      })]
    }
};