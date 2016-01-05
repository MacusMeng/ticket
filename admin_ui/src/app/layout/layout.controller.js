"use strict";
angular.module('adminUi').controller('LayoutCtrl', function LayoutCtrl($state, Logout, authentication, navData, $translate, layout) {
  var vm = this;
  vm.nav = navData;
  vm.user = function () {
    return authentication.getCurrentUser();
  };
  vm.showNav = function () {
    return layout.getShowNav();
  };

  vm.logout = function () {
    Logout.save(function () {
      $state.go("ticket.login");
    });
  };

  vm.changeLanguage = function (langKey) {
    $translate.use(langKey);
  };
});
