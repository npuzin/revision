angular.module('revision')

.controller('FicheCtrl', ['$scope', 'data', '$ionicModal', '$stateParams', '$location', '$timeout', 'remoteData',
 function($scope,data, $ionicModal, $stateParams, $location, $timeout, remoteData){

  $scope.editor = null;

  $scope.isReadOnly = function() {
    return $('#btnModify').is(':visible');
  }

  $scope.setHeaderButtonsReadOnly = function(newValue) {

    if (newValue) {

      $('#btnBack').show();
      $('#btnModify').show();
      $('#btnCancel').hide();
      $('#btnSave').hide();
    } else {

      $('#btnBack').hide();
      $('#btnModify').hide();
      $('#btnCancel').show();
      $('#btnSave').show();
    }

  };

  $scope.$on('$ionicView.loaded', function() {

    $timeout(function() {

      $scope.setHeaderButtonsReadOnly($scope.ficheContent !== '');
      $scope.initCKEditor(($scope.ficheContent !== ''));

    },0);

  });

  $scope.initCKEditor = function(readOnly) {

    if ($scope.editor) {
      $scope.editor.destroy();
    }

    var diff = readOnly ? 40 : 100;
    $scope.editor = CKEDITOR.replace('editor1', {
      language: 'fr',
      height: $(window).height()-readOnly,
      readOnly: readOnly,
      bodyClass: $scope.matiere.color,
      sharedSpaces: {
          top: 'subheader'
      }
    });
    $scope.editor.setData($scope.ficheContent);
  }


  $scope.loadData = function() {

    $scope.matiere = remoteData.getMatiere(parseInt($stateParams.matiereId)).then(function (matiere) {

      $scope.matiere = matiere;

      $scope.fiche = data.getFiche($scope.matiere.id, parseInt($stateParams.ficheId));
      if (!$scope.fiche) {
        $location.path("/matiere/" + $scope.matiere.id);
      }
      $scope.ficheContent = data.getFicheContent($scope.fiche);
      if (!$scope.ficheContent) {
        $scope.ficheContent = '';
      }

    }, function() {
      $location.path("/");
    });

  }

  $scope.$on("$destroy", function() {
      $scope.editor.destroy();
  });

  $scope.loadData();

  var saveFiche = function() {

    $scope.ficheContent = $scope.editor.getData();
    data.saveFicheContent($scope.fiche, $scope.ficheContent);
  }

  $scope.modifierFiche = function() {

    $scope.setHeaderButtonsReadOnly(false);
    $scope.initCKEditor(false);
  };

  $scope.saveFiche = function() {

    saveFiche();
    $scope.setHeaderButtonsReadOnly(true);
    $scope.initCKEditor(true);
  };

  $scope.cancel = function() {

    if ($scope.ficheContent === '') {
      $location.path('/matiere/' + $scope.matiere.id);
    } else {
      $scope.loadData();
      $scope.setHeaderButtonsReadOnly($scope.ficheContent !== '');
      $scope.initCKEditor(($scope.ficheContent !== ''));
    }

  }

  $scope.saveFicheAndBack = function() {

    saveFiche();
    $location.path('/matiere/' + $scope.matiere.id);
  };
}]);
