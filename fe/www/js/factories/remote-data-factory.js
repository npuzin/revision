'use strict';

angular.module('revision')

.factory('remoteData', ['uuid2', '$http', 'globalConfig', '$q', function(uuid2, $http, globalConfig, $q) {

  var getMatieres = function() {

    var dfr = $q.defer();
    var url = globalConfig.getBackendUrl() + '/rest/matieres';
    $http.get(url).then(function(response) {

      dfr.resolve(response.data);
    }, function() {

      dfr.reject([]);
    });

    return dfr.promise;
  };

  var updateFiche = function(fiche) {

    var dfr = $q.defer();
    var url = globalConfig.getBackendUrl() + '/rest/fiche/update';
    $http.post(url, fiche).then(function(response) {

      dfr.resolve(response.data);
    }, function() {

      dfr.reject([]);
    });

    return dfr.promise;
  };

  var updateMatiere = function(matiere) {

    var dfr = $q.defer();
    var url = globalConfig.getBackendUrl() + '/rest/matiere/update';
    $http.post(url, matiere).then(function(response) {

      dfr.resolve(response.data);
    }, function() {

      dfr.reject([]);
    });

    return dfr.promise;
  };

  var getMatiere = function(matiereId) {

    var dfr = $q.defer();
    var url = globalConfig.getBackendUrl() + '/rest/matiere/' + matiereId;
    $http.get(url).then(function(response) {

      dfr.resolve(response.data);
    }, function() {

      dfr.reject([]);
    });

    return dfr.promise;
  };

  var getFiches = function(matiereId) {

    var dfr = $q.defer();
    var url = globalConfig.getBackendUrl() + '/rest/matiere/'+ matiereId+ '/fiches';
    $http.get(url).then(function(response) {

      dfr.resolve(response.data);
    }, function() {

      dfr.reject([]);
    });

    return dfr.promise;
  };

  var deleteFiche = function(fiche) {

    var dfr = $q.defer();
    var url = globalConfig.getBackendUrl() + '/rest/fiche/delete';
    $http.post(url,fiche).then(function(response) {

      dfr.resolve(response.data);
    }, function() {

      dfr.reject([]);
    });

    return dfr.promise;
  };

  var addFiche = function(fiche) {

    var dfr = $q.defer();
    var url = globalConfig.getBackendUrl() + '/rest/fiche/add';
    $http.post(url,fiche).then(function(response) {

      dfr.resolve(response.data);
    }, function() {

      dfr.reject([]);
    });

    return dfr.promise;
  };

  var addMatiere = function(matiere) {

    var dfr = $q.defer();
    var url = globalConfig.getBackendUrl() + '/rest/matiere/add';

    $http.post(url, matiere).then(function(response) {

      dfr.resolve(response.data);
    }, function() {

      dfr.reject([]);
    });

    return dfr.promise;

  };

  var getFiche = function(guid) {

    var dfr = $q.defer();
    var url = globalConfig.getBackendUrl() + '/rest/fiche/' + guid;

    $http.get(url).then(function(response) {

      dfr.resolve(response.data);
    }, function() {

      dfr.reject();
    });

    return dfr.promise;

  };

 var saveFiche = function(fiche) {

    var dfr = $q.defer();
    var url = globalConfig.getBackendUrl() + '/rest/fiche';

    $http.post(url, fiche).then(function(response) {

      dfr.resolve(response.data);
    }, function() {

      dfr.reject();
    });

    return dfr.promise;

  };

  var login = function(email) {

    var dfr = $q.defer();
    var url = globalConfig.getBackendUrl() + '/rest/login';

    var user = {
      email: email
    };
    $http.post(url, user).then(function(response) {

      dfr.resolve(response.data);
    }, function() {

      dfr.reject();
    });

    return dfr.promise;
  };

  var deleteMatiere = function(matiereId) {

    var dfr = $q.defer();
    var url = globalConfig.getBackendUrl() + '/rest/matiere/delete';
    var matiere = {
      id: matiereId
    };

    $http.post(url, matiere).then(function(response) {

      dfr.resolve(response.data);
    }, function() {

      dfr.reject([]);
    });

    return dfr.promise;

  };

  var speed = function() {

    var dfr = $q.defer();
    var url = globalConfig.getBackendUrl() + '/rest/speed';

    $http.get(url).then(function(response) {

      dfr.resolve(response.data);
    }, function() {

      dfr.reject();
    });

    return dfr.promise;

  };

  return {
    getMatieres: getMatieres,
    getFiches: getFiches,
    addMatiere: addMatiere,
    deleteMatiere: deleteMatiere,
    getMatiere: getMatiere,
    updateMatiere: updateMatiere,
    updateFiche: updateFiche,
    deleteFiche: deleteFiche,
    addFiche: addFiche,
    getFiche: getFiche,
    saveFiche: saveFiche,
    login: login,
    speed: speed
  };
}]);
