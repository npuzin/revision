angular.module('revision')

.factory('globalConfig', function($location) {

  var LOCAL_BACKEND_URL = 'http://localhost:8080';
  var PROD_BACKEND_URL = 'http://npurevision.appspot.com';

  var getBackendUrl = function() {

    var host = $location.host();
    var port = $location.port();
    if (host === 'localhost' && port === 8100) {
      return LOCAL_BACKEND_URL;
    } else {
      return PROD_BACKEND_URL;
    }
  };

  var isProd = function() {

    return !isDev();
  };

  var isDev = function() {

    var host = $location.host();
    var port = $location.port();
    return (host === 'localhost' && port === 8100);
  };

  return {
    getBackendUrl: getBackendUrl,
    isProd: isProd,
    isDev: isDev

  }
});
