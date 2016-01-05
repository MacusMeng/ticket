"use strict";
angular.module('userUi').controller('LayoutCtrl', function LayoutCtrl(navData, $translate,authentication) {
  var vm = this;
  vm.nav = navData;

  vm.user=authentication.getCurrentUser();

  vm.changeLanguage = function (langKey) {
    $translate.use(langKey);
  };
});
