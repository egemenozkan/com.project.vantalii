// Generated using webpack-cli https://github.com/webpack/webpack-cli

const path = require("path");
const HtmlWebpackPlugin = require("html-webpack-plugin");

const isProduction = process.env.NODE_ENV == "production";

const stylesHandler = "style-loader";
const { VueLoaderPlugin } = require('vue-loader')
const WebpackShellPluginNext = require('webpack-shell-plugin-next');


const config = {
	entry: {
		index: './src/main/resources/static/js/index.js',
		// events_index: './src/main/resources/static/js/events_index.js',
		// events: './src/main/resources/static/js/events.js',
		place_detail: './src/main/resources/static/js/place_detail.js',
		place_list: './src/main/resources/static/js/place_list.js',
		styles: ['./src/main/resources/static/styles/index.scss',
			'./src/main/resources/static/styles/common.scss',
			'./src/main/resources/static/styles/place_detail.scss',
			'./src/main/resources/static/styles/place_list.scss']
	},
	output: {
		// publicPath: './src/main/resources/static/bundle/js'
		path: path.resolve(__dirname, './src/main/resources/static/bundle'),
		filename: "js/[name].js",
		publicPath: "/dist/"
	},
	plugins: [
		// make sure to include the plugin for the magic
		new VueLoaderPlugin(),
		new HtmlWebpackPlugin({
			template: "index.html",
		}),
		new WebpackShellPluginNext({
			onBuildStart: ['echo "Starting postcss command"'],
			onBuildEnd: ['postcss --dir static/bundle static/bundle/*.css']
		})
		//new VueLoaderPlugin()
		// Add your plugins here
		// Learn more about plugins from https://webpack.js.org/configuration/plugins/
	],
	resolve: {
		alias: {
			vue$: "vue/dist/vue.runtime.esm.js",
		},
		extensions: ["*", ".js", ".vue", ".json"],
	},
	module: {
		rules: [

			{
				test: /\.(woff|woff2|eot|ttf)$/,
				use: {
					loader: 'url-loader',
				},
			},
			{
				test: /\.(woff|woff2|eot|ttf|otf)$/i,
				type: 'asset/resource',
			},

			//{
			//    // Run postcss actions
			//    loader: 'postcss-loader',
			//    options: {
			//      // `postcssOptions` is needed for postcss 8.x;
			//      // if you use postcss 7.x skip the key
			//      postcssOptions: {
			//        // postcss plugins, can be exported to postcss.config.js
			//        plugins: function () {
			//          return [
			//            require('autoprefixer')
			//          ];
			//        }
			//      }
			//    }
			//  }, 
			  {
        test: /\.scss$/,
        use: [
          "style-loader",
          "css-loader",
          "sass-loader"
        ]
      },
			{
				test: /\.(js)$/,
				exclude: /node_modules/,
				use: ['babel-loader']
			},
			{
				test: /\.vue$/,
				loader: 'vue-loader'
			}
			, {
				test: /\.css$/,
				use: [
					'vue-style-loader',
					'css-loader'
				]
			}
			// Add your rules for custom modules here
			// Learn more about loaders from https://webpack.js.org/loaders/
		],
	}
};

module.exports = () => {
	if (isProduction) {
		config.mode = "production";
	} else {
		config.mode = "development";
	}
	return config;
};
