angular.module('revision')

.factory('remoteData', ['uuid2', '$http', 'globalConfig', '$q', function(uuid2, $http, globalConfig, $q) {

  var userId = 1;

  var getMatieres = function() {

    var dfr = $q.defer();
    var url = globalConfig.getBackendUrl() + '/rest/matieres?userId=' + userId;
    $http.get(url).then(function(response) {

      dfr.resolve(response.data);
    }, function(response) {

      dfr.reject([]);
    });

    return dfr.promise;
  };

  var updateFiche = function(fiche) {

    var dfr = $q.defer();
    var url = globalConfig.getBackendUrl() + '/rest/fiche/update';
    $http.post(url, fiche).then(function(response) {

      dfr.resolve(response.data);
    }, function(response) {

      dfr.reject([]);
    });

    return dfr.promise;
  };

  var updateMatiere = function(matiere) {

    var dfr = $q.defer();
    var url = globalConfig.getBackendUrl() + '/rest/matiere/update';
    $http.post(url, matiere).then(function(response) {

      dfr.resolve(response.data);
    }, function(response) {

      dfr.reject([]);
    });

    return dfr.promise;
  };

  var getMatiere = function(matiereId) {

    var dfr = $q.defer();
    var url = globalConfig.getBackendUrl() + '/rest/matiere/' + matiereId + '?userId=' + userId;
    $http.get(url).then(function(response) {

      dfr.resolve(response.data);
    }, function(response) {

      dfr.reject([]);
    });

    return dfr.promise;
  };

  var getFiches = function(matiereId) {

    var dfr = $q.defer();
    var url = globalConfig.getBackendUrl() + '/rest/matiere/'+ matiereId+ '/fiches';
    $http.get(url).then(function(response) {

      dfr.resolve(response.data);
    }, function(response) {

      dfr.reject([]);
    });

    return dfr.promise;
  };

  var deleteFiche = function(fiche) {

    var dfr = $q.defer();
    var url = globalConfig.getBackendUrl() + '/rest/fiche/delete';
    $http.post(url,fiche).then(function(response) {

      dfr.resolve(response.data);
    }, function(response) {

      dfr.reject([]);
    });

    return dfr.promise;
  };

  var addFiche = function(fiche) {

    var dfr = $q.defer();
    var url = globalConfig.getBackendUrl() + '/rest/fiche/add';
    $http.post(url,fiche).then(function(response) {

      dfr.resolve(response.data);
    }, function(response) {

      dfr.reject([]);
    });

    return dfr.promise;
  };

  var addMatiere = function(matiere) {

    var dfr = $q.defer();
    var url = globalConfig.getBackendUrl() + '/rest/matiere/add?userId=' + userId;

    $http.post(url, matiere).then(function(response) {

      dfr.resolve(response.data);
    }, function(response) {

      dfr.reject([]);
    });

    return dfr.promise;

  };

  var deleteMatiere = function(matiereId) {

    var dfr = $q.defer();
    var url = globalConfig.getBackendUrl() + '/rest/matiere/delete?userId=' + userId;
    var matiere = {
      id: matiereId
    };

    $http.post(url, matiere).then(function(response) {

      dfr.resolve(response.data);
    }, function(response) {

      dfr.reject([]);
    });

    return dfr.promise;


    var matieres = getMatieres();
    var currentMatiere = _.find(matieres, function(curr) {
      return curr.id === matiereId;
    });
    var pos = matieres.indexOf(currentMatiere);
    if (pos>=0) {
      matieres.splice(pos,1);
    }
    window.localStorage['matieres'] = JSON.stringify(matieres);
    return matieres;
  }

  return {
    getMatieres: getMatieres,
    getFiches: getFiches,
    addMatiere: addMatiere,
    deleteMatiere: deleteMatiere,
    getMatiere: getMatiere,
    updateMatiere: updateMatiere,
    updateFiche: updateFiche,
    deleteFiche: deleteFiche,
    addFiche: addFiche
  }
}]);
