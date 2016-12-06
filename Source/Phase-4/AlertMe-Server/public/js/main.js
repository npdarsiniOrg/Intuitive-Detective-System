var app =   angular.module('Push', ['ngMaterial']).run(function ($rootScope) {
    $rootScope.oldMsg = 'none';
});
// app.value('OldMsg',{oldMsg:'none'});
var socket = io();
// var oldMsg = 'none';

app.controller('pushController', function($scope,$window,$http,$mdDialog,$mdToast, $rootScope) {



socket.on('update', function (data) {

    socket.emit('update','ACK from Client !');

    if (data.update) {

    	getDevices();
    }
});

getDevices();

function getDevices(){

	$http.get('/devices').then(function(response){

		$scope.devices = response.data;
		$scope.deviceCount = response.data.length;

	});
}


function getAlerts() {
    $http.get('/c_team6_predictions').then(function(response){

        $scope.alerts = response.data;

    });
}
getAlerts();

function deleteDevice(index,devices) {

    var registrationId = $scope.devices[index].registrationId;

    $http.delete('/devices/'+registrationId).then(function(response) {

    	if (response.data.result == 'success') {

    		devices.splice(index,1);
    		$scope.deviceCount = devices.length;

    	}
    	
    })

};


$scope.showDeleteConfirmDialog = function(ev,index,devices) {

	var deviceName = $scope.devices[index].deviceName;

    var confirm = $mdDialog.confirm()
          .title('Delete device !')
          .textContent('Are you sure want to delete ' +deviceName +' ?')
          .targetEvent(ev)
          .ok('Delete Device')
          .cancel('Cancel');

    $mdDialog.show(confirm).then(function() {

      deleteDevice(index,devices);

    }, function() {

      console.log('cancel');

    });
};
    $scope.oldMsg = 'none';

$scope.sendAlert = function(ev, index) {
    var fileContents='harsha';
    // function sleep(ms) {
    //     return new Promise(resolve = setTimeout(resolve, ms));
    // }


    $scope.oldMsg  = 'none';
    // while(true) {
        console.log("Alert from main: " + $scope.alerts);
        var xmlHttp = new XMLHttpRequest();
        xmlHttp.open("GET", 'alerts.txt', false); // false for synchronous request
        xmlHttp.send(null);
        msg = xmlHttp.responseText;
        // msg = $scope.alerts;
        // //if not the previous alert message
        console.log('Previous oldMsg: ' + $scope.oldMsg);
        console.log('message: ' + msg);
        if (msg != "") {
            if ($scope.oldMsg != msg) {
                $scope.oldMsg = msg;
                console.log('Latest oldMsg: ' + $rootScope.oldMsg);
                $scope.progress = true;
                var message = msg;

                var registrationId = $scope.devices[index].registrationId;
                console.log('okay' + message + registrationId);

                var params = {

                    message: message,
                    registrationId: registrationId
                };

                $http.post('/send', params).then(function (response) {

                    $scope.progress = false;

                    // showAlert(ev,response.data.message);


                }, function (response) {

                    console.log(response);

                });
            }
            // var msg = $http.get('alerts.txt').success(function(response){
            //     return response.data;
            // });
        }
        // setTimeout(function () {
        //     continue;
        // }, 2000);

    // }
};

$scope.showSendMessageDialog = function(ev,index) {

    var confirm = $mdDialog.prompt()
          .title('Send Message to '+ $scope.devices[index].deviceName)
          .textContent('Type a message and click Send Message.')
          .placeholder('Hello !')
          .targetEvent(ev)
          .ok('Send Message')
          .cancel('Cancel');

    $mdDialog.show(confirm).then(function(result) {

		$scope.progress = true;
    	var message = result;
    	var registrationId = $scope.devices[index].registrationId;
      	console.log('okay'+ message +registrationId);

      	var params = {

      		message:message,
      		registrationId:registrationId
      	};

      	$http.post('/send', params).then(function(response) {

      		$scope.progress = false;

      		showAlert(ev,response.data.message);

			
			},function(response){

				console.log(response);

			});
      
    }, function() {

      console.log('cancel');

    });
};

function showAlert(ev,message) {

	$mdDialog.show(
      $mdDialog.alert()
        .clickOutsideToClose(true)
        .title('Alert !')
        .textContent(message)
        .ariaLabel('Alert Dialog')
        .ok('Okay')
        .targetEvent(ev)
    );
}

});