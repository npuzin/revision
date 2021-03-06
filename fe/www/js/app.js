'use strict';

angular.module('revision', ['ionic','angularUUID2','ngCookies'])

.run(function($ionicPlatform) {

  $ionicPlatform.ready(function() {
    // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
    // for form inputs)
    if(window.cordova && window.cordova.plugins && window.cordova.plugins.Keyboard) {
      cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
    }
    if(window.StatusBar) {
      window.StatusBar.styleDefault();
    }
  });
})

.config(function($stateProvider, $urlRouterProvider, $httpProvider, $provide) {


  $provide.factory('myHttpInterceptor', function($q, $cookieStore, $location) {
    return {

      request: function(config) {
        var sessionId = $cookieStore.get('Session-Id');
        config.headers['Session-Id'] = sessionId;
        return config;
      },

     responseError: function(rejection) {
        // do something on error
        /*if (canRecover(rejection)) {
          return responseOrNewPromise
        }*/
        if (rejection.status === 403) {
          $location.path('/login');
        }
        return $q.reject(rejection);
      }
    };
  });

  $httpProvider.interceptors.push('myHttpInterceptor');

  /*window.onerror = function(errorMsg, url, lineNumber) {
    //alert(errorMsg + '\n\nFile ' + url + ' line ' +lineNumber);
  };*/

  $urlRouterProvider.otherwise('/');

  $stateProvider


  .state('home', {
    url: '/',
    views: {
      'menuContent': {
        templateUrl: 'views/matieres.html',
        controller: 'MatieresCtrl'
      }
    }
  })

  .state('fiches', {
    url: '/matiere/:matiereId',
    views: {
      'menuContent': {
        templateUrl: 'views/fiches.html',
        controller: 'FichesCtrl'
      }
    }
  })

  .state('fiche', {
    url: '/fiche/:guid',
    views: {
      'menuContent': {
        templateUrl: 'views/fiche.html',
        controller: 'FicheCtrl'
      }
    }
  })

  .state('login', {
    url: '/login',
    views: {
      'menuContent': {
        templateUrl: 'views/login.html',
        controller: 'LoginCtrl'
      }
    }
  })
  ;
});

/*.factory('$exceptionHandler', function() {
  return function(exception, cause) {
    throw exception;
  };
});*/

