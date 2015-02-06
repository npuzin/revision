'use strict';

angular.module('revision')

.factory('data', function(uuid2) {

  var getMaxId = function(items) {

    var id=0;
    items.forEach(function (current) {

      if (current.id > id) {
        id=current.id;
      }
    });
    return id;
  };

  var getMatieres = function() {

    return JSON.parse(windowlocalStorage.matieres);
  };
  var getFiches = function(matiereId) {

    if (!windowlocalStorage['fiches_'+matiereId]) {
      windowlocalStorage['fiches_'+matiereId] = '[]';
    }
    return JSON.parse(windowlocalStorage['fiches_'+matiereId]);
  };
  var addMatiere = function(matiere) {

    var matieres = getMatieres();
    var max = getMaxId(matieres);
    matiere.id = max+1;
    matieres.push(matiere);
    windowlocalStorage.matieres = JSON.stringify(matieres);
    return matieres;
  };
  var updateMatiere = function(matiere) {

    var matieres = getMatieres();
    var currentMatiere = _.find(matieres, function(curr) {
      return curr.id === matiere.id;
    });
    currentMatiere.name = matiere.name;
    currentMatiere.color = matiere.color;
    windowlocalStorage.matieres = JSON.stringify(matieres);
    return matieres;
  };
  var deleteMatiere = function(matiereId) {

    var matieres = getMatieres();
    var currentMatiere = _.find(matieres, function(curr) {
      return curr.id === matiereId;
    });
    var pos = matieres.indexOf(currentMatiere);
    if (pos>=0) {
      matieres.splice(pos,1);
    }
    windowlocalStorage.matieres = JSON.stringify(matieres);
    return matieres;
  };
  var addFiche = function(matiereId, fiche) {

    var fiches = getFiches(matiereId);
    var max = getMaxId(fiches);
    fiche.id = max+1;
    fiche.guid = uuid2.newguid();
    fiches.push(fiche);
    windowlocalStorage['fiches_'+matiereId] = JSON.stringify(fiches);
    return fiches;
  };
  var updateFiche = function(matiereId, fiche) {

    var fiches = getFiches(matiereId);
    var currentFiche = _.find(fiches, function(curr) {
      return curr.id === fiche.id;
    });
    currentFiche.name = fiche.name;
    windowlocalStorage['fiches_'+matiereId] = JSON.stringify(fiches);
    return fiches;
  };
  var deleteFiche = function(matiereId, ficheId) {

    var fiches = getFiches(matiereId);
    var currentFiche = _.find(fiches, function(curr) {
      return curr.id === ficheId;
    });
    var pos = fiches.indexOf(currentFiche);
    if (pos>=0) {
      fiches.splice(pos,1);
    }
    windowlocalStorage.removeItem('fiche_'+currentFiche.guid);
    windowlocalStorage['fiches_'+matiereId] = JSON.stringify(fiches);
    return fiches;
  };
  var getMatiere = function(matiereId) {

    var matieres = getMatieres();
    return _.find(matieres, function(curr) {

      return curr.id === matiereId;
    });
  };
  var getFiche = function(matiereId, ficheId) {

    var fiches = getFiches(matiereId);
    return _.find(fiches, function(curr) {

      return curr.id === ficheId;
    });
  };
  var getFicheContent = function(fiche) {

    var guid = fiche.guid;
    return windowlocalStorage['fiche_'+guid];
  };
  var saveFicheContent = function(fiche, content) {

    var guid = fiche.guid;
    windowlocalStorage['fiche_'+guid] = content;
  };
  return {
    getMatieres: getMatieres,
    addMatiere: addMatiere,
    getMatiere: getMatiere,
    getFiches: getFiches,
    addFiche: addFiche,
    getFiche: getFiche,
    updateMatiere: updateMatiere,
    deleteMatiere: deleteMatiere,
    updateFiche: updateFiche,
    deleteFiche: deleteFiche,
    getFicheContent: getFicheContent,
    saveFicheContent: saveFicheContent
  };
});
