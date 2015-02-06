'use strict';

angular.module('revision')

.controller('FichesCtrl', ['$scope', 'data', '$ionicModal', '$stateParams', '$location', 'remoteData',
  function($scope,data, $ionicModal, $stateParams, $location, remoteData){


  $scope.loadData = function() {


      //$scope.fiches = data.getFiches($scope.matiere.id);
      remoteData.getFiches($stateParams.matiereId).then(function (matiere) {
        $scope.matiere = matiere;
        $('ion-header-bar div.title').text(matiere.name);
      });


  };

  $scope.loadData();

  $scope.back = function() {
    $location.path('/');
  };

  $scope.ficheDialogData = {};

  $scope.initFicheDialog = function() {

    $ionicModal.fromTemplateUrl('views/edit-fiche.html',  {
      scope: $scope,
      focusFirstInput: true
    }).then(function(modal) {
      $scope.modal = modal;
    });
  };
  $scope.initFicheDialog();

  $scope.openFicheDialog = function($event,fiche) {

    $event.preventDefault();

    if (fiche) {

      remoteData.getFiche(fiche.guid).then(function (fiche) {

        $scope.ficheDialogData.fiche = fiche;
        $scope.ficheDialogData.isNew = false;
        $scope.modal.show();
      });
    } else {
      $scope.ficheDialogData.fiche = {
        matiere: $scope.matiere
      };
      $scope.ficheDialogData.isNew = true;
      $scope.modal.show();
    }

  };

  $scope.closeFicheDialog = function() {
    $scope.modal.hide();
  };

  $scope.$on('$destroy',function() {

    $scope.modal.remove();
  });

  $scope.saveFiche = function() {

    if ($scope.ficheDialogData.isNew) {
      remoteData.addFiche($scope.ficheDialogData.fiche).then(function(fiche) {

        $scope.closeFicheDialog();
        $location.path('/fiche/' + fiche.guid);
      });
    } else {
      remoteData.updateFiche($scope.ficheDialogData.fiche).then(function(matiere) {
        $scope.matiere = matiere;
        $scope.closeFicheDialog();
      });
    }

  };

  $scope.deleteFiche = function() {

    remoteData.deleteFiche($scope.ficheDialogData.fiche).then(function(matiere) {
      $scope.matiere = matiere;
      $scope.closeFicheDialog();
    });

  };

}]);
