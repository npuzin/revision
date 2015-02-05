angular.module('revision')

.controller('LoginCtrl', ['$scope','remoteData', '$cookieStore', '$location', function($scope, remoteData,$cookieStore, $location){


  $scope.login = function() {

    remoteData.login('nicolas.puzin@gmail.com').then(function(session) {

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
