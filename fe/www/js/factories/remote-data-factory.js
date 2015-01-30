angular.module('revision')

.factory('remoteData', ['uuid2', '$http', 'globalConfig', '$q', function(uuid2, $http, globalConfig, $q) {

  var userId = 1;

  var getMatieres = function() {

    var dfr = $q.defer();
    var url = globalConfig.backendUrl + '/rest/matieres?userId=' + userId;
    $http.get(url).then(function(response) {

      dfr.resolve(response.data);
    }, function(response) {

      dfr.reject([]);
    });

    return dfr.promise;
  };

  var getFiches = function(matiereId) {

    var dfr = $q.defer();
    var url = globalConfig.backendUrl + '/rest/matiere/'+ matiereId+ '/fiches';
    $http.get(url).then(function(response) {

      dfr.resolve(response.data);
    }, function(response) {

      dfr.reject([]);
    });

    return dfr.promise;
  };

  var addMatiere = function(matiere) {

    var dfr = $q.defer();
    var url = globalConfig.backendUrl + '/rest/matiere/add?userId=' + userId;
    var data = {
      name: matiere.name,
      color: matiere.color
    };

    $http({method: 'POST',
      url: url,
      data: data,
      headers: {
        //'Content-Type': undefined,
        //'Access-Control-Allow-Origin': '*'
      }
    }).then(function(response) {

      dfr.resolve(response.data);
    }, function(response) {

      dfr.reject([]);
    });

    return dfr.promise;

  };

  return {
    getMatieres: getMatieres,
    getFiches: getFiches,
    addMatiere: addMatiere
  }
}]);
