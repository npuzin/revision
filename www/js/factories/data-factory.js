angular.module('revision')

.factory('data', function(uuid2) {

  var getMaxId = function(items) {

    var id=0;
    items.forEach(function (current) {

      if (current.id > id)
        id=current.id;
    });
    return id;
  };
  var initLocalStorage = function() {

    if (!window.localStorage['matieres']) {
      var matieres = [];
      // matieres.push({name:'Histoire',id:1, color:'yellow'});
      // matieres.push({name:'Géographie',id:2, color:'green'});
      // matieres.push({name:'Mathématiques',id:3, color:'blue'});
      window.localStorage['matieres'] = JSON.stringify(matieres);
    }

    // if (!window.localStorage['fiches_1']) {
    //   var fiches = [];
    //   fiches.push({name:'Test fiche histoire',id:1, guid: uuid2.newguid()});
    //   window.localStorage['fiches_1'] = JSON.stringify(fiches);
    // }
  };
  initLocalStorage();
  var getMatieres = function() {

    return JSON.parse(window.localStorage['matieres']);
  };
  var getFiches = function(matiereId) {

    if (!window.localStorage['fiches_'+matiereId]) {
      window.localStorage['fiches_'+matiereId] = '[]';
    }
    return JSON.parse(window.localStorage['fiches_'+matiereId]);
  };
  var addMatiere = function(matiere) {

    var matieres = getMatieres();
    var max = getMaxId(matieres);
    matiere.id = max+1;
    matieres.push(matiere);
    window.localStorage['matieres'] = JSON.stringify(matieres);
    return matieres;
  };
  var updateMatiere = function(matiere) {

    var matieres = getMatieres();
    var currentMatiere = _.find(matieres, function(curr) {
      return curr.id === matiere.id;
    });
    currentMatiere.name = matiere.name;
    currentMatiere.color = matiere.color;
    window.localStorage['matieres'] = JSON.stringify(matieres);
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
    window.localStorage['matieres'] = JSON.stringify(matieres);
    return matieres;
  };
  var addFiche = function(matiereId, fiche) {

    var fiches = getFiches(matiereId);
    var max = getMaxId(fiches);
    fiche.id = max+1;
    fiche.guid = uuid2.newguid();
    fiches.push(fiche);
    window.localStorage['fiches_'+matiereId] = JSON.stringify(fiches);
    return fiches;
  };
  var updateFiche = function(matiereId, fiche) {

    var fiches = getFiches(matiereId);
    var currentFiche = _.find(fiches, function(curr) {
      return curr.id === fiche.id;
    });
    currentFiche.name = fiche.name;
    window.localStorage['fiches_'+matiereId] = JSON.stringify(fiches);
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
    window.localStorage.removeItem('fiche_'+currentFiche.guid);
    window.localStorage['fiches_'+matiereId] = JSON.stringify(fiches);
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
    return window.localStorage['fiche_'+guid];
  }
  var saveFicheContent = function(fiche, content) {

    var guid = fiche.guid;
    window.localStorage['fiche_'+guid] = content;
  }
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
  }
});
