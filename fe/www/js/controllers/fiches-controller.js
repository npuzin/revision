angular.module('revision')

.controller('FichesCtrl', ['$scope', 'data', '$ionicModal', '$stateParams', '$location', function($scope,data, $ionicModal, $stateParams, $location){


  $scope.loadData = function() {

    $scope.matiere = data.getMatiere(parseInt($stateParams.matiereId));
    if (!$scope.matiere) {
      $location.path("/");
    } else {
      $scope.fiches = data.getFiches($scope.matiere.id);
    }
  }
  $scope.loadData();

  $scope.back = function() {
    $location.path("/");
  };

  $scope.ficheDialogData = {};

  $scope.initFicheDialog = function() {

    $ionicModal.fromTemplateUrl('views/edit-fiche.html',  {
      scope: $scope,
      focusFirstInput: true
    }).then(function(modal) {
      $scope.modal = modal;
    });
  }
  $scope.initFicheDialog();

  $scope.openFicheDialog = function($event,fiche) {

    $event.preventDefault();

    if (fiche) {
      $scope.ficheDialogData.fiche = angular.copy(fiche);
      $scope.ficheDialogData.isNew = false;
    } else {
      $scope.ficheDialogData.fiche = {};
      $scope.ficheDialogData.isNew = true;
    }
    $scope.modal.show();
  };

  $scope.closeFicheDialog = function() {
    $scope.modal.hide();
  };

  $scope.$on('$destroy',function() {

    $scope.modal.remove();
    console.log('Fiche Dialog destroyed');
  });

  $scope.saveFiche = function() {

    if ($scope.ficheDialogData.isNew) {
      $scope.fiches = data.addFiche($scope.matiere.id, $scope.ficheDialogData.fiche);
    } else {
      $scope.fiches = data.updateFiche($scope.matiere.id, $scope.ficheDialogData.fiche);
    }
    $scope.closeFicheDialog();
  };

  $scope.deleteFiche = function() {

    $scope.fiches = data.deleteFiche($scope.matiere.id, $scope.ficheDialogData.fiche.id);
    $scope.closeFicheDialog();
  };

}]);
