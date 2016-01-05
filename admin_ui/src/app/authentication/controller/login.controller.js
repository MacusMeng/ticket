"use strict";
angular.module('adminUi').controller('LoginCtrl', function LoginCtrl($scope, $state, $translate, $location, Login, Register, authentication, EnumTicketModule) {
  var vm = this;
  vm.alerts = [];

  vm.loginform = {
    usernameOrEmail: undefined,
    password: undefined
  };

  vm.changeLanguage = function (langKey) {
    $translate.use(langKey);
  };

  vm.login = function (form) {
    if (form.$valid) {
      Login.save(vm.loginform, function () {

        $location.path(authentication.getState());
      }, function (error) {
        $scope.$emit("error", error.data.message ? error.data.message : '');
      })
    }
  };

  vm.interacted = function (form, field) {
    return form.$submitted || field.$dirty;
  };

  $scope.$on("error", function (event, args) {
    vm.alerts.push(args);
    event.stopPropagation();
  });

  vm.openRegister = function () {
    $state.go('ticket.register');
  };

});
