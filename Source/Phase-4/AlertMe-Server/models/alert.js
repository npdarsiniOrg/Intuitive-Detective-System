/**
 * Created by harsha on 12/3/16.
 */


var mongoose2 = require('mongoose');
var Schema = mongoose2.Schema;

var c_team6_predictions = mongoose2.Schema({
    Context            : String,
    Timestamp          : Number,
    Decision           : Boolean
});

mongoose2.createConnection('mongodb://u_team6:u_team6@ds113628.mlab.com:13628/db_team6');

module.exports = mongoose2.model('c_team6_predictions', c_team6_predictions);