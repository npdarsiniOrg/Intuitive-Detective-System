var mongoose = require('mongoose');

var Schema = mongoose.Schema;

var deviceSchema = mongoose.Schema({ 

	deviceName 		: String,
	deviceId		: String, 
	registrationId	: String
	
});


mongoose.connect('mongodb://u_alertme:u_alertme@ds157987.mlab.com:57987/alertme');



//
// var alertSchema = mongoose.Schema({
//     Context            : String,
//     Timestamp          : String,
//     Decision           : String
// });

// mongoose2.createConnection('mongodb://u_team6:u_team6@ds113628.mlab.com:13628/db_team6');
// mongoose.connect('mongodb://u_team6:u_team6@ds113628.mlab.com:13628/db_team6');
module.exports = mongoose.model('device', deviceSchema);
// module.exports = mongoose.model('c_team6_predictions', alertSchema);