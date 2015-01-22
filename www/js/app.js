
angular.module('revision', ['ionic','angularUUID2'])

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

.config(function($stateProvider, $urlRouterProvider) {

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
    url: '/matiere/:matiereId/fiche/:ficheId',
    templateUrl: 'views/fiche.html',
    controller: 'FicheCtrl'
  });
})

.factory('$exceptionHandler', function() {
  return function(exception, cause) {
    throw exception;
  };
});
