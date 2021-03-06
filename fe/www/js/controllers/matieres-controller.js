'use strict';

angular.module('revision')

.controller('MatieresCtrl', ['$scope', 'data', '$ionicModal', '$ionicPopup', '$http', 'remoteData',
  function($scope,data, $ionicModal, $ionicPopup, $http, remoteData){



  $scope.loadData = function() {

    //$scope.matieres = data.getMatieres();
    $('ion-header-bar div.title').text('Classeur');

    remoteData.getMatieres().then(function(matieres) {
      $scope.matieres = matieres;
    });

  };

  $scope.loadData();

  $scope.matiereDialogData = {};

  $scope.initMatiereDialog = function() {

    $ionicModal.fromTemplateUrl('views/edit-matiere.html',  {
      scope: $scope,
      focusFirstInput: true
    }).then(function(modal) {
      $scope.modal = modal;
    });
  };
  $scope.initMatiereDialog();

  $scope.openMatiereDialog = function($event,matiere) {

    $event.preventDefault();

    $scope.matiereDialogData.colors = [
      {name:'white',label:'Blanc'},
      {name:'red',label:'Rouge'},
      {name:'green',label:'Vert'},
      {name:'blue',label:'Bleu'},
      {name:'yellow',label:'Jaune'}
    ];

    if (matiere) {
      $scope.matiereDialogData.matiere = angular.copy(matiere);
      $scope.matiereDialogData.isNew = false;
    } else {
      $scope.matiereDialogData.matiere = {
        color: $scope.matiereDialogData.colors[0].name
      };
      $scope.matiereDialogData.isNew = true;
    }
    $scope.modal.show();
  };

  $scope.closeMatiereDialog = function() {
    $scope.modal.hide();
  };

  $scope.$on('$destroy', function() {

    $scope.modal.remove();
  });

  $scope.saveMatiere = function() {

    if ($scope.matiereDialogData.isNew) {
      //$scope.matieres = data.addMatiere($scope.matiereDialogData.matiere);
      remoteData.addMatiere($scope.matiereDialogData.matiere).then(function(matieres) {
        $scope.matieres = matieres;
        $scope.closeMatiereDialog();
      });
    } else {
      remoteData.updateMatiere($scope.matiereDialogData.matiere).then(function(matieres) {
        $scope.matieres = matieres;
        $scope.closeMatiereDialog();
      });
    }

  };

  $scope.deleteMatiere = function() {

    remoteData.getFiches($scope.matiereDialogData.matiere.id).then(function(matiere) {

      if (matiere.fiches.length > 0) {

        $scope.closeMatiereDialog();
        $ionicPopup.show({
          template: 'La matière ne peut être effacée car elle contient des fiches',
          title: 'Information',
          buttons: [
            {
              text: '<b>OK</b>',
              type: 'button-positive'
            }
          ]
        });

      } else {

        remoteData.deleteMatiere($scope.matiereDialogData.matiere.id).then(function(matieres) {
          $scope.matieres = matieres;
          $scope.closeMatiereDialog();
        });

      }

    });


  };

}]);
