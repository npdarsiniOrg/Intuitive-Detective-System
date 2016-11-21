var mongoose = require('mongoose');

var Schema = mongoose.Schema;

var deviceSchema = mongoose.Schema({ 

	deviceName 		: String,
	deviceId		: String, 
	registrationId	: String
	
});

mongoose.connect('mongodb://u_alertme:u_alertme@ds157987.mlab.com:57987/alertme');

module.exports = mongoose.model('device', deviceSchema);        