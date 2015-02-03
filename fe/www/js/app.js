
angular.module('revision', ['ionic','angularUUID2','ngCookies'])

.run(function($ionicPlatform) {



  $ionicPlatform.ready(function() {
    // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
    // for form inputs)
    if(window.cordova && window.cordova.plugins && window.cordova.plugins.Keyboard) {
      cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
    }
    if(window.StatusBar) {
      StatusBar.styleDefault();
    }
  });
})

.config(function($stateProvider, $urlRouterProvider, $httpProvider, $provide) {


  $provide.factory('myHttpInterceptor', function($q, $cookieStore, $location) {
    return {

      request: function(config) {
        var userId = $cookieStore.get('User-Id');
        config.headers['User-Id'] = userId;
        return config;
      },

     responseError: function(rejection) {
        // do something on error
        /*if (canRecover(rejection)) {
          return responseOrNewPromise
        }*/
        if (rejection.status === 403) {
          return $location.path('/login');
        } else {
          return $q.reject(rejection);
        }
      }
    };
  });

  $httpProvider.interceptors.push('myHttpInterceptor');

  window.onerror = function(errorMsg, url, lineNumber) {
    //alert(errorMsg + '\n\nFile ' + url + ' line ' +lineNumber);
  }

  $urlRouterProvider.otherwise('/');

  $stateProvider

  .state('home', {
    url: '/',
    templateUrl: 'views/matieres.html',
    controller: 'MatieresCtrl'
  })

  .state('fiches', {
    url: '/matiere/:matiereId',
    templateUrl: 'views/fiches.html',
    controller: 'FichesCtrl'
  })

  .state('fiche', {
    url: '/matiere/:matiereId/fiche/:guid',
    templateUrl: 'views/fiche.html',
    controller: 'FicheCtrl'
  })

  .state('login', {
    url: '/login',
    templateUrl: 'views/login.html',
    controller: 'LoginCtrl'
  });
});

/*.factory('$exceptionHandler', function() {
  return function(exception, cause) {
    throw exception;
  };
});*/

