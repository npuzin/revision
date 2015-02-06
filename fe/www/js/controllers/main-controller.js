'use strict';

angular.module('revision')

.controller('MainCtrl', ['$scope', 'globalConfig', function($scope, globalConfig){


  $scope.globalConfig = globalConfig;
}]);
