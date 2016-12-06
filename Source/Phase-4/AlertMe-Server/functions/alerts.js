/**
 * Created by harsha on 12/3/16.
 */

var mongoose = require('mongoose');
var request = require('request');
var alert = require('../models/alert');
var constants = require('../constants/constants.json');


exports.listAlerts = function(callback) {

    alert.find( {}, {_id : false,__v : false }, function(err,c_team6_predictions){

        if(!err){

            console.log("Predictions: " + c_team6_predictions);
            callback(c_team6_predictions)

        }
        else{
            console.log("Error in alerts.js");
        }
    });
}