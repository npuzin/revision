angular.module('revision')

.controller('LoginCtrl', ['$scope','remoteData', '$cookieStore', '$location', function($scope, remoteData,$cookieStore, $location){

  $scope.user = { email:'nicolas.puzin@gmail.com'};

  $scope.login = function() {


    remoteData.login($scope.user.email).then(function(session) {

        $cookieStore.put('Session-Id', session.sessionId);
        $location.path('/matieres');
    });
  };

  $scope.speed = function() {

    remoteData.speed().then(function(response) {

        console.log(response.data);
    });
  };

}]);
