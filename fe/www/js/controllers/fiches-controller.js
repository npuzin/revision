angular.module('revision')

.controller('FichesCtrl', ['$scope', 'data', '$ionicModal', '$stateParams', '$location', 'remoteData',
  function($scope,data, $ionicModal, $stateParams, $location, remoteData){


  $scope.$on('$ionicView.beforeEnter', function() {

    $scope.loadData();
  });

  $scope.loadData = function() {

    remoteData.getMatiere(parseInt($stateParams.matiereId)).then(function (matiere) {

      $scope.matiere = matiere;
      //$scope.fiches = data.getFiches($scope.matiere.id);
      remoteData.getFiches($scope.matiere.id).then(function (fiches) {
        $scope.fiches = fiches;
      });

    }, function () {
      $location.path("/");
    });

  }

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

      remoteData.getFiche(fiche.guid).then(function (fiche) {

        $scope.ficheDialogData.fiche = fiche;
        $scope.ficheDialogData.isNew = false;
        $scope.modal.show();
      });
    } else {
      $scope.ficheDialogData.fiche = {
        matiereId: $scope.matiere.id
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
    console.log('Fiche Dialog destroyed');
  });

  $scope.saveFiche = function() {

    if ($scope.ficheDialogData.isNew) {
      remoteData.addFiche($scope.ficheDialogData.fiche).then(function(fiche) {

        $scope.closeFicheDialog();
        $location.path('/matiere/' + fiche.matiereId + '/fiche/' + fiche.guid);
      });
    } else {
      remoteData.updateFiche($scope.ficheDialogData.fiche).then(function(fiches) {
        $scope.fiches = fiches;
        $scope.closeFicheDialog();
      });
    }

  };

  $scope.deleteFiche = function() {

    remoteData.deleteFiche($scope.ficheDialogData.fiche).then(function(fiches) {
      $scope.fiches = fiches;
      $scope.closeFicheDialog();
    })

  };

}]);
