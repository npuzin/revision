'use strict';

angular.module('nputest2015', ['ngRoute'])

 .config(function ($routeProvider) {


    // define routes
    $routeProvider
      .when('/', {
        templateUrl: 'static/html/home.html',
        controller: 'HomeController'        
      })      
      
      .when('/login', {
        templateUrl: 'static/html/login.html',
        controller: 'LoginController'        
      }) 
      
      .otherwise({
        redirectTo: '/'
      });

  });
  
