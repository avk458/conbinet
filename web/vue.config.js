module.exports = {
	css: {
		loaderOptions: {
			less: {
				lessOptions:{
					javascriptEnabled: true,
				}
			}
		}
	},
	configureWebpack: {
		devServer: {
			proxy: 'http://localhost:8081',
			headers: {
				'Access-Control-Allow-Origin': '*',
				'Access-Control-Allow-Methods': '*'
			}
		}
	},
	outputDir: '../src/main/resources/static',
	publicPath: process.env.NODE_ENV === 'production'
		? '/cabinet-server/'
		: '/'
}
