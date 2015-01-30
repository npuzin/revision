angular.module('revision')

.controller('MatieresCtrl', ['$scope', 'data', '$ionicModal', '$ionicPopup', '$http', 'remoteData',
  function($scope,data, $ionicModal, $ionicPopup, $http, remoteData){

  $scope.$on('$ionicView.beforeEnter', function() {

    $scope.loadData();
  });


  $scope.loadData = function() {

    //$scope.matieres = data.getMatieres();
    remoteData.getMatieres().then(function(matieres) {
      $scope.matieres = matieres;
    });
  };

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
    console.log('Matiere Dialog destroyed');
  });

  $scope.saveMatiere = function() {

    if ($scope.matiereDialogData.isNew) {
      $scope.matieres = data.addMatiere($scope.matiereDialogData.matiere);
    } else {
      $scope.matieres = data.updateMatiere($scope.matiereDialogData.matiere);
    }
    $scope.closeMatiereDialog();
  };

  $scope.deleteMatiere = function() {

    var fiches = data.getFiches($scope.matiereDialogData.matiere.id);

    if (fiches.length > 0) {

      $scope.closeMatiereDialog();
      var myPopup = $ionicPopup.show({
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

      $scope.matieres = data.deleteMatiere($scope.matiereDialogData.matiere.id);
      $scope.closeMatiereDialog();

    }

  };

}]);
