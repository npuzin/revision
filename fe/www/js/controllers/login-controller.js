angular.module('revision')

.controller('LoginCtrl', ['$scope','remoteData', '$cookieStore', '$location', function($scope, remoteData,$cookieStore, $location){


  $scope.login = function() {

    remoteData.login().then(function(user) {

        $cookieStore.put('User-Id', user.id);
        $location.path('/matieres');
    });
  }

}]);
