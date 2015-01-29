angular.module('nputest2015')

.controller('LoginController', function ($scope,$http) {
	
	$http.get('rest/users').then(function(response) {

		$scope.users = response.data;
	});
	
});