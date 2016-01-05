'use strict';

angular.module('adminUi').service('layout', function () {
  var showNav;

  this.getShowNav = function () {
    return showNav;
  };

  this.setShowNav = function (isShowNav) {
    showNav = isShowNav;
  };

});
