angular.module('revision')

.controller('LoginController', function ($scope,$http) {
	
	$http.get('rest/users').then(function(response) {

		$scope.users = response.data;
	});
	
});