const path = require('path');
const WebpackMd5Hash = require('webpack-md5-hash');
const { CleanWebpackPlugin } = require('clean-webpack-plugin');
const {  VueLoaderPlugin }= require('vue-loader')

const webpack = require('webpack');
const CopyPlugin = require('copy-webpack-plugin');
const CompressionPlugin = require("compression-webpack-plugin");
const OptimizeCSSAssetsPlugin = require("optimize-css-assets-webpack-plugin");

module.exports = {
	mode : 'development',
	watch : true,
	entry : {
		index : './src/main/resources/static/js/index.js',
		// events_index: './src/main/resources/static/js/events_index.js',
		// events: './src/main/resources/static/js/events.js',
		place_detail : './src/main/resources/static/js/place_detail.js',
		place_list : './src/main/resources/static/js/place_list.js',

		// places_index: './src/main/resources/static/js/places_index.js',
		styles : [ './src/main/resources/static/styles/index.scss',
				'./src/main/resources/static/styles/common.scss',
				'./src/main/resources/static/styles/place_detail.scss',
				'./src/main/resources/static/styles/place_list.scss' ]
	},
	output : {
		path : path.resolve(__dirname, './src/main/resources/static/bundle'),
		// filename: '[name].[chunkhash].js'
		filename : 'js/[name].js',
	// publicPath: './src/main/resources/static/bundle/js'
	},
	resolve : {
		alias : {
			'vue$' : 'vue/dist/vue.esm.js' // 'vue/dist/vue.common.js' for
		// webpack 1
		}
	},
	module : {
		rules : [ {
			test : /\.js$/,
			exclude : /node_modules/,
			use : {
				loader : "babel-loader"
			}
		}, {
			test : /\.css$/,
			use : [ 'style-loader', 'css-loader', 'vue-style-loader' ]
			
		}, {
			test : /\.scss$/,
			use : [ {
				loader : 'file-loader',
				options : {
					name : '/css/[name].css',
				}
			}, {
				loader : 'extract-loader'
					
			}, {
				loader : 'css-loader?-url'
			}, {
				loader : 'postcss-loader'
			}, {
				loader : 'sass-loader'
			} ]
		}, {
			test : /\.vue$/,
			loader : 'vue-loader'
		}, {
			test : /\.(gif|svg|jpg|png)$/,
			loader : 'file-loader'
		},
		{
			test: /\.(woff|ttf|otf|eot|woff2|svg)$/i,
			loader: "file-loader"
		} ]
	},
	plugins : [
			new CleanWebpackPlugin(),
			new WebpackMd5Hash(),
			new VueLoaderPlugin(),
			new webpack.ProvidePlugin({
				$ : "jquery",
				jQuery : "jquery"
			}),
			new CompressionPlugin({
				test: /\.(js|css)/
			}),
			new CopyPlugin({
			      patterns: [
			        { from: './src/main/resources/static/styles/fa/webfonts/', to: path.resolve(__dirname, './src/main/resources/static/bundle/webfonts') }
			      ],
			    })],
			    
				optimization: {
					minimizer: [
						new OptimizeCSSAssetsPlugin({
							cssProcessorOptions: {
								safe: true,
								discardComments: {
									removeAll: true,
								},
							},
						})
					]
				},

};