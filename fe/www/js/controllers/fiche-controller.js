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

  var init = function() {

    var readonly = false;
    if ($scope.fiche && $scope.fiche.content !== null && $scope.fiche.content !== '') {
      readonly = true;
    }
    $scope.setHeaderButtonsReadOnly(readonly);
    $scope.initCKEditor(readonly);


  };

  $scope.initCKEditor = function(readOnly) {

    if ($scope.editor) {
      $scope.editor.destroy();
    }

    var diff = readOnly ? 40 : 100;
    $scope.editor = CKEDITOR.replace('editor1', {
      language: 'fr',
      height: $(window).height()-readOnly,
      readOnly: readOnly,
      bodyClass: $scope.fiche.matiere.color,
      sharedSpaces: {
          top: 'subheader'
      }
    });
    $scope.editor.setData($scope.fiche ? $scope.fiche.content : '');
  };

  $scope.loadData = function() {

    remoteData.getFiche($stateParams.guid).then(function(fiche) {

      $scope.fiche = fiche;
      $('ion-header-bar div.title').text(fiche.name);
      init();

    }, function() {
      $location.path("/");
    });

  };

  $scope.loadData();

  $scope.$on("$destroy", function() {
    //$scope.editor.destroy();
  });


  $scope.modifierFiche = function() {

    $scope.setHeaderButtonsReadOnly(false);
    $scope.initCKEditor(false);
  };

  $scope.saveFiche = function() {

    $scope.fiche.content = $scope.editor.getData();
    remoteData.saveFiche($scope.fiche).then(function(fiche){
      $scope.setHeaderButtonsReadOnly(true);
      $scope.initCKEditor(true);
    });

  };

  $scope.cancel = function() {

    if ($scope.fiche.content === '' || $scope.fiche.content === null) {
      $location.path('/matiere/' + $scope.matiere.id);
    } else {
      $scope.loadData();
      $scope.setHeaderButtonsReadOnly($scope.fiche.content !== '');
      $scope.initCKEditor(($scope.fiche.content !== ''));
    }

  }

  $scope.back = function() {

    $location.path('/matiere/' + $scope.fiche.matiere.id);

  };
}]);
